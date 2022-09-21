package application.view.customer;

import java.util.HashMap;

import javax.swing.JOptionPane;
import application.Main;
import application.controller.customer.CompleteReturnBikeController;
import application.controller.customer.ReturnBikeController;
import application.model.BankAccount;
import application.utils.CurrencyUtil;
import application.utils.Define;
import application.utils.Utils;
import application.view.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ReturnBikeViewController extends ViewController{

    @FXML
    private Label lbID;
    
    @FXML
    private Label lbTitleExtraAmount;

    @FXML
    private Label lbTitleRemainingAmount;
    
    @FXML
    private Label lbBikeType;

    @FXML
    private Label lbRemainingAmount;

    @FXML
    private Label lbStationAddress;

    @FXML
    private Label lbDepositAmount;

    @FXML
    private Label lbTotalPayment;

    @FXML
    private BorderPane view;

    @FXML
    private Label lbName;

    @FXML
    private Label lbBikeCode;

    @FXML
    private ChoiceBox<String> cbStationCode;

    @FXML
    private ChoiceBox<String> cbBankAccount;

    @FXML
    private Label lbStatus;

    @FXML
    private Label lbRentalTime;

    @FXML
    private Label lbStationName;
    
    @FXML
    private Label lbEmail;
    
    private ReturnBikeController controller;
    private CompleteReturnBikeController completeController;
    
    public void setController(ReturnBikeController controller) {
		this.controller = controller;
	}

    @FXML
    void didTapCancelBtn(ActionEvent event) {
    	Main.shared.popViewController();
    }

    @FXML
    void didTapReturnBtn(ActionEvent event) {
    	int input = JOptionPane.showConfirmDialog(null, "Do you want to confirm to return the bike?", "Confirming",JOptionPane.YES_NO_OPTION);
    	if (input == 0) {
    		completeController = new CompleteReturnBikeController(controller.getParams());
    		completeController.updateBillAndSaveTransaction();
    		completeController.sendBill();
    		JOptionPane.showMessageDialog(null, "Successfully!");
    		Main.shared.section.replace("rentBikeCustomerStatus", false);
    		
    		Main.shared.popToRootViewController();
    		StationListViewController vc = (StationListViewController)Utils.awakeFromFXML(Define.STATIONLIST_VIEW_PATH);
        	Main.shared.pushViewController(vc);
    	}
    }

	@Override
	public void poplulateData() {
		lbTitleRemainingAmount.setVisible(false);
		lbTitleExtraAmount.setVisible(false);
		controller.poplulateData();
		HashMap<String, String> params = new HashMap<String, String>();
		params = controller.getParams();
		lbName.setText(params.get("customerName"));
		lbID.setText(params.get("customerCode"));
		lbEmail.setText(params.get("customerEmail"));
		lbBikeCode.setText(params.get("bikeCode"));
		lbDepositAmount.setText(CurrencyUtil.currencyFormat(params.get("depositAmount")));
		int diffHour = Integer.parseInt(params.get("diffHours"));
		int diffMin = Integer.parseInt(params.get("diffMinutes"));
		if (diffHour > 1) {
			if (diffMin > 1) lbRentalTime.setText(diffHour + " hours " + (diffMin-diffHour*60) + " minutes");
			else lbRentalTime.setText(diffHour + " hours " + (diffMin-diffHour*60) + " minute");
		} else {
			if (diffMin > 1) lbRentalTime.setText(diffHour + " hour " + (diffMin-diffHour*60) + " minutes");
			else lbRentalTime.setText(diffHour + " hour " + (diffMin-diffHour*60) + " minute");
		}
		lbBikeType.setText(params.get("bikeType"));
		lbTotalPayment.setText(CurrencyUtil.currencyFormat(params.get("totalPayment")));
		
		int amount = Integer.parseInt(params.get("remainingAmount"));
		if (amount < 0) {
			lbTitleExtraAmount.setVisible(true);
		}  else {
			lbTitleRemainingAmount.setVisible(true);
		}
		lbRemainingAmount.setText(CurrencyUtil.currencyFormat(String.valueOf(amount)));
		
		cbBankAccount.getItems().addAll(controller.getAllBankAccount(Integer.parseInt(params.get("customerCode"))));
		cbBankAccount.getItems().add("Use other");
		cbBankAccount.getSelectionModel().select(0);
		
		cbStationCode.getItems().addAll(controller.getAllAvailableStationCodes());
		cbStationCode.getSelectionModel().select(0);
	}
	
	@FXML
    void didChoiceStationCodeCb(ActionEvent event) {
		controller.updateChooseStationView(Integer.parseInt(cbStationCode.getValue()));
		lbStationName.setText(controller.getParams().get("stationName"));
		lbStationAddress.setText(controller.getParams().get("stationAddress"));
		lbStatus.setText(controller.getParams().get("stationStatus"));
    }
	
	@FXML
	void didChoiceBankAccountCb(ActionEvent event) {
		if (cbBankAccount.getValue().equals("Use other")) {
			PopupCreateNewBankAccount popupNewBankAccount = new PopupCreateNewBankAccount();
			BankAccount bankAccount = popupNewBankAccount.populateData("Add a new bank account", controller.getCustomer());
    		if(bankAccount != null) {
    			cbBankAccount.getItems().clear();
    			cbBankAccount.getItems().addAll(controller.getAllBankAccount(Integer.parseInt(controller.getParams().get("customerCode"))));
    			cbBankAccount.getItems().add("Use other");
    			cbBankAccount.getSelectionModel().select(bankAccount.getIssueBank() + " - " + bankAccount.getCardNumber());
    		} else {
    			cbBankAccount.getItems().clear();
    			cbBankAccount.getItems().addAll(controller.getAllBankAccount(Integer.parseInt(controller.getParams().get("customerCode"))));
    			cbBankAccount.getItems().add("Use other");
    			cbBankAccount.getSelectionModel().select(0);
    		}
		} 
		controller.updateBankAccount(cbBankAccount.getValue());
    }

}
