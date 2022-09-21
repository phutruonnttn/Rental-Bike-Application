package application.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
	
	static boolean answer;

	public boolean displayConfirm(String title,String message) {
		Stage window = new Stage();
		
		//Blocks event to window
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(300);
		
		Label label = new Label();
		label.setText(message);
		
		//Create two buttons
		Button yesButton = new Button("Confirm");
		Button noButton = new Button("Cancel");
		
		yesButton.setOnAction(e-> {
			answer = true;
			window.close();
		});
		
		noButton.setOnAction(e-> {
			answer = false;
			window.close();
		});
		
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}
	public boolean displayWarning(String title,String message) {
		Stage window = new Stage();
		
		//Blocks event to window
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(300);
		
		Label label = new Label();
		label.setText(message);
		
		//Create one buttons
		Button noButton = new Button("Cancel");
		
		noButton.setOnAction(e-> {
			answer = false;
			window.close();
		});
		
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, noButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}

}