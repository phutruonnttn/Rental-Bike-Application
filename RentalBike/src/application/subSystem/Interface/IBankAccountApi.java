package application.subSystem.Interface;

import java.util.List;

import application.model.BankAccount;

public interface IBankAccountApi extends IBaseApi<BankAccount> {
	public List<Object[]> getAllBankAccountByCustomerCode(int customerCode);
}
