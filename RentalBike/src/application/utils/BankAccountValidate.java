package application.utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.BankAccount;
import application.subSystem.BankAccountApi;
import application.subSystem.Interface.IBankAccountApi;

public class BankAccountValidate {
	private BankAccount bankAccount;
	private IBankAccountApi bankAccountApi;
	
	public BankAccountValidate(BankAccount bankAccount) {
		super();
		this.bankAccount = bankAccount;
		this.bankAccountApi = BankAccountApi.getInstance(BankAccount.class);
	}

	public String validateBankAccount() {
		if(this.bankAccount == null) {
			return "bankAccount cant null";
		}
		String isNullEmptyResult = this.checkNullOrEmpty();
		if(!isNullEmptyResult.equals("oke")) {
			return isNullEmptyResult;
		}
		//check length card holder number <= 20 and digits
		if(this.bankAccount.getCardNumber().length() > 20) {
			return "length cardholder number must <= 20";
		}
		if(!isFullDigits(this.bankAccount.getCardNumber())) {
			return "cardholder number must be full digits";
		}
		if(DateUtil.parseDate(LocalDate.now().toString()).after(this.bankAccount.getExpirationDate())) {
			return "expiration date invalid";
		}
		if(this.isRegister(bankAccount)) {
			return "bankAccount already register";
		}
		return "oke";
	}
	
	private boolean isFullDigits(String value) {
		for (int i = 0; i < value.length(); i++) {
			if(!Character.isDigit(value.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isRegister(BankAccount bankAccountCheck) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cardNumber", bankAccountCheck.getCardNumber());
		List<BankAccount> bankAccount = this.bankAccountApi.filter(param);
		if(bankAccount.size() != 0) {
			return true;
		}
		return false;
	}
	
	private String checkNullOrEmpty() {
		if(this.bankAccount.getCardholderName() == null || this.bankAccount.getCardholderName().isEmpty()) {
			return "CardHolder name cant null or empty";
		}

		if(this.bankAccount.getCardNumber() == null || this.bankAccount.getCardNumber().isEmpty()) {
			return "Card number cant null or empty";
		}
		if(this.bankAccount.getIssueBank() == null || this.bankAccount.getIssueBank().isEmpty()) {
			return "IssueBank cant null or empty";
		}
		if(this.bankAccount.getSecurityCode()== null || this.bankAccount.getSecurityCode().isEmpty()) {
			return "Security code cant null or empty";
		}
		if(this.bankAccount.getExpirationDate() == null) {
			return "Expiration cant null or empty";
		}
		return "oke";
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	
}
