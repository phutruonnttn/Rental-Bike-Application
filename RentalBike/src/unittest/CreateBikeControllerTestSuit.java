 package unittest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;

import application.controller.manager.CreateBikeController;
import application.model.Station;
import application.subSystem.StationApi;
import application.subSystem.Interface.IStationApi;

public class CreateBikeControllerTestSuit {
	CreateBikeController controller = new CreateBikeController();
	IStationApi stationService = StationApi.getInstance(Station.class);
	
	/*
	 * Expect: controller.getAllAvailableStationCodes should return all station that is not full
	 * */
	@Test
	public void testGetAllAvailableStationCodes() {
		ArrayList<String> codes = controller.getAllAvailableStationCodes();
		if(codes.size() == 0) {
			assertTrue(true);
		} else {
			for(String code: codes) {
				Station station = stationService.getById(Integer.parseInt(code));
				assertTrue(station.getEmptySlot() > 0);
			}
		}
	}
	
	/*
	 * Expect: controller.getAllCategoryType should return all type of bikes (Bike, Ebike, Twin Bike)
	 */
	@Test
	public void testGetAllCategoryType() {
		ArrayList<String> expectedCategoryTypes = new ArrayList<String>(Arrays.asList("Bike", "Twin bike", "Ebike"));
		ArrayList<String> categoryTypes = controller.getAllCategoryTypes();
		// sort
		Collections.sort(expectedCategoryTypes);
		Collections.sort(categoryTypes);
		// compare
		assertTrue(categoryTypes.equals(expectedCategoryTypes));
	}
	
	/*
	 * Expect: controller.validateBikeInformation should validate correctly bike information
	 */
	@Test
	public void testValidateBikeInformation() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", "new bike");
		params.put("producer", "new manufacturer");
		params.put("weight", "23");
		params.put("cost", "23.4");
		params.put("date", "2021-12-21");
		params.put("categoryType", "Bike");
		params.put("stationCode", "1");
		
		// should be valid
		assertNull(controller.validateBikeInformation(params));
		
		// should be invalid
		params.put("name", "");
		assertNotNull(controller.validateBikeInformation(params));
		
		// should be invalid
		params.put("name", "new bike");
		params.put("producer", "");
		assertNotNull(controller.validateBikeInformation(params));
		
		// should be invalid
		params.put("producer", "producer name");
		params.put("weight", "weight");
		assertNotNull(controller.validateBikeInformation(params));
		
		// should be invalid
		params.put("weight", "2");
		params.put("cost", "cost");
		assertNotNull(controller.validateBikeInformation(params));
	}
}
