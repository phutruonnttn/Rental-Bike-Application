package unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import application.calculateFee.NormalCalculateReturnBikeFee;
import application.calculateFee.Interface.ICalculateReturnBikeFeeModel;

public class CalculateReturnBikeFeeTest {
	ICalculateReturnBikeFeeModel calculateFeeService = new NormalCalculateReturnBikeFee();
	
	@Test
	public void testCalculateFee1() {
		//total minute <= 30, BikeType is Bike
		int result = calculateFeeService.CalculateFee(1, 10000, 200, 4);
		assertEquals(result, 10000);
	}
	
	@Test
	public void testCalculateFee2() {
		//total minute <= 30, BikeType is not Bike
		int result = calculateFeeService.CalculateFee(1.5f, 10000, 200, 4);
		assertEquals(result, 15000);
	}
	
	@Test
	public void testCalculateFee3() {
		//total minute > 30,(Total Minutes - 30) % 15 = 0, BikeType is Bike
		int result = calculateFeeService.CalculateFee(1, 10000, 200, 45);
		assertEquals(result, 13000);
	}
	
	@Test
	public void testCalculateFee4() {
		//total minute > 30,(Total Minutes - 30) % 15 = 0, BikeType is not Bike
		int result = calculateFeeService.CalculateFee(1.5f, 10000, 200, 45);
		assertEquals(result, 19500);
	}
	
	@Test
	public void testCalculateFee5() {
		//total minute > 30,(Total Minutes - 30) % 15 != 0, BikeType is Bike
		int result = calculateFeeService.CalculateFee(1, 10000, 200, 70);
		assertEquals(result, 19000);
	}
	
	@Test
	public void testCalculateFee6() {
		//total minute > 30,(Total Minutes - 30) % 15 != 0, BikeType is not Bike
		int result = calculateFeeService.CalculateFee(1.5f, 10000, 200, 70);
		assertEquals(result, 28500);
	}
}
