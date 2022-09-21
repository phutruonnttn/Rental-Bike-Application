package application.subSystem;

import application.model.EBike;
import application.subSystem.Interface.IEBikeApi;

public class EBikeApi extends BaseApi<EBike> implements IEBikeApi{

	private static EBikeApi instance;
	private EBikeApi(Class<EBike> typeParameterClass) {
		super(typeParameterClass);
	}

	public static synchronized EBikeApi getInstance(Class<EBike> typeParameterClass) {
		if (instance == null)
			instance = new EBikeApi(typeParameterClass);
		return instance;
	}

}
