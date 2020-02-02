package Hrehova.Stedlova.PlanEat;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class MySqlIngrediencieDaoTest {

	IngrediencieDao dao = DaoFactory.INSTANCE.getIngrediencieDao(true);

	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getAllTest() {
		List<Ingrediencia> all = dao.getAll();
		assertNotNull(all);
		assertTrue(all.size() > 0);
	}

	
}
