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
import javafx.stage.Stage;
import javafx.util.Duration;
import sa_project.model.Account;
import sa_project.model.Products;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


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
    public Account account;


    public void initialize(){
        Platform.runLater(() -> usernameLabel.setText(account.getUsername()));
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
