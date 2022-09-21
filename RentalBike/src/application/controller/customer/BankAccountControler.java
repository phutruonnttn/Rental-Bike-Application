package application.controller.customer;

import application.Main;
import application.model.BankAccount;
import application.subSystem.BankAccountApi;
import application.subSystem.Interface.IBankAccountApi;

public class BankAccountControler {
	private IBankAccountApi bankAccountApi;
	private BankAccount bankAccount;

	public BankAccountControler(BankAccount bankAccount) {
		super();
		this.bankAccountApi = BankAccountApi.getInstance(BankAccount.class);
		this.bankAccount = bankAccount;
		
	}
	
	public BankAccountControler() {
		super();
		this.bankAccountApi = BankAccountApi.getInstance(BankAccount.class);
	}

	public BankAccount save() throws Exception{
		BankAccount result = this.bankAccountApi.saveOrUpdate(this.bankAccount);
		if(result == null) {
			throw new Exception("Cant save bankAcount");
		}

		Main.shared.section.replace("cardNumber", result.getCardNumber());
		
		return result;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
}
