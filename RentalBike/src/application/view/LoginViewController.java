package application.view;

import application.Main;
import application.controller.manager.ManagerHomeController;
import application.utils.Define;
import application.utils.Utils;
import application.view.customer.StationListViewController;
import application.view.manager.ManagerHomeViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class LoginViewController extends ViewController {
	
    @FXML
    public AnchorPane view;

    @FXML
    void didTapLoginAsCustomer(ActionEvent event) {
    	StationListViewController vc = (StationListViewController)Utils.awakeFromFXML(Define.STATIONLIST_VIEW_PATH);
    	//vc.poplulateData();
    	Main.shared.pushViewController(vc);
    }

    @FXML
    void didTapLoginAsManager(ActionEvent event) {
    	ManagerHomeViewController vc = (ManagerHomeViewController)Utils.awakeFromFXML(Define.MANAGER_HOME_VIEW_PATH);
    	vc.setController(new ManagerHomeController());
    	Main.shared.pushViewController(vc);
    }

	@Override
	public void poplulateData() {
		// TODO Auto-generated method stub
	}
}
