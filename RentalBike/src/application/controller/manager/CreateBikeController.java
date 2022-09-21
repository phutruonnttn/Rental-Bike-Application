package application.controller.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import application.model.Bike;
import application.model.Category;
import application.model.EBike;
import application.model.Station;
import application.subSystem.BikeApi;
import application.subSystem.CategoryApi;
import application.subSystem.EBikeApi;
import application.subSystem.StationApi;
import application.subSystem.Interface.IBikeApi;
import application.subSystem.Interface.ICategoryApi;
import application.subSystem.Interface.IEBikeApi;
import application.subSystem.Interface.IStationApi;

public class CreateBikeController {
	IStationApi stationService = StationApi.getInstance(Station.class);
	ICategoryApi categoryService = CategoryApi.getInstance(Category.class);
	IBikeApi bikeService = BikeApi.getInstance(Bike.class);
	IEBikeApi ebikeService = EBikeApi.getInstance(EBike.class);
	HashMap<String, Integer> categoryDict = new HashMap<String, Integer>();
	
	public ArrayList<String> getAllAvailableStationCodes() {
		return stationService.getAllAvailableStationCodes();
	}
	
	public ArrayList<String> getAllCategoryTypes() {
		List<Object[]> result = categoryService.getAllCategoryCodeAndTypes();
		ArrayList<String> types = new ArrayList<String>();
		String type;
		int code;
		for(Object[] row: result) {
			code = Integer.parseInt(row[0].toString());
			type = row[1].toString();
			types.add(type);
			categoryDict.put(type, code);
		}
		return types;
	}
	
	public String validateBikeInformation(HashMap<String, String> params) {
		String name = params.get("name");
		String producer = params.get("producer");
		String weight = params.get("weight");
		String cost = params.get("cost");
		String date = params.get("date");
		String categoryType = params.get("categoryType");
		String stationCode = params.get("stationCode");
		
		if(name == null || name.isEmpty()) return "Name must not be empty.";
		if(producer == null || producer.isEmpty()) return "Manufacturer must not be empty.";
		try {
			float num = Float.parseFloat(weight);
			if(num < 0) throw new Exception();
		} catch(Exception e) {
			return "Weight must be a positive number.";
		}
		try {
			float num = Float.parseFloat(cost);
			if(num < 0) throw new Exception();
		} catch(Exception e) {
			return "Cost must be a positive number.";
		}
		if(date == null || date.isEmpty()) return "Date must not be empty.";
		if(categoryType == null || categoryType.isEmpty()) return "Category Type must not be empty.";
		if(stationCode == null || stationCode.isEmpty()) return "Station Code must not be empty.";
		return null;
	}
	
	public void saveBike(HashMap<String, String> params) {
		if(validateBikeInformation(params) != null) return; 
		
		String name = params.get("name");
		String producer = params.get("producer");
		int weight = Integer.parseInt(params.get("weight"));
		int cost = Integer.parseInt(params.get("cost"));
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(params.get("date"));
		} catch (ParseException e) {
			return;
		}
		String categoryType = params.get("categoryType");
		int stationCode = Integer.parseInt(params.get("stationCode"));
		String license = "L" + (10000 + new Random().nextInt(89999));
		
		Station station = this.stationService.getById(stationCode);
		Category category = this.categoryService.getById(categoryDict.get(categoryType));
		Bike bike = new Bike(station, category, name, weight, producer,
				cost, license, date);
		bike.setRentingStatus(false);
		bikeService.saveOrUpdate(bike);
		if(categoryType.equals("Ebike")) {
			EBike ebike = new EBike(bike, 100, 1000, 0);
			ebikeService.saveOrUpdate(ebike);
		}
		
		//fix after add getInstance
		Set<Bike> bikes = station.getBikes();
		bikes.add(bike);
		station.setBikes(bikes);
	}
	
}