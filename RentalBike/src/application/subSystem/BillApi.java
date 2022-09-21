package application.subSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.Bill;
import application.subSystem.Interface.IBillApi;
import application.subSystem.Interface.IQueryService;

public class BillApi extends BaseApi<Bill> implements IBillApi{
	
	private static BillApi instance;

	private BillApi(Class<Bill> typeParameterClass) {
		super(typeParameterClass);
	}

	public List<Object[]> getBikeCodeAndBillCode(int customerID) {
		IQueryService queryService = new QueryService();
		String sql = "SELECT b.bike.bikeCode, b.billCode FROM Bill b WHERE b.customer.customerCode =:customerCode AND b.minutes =:minutes";
 		Map<String, Object> param = new HashMap<String, Object>();
 		param.put("customerCode", customerID);
 		param.put("minutes", 0);
 		List<Object[]> res = queryService.ExecuteQuery(sql, param);
 		return res;
	}
	
	public static synchronized BillApi getInstance(Class<Bill> typeParameterClass) {
		if (instance == null)
			instance = new BillApi(typeParameterClass);
		return instance;
	}
}
