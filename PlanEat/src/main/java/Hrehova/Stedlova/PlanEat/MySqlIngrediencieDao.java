package Hrehova.Stedlova.PlanEat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MySqlIngrediencieDao implements IngrediencieDao {
	JdbcTemplate jdbcTemplate;

	public MySqlIngrediencieDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// pridavanie novej ingrediencie
	@Override
	public void pridajIngredienciu(Ingrediencia ingrediencia) {
		// ak sa pridavana ingrediencia uz v databaze nachadza alebo je bez nazvu... return
		for (Ingrediencia i : getAll()) {
			if (i.getNazov_ingrediencie().equals(ingrediencia.getNazov_ingrediencie())
					|| ingrediencia.getNazov_ingrediencie() == null || ingrediencia.getNazov_ingrediencie() == "") {
				return;
			}
		}
		SimpleJdbcInsert vkladanie = new SimpleJdbcInsert(jdbcTemplate);

		vkladanie.setTableName("ingrediencie");
		vkladanie.usingGeneratedKeyColumns("id_ingrediencie");
		vkladanie.usingColumns("nazov_ingrediencie", "alergen");

		Map<String, Object> hodnoty = new HashMap<String, Object>();
		hodnoty.put("nazov_ingrediencie", ingrediencia.getNazov_ingrediencie());
		hodnoty.put("alergen", ingrediencia.getAlergen());
		ingrediencia.setId_ingrediencie(vkladanie.executeAndReturnKey(hodnoty).longValue());

	}

	@Override
	public List<Ingrediencia> getAll() {
		String sql = "SELECT id_ingrediencie, nazov_ingrediencie, alergen FROM ingrediencie";

		List<Ingrediencia> ingrediencie = jdbcTemplate.query(sql, new RowMapper<Ingrediencia>() {

			@Override
			public Ingrediencia mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ingrediencia ingrediencia = new Ingrediencia();
				ingrediencia.setId_ingrediencie(rs.getLong("id_ingrediencie"));
				ingrediencia.setNazov_ingrediencie(rs.getString("nazov_ingrediencie"));
				ingrediencia.setAlergen(rs.getString("alergen"));
				return ingrediencia;
			}

		});

		return ingrediencie;
	}

	@Override
	public void odstran(Ingrediencia ingrediencia) {

		String deleteSqlRMI = "DELETE FROM recepty_maju_ingrediencie WHERE ingrediencie_id_ingrediencie  = "
				+ ingrediencia.getId_ingrediencie();
		jdbcTemplate.update(deleteSqlRMI);

		String deleteSql = "DELETE FROM ingrediencie WHERE id_ingrediencie  = " + ingrediencia.getId_ingrediencie();
		jdbcTemplate.update(deleteSql);

	}

	@Override
	public void update(Ingrediencia ingrediencia) {

		String sql = "UPDATE ingrediencie set nazov_ingrediencie = ?, alergen = ?  where id_ingrediencie = "
				+ ingrediencia.getId_ingrediencie();
		jdbcTemplate.update(sql, ingrediencia.getNazov_ingrediencie(), ingrediencia.getAlergen());

	}

}
