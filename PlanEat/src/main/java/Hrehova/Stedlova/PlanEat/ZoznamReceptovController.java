package Hrehova.Stedlova.PlanEat;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ZoznamReceptovController {

	@FXML
	private ListView<Recept> listViewRecepty;

	@FXML
	private Button buttonZavriet;

	@FXML
	private TextField textFildPodlaNazvu;

	@FXML
	private Button buttonVyhladaj;

	private ReceptyDao receptyDao = DaoFactory.INSTANCE.getReceptyDao();

	// pomocna metoda na vratenie vybraneho receptu
	public Recept getRecept() {
		Recept recept = new Recept();
		recept = listViewRecepty.getSelectionModel().getSelectedItem();
		return recept;
	}

	// klik na button vyhladaj podla nazvu
	@FXML
	void actionVyhladaj(ActionEvent event) {
		listViewRecepty.setItems(FXCollections
				.observableArrayList(receptyDao.getPodlaNazvu(textFildPodlaNazvu.getText().toLowerCase())));
	}

	//klik na button odstran recept
	@FXML
	void actionOdstranRecept(ActionEvent event) {
		receptyDao.odstran(listViewRecepty.getSelectionModel().getSelectedItem());
		listViewRecepty.setItems(FXCollections.observableArrayList(receptyDao.getAll()));
	}

	//klik na button zavriet alebo pridat do jedalnicku
	@FXML
	void actionZavriet(ActionEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();

	}

	@FXML
	void initialize() {
		listViewRecepty.setItems(FXCollections.observableArrayList(receptyDao.getAll()));

		//na dvojklik upravujeme dany recept
		listViewRecepty.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					try {
						Recept recept = listViewRecepty.getSelectionModel().getSelectedItem();

						ReceptController controller = new ReceptController(recept);
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Recept.fxml"));
						fxmlLoader.setController(controller);
						Parent rootPane = fxmlLoader.load();
						Scene scene = new Scene(rootPane);
						Stage dialog = new Stage();

						dialog.setScene(scene);
						dialog.showAndWait();

						listViewRecepty.setItems(FXCollections.observableArrayList(receptyDao.getAll()));

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
	}
}