package application.controller.customer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

import application.model.BankAccount;
import application.model.Bike;
import application.model.Bill;
import application.model.Station;
import application.model.Transaction;
import application.model.TransactionId;
import application.subSystem.BankAccountApi;
import application.subSystem.BikeApi;
import application.subSystem.BillApi;
import application.subSystem.StationApi;
import application.subSystem.TransactionApi;
import application.subSystem.Interface.IBankAccountApi;
import application.subSystem.Interface.IBikeApi;
import application.subSystem.Interface.IBillApi;
import application.subSystem.Interface.IStationApi;
import application.subSystem.Interface.ITransactionApi;
import application.utils.CurrencyUtil;
import application.utils.MailUtil;

public class CompleteReturnBikeController {
	private IStationApi stationService;
    private IBillApi billService;
    private ITransactionApi transactionService;
    private IBikeApi bikeService;
    private IBankAccountApi bankAccountService;
    private HashMap<String, String> params = new HashMap<String, String>();
	
    public CompleteReturnBikeController(HashMap<String, String> params) {
    	super();
    	this.params = params;
    	this.stationService = StationApi.getInstance(Station.class);
    	this.billService = BillApi.getInstance(Bill.class);
    	this.transactionService = TransactionApi.getInstance(Transaction.class);
    	this.bikeService = BikeApi.getInstance(Bike.class);
    	this.bankAccountService = BankAccountApi.getInstance(BankAccount.class);
    }
    
	public void updateBillAndSaveTransaction() {
		Bill bill = billService.getById(Integer.parseInt(params.get("billCode").toString()));
		bill.setMinutes(Integer.parseInt(params.get("diffMinutes").toString()));
		billService.saveOrUpdate(bill);

		BankAccount bankAccount = bankAccountService.getById(params.get("cardNumber").toString());
		TransactionId tranId = new TransactionId(Integer.parseInt(params.get("billCode").toString()), params.get("cardNumber").toString(), Timestamp.valueOf(LocalDateTime.now()));
		Transaction trans;
		int remainingAmount = Integer.parseInt(params.get("remainingAmount").toString());
		if (remainingAmount < 0) {
			trans = new Transaction(tranId, bankAccount, bill, -remainingAmount, "Extra amount", 1);
		} else {
			trans = new Transaction(tranId, bankAccount, bill, remainingAmount, "Remaining amount", 0);
		}
		transactionService.saveOrUpdate(trans);

		Bike bike = bikeService.getById(Integer.parseInt(params.get("bikeCode").toString()));
		bike.setRentingStatus(false);
		
		Station firstStation = stationService.getById(bike.getStation().getStationCode());
		Set<Bike> bikes = firstStation.getBikes();
		bikes.remove(bike);
		firstStation.setBikes(bikes);
		
		bike.setStation(stationService.getById(Integer.parseInt(params.get("stationCode"))));
		Station secondStation = stationService.getById(Integer.parseInt(params.get("stationCode")));
		Set<Bike> bikes2 = secondStation.getBikes();
		bikes2.add(bike);
		secondStation.setBikes(bikes2);
		
		bikeService.saveOrUpdate(bike);
	}
	
	public void sendBill() {
		String content = "BIKE RETURN BILL INFORMATION\n\n";
		content += "Bike code:\t" + params.get("bikeCode") + "\n";
		content += "Bike type:\t" + params.get("bikeType") + "\n\n";
		content += "Station code:\t" + params.get("stationCode") + "\n";
		content += "Station name:\t" + params.get("stationName") + "\n";
		content += "Station address:\t" + params.get("stationAddress") + "\n\n";
		String rentalTime;
		int diffHour = Integer.parseInt(params.get("diffHours"));
		int diffMin = Integer.parseInt(params.get("diffMinutes"));
		if (diffHour > 1) {
			if (diffMin > 1) rentalTime = diffHour + " hours " + (diffMin-diffHour*60) + " minutes";
			else rentalTime = diffHour + " hours " + (diffMin-diffHour*60) + " minute";
		} else {
			if (diffMin > 1) rentalTime = diffHour + " hour " + (diffMin-diffHour*60) + " minutes";
			else rentalTime = diffHour + " hour " + (diffMin-diffHour*60) + " minute";
		}
		content += "Rental time:\t" + rentalTime + "\n";
		content += "Total payment:\t" + CurrencyUtil.currencyFormat(params.get("totalPayment")) + "\n";
		content += "Deposit amount:\t" + CurrencyUtil.currencyFormat(params.get("depositAmount")) + "\n";
		int amount = Integer.parseInt(params.get("remainingAmount"));
		if (amount < 0) {
			content += "Extra amount:\t" + CurrencyUtil.currencyFormat(String.valueOf(amount)) + "\n";
		}  else {
			content += "Remaining amount:\t" + CurrencyUtil.currencyFormat(String.valueOf(amount)) + "\n";
		}
	
		MailUtil.sendMail(params.get("customerEmail"), "Eco Bike Rental", content);
	}
}
