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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sa_project.model.*;
import sa_project.service.reqService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class SalesController {
    private String styleHover = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #081F37;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #61BDF6;";
    private String styleNormal = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #61BDF6;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #081F37;";
    private ReqList rqList;
    private ProductsDocList prList;
    private reqService service;
    private NumberFormat rqNumFormat = new DecimalFormat("0000");
    private ReqForm rqselect;
    @FXML Label usernameLabel, dateLabel,nameLabel,rqNumFm,rqEmpFm,rqDateFm;
    @FXML Button logoutBtn,listRQBtn,createRQMenuBtn,searchBtn;
    @FXML Button CancelReqBtn,Backbtn;
    @FXML Pane reqDetails,reqForm,ReqList;
    @FXML TextField inputSearch;
    @FXML private TableView<ReqForm> reqTable;
    @FXML private TableColumn<ReqForm,String> reqNo;
    @FXML private TableColumn<ReqForm,String> reqEmp;
    @FXML private TableColumn<ReqForm,String> status;
    @FXML private TableColumn<ReqForm,Date> reqDate;
    @FXML private TableColumn<ReqForm,Date> reqDueDate;
    @FXML Label rqNum,orNum,empName,rqDate,rqDue,rqShipDate;

    @FXML private TableView<ProductDoc> saleTable1;
    @FXML private TableColumn<ProductDoc,Integer> itemNum;
    @FXML private TableColumn<ProductDoc,String> product;
    @FXML private TableColumn<ProductDoc,String> description;
    @FXML private TableColumn<ProductDoc,Integer> qty;



    public Account account;
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                service = new reqService();
                rqList = service.getRqList("SELECT RQ_no,RQ_date,RQ_due_date,Deliveried_date,RQ_status,OR_no,Emp_id,Emp_name FROM req_forms NATURAL JOIN employees");
                showData();
                usernameLabel.setText(account.getUsername());
                nameLabel.setText(account.getName());
                listRQBtn.setStyle(styleHover);
            }
        });
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm", new Locale("en"));
            dateLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    private void resetData(){
        rqList = service.getRqList("SELECT RQ_no,RQ_date,RQ_due_date,Deliveried_date,RQ_status,OR_no,Emp_id,Emp_name FROM req_forms NATURAL JOIN employees");
        showData();
    }
    private void showData() {
        ObservableList<ReqForm> reqList = FXCollections.observableArrayList(rqList.toList());
        reqNo.setCellValueFactory(new PropertyValueFactory<>("rqNumber"));
        reqEmp.setCellValueFactory(new PropertyValueFactory<>("empId"));
        reqDate.setCellValueFactory(new PropertyValueFactory<>("rqDate"));
        reqDueDate.setCellValueFactory(new PropertyValueFactory<>("rqDueDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("rqStatus"));
        reqTable.setItems(reqList);
    }
    @FXML private void handleLogOutBtn(ActionEvent event) throws IOException {
        logoutBtn = (Button) event.getSource();
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        stage.setScene(new Scene(loader.load(), 1280, 768));

        HomeController c = new HomeController();

        stage.show();
    }

    @FXML private void handleSidemenu(ActionEvent menu){
        if(menu.getSource() == createRQMenuBtn){
            createRQMenuBtn.setStyle(styleHover);
            createRQMenuBtn.setOnMouseExited(event -> createRQMenuBtn.setStyle(styleHover));
            reqForm.toFront();
            listRQBtn.setStyle(styleNormal);
            listRQBtn.setOnMouseEntered(event -> listRQBtn.setStyle(styleHover));
            listRQBtn.setOnMouseExited(event -> listRQBtn.setStyle(styleNormal));
            rqNumFm.setText("เลขที่ใบเบิก : "+"RQ"+rqNumFormat.format(rqList.toList().size()+1));
            rqEmpFm.setText("ผู้ออกใบเบิก : "+account.getName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy", new Locale("en"));
            rqDateFm.setText("วันที่ออกใบเบิก : "+(LocalDateTime.now().format(formatter)));
        }
        else if(menu.getSource() == listRQBtn){
            listRQBtn.setStyle(styleHover);
            listRQBtn.setOnMouseExited(event -> listRQBtn.setStyle(styleHover));
            ReqList.toFront();
            showData();
            createRQMenuBtn.setStyle(styleNormal);
            createRQMenuBtn.setOnMouseEntered(event -> createRQMenuBtn.setStyle(styleHover));
            createRQMenuBtn.setOnMouseExited(event -> createRQMenuBtn.setStyle(styleNormal));
        }
    }
    @FXML public void tableRowOnMouseClick(MouseEvent mouseEvent)  {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if(mouseEvent.getClickCount() == 2 && !reqTable.getSelectionModel().getSelectedCells().isEmpty()) {
                ReqForm clickedReq = reqTable.getSelectionModel().getSelectedItem();
                rqselect = clickedReq;
                prList = service.getProductList("SELECT RQ_item_num,Product_id,RQ_qty,Product_name,Description FROM req_product_list NATURAL JOIN product_stocks WHERE RQ_no = "+ "'"+clickedReq.getRqNumber() + "'");
                reqDetails.toFront();
                if(rqselect.getRqStatus().equals("Cancelled")) {CancelReqBtn.setDisable(true);}
                else {CancelReqBtn.setDisable(false);}
                rqNum.setText("เลขที่ใบเบิก : "+clickedReq.getRqNumber());
                orNum.setText("เลขออเดอร์  : "+clickedReq.getOrderNum());
                empName.setText("ผู้ออกใบเบิก : "+clickedReq.getEmpName());
                rqDate.setText("วันที่ออกใบเบิก : "+clickedReq.getRqDate());
                rqDue.setText("วันกำหนดส่ง : "+clickedReq.getRqDueDate());
                rqShipDate.setText("วันนำส่งสินค้า : "+clickedReq.getDeliveriedDate());
                ObservableList<ProductDoc> productList = FXCollections.observableArrayList(prList.toList());
                itemNum.setCellValueFactory(new PropertyValueFactory<>("itemNum"));
                product.setCellValueFactory(new PropertyValueFactory<>("productName"));
                description.setCellValueFactory(new PropertyValueFactory<>("description"));
                qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                saleTable1.setItems(productList);
            }
        }
    }
    @FXML private  void handletopBtn(ActionEvent btn) throws SQLException {
        if(btn.getSource() == Backbtn){
            ReqList.toFront();
        }
        else if(btn.getSource() == CancelReqBtn){
            rqList.setStatus(rqselect,"Cancelled");
            service.updateRqForm("UPDATE req_forms SET RQ_Status =" + "'"+rqselect.getRqStatus()+ "'"+
                    " WHERE RQ_no = ",rqselect);
            ReqList.toFront();
            resetData();
        }


    }
    public void setAccount(Account account){
        this.account = account;
    }
}
