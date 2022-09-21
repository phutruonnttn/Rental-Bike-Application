package application.controller.customer;

import java.util.ArrayList;
import java.util.List;

import application.model.Station;
import application.model.SubStation;
import application.subSystem.StationApi;
import application.subSystem.Interface.IStationApi;

public class StationListController {
	private ArrayList<SubStation> subStation;
	private IStationApi stationApi;
	public StationListController() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadData(){
		IStationApi stationApi = StationApi.getInstance(Station.class);
		
		List<Station> stationList1 = stationApi.getAll();
		
		subStation = new ArrayList<SubStation>();
		
		for (Station item : stationList1) {
			SubStation subS = new SubStation(item.getStationCode(),item.getStationName(),item.getAddress(),item.getCapacity(),item.getBikes(),item.getNumberBikeAvailable());
			subStation.add(subS);
		}
	}
	
	public ArrayList<SubStation> searchStation(String sItem) {
		ArrayList<SubStation> searchStation = new ArrayList<SubStation>();
    	for (SubStation item : subStation) {
    		if(item.getStationName().toLowerCase().contains(sItem.toLowerCase()) || item.getAddress().toLowerCase().contains(sItem.toLowerCase())) {
    			searchStation.add(item);
    		} 
    	}
		return searchStation;
	}
	
	public ArrayList<SubStation> getSubStation() {
		return subStation;
	}
	public void setSubStation(ArrayList<SubStation> subStation) {
		this.subStation = subStation;
	}

	public IStationApi getStationApi() {
		return stationApi;
	}

	public void setStationApi(IStationApi stationApi) {
		this.stationApi = stationApi;
	}

}

