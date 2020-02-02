package Hrehova.Stedlova.PlanEat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MySqlReceptDao implements ReceptyDao {
	JdbcTemplate jdbcTemplate;

	public MySqlReceptDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//pridanie noveho receptu
	public void pridajRecept(Recept recept) {
		//ak sa taky recept uz v databaze nachadza...return
		for (Recept r : getAll()) {
			if (r.getNazov_receptu().equals(recept.getNazov_receptu())) {
				return;
			}
		}

		SimpleJdbcInsert vkladanie = new SimpleJdbcInsert(jdbcTemplate);

		vkladanie.setTableName("recepty");
		vkladanie.usingGeneratedKeyColumns("id_receptu");
		vkladanie.usingColumns("nazov_receptu", "pocet_porcii", "cas_pripravy", "oblubeny_recept", "postup");

		Map<String, Object> hodnoty = new HashMap<String, Object>();

		hodnoty.put("nazov_receptu", recept.getNazov_receptu().toLowerCase());
		hodnoty.put("pocet_porcii", recept.getPocet_porcii());
		hodnoty.put("cas_pripravy", recept.getCas_pripravy());
		hodnoty.put("oblubeny_recept", recept.isOblubeny_recept());
		hodnoty.put("postup", recept.getPostup());

		recept.setId_receptu(vkladanie.executeAndReturnKey(hodnoty).longValue());
		
		//vkladanie ingrediencii pre dany recept do prepojovacej tabulky 
		SimpleJdbcInsert vkladanieRMI = new SimpleJdbcInsert(jdbcTemplate);

		vkladanieRMI.setTableName("recepty_maju_ingrediencie");
		vkladanieRMI.usingColumns("recepty_id_receptu", "ingrediencie_id_ingrediencie", "mnozstvo_ingrediencie");

		Map<String, Object> hodnotyRMI = new HashMap<String, Object>();
		List<Ingrediencia> ingrediencie = recept.getIngrediencie();
		for (Ingrediencia i : ingrediencie) {

			hodnotyRMI.put("recepty_id_receptu", recept.getId_receptu());
			hodnotyRMI.put("ingrediencie_id_ingrediencie", i.getId_ingrediencie());

			if (recept.getIngrediencie_mnozstvo() != null) {
				hodnotyRMI.put("mnozstvo_ingrediencie", recept.getIngrediencie_mnozstvo().get(i));
			}
			vkladanieRMI.execute(hodnotyRMI);
		}

	}

	public List<Recept> getAll() {

		String sql = "select id_receptu, nazov_receptu, pocet_porcii, cas_pripravy, oblubeny_recept, postup, rmi.ingrediencie_id_ingrediencie, "
				+ "rmi.mnozstvo_ingrediencie from recepty left outer join recepty_maju_ingrediencie as rmi on recepty.id_receptu= rmi.recepty_id_receptu";

		return jdbcTemplate.query(sql, new ReceptResultSetExtractor());
	}
	
	//vrati recept so zadanym nazvom
	public List<Recept> getPodlaNazvu(String nazovReceptu) {

		String sql = "select id_receptu, nazov_receptu, pocet_porcii, cas_pripravy, oblubeny_recept, postup, rmi.ingrediencie_id_ingrediencie,"
				+ "  rmi.mnozstvo_ingrediencie from recepty left outer join recepty_maju_ingrediencie as rmi on recepty.id_receptu= rmi.recepty_id_receptu"
				+ " where nazov_receptu = '" + nazovReceptu.toLowerCase() + "'";

		return jdbcTemplate.query(sql, new ReceptResultSetExtractor());
	}

	//uprava/pridanie receptu
	@Override
	public void update(Recept recept) {
		
		//ak sa recept este v databaze nenachadza pridavame novy
		if(recept.getId_receptu() == null) {
			pridajRecept(recept);
			return;
		}
		
		//ak uz v databaze je upravujeme recept
		String sql = "UPDATE recepty set nazov_receptu = ?, pocet_porcii = ? , cas_pripravy = ?, oblubeny_recept = ?, postup = ? where id_receptu = " + recept.getId_receptu();
		int upravene = jdbcTemplate.update(sql, recept.getNazov_receptu(),recept.getPocet_porcii(),recept.getCas_pripravy(),recept.isOblubeny_recept(),recept.getPostup());
		if(upravene >0) {
			String deleteSql = "DELETE FROM recepty_maju_ingrediencie WHERE recepty_id_receptu  = " + recept.getId_receptu();
			jdbcTemplate.update(deleteSql);
			

			SimpleJdbcInsert vkladanieRMI = new SimpleJdbcInsert(jdbcTemplate);

			vkladanieRMI.setTableName("recepty_maju_ingrediencie");
			vkladanieRMI.usingColumns("recepty_id_receptu", "ingrediencie_id_ingrediencie", "mnozstvo_ingrediencie");

			Map<String, Object> hodnotyRMI = new HashMap<String, Object>();
			List<Ingrediencia> ingrediencie = recept.getIngrediencie();
			for (Ingrediencia i : ingrediencie) {

				hodnotyRMI.put("recepty_id_receptu", recept.getId_receptu());
				hodnotyRMI.put("ingrediencie_id_ingrediencie", i.getId_ingrediencie());

				if (recept.getIngrediencie_mnozstvo() != null) {
					hodnotyRMI.put("mnozstvo_ingrediencie", recept.getIngrediencie_mnozstvo().get(i));
				}
				vkladanieRMI.execute(hodnotyRMI);
			}

		}
	}

	@Override
	public void odstran(Recept recept) {
		
		String deleteSqlJedalnicek = "DELETE FROM dennicek_jedal WHERE id_receptu  = " + recept.getId_receptu();
		jdbcTemplate.update(deleteSqlJedalnicek);
		
		String deleteSqlIngrediencie = "DELETE FROM recepty_maju_ingrediencie WHERE recepty_id_receptu  = " + recept.getId_receptu();
		jdbcTemplate.update(deleteSqlIngrediencie);
		
		String deleteSql = "DELETE FROM recepty WHERE id_receptu  = " + recept.getId_receptu();
		jdbcTemplate.update(deleteSql);
		
		
	}
	
	

	


}