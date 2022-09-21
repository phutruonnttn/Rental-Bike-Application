package application.subSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.BankAccount;
import application.subSystem.Interface.IBankAccountApi;
import application.subSystem.Interface.IQueryService;

public class BankAccountApi extends BaseApi<BankAccount> implements IBankAccountApi{
	
	private static BankAccountApi instance;

	private BankAccountApi(Class<BankAccount> typeParameterClass) {
		super(typeParameterClass);
	}
	
	public List<Object[]> getAllBankAccountByCustomerCode(int customerCode) {
		IQueryService queryService = new QueryService();
		String sql = "SELECT b.issueBank, b.cardNumber FROM BankAccount b WHERE b.customer.customerCode =:customerCode";
 		Map<String, Object> param = new HashMap<String, Object>();
 		param.put("customerCode", customerCode);
 		List<Object[]> res = queryService.ExecuteQuery(sql, param);
 		return res;
	}

	public static synchronized BankAccountApi getInstance(Class<BankAccount> typeParameterClass) {
		if (instance == null)
			instance = new BankAccountApi(typeParameterClass);
		return instance;
	}
}
