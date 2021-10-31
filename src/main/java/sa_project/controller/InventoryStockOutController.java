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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sa_project.model.Account;
import sa_project.model.ReqForm;
import sa_project.model.ReqList;
import sa_project.service.reqService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class InventoryStockOutController {

    private String styleHover = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #081F37;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #61BDF6;";
    private String styleNormal = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #61BDF6;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #081F37;";
    @FXML private Label dateLabel,usernameLabel,nameLabel;
    @FXML private Button rqListBtn, listRQBtn, logoutBtn;
    @FXML private TableView<ReqForm> reqTable;
    @FXML private TableColumn<ReqForm,String> reqNo;
    @FXML private TableColumn<ReqForm,String> reqEmp;
    @FXML private TableColumn<ReqForm,String> status;
    @FXML private TableColumn<ReqForm,Date> reqDate;
    @FXML private TableColumn<ReqForm,Date> reqDueDate;
    @FXML private ChoiceBox<String> type;
    @FXML private Pane reqList;
    private reqService service;
    private NumberFormat rqNumFormat = new DecimalFormat("0000");
    private ReqList rqList;
    private Account account;

    public InventoryStockOutController() {
    }

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                service = new reqService();
                rqList = service.getRqList("SELECT RQ_no,RQ_date,RQ_due_date,Deliveried_date,RQ_status,OR_no,Emp_id,Emp_name FROM req_forms NATURAL JOIN employees");
                usernameLabel.setText(account.getUsername());
                nameLabel.setText(account.getName());
                rqListBtn.setStyle(styleHover);
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
    private void showData() {
        ObservableList<ReqForm> reqList = FXCollections.observableArrayList(rqList.toList());
        reqNo.setCellValueFactory(new PropertyValueFactory<>("rqNumber"));
        reqEmp.setCellValueFactory(new PropertyValueFactory<>("empId"));
        reqDate.setCellValueFactory(new PropertyValueFactory<>("rqDate"));
        reqDueDate.setCellValueFactory(new PropertyValueFactory<>("rqDueDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("rqStatus"));
        reqTable.setItems(reqList);
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
        else if(menu.getSource() == rqListBtn){
            rqListBtn.setStyle(styleHover);
            rqListBtn.setOnMouseExited(event -> rqListBtn.setStyle(styleHover));
            reqList.toFront();
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
