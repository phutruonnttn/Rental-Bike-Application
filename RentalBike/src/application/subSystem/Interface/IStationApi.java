package application.subSystem.Interface;

import java.util.ArrayList;

import application.model.Station;

public interface IStationApi extends IBaseApi<Station>{
	public ArrayList<String> getAllAvailableStationCodes();
}
