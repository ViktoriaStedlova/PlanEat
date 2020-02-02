package Hrehova.Stedlova.PlanEat;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class ReceptController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField textFieldNazovReceptu;

	@FXML
	private TextField textFieldPocetPorcii;

	@FXML
	private TextField textFieldCas;

	@FXML
	private TextArea textAreaPostup;

	@FXML
	private CheckBox checkBoxOblubeny;

	@FXML
	private TableView<Ingrediencia> tableViewIngrediencie;

	@FXML
	private TableColumn<Ingrediencia, String> stlpecNazov;

	@FXML
	private ComboBox<Ingrediencia> comboBoxIngrediencie;

	@FXML
	private TextField textFieldMnozstvo;

	@FXML
	private Button buttonPridatIngredienciu;

	@FXML
	private Button buttonPridat;

	@FXML
	private TableColumn<Ingrediencia, String> stlpecMnozsvo;

	@FXML
	private TableColumn<Ingrediencia, String> stlpecAlergen;

	@FXML
	private ImageView imageViewRecept;

	@FXML
	private Button buttonUlozit;

	private ReceptFxModel model = new ReceptFxModel();
	private IngrediencieDao ingrediencieDao = DaoFactory.INSTANCE.getIngrediencieDao();
	private ObservableList<Ingrediencia> ingrediencieList;
	private Map<Ingrediencia, String> ingrediencie_mnozstvo = new HashMap<Ingrediencia, String>();

	// bezparamentrovy kontroler sa vyuziva ak pridavame novy recept, s parametrom
	// ak upravujeme uz existujuci
	public ReceptController() {
	}

	public ReceptController(Recept recept) {
		model.setRecept(recept);
	}

	// klik nu button pridat - pridanie ingrediencie do receptu
	@FXML
	void actionPridat(ActionEvent event) {
		// pridava sa ingrediencia ktora je vybrana v comboboxe
		Ingrediencia ingrediencia = comboBoxIngrediencie.getSelectionModel().getSelectedItem();

		// ak uz dana ingrediencia je v recepte... return
		for (Ingrediencia i : tableViewIngrediencie.getItems()) {
			if (i.equals(ingrediencia)) {
				new Alert(Alert.AlertType.INFORMATION, "Tuto ingredienciu v receptu uz mas.").showAndWait();
				return;
			}
		}
		// ingrediencii sa prida mnozstvo a prida sa do tabulky
		ingrediencia.setMnozstvo(textFieldMnozstvo.getText());
		tableViewIngrediencie.getItems().add(ingrediencia);

		// mapovanie mnozstva pre kazdu novu ingredienciu
		ingrediencie_mnozstvo.put(ingrediencia, textFieldMnozstvo.getText());

	}

	// klik na button pridat novu ingredienciu
	// otvorenie okna na pridavanie novych ingrediencii (do databazy/comboboxu)
	@FXML
	void actionPridatIngrediencie(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ingrediencie.fxml"));

			Parent parent = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Ingrediencie");
			stage.setScene(new Scene(parent));
			stage.showAndWait();
			// po pridani novej ingrediencie sa aktualizuje combobox s ingredienciami
			ingrediencieList = FXCollections.observableArrayList(ingrediencieDao.getAll());
			comboBoxIngrediencie.setItems(ingrediencieList);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// odstranenie ingrediencie z tabulky
	@FXML
	void actionOdstranIngredienciu(ActionEvent event) {
		tableViewIngrediencie.getItems().removeAll(tableViewIngrediencie.getSelectionModel().getSelectedItem());
	}

	// klik na button ulozit
	@FXML
	void actionUlozit(ActionEvent event) {
		// receptu sa nastavia tie ingrediencie ktore su momentalne v tabulke
		model.getIngrediencie().clear();
		List<Ingrediencia> ingrediencie = tableViewIngrediencie.getItems();
		model.setIngrediencie(ingrediencie);

		// nastavenie mnozstvo ingredienciam
		// ake maju ingrediencie mnozstvo vieme pomocou mapy ingrediencie_mnozstvo
		// mapuje sa aj pre nove ingrediencie aj tie ktore tam uz boli
		model.setIngrediencie_mnozstvo(ingrediencie_mnozstvo);

		DaoFactory.INSTANCE.getReceptyDao().update(model.getRecept());

		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void initialize() {
		// nastavenie comboboxu - vsetky ingrediencie z databazy
		ingrediencieList = FXCollections.observableArrayList(ingrediencieDao.getAll());
		comboBoxIngrediencie.setItems(ingrediencieList);

		textFieldNazovReceptu.textProperty().bindBidirectional(model.nazovReceptuProperty());
		textFieldCas.textProperty().bindBidirectional(model.casPripravyProperty());
		textFieldPocetPorcii.textProperty().bindBidirectional(model.pocetPorciiProperty(), new NumberStringConverter());
		textAreaPostup.textProperty().bindBidirectional(model.postupProperty());
		checkBoxOblubeny.selectedProperty().bindBidirectional(model.oblubenyProperty());
		tableViewIngrediencie.setItems(FXCollections.observableArrayList(model.getIngrediencie()));

		// nastavenie mnozstva pre ingrediencie v modeli
		for (Ingrediencia i : model.getIngrediencie()) {
			i.setMnozstvo(model.getIngrediencie_mnozstvo().get(i));
		}

		stlpecNazov.setCellValueFactory(new PropertyValueFactory<>("nazov_ingrediencie"));
		stlpecMnozsvo.setCellValueFactory(new PropertyValueFactory<>("mnozstvo"));
		stlpecAlergen.setCellValueFactory(new PropertyValueFactory<>("alergen"));

		// mapovanie mnozstva pre ingrediencie ktore uz su v recepte
		for (Ingrediencia i : tableViewIngrediencie.getItems()) {
			ingrediencie_mnozstvo.put(i, stlpecMnozsvo.getCellData(i));
		}
	}
}
