package application.view.customer;

import java.util.List;
import java.util.Set;

import application.Main;
import application.controller.customer.BikeListController;
import application.controller.customer.ReturnBikeController;
import application.model.Bike;
import application.model.Bill;
import application.model.SubBike;
import application.subSystem.BillApi;
import application.utils.Define;
import application.utils.Utils;
import application.view.ConfirmBox;
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




public class BikeListViewController extends ViewController{
	
	@FXML
	public AnchorPane view;
	
	@FXML
    private Button btnReturnBike;

    @FXML
    public Label lblStr;
    
    @FXML
    public TextField textF;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Label rentBikeStatus;
    
    @FXML
    private TableView<SubBike> table;
    
    @FXML
	private TableColumn<SubBike,Integer> index;
    
    @FXML
	private TableColumn<SubBike,Integer> bikeId;
    
    @FXML
	private TableColumn<SubBike,String> bikeName;
    
    @FXML
	private TableColumn<SubBike,String> category;
    
    @FXML
	private TableColumn<SubBike,String> licensePlate;
    
	private BikeListController controller = new BikeListController();
    
	public BikeListViewController() {
	}

	@FXML
    void diTapExitBtn(ActionEvent event) {
    	Main.shared.popViewController();
    }
	
	@FXML
    void diTapSearchBtn(ActionEvent event) {
    	table.getItems().removeAll(table.getItems());
    	String sItem = textF.getText();
    	System.out.print(sItem);
    	
    	table.getItems().addAll(controller.searchBike(sItem));
    	
    	index.setCellValueFactory(new PropertyValueFactory<SubBike,Integer>("index"));
    	bikeId.setCellValueFactory(new PropertyValueFactory<SubBike,Integer>("bikeCode"));
		bikeName.setCellValueFactory(new PropertyValueFactory<SubBike,String>("bikeName"));
		category.setCellValueFactory(new PropertyValueFactory<SubBike,String>("category"));
		licensePlate.setCellValueFactory(new PropertyValueFactory<SubBike,String>("licensePlate"));
    	System.out.print("search button");
    }
    
    @FXML
    void diTapResetBtn(ActionEvent event) {
    	table.getItems().removeAll(table.getItems());
    	initialize1();
    }
    
	
	public void initialize1() {
		// Get bike data and display on View Table
		table.getItems().clear();
		table.getItems().addAll(controller.getBikeList());
		
		index.setCellValueFactory(new PropertyValueFactory<SubBike,Integer>("index"));
    	bikeId.setCellValueFactory(new PropertyValueFactory<SubBike,Integer>("bikeCode"));
		bikeName.setCellValueFactory(new PropertyValueFactory<SubBike,String>("bikeName"));
		category.setCellValueFactory(new PropertyValueFactory<SubBike,String>("category"));
		licensePlate.setCellValueFactory(new PropertyValueFactory<SubBike,String>("licensePlate"));
		// event to pop up confirmation
		table.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
            	ConfirmBox cfBox = new ConfirmBox();
            	if(table.getSelectionModel().getSelectedItem().getRentingStatus()) {
            		if(cfBox.displayWarning("Warning box", "Cant rent more than one bike at a time !")) {
                		return;
                	}
            	}
            	else {
            		if(cfBox.displayConfirm("Confirming box", "Confirmation to rent this " + table.getSelectionModel().getSelectedItem().getLicensePlate() + " bike") == true) {
            			// Generate Bill after confirmation here for Tien
            			BillViewController billView = (BillViewController)Utils.awakeFromFXML(Define.BILL_VIEW_PATH);
            			billView.setCustomerCode((int)Main.shared.section.get("customerCode"));
            			billView.setBikeCode(table.getSelectionModel().getSelectedItem().getBikeCode());
            			Main.shared.section.replace("bikeCode", table.getSelectionModel().getSelectedItem().getBikeCode());
            			Main.shared.pushViewController(billView);
            		}
            	}
            }
        });
	}
	
	@FXML
	void didtapReturnBike(ActionEvent event) {//Truong them de sang phan return
		ReturnBikeViewController vc = (ReturnBikeViewController)Utils.awakeFromFXML(Define.RETURN_BIKE_VIEW_PATH);
    	vc.setController(new ReturnBikeController());
    	Main.shared.pushViewController(vc);
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
		
		// transform to Object SubBike
		controller.loadData();
		initialize1();
	}

	public void setBikes(Set<Bike> bikes,String stationName) {
		controller.setBikes(bikes);
		lblStr.setText("Bike list of " + stationName);
	}
	
}
