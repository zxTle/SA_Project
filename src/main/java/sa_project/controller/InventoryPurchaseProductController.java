package sa_project.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sa_project.DatabaseConnection;
import sa_project.model.*;
import sa_project.service.prService;
import sa_project.service.productService;
import sa_project.service.reqService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class InventoryPurchaseProductController {
    private String styleHover = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #081F37;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #61BDF6;";
    private String styleNormal = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #61BDF6;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #081F37;";
    @FXML
    private Label dateLabel,usernameLabel,nameLabel
            ,rqNum,orNum,empName,rqDate,rqDue,rqShipDate
            ,prNum,prDate;
    @FXML private DatePicker prDue;
    @FXML private Button rqListBtn, listRQBtn, logoutBtn, purchaseProductBtn,discardBtn,AddBtn;
    @FXML private Pane reqList,purchaseProduct,reqDetails;
    @FXML private MenuButton typeChoice;
    @FXML private TextField orderNum;
    @FXML private TableView<PrForm> prTable;
    @FXML private TableColumn<PrForm,Integer> prOrder;
    @FXML private TableColumn<PrForm,String> prProduct;
    @FXML private TableColumn<PrForm,String> prProductDetail;
    @FXML private TableColumn<PrForm,Integer> rqProductNum;
    @FXML private TableColumn<PrForm,Integer> inventory;
    @FXML private TableColumn<PrForm,Integer> availableForecast;
    private CategoryList caList;
    private productService productService;
    private prService prService;
    private reqService service;
    private NumberFormat prNumFormat = new DecimalFormat("0000");
    private ReqList rqList;
    private ProductsList productsList;
    private ReqForm rqselect;
    private PrList prList;
    private PrForm prForm;
    private Account account;



    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                service = new reqService();
                productService = new productService();
                usernameLabel.setText(account.getUsername());
                nameLabel.setText(account.getName());
                purchaseProductBtn.setStyle(styleHover);
                EventHandler<ActionEvent> handler = this::setSelectLabel;
                prService =  new prService();
                try {
                    prList = prService.getAllPrFrom("SELECT PR_no,PR_date,PR_status,IN_due_date,Emp_id,Emp_name FROM pr_forms NATURAL JOIN employees");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    productsList = productService.getProductsList("SELECT * FROM product_stocks");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                prNum.setText("PR"+prNumFormat.format(prList.toList().size()+1));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", new Locale("en"));
                prDate.setText(LocalDateTime.now().format(formatter));
                empName.setText(account.getName());
                productsList.setMenuItem(typeChoice,handler);
            }

            private void setSelectLabel(ActionEvent event){
                MenuItem source = (MenuItem) event.getSource();
                typeChoice.setText(source.getText());
            }
        });
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm", new Locale("en"));
            dateLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
//    @FXML private void handleCreatePrPage(ActionEvent e) throws SQLException {
//        if(e.getSource() == discardBtn){
//            prDue.getEditor().clear();
//            typeChoice.setText("ชื่อสินค้า");
//            orderNum.clear();
//        }
//        if(e.getSource() == AddBtn){
//            ObservableList<PrForm> createPrList = FXCollections.observableArrayList(prList.toList());
//            prOrder.setCellValueFactory(new PropertyValueFactory<>("itemNum"));
//            prProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
//            prProductDetail.setCellValueFactory(new PropertyValueFactory<>("description"));
//            rqProductNum.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//            inventory.setCellValueFactory(new PropertyValueFactory<>(""));
//            availableForecast.setCellValueFactory(new PropertyValueFactory<>(""));
//            prTable.setItems(createPrList);
//            if(productSelect.getText().equals("สินค้า") && (rqQtyF.getText().equals("") || Integer.parseInt(rqQtyF.getText()) == 0)){
//                alert2("");
//            }
//            else if (productSelect.getText().equals("สินค้า") && (!(rqQtyF.getText().equals(""))  || Integer.parseInt(rqQtyF.getText()) != 0)){
//                alert2("สินค้า");
//            }
//            else if(!(productSelect.getText().equals("สินค้า")) && (rqQtyF.getText().equals("")  || Integer.parseInt(rqQtyF.getText()) == 0)){
//                alert2("จำนวน");
//            }
//            else{
//                String id = productSelect.getText().split(":")[0];
//                String name = productSelect.getText().split(":")[1];
//                Integer qty = Integer.valueOf(rqQtyF.getText());
//                String des = productsList.getDescription(id);
//                ProductDoc product = new ProductDoc(createList.toList().size()+1,id,name,des,qty,"",0,0);
//                createList.addProduct(product);
//                saleTable.getItems().add(product);
//                rqQtyF.clear();
//                productSelect.setText("สินค้า");
//            }
//        }
//        if(e.getSource() == CreateReq){
//            if(OrderNumInput.getText().equals("") && datePick.getEditor().getText().equals("") && createList.toList().size()==0){
//                alert1("");
//            }
//            else if(OrderNumInput.getText().equals("") && !(datePick.getEditor().getText().equals("")) && createList.toList().size()!=0){
//                alert1("ออเดอร์");
//            }
//            else if(!(OrderNumInput.getText().equals("")) && datePick.getEditor().getText().equals("") && createList.toList().size()!=0){
//                alert1("วันที่");
//            }
//            else if(!(OrderNumInput.getText().equals("")) && !(datePick.getEditor().getText().equals("")) && createList.toList().size()==0){
//                alert1("สินค้า");
//            }
//            else if(OrderNumInput.getText().equals("") && datePick.getEditor().getText().equals("") && createList.toList().size()!=0){
//                alert1("ออเดอร์-วันที่");
//            }
//            else if(!(OrderNumInput.getText().equals("")) && datePick.getEditor().getText().equals("") && createList.toList().size()==0){
//                alert1("สินค้า-วันที่");
//            }
//            else if(OrderNumInput.getText().equals("") && !(datePick.getEditor().getText().equals("")) && createList.toList().size()==0){
//                alert1("สินค้า-ออเดอร์");
//            }
//            else {
//                String rqNo = "RQ"+rqNumFormat.format(rqList.toList().size()+1);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", new Locale("en"));
//                String rqDate = LocalDateTime.now().format(formatter);
//                String dueDate = datePick.getValue().toString();
//                ReqForm createReq = new ReqForm(rqNo,rqDate,dueDate,"","Waiting",OrderNumInput.getText(),account.getUsername(), account.getName());
//                service.addRqForm(createReq);
//                service.addRqList(rqNo,createList);
//                ReqList.toFront();
//                showSuccess(rqNo);
//                initialize();
//            }
//        }
//    }
    @FXML private void handleSidemenu(ActionEvent menu) throws IOException {
        if(menu.getSource() == listRQBtn){
            listRQBtn = (Button) menu.getSource();
            Stage stage = (Stage) listRQBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryProducts.fxml"));
            stage.setScene(new Scene(loader.load(),1280,768));
            InventoryProductController controller = loader.getController();
            controller.setAccount(account);

            stage.show();
        }
        else if(menu.getSource() == rqListBtn){
            rqListBtn = (Button) menu.getSource();
            Stage stage = (Stage) rqListBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StockOut.fxml"));
            stage.setScene(new Scene(loader.load(),1280,768));
            InventoryStockOutController controller = loader.getController();
            controller.setAccount(account);

            stage.show();
        }
        else if(menu.getSource() == purchaseProductBtn){
            purchaseProduct.toFront();
            purchaseProductBtn.setStyle(styleHover);
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
