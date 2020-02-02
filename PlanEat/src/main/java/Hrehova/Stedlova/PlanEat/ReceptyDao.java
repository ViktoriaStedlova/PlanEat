package Hrehova.Stedlova.PlanEat;
import java.util.List;

public interface ReceptyDao {

	void pridajRecept(Recept recept);

	List<Recept> getAll();
	
	 List<Recept> getPodlaNazvu(String nazovReceptu);

	void update(Recept recept);

	void odstran(Recept selectedItem);

}