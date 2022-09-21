package application.view.customer;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.controller.customer.ReturnBikeController;
import application.controller.customer.StationListController;
import application.model.Bill;
import application.model.Station;
import application.model.SubStation;
import application.subSystem.BillApi;
import application.subSystem.StationApi;
import application.subSystem.Interface.IStationApi;
import application.utils.Define;
import application.utils.Utils;
import application.view.ViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class StationListViewController extends ViewController{
	
	@FXML
	public AnchorPane view;
	
	@FXML
    private Button btnReturnBike;

    @FXML
    public String lblStr;
    
    @FXML
    public TextField textF;

    @FXML
    private Button btn;
    
    @FXML
    private Label rentBikeStatus;
    
    @FXML
    private TableView<SubStation> table;
    
    @FXML
	private TableColumn<Station,Integer> stationId;
    
    @FXML
	private TableColumn<Station,String> stationName;
    
    @FXML
	private TableColumn<Station,String> stationAdd;
    
    @FXML
	private TableColumn<Station,Integer> stationCap;
    
    @FXML
	private TableColumn<Station,Integer> avaiBike;
    
    private StationListController controller = new StationListController();

    @FXML
    void diTapExitBtn(ActionEvent event) {
    	Main.shared.popViewController();
    }
    
    @FXML
    void diTapSearchBtn(ActionEvent event) {
    	table.getItems().removeAll(table.getItems());
    	String sItem = textF.getText();
    	System.out.print(sItem);
    	
    	table.getItems().addAll(controller.searchStation(sItem));
    	
		stationId.setCellValueFactory(new PropertyValueFactory<Station,Integer>("stationCode"));
		stationName.setCellValueFactory(new PropertyValueFactory<Station,String>("stationName"));
		stationAdd.setCellValueFactory(new PropertyValueFactory<Station,String>("address"));
		stationCap.setCellValueFactory(new PropertyValueFactory<Station,Integer>("capacity"));
		avaiBike.setCellValueFactory(new PropertyValueFactory<Station,Integer>("avaiBike"));
    	System.out.print("search button");
    }
    
    @FXML
    void diTapResetBtn(ActionEvent event) {
    	table.getItems().removeAll(table.getItems());
    	initialize1();
    }
    
    
	@Override
	public void poplulateData() {
		
		//Truong them: neu dang thue xe thi moi hien nut check de tra xe
		btnReturnBike.setVisible(false);
		List<Object[]> result = BillApi.getInstance(Bill.class).getBikeCodeAndBillCode(Integer.parseInt(Main.shared.section.get("customerCode").toString()));
		if (result.size() > 0) {
			btnReturnBike.setVisible(true);
			Main.shared.section.replace("rentBikeCustomerStatus", true);
			this.rentBikeStatus.setText("Renting");
		} else {
			this.rentBikeStatus.setText("No bike rented");
		}
		
		
		//Load Station api
	 	controller.loadData();
		initialize1();
	}
	
	@FXML
	void didtapReturnBike(ActionEvent event) {//Truong them de sang phan return
		ReturnBikeViewController vc = (ReturnBikeViewController)Utils.awakeFromFXML(Define.RETURN_BIKE_VIEW_PATH);
    	vc.setController(new ReturnBikeController());
    	Main.shared.pushViewController(vc);
	}

	public void initialize1() {
	//Displaying station list on table view
		table.getItems().clear();
		table.getItems().addAll(controller.getSubStation());
		stationId.setCellValueFactory(new PropertyValueFactory<Station,Integer>("stationCode"));
		stationName.setCellValueFactory(new PropertyValueFactory<Station,String>("stationName"));
		stationAdd.setCellValueFactory(new PropertyValueFactory<Station,String>("address"));
		stationCap.setCellValueFactory(new PropertyValueFactory<Station,Integer>("capacity"));
		avaiBike.setCellValueFactory(new PropertyValueFactory<Station,Integer>("avaiBike"));
		// mouse-click event handling
		 table.setOnMouseClicked(new EventHandler<MouseEvent>(){
		    @Override
		    public void handle(MouseEvent event) {
			   System.out.print("oke");
			   BikeListViewController viewController = (BikeListViewController)Utils.awakeFromFXML(Define.BIKELIST_VIEW_PATH);
			   viewController.setBikes(table.getSelectionModel().getSelectedItem().getBikes(),table.getSelectionModel().getSelectedItem().getStationName());
	//		   viewController.poplulateData();
	//		   viewController.initialize1();
			   Main.shared.pushViewController(viewController);     
			}	                   
		});
	}
}
