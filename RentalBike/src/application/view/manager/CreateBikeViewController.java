package application.view.manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import application.Main;
import application.controller.manager.CreateBikeController;
import application.view.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CreateBikeViewController extends ViewController {

	@FXML
	public AnchorPane view;
	
    @FXML
    private TextField tfWeight;

    @FXML
    private TextField tfCost;

    @FXML
    private ChoiceBox<String> cbCategoryType;

    @FXML
    private ChoiceBox<String> cbStationCode;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfManufacturer;

    private CreateBikeController controller;
    
    @FXML 
    private Label lblIndicator;
    
    @FXML
    void didTapBackBtn(ActionEvent event) {
    	Main.shared.popViewController();
    }

    @FXML
    void didTapExitBtn(ActionEvent event) {
    	Main.shared.popToRootViewController();
    }
    
    @FXML
    void didTapSubmit(ActionEvent event) {
    	lblIndicator.setVisible(false);
    	HashMap params = new HashMap<String, String>();
    	params.put("name", tfName.getText());
    	params.put("producer", tfManufacturer.getText());
    	params.put("weight", tfWeight.getText());
    	params.put("cost", tfCost.getText());
    	params.put("date", getFormattedDate());
    	params.put("categoryType", cbCategoryType.getValue());
    	params.put("stationCode", cbStationCode.getValue());
    	String invalidMessage = controller.validateBikeInformation(params);
    	if(invalidMessage == null) {
    		showLoading();
    		controller.saveBike(params);
    		// show alert
    		showSuccessfulDialog();
    	} else {
    		showError(invalidMessage);
    	}
    }

	@Override
	public void poplulateData() {
		// valid message
		lblIndicator.setVisible(false);
		// station code
		cbStationCode.getItems().addAll(controller.getAllAvailableStationCodes());
		cbStationCode.getSelectionModel().select(0);
		// category type
		cbCategoryType.getItems().addAll(controller.getAllCategoryTypes());
		cbCategoryType.getSelectionModel().select(0);
		// date
		dpDate.setValue(LocalDate.now());
	}
	
	private void showSuccessfulDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Successfully save new bike!");
		alert.showAndWait();
		Main.shared.popViewController();
	}
	
	private String getFormattedDate() {
		LocalDate date = dpDate.getValue();
		return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	private void showError(String message) {
		lblIndicator.setVisible(true);
		lblIndicator.setText(message);
		lblIndicator.setStyle("-fx-text-fill: red");
	}
	
	private void showLoading() {
		lblIndicator.setVisible(true);
		lblIndicator.setText("Loading ...");
		lblIndicator.setStyle("-fx-text-fill: black");
	}
	
	public void setController(CreateBikeController controller) {
		this.controller = controller;
	}

}
