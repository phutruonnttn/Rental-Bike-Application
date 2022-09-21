package application.view.manager;

import application.Main;
import application.controller.manager.ManagerHomeController;
import application.view.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ManagerHomeViewController extends ViewController {

    @FXML
    public AnchorPane view;
    
    private ManagerHomeController controller;
    
    public void setController(ManagerHomeController controller) {
    	this.controller = controller;
    }
    
    @FXML
    void didTapStation(ActionEvent event) {

    }

    @FXML
    void didTapBike(ActionEvent event) {
    	Main.shared.pushViewController(controller.getCreateBikeView());
    }

    @FXML
    void didTapExit(ActionEvent event) {
    	Main.shared.popToRootViewController();
    }

	@Override
	public void poplulateData() {
		// TODO Auto-generated method stub
	}

}
