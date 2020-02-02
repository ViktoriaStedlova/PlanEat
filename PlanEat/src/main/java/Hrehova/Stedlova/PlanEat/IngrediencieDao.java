package Hrehova.Stedlova.PlanEat;

import java.util.List;

public interface IngrediencieDao {

	void pridajIngredienciu(Ingrediencia ingrediencia);

	List<Ingrediencia> getAll();

	void odstran(Ingrediencia ingrediencia);

	void update(Ingrediencia ingrediencia);

}
