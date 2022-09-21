package application;
	
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.utils.Define;
import application.utils.Utils;
import application.view.ViewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Main shared;
	public Stage stage;
	private ArrayList<ViewController> viewControllers = new ArrayList<ViewController>();
	public Map<String, Object> section = new HashMap<String, Object>();
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			shared = this;
			this.section.put("customerCode", 1);
			this.section.put("rentBikeCustomerStatus", false);
			this.section.put("bikeCode", null);
			this.section.put("cardNumber", null);
			this.section.put("billCode", null);
			this.section.put("timeStartRentBike", null);
			ViewController vc = Utils.awakeFromFXML(Define.LOGIN_VIEW_PATH);
			pushViewController(vc);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeScreen(ViewController vc) {
		Scene scene = vc.view.getScene();
		stage.setScene(scene != null ? scene : new Scene(vc.view));
		vc.poplulateData();
	}
	
	public void pushViewController(ViewController vc) {
		viewControllers.add(vc);
		changeScreen(vc);
	}
	
	public void popViewController() {
		popViewControllers(1);
	}
	
	public void popViewControllers(int n) {
		int size = viewControllers.size();
		if(size - n <= 0) {
			return;
		}
		changeScreen(viewControllers.get(size-1-n));
		for(int i = size-1; i > size-1-n; i--) {
			viewControllers.remove(i);
		}
	}
	
	public void popToRootViewController() {
		ViewController vc = viewControllers.get(0);
		viewControllers.clear();
		pushViewController(vc);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
