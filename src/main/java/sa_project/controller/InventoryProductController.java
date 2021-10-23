package sa_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sa_project.model.Products;



public class InventoryProductController {
    @FXML private Label dateLabel,usernameLabel,productCode,productType;
    @FXML private Button listRQBtn,createRQBtn,logoutBtn,createRQBtn1,EditBtn,BackBtn,searchBtn;
    @FXML private TextField inputSearch,productName;
    @FXML private TextArea productSpec;
//    private TableView<?> productTable;
//    private TableColumn<?, ?> productCodeTb;
//    private TableColumn<?, ?> productNameTb;
//    private TableColumn<?, ?> productSpecTb;
//    private TableColumn<?, ?> productInTb;
//    private TableColumn<?, ?> productWantTb;
//    private TableColumn<?, ?> productAvailTb;
    @FXML private ImageView icon;


    public void initialize(){}
    @FXML
    private void handleEditPageClick(ActionEvent click){
        if(click.getSource() == EditBtn){
            productName.setDisable(false);
            productSpec.setDisable(false);
            EditBtn.setText("บันทึก");
            Image ic = new Image("image/file-download-solid.png");
            icon.setImage(ic);
        }
    }

//    ObservableList<Products> list = FXCollections.observableArrayList(
//        new Products("CAM001","Camera1245","ppppppppp",2,3,4)
//    );
//
//    public void initialize(){
//        productCodeTb.setCellValueFactory(new PropertyValueFactory<Products, String>("productCode"));
//        productNameTb.setCellValueFactory(new PropertyValueFactory<Products, String>("productName"));
//        productSpecTb.setCellValueFactory(new PropertyValueFactory<Products, String>("productSpec"));
//        productInTb.setCellValueFactory(new PropertyValueFactory<Products, Integer>("quantityIn"));
//        productWantTb.setCellValueFactory(new PropertyValueFactory<Products, Integer>("quantityWant"));
//        productAvailTb.setCellValueFactory(new PropertyValueFactory<Products, Integer>("quantityAvailable"));
//
//        productTable.setItems(list);
//    }
}
