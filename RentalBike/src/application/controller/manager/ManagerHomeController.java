package application.controller.manager;

import application.utils.Define;
import application.utils.Utils;
import application.view.manager.CreateBikeViewController;

public class ManagerHomeController {
	public CreateBikeViewController getCreateBikeView() {
		CreateBikeViewController vc = (CreateBikeViewController)Utils.awakeFromFXML(Define.CREATE_BIKE_VIEW_PATH);
		vc.setController(new CreateBikeController());
		return vc;
	}
}
