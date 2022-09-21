package application.view.customer;


import application.Main;
import application.controller.customer.BillController;
import application.model.Bike;
import application.model.Bill;
import application.model.Customer;
import application.utils.CurrencyUtil;
import application.utils.Define;
import application.utils.Utils;
import application.view.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

public class BillViewController extends ViewController{

    @FXML
    private TitledPane view;

    @FXML
    private Label bikeName;

    @FXML
    private Button cancel;

    @FXML
    private Label stationCode;

    @FXML
    private Label bikeCodeLabel;

    @FXML
    private Label weight;

    @FXML
    private Label customerName;

    @FXML
    private Label stationAddress;

    @FXML
    private Label manuafacturingDate;

    @FXML
    private Button confirm;

    @FXML
    private Label producter;

    @FXML
    private Label licensePlate;

    @FXML
    private Label totalFee;

    @FXML
    private Label deposit;

    @FXML
    private Label baseFee;

    @FXML
    private Label stationName;

    @FXML
    private Label typeBike;

    @FXML
    private Label email;
    
    private BillController billController;
    
    private int customerCode;
    
    private int bikeCode;
    
    private Bill bill;

	public int getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(int customerCode) {
		this.customerCode = customerCode;
	}


	public int getBikeCode() {
		return bikeCode;
	}


	public void setBikeCode(int bikeCode) {
		this.bikeCode = bikeCode;
	}


	@Override
	public void poplulateData() {
		this.billController = new BillController(this.customerCode, this.bikeCode);
		
		Customer customer = this.billController.getOwnerBill();
		this.bill = this.billController.getBill();
		
		this.customerName.setText(customer.getCustomerName());
		this.email.setText(customer.getEmail());
		
		Bike bike = this.billController.getBikeInBill();
		this.bikeCodeLabel.setText(String.valueOf(bike.getBikeCode()));
		this.bikeName.setText(bike.getBikeName());
		this.typeBike.setText(bike.getCategory().getCategoryName());
		this.weight.setText(String.valueOf(bike.getWeight()));
		this.licensePlate.setText(bike.getLicensePlate());
		this.manuafacturingDate.setText(String.valueOf(bike.getManuafactureDate()));
		this.producter.setText(bike.getProducer());
		this.stationCode.setText(String.valueOf(bike.getStation().getStationCode()));
		this.stationName.setText(String.valueOf(bike.getStation().getStationName()));
		this.stationAddress.setText(bike.getStation().getAddress());
		this.deposit.setText(CurrencyUtil.currencyFormat(String.valueOf(bike.getCategory().getDepositFee())));
		this.baseFee.setText(CurrencyUtil.currencyFormat(String.valueOf(this.bill.calculateInitFee())));
		this.totalFee.setText(CurrencyUtil.currencyFormat(String.valueOf(this.bill.calculateFirstTransactionAmount())));
		
	}
	

    @FXML
    void cancelBill(ActionEvent event) {
    	Main.shared.popViewController();
    }

    @FXML
    void confirmBill(ActionEvent event) {
    	if((boolean) Main.shared.section.get("rentBikeCustomerStatus")) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("WARNING");
    		alert.setContentText("Cant rent more than one bike at a time");
    		alert.showAndWait();
    		return;
    	}
    	BankAccountViewController bankAccountViewController = (BankAccountViewController)Utils.awakeFromFXML(Define.BANKACCOUNT_VIEW_PATH);
    	if(!this.billController.getBankAccount().isEmpty()) {
    		bankAccountViewController.setCurrentBankAccounts(this.billController.getBankAccount());
    	}
    	else {
    		bankAccountViewController.setCurrentBankAccounts(null);
    	}
    	bankAccountViewController.setBill(this.bill);
    	int transactionAmount = this.billController.getTransactionAmount();
    	bankAccountViewController.setAmountTransaction(transactionAmount);
    	Main.shared.pushViewController(bankAccountViewController);
    }

}

