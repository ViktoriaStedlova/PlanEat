package Hrehova.Stedlova.PlanEat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IngrediencieController {

	private IngrediencieDao ingrediencieDao = DaoFactory.INSTANCE.getIngrediencieDao();

	@FXML
	private Button buttonPridatIngredienciu;

	@FXML
	private TextField textFieldNazov;

	@FXML
	private ComboBox<String> comboboxAlergen;

	@FXML
	private ListView<Ingrediencia> listViewIngrediencie;

	private ObservableList<String> alergeny = FXCollections.observableArrayList("žiadny",
			"1. Obilniny obsahujúce lepok", "2. Kôrovce a výrobky z nich", "3. Vajcia a výrobky z nich",
			"4. Ryby a výrobky z nich", "5. Arašidy a výrobky z nich", "6. Sójové zrná a výrobky z nich",
			"7. Mlieko a výrobky z neho", "8. Orechy", "9. Zeler a výrobky z neho", "10. Horčica a výrobky z nej",
			"14. Mäkkýše a výrobky z nich");

	// klik na button pridat ingredienciu, po pridani sa okno zavrie
	@FXML
	void actionPridatIngredienciu(ActionEvent event) {
		Ingrediencia ingrediencia = new Ingrediencia();
		ingrediencia.setNazov_ingrediencie(textFieldNazov.getText());
		String selectedItem = comboboxAlergen.getSelectionModel().getSelectedItem().toString();
		ingrediencia.setAlergen(selectedItem);

		// pridanie do databazy
		ingrediencieDao.pridajIngredienciu(ingrediencia);

		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();

	}

	@FXML
	void initialize() {
		// vyplnenie comboboxu a zobrazenie prveho alergenu
		comboboxAlergen.setItems(alergeny);
		comboboxAlergen.getSelectionModel().selectFirst();

	}
}