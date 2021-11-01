package sa_project.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
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
import java.util.Locale;

public class InventoryPurchaseProductController {
    private String styleHover = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #081F37;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #61BDF6;";
    private String styleNormal = "-fx-font-family: 'Kanit';\n" + "-fx-font-size: 20px;\n" + "-fx-background-color: #61BDF6;\n" +
            "-fx-background-radius : 0;\n" + "-fx-text-fill : #081F37;";
    @FXML
    private Label dateLabel,usernameLabel,nameLabel,rqNum,orNum,empName,rqDate,rqDue,rqShipDate;
    @FXML private Button rqListBtn, listRQBtn, logoutBtn, purchaseProductBtn;
    @FXML private Pane reqList,purchaseProduct,reqDetails;
    @FXML private MenuButton receiveProductBtn;
    @FXML private MenuItem receiveMenuBtn,claimsMenuBtn;
    private reqService service;
    private NumberFormat rqNumFormat = new DecimalFormat("0000");
    private ReqList rqList;
    private ReqForm rqselect;
    private ProductsDocList prList;
    private Account account;



    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                service = new reqService();
                usernameLabel.setText(account.getUsername());
                nameLabel.setText(account.getName());
                purchaseProductBtn.setStyle(styleHover);
            }
        });
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm", new Locale("en"));
            dateLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    @FXML private void handleSidemenu(ActionEvent menu) throws IOException, SQLException {
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
        else if(menu.getSource() == receiveMenuBtn){
            receiveProductBtn.setText(receiveMenuBtn.getText());
            receiveProductBtn.setTextFill(Paint.valueOf("#61BDF6"));
            Stage stage = (Stage) receiveProductBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryReceiveAndClaim.fxml"));
            stage.setScene(new Scene(loader.load(),1280,768));
            InventoryReceiveAndClaimController controller = loader.getController();
            controller.setAccount(account);
            stage.show();

        }
        else if(menu.getSource() == claimsMenuBtn){
            receiveProductBtn.setText(claimsMenuBtn.getText());
            receiveProductBtn.setTextFill(Paint.valueOf("#61BDF6"));
            Stage stage = (Stage) receiveProductBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryReceiveAndClaim.fxml"));
            stage.setScene(new Scene(loader.load(),1280,768));
            InventoryReceiveAndClaimController controller = loader.getController();
            controller.setAccount(account);
            controller.setPaneClaim();
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
