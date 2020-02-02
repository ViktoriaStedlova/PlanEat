package Hrehova.Stedlova.PlanEat;
import java.util.List;

public interface JedalnicekDao {

	void pridaj(Jedalnicek jedlo);

	List<Jedalnicek> getAll();

	void odstran(Jedalnicek jedlo);

	void update(Jedalnicek jedlo);

}
