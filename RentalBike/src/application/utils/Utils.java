package application.utils;

import application.view.ViewController;
import javafx.fxml.FXMLLoader;

public class Utils {
	public static ViewController awakeFromFXML(String path) {
		FXMLLoader loader;
		try {
			loader = new FXMLLoader(Utils.class.getResource(path));
			loader.load();
			return loader.getController();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
