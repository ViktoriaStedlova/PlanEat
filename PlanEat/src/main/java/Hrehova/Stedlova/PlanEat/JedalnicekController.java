package Hrehova.Stedlova.PlanEat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class JedalnicekController {

	@FXML
	private TableView<Jedalnicek> tableViewJedalnicek;

	@FXML
	private TableColumn<Jedalnicek, String> denStlpec;

	@FXML
	private TableColumn<Jedalnicek, String> nazovStlpec;

	@FXML
	private TableColumn<Jedalnicek, LocalDate> datumStlpec;

	@FXML
	private TextField textFieldNazov;

	@FXML
	private TextField textFieldDen;

	@FXML
	private DatePicker datePickerDatum;

	@FXML
	private Button buttonUloz;

	@FXML
	private Button buttonVybratRecept;

	private JedalnicekDao jedalnicekDao = DaoFactory.INSTANCE.getJedalnicekDao();

	private ReceptFxModel receptFxModel = new ReceptFxModel();

	// klik na date picker
	@FXML
	void actionDatum(ActionEvent event) {
		// automaticke nastavenie dna prisluchajucemu k vybranemu datumu
		textFieldDen
				.setText(datePickerDatum.getValue().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
	}

	// klik na button vybrat recept, otvori sa okno so zoznamom receptov
	@FXML
	void actionVybratRecept(ActionEvent event) {

		try {
			ZoznamReceptovController zrc = new ZoznamReceptovController();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ZoznamPreJedalnicek.fxml"));
			fxmlLoader.setController(zrc);
			Parent parent = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("ZoznamReceptov");
			stage.setScene(new Scene(parent));

			// caka kym sa okno zavrie
			stage.showAndWait();

			// po zavreti okna pomocou metody getRecept zistime aky recept bol vybraty
			receptFxModel.setRecept(zrc.getRecept());
			textFieldNazov.setText(receptFxModel.getNazovReceptu());

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// klik na button ulozit
	@FXML
	void actionUlozit(ActionEvent event) {
		Jedalnicek jedalnicek = new Jedalnicek();

		//ak uz mame vo vybranom dni naplanovane ine jedlo... return
		for (Jedalnicek j : jedalnicekDao.getAll()) {
			if (j.getDatum().equals(datePickerDatum.getValue())) {
				new Alert(Alert.AlertType.INFORMATION, "Na tento den uz mas naplanovane ine jedlo").showAndWait();
				return;
			}
		}

		jedalnicek.setDatum(datePickerDatum.getValue());

		jedalnicek.setDen(textFieldDen.getText());

		jedalnicek.setRecept(receptFxModel.getRecept());
		//jedalnicek si pamata aj nazov receptu aby sme ho mohli dat do tabulky
		jedalnicek.setNazovReceptu(receptFxModel.getRecept().getNazov_receptu());

		jedalnicekDao.pridaj(jedalnicek);
		tableViewJedalnicek.setItems(FXCollections.observableArrayList(jedalnicekDao.getAll()));

	}

	@FXML
	void initialize() {

		tableViewJedalnicek.setItems(FXCollections.observableArrayList(jedalnicekDao.getAll()));

		// pri otvoreni okna jedalnicek sa nastavi datum a cas na dnesny
		datePickerDatum.setValue(LocalDate.now());
		textFieldDen
				.setText(datePickerDatum.getValue().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
		textFieldDen.setEditable(false);

		denStlpec.setCellValueFactory(new PropertyValueFactory<>("den"));
		nazovStlpec.setCellValueFactory(new PropertyValueFactory<>("nazovReceptu"));
		datumStlpec.setCellValueFactory(new PropertyValueFactory<>("datum"));

	}

}