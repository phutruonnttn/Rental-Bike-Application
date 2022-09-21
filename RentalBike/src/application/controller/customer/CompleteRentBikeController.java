package application.controller.customer;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import application.Main;
import application.model.BankAccount;
import application.model.Bike;
import application.model.Bill;
import application.model.Transaction;
import application.model.TransactionId;
import application.subSystem.BikeApi;
import application.subSystem.TransactionApi;
import application.subSystem.Interface.IBikeApi;
import application.subSystem.Interface.ITransactionApi;

public class CompleteRentBikeController {
	private ITransactionApi transactionApi;
	private IBikeApi bikeApi;
	private BankAccount bankAccount;
	private Bill bill;
	private int amountFee;
	private String transactionDescription;
	private BillController billController;
	
	public CompleteRentBikeController(BankAccount bankAccount, Bill bill, int amountFee, String transactionDescription) {
		super();
		this.transactionApi = TransactionApi.getInstance(Transaction.class);
		this.bikeApi = BikeApi.getInstance(Bike.class);
		this.bankAccount = bankAccount;
		this.bill = bill;
		this.amountFee = amountFee;
		this.transactionDescription = transactionDescription;
		this.billController = new BillController();
		
	}

	public String CompleteRentBike() {
		//save bill
		this.bill = billController.save(this.bill);
		//save section
		Main.shared.section.replace("billCode", this.bill.getBillCode());
		Main.shared.section.replace("cardNumber", this.bankAccount.getCardNumber());
		
		//save transaction
		TransactionId tranId = new TransactionId(this.bill.getBillCode(), this.bankAccount.getCardNumber(), Timestamp.valueOf(LocalDateTime.now()));
		Transaction transaction = new Transaction(tranId, this.bankAccount, this.bill, this.amountFee,this.transactionDescription, 1);
		this.transactionApi.saveOrUpdate(transaction);
		
		//update bike status
		Bike bike = this.bikeApi.getById(Main.shared.section.get("bikeCode"));
		bike.setRentingStatus(true);
		this.bikeApi.saveOrUpdate(bike);
		
		Main.shared.section.replace("timeStartRentBike", Timestamp.valueOf(LocalDateTime.now()));
		return "oke";
	}
	
}
