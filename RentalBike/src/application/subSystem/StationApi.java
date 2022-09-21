package application.subSystem;

import java.util.ArrayList;
import java.util.List;

import application.model.Station;
import application.subSystem.Interface.IStationApi;

public class StationApi extends BaseApi<Station> implements IStationApi{

	private static StationApi instance;
	private StationApi(Class<Station> typeParameterClass) {
		super(typeParameterClass);
	}

	public ArrayList<String> getAllAvailableStationCodes() {
		List<Station> stations = getAll();
		ArrayList<String> codes = new ArrayList<String>();
		for(Station station: stations) {
			if(station.getEmptySlot() > 0) {
				codes.add("" + station.getStationCode());
			}
		}
		return codes;
	}

	public static synchronized StationApi getInstance(Class<Station> typeParameterClass) {
		if (instance == null)
			instance = new StationApi(typeParameterClass);
		return instance;
	}
}
