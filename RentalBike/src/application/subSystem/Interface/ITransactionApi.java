package application.subSystem.Interface;

import java.util.List;

import application.model.Transaction;

public interface ITransactionApi extends IBaseApi<Transaction>{
	public List<Object[]> getCardNumberAndAmountAndTime(int billCode);
}
