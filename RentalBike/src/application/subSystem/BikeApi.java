package application.subSystem;

import application.model.Bike;
import application.subSystem.Interface.IBikeApi;

public class BikeApi extends BaseApi<Bike> implements IBikeApi{
	
	private static BikeApi instance;

	private BikeApi(Class<Bike> typeParameterClass) {
		super(typeParameterClass);
	}
	
	public static synchronized BikeApi getInstance(Class<Bike> typeParameterClass) {
		if (instance == null)
			instance = new BikeApi(typeParameterClass);
		return instance;
	}
}
