package sa_project.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sa_project.model.*;
import sa_project.service.inService;
import sa_project.service.prService;
import sa_project.service.retService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class InventoryReceiveAndClaimController {
    private String styleHover = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #081F37;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #61BDF6;";
    private String styleNormal = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #61BDF6;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #081F37;";
    @FXML private Pane purchaseList,claimList, receivePage;
    @FXML private MenuButton receiveProductBtn;
    @FXML private MenuItem receiveMenuBtn,claimsMenuBtn;
    @FXML private Label usernameLabel,nameLabel,dateLabel, receiver, inDate, prNo, inDueDate, inNo;
    @FXML private Button createRQBtn,listRQBtn,purchaseProductBtn,logoutBtn;

    //PRList
    @FXML private TableView<PrForm> prFormTable;
    @FXML private TableColumn<PrForm,String> prNum;
    @FXML private TableColumn<PrForm,String> prDate;
    @FXML private TableColumn<PrForm,String> prEmp;
    @FXML private TableColumn<PrForm,String> prDuedate;
    @FXML private TableColumn<PrForm,String> prStatus;
    @FXML private TextField searchText;

    //RetList
    @FXML private TableView<RtForm> claimsTable;
    @FXML private TableColumn<RtForm,String> rtNumCol;
    @FXML private TableColumn<RtForm,String> inNumCol;
    @FXML private TableColumn<RtForm,String>rtStatus;
    @FXML private TextField searchClaim;


    private Account account;
    private PrForm prSelect;
    private PrList prFormList;
    private RtFormList retFormList;
    private retService rt_service;
    private prService pr_service;
    private inService in_service;
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pr_service = new prService();
                try {
                    prFormList = pr_service.getAllPrFrom("SELECT PR_no,Emp_id,PR_date,PR_status,IN_due_date,Emp_name FROM pr_forms NATURAL JOIN employees;");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                rt_service = new retService();
                try {
                    retFormList = rt_service.getReturnList("SELECT * FROM ret_forms");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                usernameLabel.setText(account.getUsername());
                nameLabel.setText(account.getName());
                receiveProductBtn.setStyle(styleHover);
                showRetList();
                showPrList();
            }
        });
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm", new Locale("en"));
            dateLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    private void showPrList(){
        ObservableList<PrForm> prList = FXCollections.observableList(prFormList.toList());
        prNum.setCellValueFactory(new PropertyValueFactory<>("prNumber"));
        prDate.setCellValueFactory(new PropertyValueFactory<>("prDate"));
        prEmp.setCellValueFactory(new PropertyValueFactory<>("empId"));
        prDuedate.setCellValueFactory(new PropertyValueFactory<>("prDueDate"));
        prStatus.setCellValueFactory(new PropertyValueFactory<>("prStatus"));
        prFormTable.setItems(prList);
        FilteredList<PrForm> searchFilter = new FilteredList<>(prList, b -> true);
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFilter.setPredicate(pr -> {
                if(newValue == null || newValue.isEmpty()) return true;
                if(pr.getPrNumber().indexOf(newValue) != -1 || pr.getPrStatus().indexOf(newValue) != -1
                        || pr.getEmpName().indexOf(newValue) != -1 || pr.getEmpId().indexOf(newValue) != -1
                        || pr.getPrNumber().toLowerCase().indexOf(newValue) != -1 || pr.getPrStatus().toLowerCase().indexOf(newValue) != -1
                        || pr.getEmpName().toLowerCase().indexOf(newValue) != -1 || pr.getEmpId().toLowerCase().indexOf(newValue) != -1) return true;
                else return false;
            });
        });
        SortedList<PrForm> sortedPr = new SortedList<>(searchFilter);
        sortedPr.comparatorProperty().bind(prFormTable.comparatorProperty());
        prFormTable.setItems(sortedPr);
    }

    private void showRetList(){
        ObservableList<RtForm> rtList = FXCollections.observableList(retFormList.toList());
        rtNumCol.setCellValueFactory(new PropertyValueFactory<>("rtNum"));
        inNumCol.setCellValueFactory(new PropertyValueFactory<>("inNum"));
        rtStatus.setCellValueFactory(new PropertyValueFactory<>("rtStatus"));
        claimsTable.setItems(rtList);
        FilteredList<RtForm> searchFilter = new FilteredList<>(rtList, b -> true);
        searchClaim.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFilter.setPredicate(rt -> {
                if(newValue == null || newValue.isEmpty()) return true;
                if(rt.getRtNum().indexOf(newValue) != -1 || rt.getRtStatus().indexOf(newValue) != -1
                        || rt.getInNum().indexOf(newValue) != -1 || rt.getInNum().toLowerCase().indexOf(newValue) != -1
                        || rt.getRtNum().toLowerCase().indexOf(newValue) != -1 || rt.getRtStatus().toLowerCase().indexOf(newValue) != -1) return true;
                else return false;
            });
        });
        SortedList<RtForm> sortedRt = new SortedList<>(searchFilter);
        sortedRt.comparatorProperty().bind(claimsTable.comparatorProperty());
        claimsTable.setItems(sortedRt);
    }

    @FXML public void tableRowOnMouseClickPr(MouseEvent mouseEvent)  {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if(mouseEvent.getClickCount() == 2 && !prFormTable.getSelectionModel().getSelectedCells().isEmpty()) {
                PrForm clickedPr = prFormTable.getSelectionModel().getSelectedItem();
                prSelect = clickedPr;
                prList = service.getProductList("SELECT RQ_item_num,Product_id,RQ_qty,Product_name,Description,Qty_onhand,(Qty_onhand-Rq_qty) AS amount FROM req_product_list NATURAL JOIN product_stocks WHERE RQ_no = "+ "'"+clickedReq.getRqNumber() + "'");
                receivePage.toFront();
                int num = in_service.getAllInForm("SELECT count(IN_no)+1 AS inNum FROM in_forms");
                inNo.setText(clickedPr.getRqNumber());
                prNo.setText(clickedPr.getPrNumber());
                receiver.setText(clickedPr.getEmpId()+" , "+clickedPr.getEmpName());
                rqDate.setText(clickedReq.getRqDate());
                rqDue.setText(clickedReq.getRqDueDate());
                rqShipDate.setText(clickedReq.getDeliveriedDate());
                ObservableList<ProductDoc> productList = FXCollections.observableArrayList(prList.toList());
                itemNum.setCellValueFactory(new PropertyValueFactory<>("itemNum"));
                product.setCellValueFactory(new PropertyValueFactory<>("productName"));
                description.setCellValueFactory(new PropertyValueFactory<>("description"));
                qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                inventory.setCellValueFactory(new PropertyValueFactory<>("onHand"));
                productLeft.setCellValueFactory(new PropertyValueFactory<>("itemNumForecast"));
                reqTableDetail.setItems(productList);
            }
        }
    }

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
        else if(menu.getSource() == createRQBtn){
            createRQBtn = (Button) menu.getSource();
            Stage stage = (Stage) createRQBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StockOut.fxml"));
            stage.setScene(new Scene(loader.load(),1280,768));
            InventoryStockOutController controller = loader.getController();
            controller.setAccount(account);

            stage.show();
        }
        else if(menu.getSource() == purchaseProductBtn){
            purchaseProductBtn = (Button) menu.getSource();
            Stage stage = (Stage) purchaseProductBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryPurchaseProduct.fxml"));
            stage.setScene(new Scene(loader.load(),1280,768));
            InventoryPurchaseProductController controller = loader.getController();
            controller.setAccount(account);

            stage.show();
        }
        else if(menu.getSource() == receiveMenuBtn){
            receiveProductBtn.setText(receiveMenuBtn.getText());
            purchaseList.toFront();

        }
        else if(menu.getSource() == claimsMenuBtn){
            receiveProductBtn.setText(claimsMenuBtn.getText());
            claimList.toFront();

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
    public void setPaneClaim() {
        claimList.toFront();
    }
}
