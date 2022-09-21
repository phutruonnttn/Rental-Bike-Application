package application.calculateFee;

import application.calculateFee.Interface.ICalculateRentBikeFeeModel;
import application.model.Bike;

public class NormalCalculateRentBikeFee implements ICalculateRentBikeFeeModel{
	private Bike bike;
	
	public NormalCalculateRentBikeFee(Bike bike) {
		super();
		this.bike = bike;
	}

	@Override
	public int calculateFirstTransactionAmount() {
		return (int) (this.bike.getCategory().getDepositFee() + this.bike.getCategory().getBaseFee()*this.bike.getCategory().getCoefficient());
	}

	@Override
	public int calculateInitFee() {
		return (int) (this.bike.getCategory().getBaseFee()*this.bike.getCategory().getCoefficient());
	}

}
