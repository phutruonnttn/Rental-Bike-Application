package application.calculateFee.Interface;

public interface ICalculateReturnBikeFeeModel {
	int CalculateFee(float coefficient, int baseFee, int minuteFee, int totalMinutes);
}
