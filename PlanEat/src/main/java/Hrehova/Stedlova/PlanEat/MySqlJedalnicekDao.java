package Hrehova.Stedlova.PlanEat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MySqlJedalnicekDao implements JedalnicekDao {
	JdbcTemplate jdbcTemplate;

	public MySqlJedalnicekDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// pridanie noveho naplanovaneho jedla
	@Override
	public void pridaj(Jedalnicek jedlo) {

		SimpleJdbcInsert vkladanie = new SimpleJdbcInsert(jdbcTemplate);

		vkladanie.setTableName("dennicek_jedal");
		vkladanie.usingGeneratedKeyColumns("id");
		vkladanie.usingColumns("den", "datum", "id_receptu");

		Map<String, Object> hodnoty = new HashMap<String, Object>();
		hodnoty.put("den", jedlo.getDen());
		hodnoty.put("datum", jedlo.getDatum());
		hodnoty.put("id_receptu", jedlo.getRecept().getId_receptu());

		jedlo.setId(vkladanie.executeAndReturnKey(hodnoty).longValue());

	}

	@Override
	public List<Jedalnicek> getAll() {
		String sql = "SELECT id, den, datum, dj.id_receptu, r.nazov_receptu FROM dennicek_jedal dj  join recepty r on r.id_receptu = dj.id_receptu order by datum desc";

		List<Jedalnicek> jedla = jdbcTemplate.query(sql, new RowMapper<Jedalnicek>() {
			List<Recept> recepty = DaoFactory.INSTANCE.getReceptyDao().getAll();

			@Override
			public Jedalnicek mapRow(ResultSet rs, int rowNum) throws SQLException {
				Jedalnicek jedlo = new Jedalnicek();
				jedlo.setId(rs.getLong("id"));
				jedlo.setDen(rs.getString("den"));
				jedlo.setDatum(rs.getDate("datum").toLocalDate());
				for (Recept r : recepty) {
					if (r.getId_receptu() == rs.getLong("id_receptu")) {
						jedlo.setRecept(r);
					}
				}
				jedlo.setNazovReceptu(rs.getString("nazov_receptu"));
				return jedlo;
			}

		});

		return jedla;
	}

	@Override
	public void odstran(Jedalnicek jedlo) {

		String deleteSql = "DELETE FROM dennicek_jedal WHERE id  = " + jedlo.getId();
		jdbcTemplate.update(deleteSql);

	}

	@Override
	public void update(Jedalnicek jedlo) {

		String sql = "UPDATE dennicek_jedal set den = ?, datum = ? ,id_receptu = ? where id = " + jedlo.getId();
		jdbcTemplate.update(sql, jedlo.getDen(), jedlo.getDatum(), jedlo.getRecept().getId_receptu());

	}

}
