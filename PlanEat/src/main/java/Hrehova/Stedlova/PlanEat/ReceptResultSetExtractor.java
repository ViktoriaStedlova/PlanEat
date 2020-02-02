package Hrehova.Stedlova.PlanEat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

//pomocna trieda pre metody getAll a getPodlaNazvu
public class ReceptResultSetExtractor implements ResultSetExtractor<List<Recept>> {

	@Override
	public List<Recept> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<Ingrediencia> ingrediencie = DaoFactory.INSTANCE.getIngrediencieDao().getAll();
		Map<Long, Ingrediencia> mapa = new HashMap<Long, Ingrediencia>();
		for (Ingrediencia i : ingrediencie) {
			mapa.put(i.getId_ingrediencie(), i);

		}

		List<Recept> recepty = new ArrayList<Recept>();

		Recept recept = null;
		while (rs.next()) {
			long id = rs.getLong("id_receptu");
			if (recept == null || recept.getId_receptu() != id) {
				recept = new Recept();
				recept.setId_receptu(rs.getLong("id_receptu"));
				recept.setNazov_receptu(rs.getString("nazov_receptu"));
				recept.setPocet_porcii(rs.getInt("pocet_porcii"));
				recept.setCas_pripravy(rs.getString("cas_pripravy"));
				recept.setOblubeny_recept(rs.getBoolean("oblubeny_recept"));
				recept.setPostup(rs.getString("postup"));
				recept.setIngrediencie(new ArrayList<Ingrediencia>());
				recept.setIngrediencie_mnozstvo(new HashMap<Ingrediencia, String>());
				recepty.add(recept);
			}
			Long idI = rs.getLong("ingrediencie_id_ingrediencie");

			if (!rs.wasNull()) {
				recept.getIngrediencie().add(mapa.get(idI));
			}

			recept.getIngrediencie_mnozstvo().put(mapa.get(idI), rs.getString("mnozstvo_ingrediencie"));

		}
		return recepty;

	}
}
