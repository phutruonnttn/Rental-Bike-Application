package application.controller.customer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import application.model.Bike;
import application.model.SubBike;

public class BikeListController {
	private Set<Bike> bikes;
	private ArrayList<SubBike> bikeList;
	public BikeListController() {
		// TODO Auto-generated constructor stub
	}
	public void loadData() {
		bikeList = new ArrayList<SubBike>();
		int index = 0;
		Iterator<Bike> iterator = bikes.iterator();
		while (iterator.hasNext()) {
			Bike bike1 = iterator.next();
			if (bike1.getRentingStatus() == false) {
				index++;	
			SubBike subBike = new SubBike(index,bike1.getBikeCode(),bike1.getCategory().getCategoryName(),bike1.getBikeName(),bike1.getLicensePlate(),bike1.getRentingStatus());
			bikeList.add(subBike);
			}
        }
	}
	public ArrayList<SubBike> searchBike(String sItem) {
		ArrayList<SubBike> searchBike = new ArrayList<SubBike>();
    	for (SubBike item : bikeList) {
    		if(item.getBikeName().toLowerCase().contains(sItem.toLowerCase()) || item.getCategory().toLowerCase().contains(sItem.toLowerCase()) || String.valueOf(item.getBikeCode()).toLowerCase().contains(sItem.toLowerCase())) {
    			searchBike.add(item);
    		} 
    	}
		return searchBike;
	}
	public Set<Bike> getBikes() {
		return bikes;
	}
	public void setBikes(Set<Bike> bikes) {
		this.bikes = bikes;
	}
	public ArrayList<SubBike> getBikeList() {
		return bikeList;
	}

}
