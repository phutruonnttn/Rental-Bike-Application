package application.model;


public class SubBike {
	private int index;
	private int bikeCode;
	private String category;
	private String bikeName;
	private String licensePlate;
	private Boolean rentingStatus;
	public SubBike(int index, int bikeCode, String category, String bikeName, String licensePlate, Boolean rentingStatus) {
		this.index = index;
		this.bikeCode = bikeCode;
		this.category = category;
		this.bikeName = bikeName;
		this.licensePlate = licensePlate;
		this.rentingStatus = rentingStatus;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getBikeCode() {
		return bikeCode;
	}
	public void setBikeCode(int bikeCode) {
		this.bikeCode = bikeCode;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBikeName() {
		return bikeName;
	}
	public void setBikeName(String bikeName) {
		this.bikeName = bikeName;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public Boolean getRentingStatus() {
		return rentingStatus;
	}
	public void setRentingStatus(Boolean rentingStatus) {
		this.rentingStatus = rentingStatus;
	}

}
