package Hrehova.Stedlova.PlanEat;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HlavnaController {

	@FXML
	private Button buttonPridajRecept;

	@FXML
	private Button buttonZoznamReceptov;

	@FXML
	private Button buttonJedlnicek;

	@FXML
	void actionJedalnicek(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Jedálniček.fxml"));
			Parent parent = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Jedalnicek");
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@FXML
	void actionPridatRecept(ActionEvent event) {
		try {
			ReceptController controller = new ReceptController();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Recept.fxml"));
			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Recept");
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@FXML
	void actionZoznamReceptov(ActionEvent event) {
		try {
			ZoznamReceptovController zrc = new ZoznamReceptovController();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ZoznamReceptov.fxml"));
			fxmlLoader.setController(zrc);

			Parent parent = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("ZoznamReceptov");
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@FXML
	void initialize() {

	}
}
