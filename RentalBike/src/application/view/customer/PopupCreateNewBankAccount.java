package application.view.customer;

import java.sql.Date;

import application.controller.customer.BankAccountControler;
import application.model.BankAccount;
import application.model.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupCreateNewBankAccount {
	
	private static boolean ans;
	private BankAccountControler bankAccountController;
	
	public BankAccount populateData(String title, Customer cus) {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(400);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Label lbCardName = new Label("Card holder name:");
		grid.add(lbCardName, 0, 1);

		TextField tfCardName = new TextField();
		grid.add(tfCardName, 1, 1);
		
		Label lbCardNumber = new Label("Card number:");
		grid.add(lbCardNumber, 0, 2);

		TextField tfCardNumber = new TextField();
		grid.add(tfCardNumber, 1, 2);
		
		Label lbIssueBank = new Label("Issuing bank:");
		grid.add(lbIssueBank, 0, 3);

		ComboBox<String> tfIssueBank = new ComboBox<>();
		grid.add(tfIssueBank, 1, 3);
		tfIssueBank.setMinWidth(175);
		tfIssueBank.getItems().addAll("ViettinBank", "ACBBank", "VietComBank", "ODCBank");
		
		Label lbExpDate = new Label("Expiration date:");
		grid.add(lbExpDate, 0, 4);

		DatePicker tfExpDate = new DatePicker();
		grid.add(tfExpDate, 1, 4);
		
		Label lbSecCode = new Label("Security code:");
		grid.add(lbSecCode, 0, 5);

		TextField tfSecCode = new TextField();
		grid.add(tfSecCode, 1, 5);
		
		Label lbNoticeValue = new Label("Notice value");
		lbNoticeValue.setTextFill(Color.RED);
		lbNoticeValue.setVisible(false);
		
		HBox hbBtn1 = new HBox();
		hbBtn1.setAlignment(Pos.CENTER);
		hbBtn1.getChildren().add(lbNoticeValue);
		grid.add(hbBtn1, 0, 6, 2, 1);
		
		
		Button btnCancel = new Button("Cancel");
		HBox hbBtn2 = new HBox();
		hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn2.getChildren().add(btnCancel);
		grid.add(hbBtn2, 0, 7);
		
		Button btnAdd = new Button("Add");
		HBox hbBtn3 = new HBox();
		hbBtn3.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn3.getChildren().add(btnAdd);
		grid.add(hbBtn3, 1, 7);
		
		BankAccount account = new BankAccount();
		
		btnAdd.setOnAction(e-> {
			account.setCardholderName(tfCardName.getText());
			account.setCardNumber(tfCardNumber.getText());
			account.setCustomer(cus);
			if(tfExpDate.getValue() != null) {
				account.setExpirationDate(Date.valueOf(tfExpDate.getValue()));
			} else {
				account.setExpirationDate(null);
			}
			account.setIssueBank(tfIssueBank.getSelectionModel().getSelectedItem());
			account.setSecurityCode(tfSecCode.getText());
			
			String resultValidate = account.validate();
			if(!resultValidate.equals("oke")) {
				lbNoticeValue.setVisible(true);
				lbNoticeValue.setText(resultValidate);
        		return;
    		}
			
    		bankAccountController = new BankAccountControler(account);
            try {
				bankAccountController.save();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		
			ans = true;
			window.close();
		});
		
		btnCancel.setOnAction(e-> {
			ans = false;
			window.close();
		});
		
		Scene scene = new Scene(grid, 300, 275);
		window.setScene(scene);
		window.showAndWait();
		
		if (ans == true) {
			return account;
		}
		return null;
	}
}