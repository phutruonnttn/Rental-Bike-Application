package application.controller.customer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

import application.model.BankAccount;
import application.model.Bike;
import application.model.Bill;
import application.model.Customer;
import application.subSystem.BikeApi;
import application.subSystem.BillApi;
import application.subSystem.CustomerApi;
import application.subSystem.Interface.IBikeApi;
import application.subSystem.Interface.IBillApi;
import application.subSystem.Interface.ICustomerApi;

public class BillController {
	private IBillApi billApi;
	private ICustomerApi customerApi;
	private IBikeApi bikeApi;
	private Bill bill;
    
	public BillController(int customerCode, int bikeCode) {
		super();
		this.customerApi = CustomerApi.getInstance(Customer.class);
		this.bikeApi = BikeApi.getInstance(Bike.class);
		Customer customer = this.customerApi.getById(customerCode);
		Bike bike = this.bikeApi.getById(bikeCode);
		this.bill = new Bill(bike, customer, Date.valueOf(LocalDate.now()), 0);
	}
	
	public BillController() {
		super();
		this.billApi = BillApi.getInstance(Bill.class);
	}
	
	public Bill save() {
		this.billApi = BillApi.getInstance(Bill.class);
		Bill result = this.billApi.saveOrUpdate(this.bill);
		return result;
	}
	
	public Bill save(Bill bill) {
		this.billApi = BillApi.getInstance(Bill.class);
		Bill result = this.billApi.saveOrUpdate(bill);
		return result;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	public int getTransactionAmount() {
		return this.bill.calculateFirstTransactionAmount();
	}
	
	public Customer getOwnerBill() {
		return this.bill.getCustomer();
	}
	
	public Bike getBikeInBill() {
		return this.bill.getBike();
	}
	
	public Set<BankAccount> getBankAccount() {
		return this.getOwnerBill().getBankAccounts();
	}
    
}
