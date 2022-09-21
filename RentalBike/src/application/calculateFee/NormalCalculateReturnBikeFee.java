package application.calculateFee;

import application.calculateFee.Interface.ICalculateReturnBikeFeeModel;

public class NormalCalculateReturnBikeFee implements ICalculateReturnBikeFeeModel {

	@Override
	public int CalculateFee(float coefficient, int baseFee, int minuteFee, int totalMinutes) {
		if (totalMinutes <= 30) {
			return (int) (baseFee*coefficient);
		} else {
			if ((totalMinutes - 30) % 15 == 0) {
				return (int) (baseFee*coefficient + ((totalMinutes - 30)/15)*(minuteFee*15)*coefficient);
			} else {
				return (int) (baseFee*coefficient + ((totalMinutes - 30)/15)*(minuteFee*15)*coefficient + (minuteFee*15)*coefficient);
			}
		}
	}
	
		
}
