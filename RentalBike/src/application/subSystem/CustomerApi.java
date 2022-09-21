package application.subSystem;

import application.model.Customer;
import application.subSystem.Interface.ICustomerApi;

public class CustomerApi extends BaseApi<Customer> implements ICustomerApi{

	private static CustomerApi instance;
	private CustomerApi(Class<Customer> typeParameterClass) {
		super(typeParameterClass);
	}

	public static synchronized CustomerApi getInstance(Class<Customer> typeParameterClass) {
		if (instance == null)
			instance = new CustomerApi(typeParameterClass);
		return instance;
	}

}
