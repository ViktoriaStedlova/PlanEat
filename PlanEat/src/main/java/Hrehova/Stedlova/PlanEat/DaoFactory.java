package Hrehova.Stedlova.PlanEat;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;

	private ReceptyDao receptyDao;
	private IngrediencieDao ingrediencieDao;
	private JedalnicekDao jedalnicekDao;
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("Viky");
			dataSource.setPassword("Matka1117");
			dataSource.setDatabaseName("planeat");
			dataSource.setUrl("jdbc:mysql://localhost/planeat?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}

	private JdbcTemplate getJdbcTemplateTest() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("Viky");
			dataSource.setPassword("Matka1117");
			dataSource.setUrl("jdbc:mysql://localhost/planeat?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}

	public ReceptyDao getReceptyDao() {
		return getReceptyDao(false);
	}

	public ReceptyDao getReceptyDao(boolean test) {
		if (receptyDao == null) {
			if (test) {
				receptyDao = new MySqlReceptDao(getJdbcTemplateTest());
			} else {
				receptyDao = new MySqlReceptDao(getJdbcTemplate());
			}
		}
		return receptyDao;
	}

	public IngrediencieDao getIngrediencieDao() {
		return getIngrediencieDao(false);
	}

	public IngrediencieDao getIngrediencieDao(boolean test) {
		if (ingrediencieDao == null) {
			if (test) {
				ingrediencieDao = new MySqlIngrediencieDao(getJdbcTemplateTest());
			} else {
				ingrediencieDao = new MySqlIngrediencieDao(getJdbcTemplate());
			}
		}
		return ingrediencieDao;
	}

	public JedalnicekDao getJedalnicekDao() {
		return getJedalnicekDao(false);
	}

	public JedalnicekDao getJedalnicekDao(boolean test) {
		if (jedalnicekDao == null) {
			if (test) {
				jedalnicekDao = new MySqlJedalnicekDao(getJdbcTemplateTest());
			} else {
				jedalnicekDao = new MySqlJedalnicekDao(getJdbcTemplate());
			}
		}
		return jedalnicekDao;
	}

}