package application.subSystem.Interface;

import java.util.List;

import application.model.Bill;

public interface IBillApi extends IBaseApi<Bill>{
	public List<Object[]> getBikeCodeAndBillCode(int customerID);
}
