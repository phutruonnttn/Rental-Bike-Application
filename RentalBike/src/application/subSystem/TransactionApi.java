package application.subSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.Transaction;
import application.subSystem.Interface.IQueryService;
import application.subSystem.Interface.ITransactionApi;

public class TransactionApi extends BaseApi<Transaction> implements ITransactionApi{

	private static TransactionApi instance;
	private TransactionApi(Class<Transaction> typeParameterClass) {
		super(typeParameterClass);
	}

	public List<Object[]> getCardNumberAndAmountAndTime(int billCode) {
		IQueryService queryService = new QueryService();
		String sql = "SELECT t.id.cardNumber, t.bankAccount.issueBank, t.amount, t.id.date FROM Transaction t WHERE t.bill.billCode =:billCode"; 		 		
		Map<String, Object> param = new HashMap<String, Object>();
 		param.put("billCode", billCode);
 		List<Object[]> res = queryService.ExecuteQuery(sql, param);
 		return res;
	}

	public static synchronized TransactionApi getInstance(Class<Transaction> typeParameterClass) {
		if (instance == null)
			instance = new TransactionApi(typeParameterClass);
		return instance;
	}
}
