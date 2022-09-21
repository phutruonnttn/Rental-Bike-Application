package application.model;

import java.util.HashSet;
import java.util.Set;



public class SubStation {
	private static final long serialVersionUID = 1L;
	
	private int stationCode;
	
	private String stationName;

	private String address;

	private Integer capacity;
	

	private Set<Bike> bikes = new HashSet<Bike>(0);
	
	private int avaiBike;
	
	
	public SubStation(int stationCode, String stationName, String address, Integer capacity, Set<Bike> bikes, int avaiBike) {
		this.setStationCode(stationCode);
		this.setStationName(stationName);
		this.setAddress(address);
		this.setCapacity(capacity);
		this.setBikes(bikes);
		this.setAvaiBike(avaiBike);
	}
	
	public SubStation() {
		// TODO Auto-generated constructor stub
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getStationCode() {
		return stationCode;
	}

	public void setStationCode(int stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Set<Bike> getBikes() {
		return bikes;
	}

	public void setBikes(Set<Bike> bikes) {
		this.bikes = bikes;
	}

	public int getAvaiBike() {
		return avaiBike;
	}

	public void setAvaiBike(int avaiBike) {
		this.avaiBike = avaiBike;
	}
}
