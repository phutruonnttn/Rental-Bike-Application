package application.view.customer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Set;

import application.Main;
import application.controller.customer.BankAccountControler;
import application.controller.customer.CompleteRentBikeController;
import application.model.BankAccount;
import application.model.Bill;
import application.view.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

public class BankAccountViewController extends ViewController{

    @FXML
    private Button cancel;

    @FXML
    private TextField holdNameValue;

    @FXML
    private RadioButton currentBankAccount;

    @FXML
    private TextField cardNumberValue;

    @FXML
    private TextArea transationDiscription;

    @FXML
    private Button confirm;

    @FXML
    private TitledPane view;

    @FXML
    private DatePicker  expirationDateValue;

    @FXML
    private ComboBox<String> issueBankValue;
    
    @FXML
    private ChoiceBox<String> choiceBoxBankACcount;

    @FXML
    private TextField securityCodeValue;

    @FXML
    private RadioButton newBankAccount;
    
    @FXML
    private Pane currentBankLabel;
    
    @FXML
    private Pane newBankAccountLabel;
    
    @FXML
    private Label warming;
    
    @FXML
    private Label warmingValue;
    
    private BankAccount bankAccount;
    
    private Set<BankAccount> currentBankAccounts;
    
    private BankAccountControler bankAccountController;
    
    private Bill bill;

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Set<BankAccount> getCurrentBankAccounts() {
		return currentBankAccounts;
	}

	public void setCurrentBankAccounts(Set<BankAccount> currentBankAccounts) {
		this.currentBankAccounts = currentBankAccounts;
	}

	private int amountTransaction;
    

    public int getAmountTransaction() {
		return amountTransaction;
	}

	public void setAmountTransaction(int amountTransaction) {
		this.amountTransaction = amountTransaction;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	@FXML
    void cancelBankAccount(ActionEvent event) {
    	Main.shared.popViewController();
    }

    @FXML
    void confirmBankAccount(ActionEvent event) throws Exception {
    	if(!this.currentBankAccount.isSelected()) {
    		this.bankAccount = new BankAccount();
    		this.bankAccount.setCustomer(this.bill.getCustomer());
    		getBankAccountValue();
    		String resultValidate = this.bankAccount.validate();
    		if(!resultValidate.equals("oke")) {
    			this.warming.setVisible(true);
        		this.warmingValue.setVisible(true);
        		this.warmingValue.setText(resultValidate);
        		return;
    		}
    		this.bankAccountController = new BankAccountControler(this.bankAccount);
    		BankAccount resultSaveBankAccount = this.bankAccountController.save();
    		this.bankAccount = resultSaveBankAccount;
    	}
    	else {
    		int index = this.choiceBoxBankACcount.getSelectionModel().getSelectedIndex();
    		this.bankAccount = (BankAccount) this.currentBankAccounts.toArray()[index];
    	}
    	
    	if(this.bankAccount != null) {
    		CompleteRentBikeController completeRentBikeController = new CompleteRentBikeController(this.bankAccount, this.bill, this.amountTransaction, this.transationDiscription.getText());
    		completeRentBikeController.CompleteRentBike();
    		Main.shared.popViewControllers(3);
    	}
    	else {
    		this.warming.setVisible(true);
    		this.warmingValue.setVisible(true);
    		this.warmingValue.setText("Something went wrong, try later !");
    		return;
    	}
    }
    
    @FXML
    void choseCurrentBankAccount(ActionEvent event) {
    	this.currentBankAccount.setSelected(true);
		this.newBankAccount.setSelected(false);
		this.setDisableInputField();
		this.choiceBoxBankACcount.setDisable(false);
    }

    @FXML
    void choseNewBankAccount(ActionEvent event) {
    	this.currentBankAccount.setSelected(false);
		this.newBankAccount.setSelected(true);
		this.setUnDisableInputField();
		this.choiceBoxBankACcount.setDisable(true);
    }

	@Override
	public void poplulateData() {
		this.warming.setVisible(false);
		this.warmingValue.setVisible(false);
		this.issueBankValue.getItems().addAll("ViettinBank", "ACBBank", "VietComBank", "ODCBank");
		ArrayList<String> bankAccountNames = new ArrayList<>();
		
		if(this.currentBankAccounts == null) {
			this.currentBankAccount.setDisable(true);
		}
		else {
			for (BankAccount bankAccount : currentBankAccounts) {
				String bankAccountName = "IssueBank: " + bankAccount.getIssueBank() + '-' + "Cardnumber: " + bankAccount.getCardNumber();
				bankAccountNames.add(bankAccountName);
			}
			this.choiceBoxBankACcount.getItems().addAll(bankAccountNames);
			this.choiceBoxBankACcount.getSelectionModel().select(0);
			this.currentBankAccount.setSelected(true);
			this.newBankAccount.setSelected(false);
			this.setDisableInputField();
		}
	}
	
	private void getBankAccountValue() {
		this.bankAccount.setCardholderName(this.holdNameValue.getText());
		this.bankAccount.setCardNumber(this.cardNumberValue.getText());
		this.bankAccount.setCustomer(this.bill.getCustomer());
		if(this.expirationDateValue.getValue() != null) {
			this.bankAccount.setExpirationDate(Date.valueOf(this.expirationDateValue.getValue()));
		}
		else {
			this.bankAccount.setExpirationDate(null);
		}
		this.bankAccount.setIssueBank(this.issueBankValue.getSelectionModel().getSelectedItem());
		this.bankAccount.setSecurityCode(this.securityCodeValue.getText());
	}
	
	private void setDisableInputField() {
		this.holdNameValue.setDisable(true);
		this.cardNumberValue.setDisable(true);
		this.issueBankValue.setDisable(true);
		this.expirationDateValue.setDisable(true);
		this.securityCodeValue.setDisable(true);
	}
	
	private void setUnDisableInputField() {
		this.holdNameValue.setDisable(false);
		this.cardNumberValue.setDisable(false);
		this.issueBankValue.setDisable(false);
		this.expirationDateValue.setDisable(false);
		this.securityCodeValue.setDisable(false);
	}

}
