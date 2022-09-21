package application.controller.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import application.Main;
import application.calculateFee.NormalCalculateReturnBikeFee;
import application.calculateFee.Interface.ICalculateReturnBikeFeeModel;
import application.model.BankAccount;
import application.model.Bike;
import application.model.Bill;
import application.model.Category;
import application.model.Customer;
import application.model.Station;
import application.model.Transaction;
import application.subSystem.BankAccountApi;
import application.subSystem.BikeApi;
import application.subSystem.BillApi;
import application.subSystem.CategoryApi;
import application.subSystem.CustomerApi;
import application.subSystem.StationApi;
import application.subSystem.TransactionApi;
import application.subSystem.Interface.IBankAccountApi;
import application.subSystem.Interface.IBikeApi;
import application.subSystem.Interface.IBillApi;
import application.subSystem.Interface.ICategoryApi;
import application.subSystem.Interface.ICustomerApi;
import application.subSystem.Interface.IStationApi;
import application.subSystem.Interface.ITransactionApi;
import application.utils.DateUtil;

public class ReturnBikeController {
	
	private IStationApi stationService = StationApi.getInstance(Station.class);
    private ICustomerApi customerService = CustomerApi.getInstance(Customer.class);
    private IBillApi billService = BillApi.getInstance(Bill.class);
    private ITransactionApi transactionService = TransactionApi.getInstance(Transaction.class);
    private IBikeApi bikeService = BikeApi.getInstance(Bike.class);
    private ICategoryApi categoryService = CategoryApi.getInstance(Category.class);
    private IBankAccountApi bankAccountService = BankAccountApi.getInstance(BankAccount.class);
    private ICalculateReturnBikeFeeModel calculateFeeService = new NormalCalculateReturnBikeFee();
    private HashMap<String, String> params = new HashMap<String, String>();
    
	public HashMap<String, String> getParams() {
		return params;
	}

	public ArrayList<String> getAllAvailableStationCodes() {
		return stationService.getAllAvailableStationCodes();
	}
	
	public Customer getCustomer() {
		return customerService.getById(Main.shared.section.get("customerCode"));
	}
	
	public ArrayList<String> getAllBankAccount(int customerCode) {
		List<Object[]> result = bankAccountService.getAllBankAccountByCustomerCode(customerCode);
		ArrayList<String> res = new ArrayList<>();
		for(Object[] row: result) {
			res.add(row[0].toString() + " - " + row[1].toString());
		}
		return res;
	}
	
	public void poplulateData() {
		params.put("customerCode", Main.shared.section.get("customerCode").toString());
		params.put("customerName", getCustomer().getCustomerName());
		params.put("customerEmail", getCustomer().getEmail());
		
		List<Object[]> result1 = billService.getBikeCodeAndBillCode(Integer.parseInt(params.get("customerCode").toString()));
		for(Object[] row: result1) {
			params.put("bikeCode", row[0].toString());
			params.put("billCode", row[1].toString());
			break;
		}
		
		List<Object[]> result2 = transactionService.getCardNumberAndAmountAndTime(Integer.parseInt(params.get("billCode")));
		Date date = null;
		for(Object[] row: result2) {
			params.put("cardNumber", row[0].toString());
			params.put("issueBank", row[1].toString());
			params.put("depositAmount", row[2].toString());
			date = DateUtil.parseDetailDate(row[3].toString());
			params.put("date", date.toString());
			break;
		}
		
		Date today = new java.util.Date();   
		long diff = today.getTime() - date.getTime() + 60000;
        params.put("diffMinutes", "" + (diff / (60 * 1000)));
        params.put("diffHours","" + (diff / (60 * 60 * 1000)));
        
        Category cate =  categoryService.getById(bikeService.getById(Integer.parseInt(params.get("bikeCode"))).getCategory().getCategoryCode());
        params.put("bikeType", cate.getCategoryName());
        params.put("totalPayment", "" + (calculateFeeService.CalculateFee(cate.getCoefficient(), cate.getBaseFee(), cate.getMinuteFee(), Integer.parseInt(params.get("diffMinutes")))));
        params.put("remainingAmount", "" + (Integer.parseInt(params.get("depositAmount")) - Integer.parseInt(params.get("totalPayment"))));
	
        params.put("stationCode", null);
        params.put("stationName", null);
        params.put("stationAddress", null);
        params.put("stationStatus", null);
	}
	
	public void updateChooseStationView(int stationCode) {
		Station s = stationService.getById(stationCode);
		params.replace("stationCode", "" + s.getStationCode());
		params.replace("stationName", s.getStationName());
		params.replace("stationAddress", s.getAddress());
		params.replace("stationStatus", s.getCapacity() - s.getEmptySlot() + "/" + s.getCapacity());
	}
	
	public void updateBankAccount(String bankAccount) {
		params.replace("cardNumber", bankAccount.substring(bankAccount.indexOf('-') + 2));
	}
}
