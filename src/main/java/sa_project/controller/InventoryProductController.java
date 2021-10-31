package sa_project.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sa_project.model.*;
import sa_project.service.reqService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class InventoryProductController {
    private String styleHover = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #081F37;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #61BDF6;";
    private String styleNormal = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #61BDF6;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #081F37;";
    @FXML private Label dateLabel,usernameLabel,nameLabel,productCode,productType,productID;
    @FXML private Button listRQBtn,createRQBtn,logoutBtn,createRQBtn1,EditBtn,BackBtn,searchBtn;
    @FXML private TextField inputSearch,productName;
    @FXML private TextArea productSpec;
    @FXML private TableView<ProductDoc> productTable;
    @FXML private TableColumn<ProductDoc, String> productCodeTb;
    @FXML private TableColumn<ProductDoc, String> productNameTb;
    @FXML private TableColumn<ProductDoc, String> productSpecTb;
    @FXML private TableColumn<ProductDoc, Integer> productInTb;
    @FXML private TableColumn<ProductDoc, Integer> productWantTb;
    @FXML private TableColumn<ProductDoc, Integer> productAvailTb;
    @FXML private ImageView icon;
    @FXML private Pane editProductPage, RqList, productList, createProductPage;
    public Account account;
    private reqService service;
    private ProductsDocList prList;



    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                service = new reqService();
                usernameLabel.setText(account.getUsername());
                nameLabel.setText(account.getName());
                prList = service.getSpecificProductList("SELECT Product_id, Product_name, Product_type, Description, Qty_onhand, Total_qty_req FROM product_stocks");
                listRQBtn.setStyle(styleHover);
                productName.setDisable(true);
                productSpec.setDisable(true);
                showData();
            }
        });
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm", new Locale("en"));
            dateLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
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

    @FXML public void showData(){
        ObservableList<ProductDoc> productObservableList =  FXCollections.observableArrayList(prList.toList());
        productCodeTb.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameTb.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productSpecTb.setCellValueFactory(new PropertyValueFactory<>("description"));
        productInTb.setCellValueFactory(new PropertyValueFactory<>("itemNum"));
        productWantTb.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        productTable.setItems(productObservableList);
    }
    @FXML public void tableRowOnMouseClick(MouseEvent mouseEvent){
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2 && !productTable.getSelectionModel().getSelectedCells().isEmpty()){
                ProductDoc selectProduct = productTable.getSelectionModel().getSelectedItem();
                prList = service.getSpecificProductList("SELECT Product_id, Product_type, Product_name, Description, Qty_onhand, Total_qty_req FROM product_stocks");
                editProductPage.toFront();
                productID.setText(selectProduct.getProductId());
                productName.setText(selectProduct.getProductName());
                productType.setText(selectProduct.getProductType());
                productSpec.setText(selectProduct.getDescription());
            }
        }
    }
    @FXML public void handleBackBtn(ActionEvent event){
        productList.toFront();
    }
    @FXML private void handleSidemenu(ActionEvent menu) throws IOException {
        if(menu.getSource() == listRQBtn){
            listRQBtn.setStyle(styleHover);
            listRQBtn.setOnMouseExited(event -> listRQBtn.setStyle(styleHover));
            productList.toFront();
        }
        else if(menu.getSource() == createRQBtn){
            createRQBtn = (Button) menu.getSource();
            Stage stage = (Stage) createRQBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StockOut.fxml"));
            stage.setScene(new Scene(loader.load(),1280,768));
            InventoryStockOutController controller = loader.getController();
            controller.setAccount(account);

            stage.show();
        }
    }
    @FXML public void handleLogOutBtn(ActionEvent event) throws IOException {
        logoutBtn = (Button) event.getSource();
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        stage.setScene(new Scene(loader.load(), 1280, 768));

        HomeController c = new HomeController();

        stage.show();
    }

    public void setAccount(Account account){
        this.account = account;
    }
}
