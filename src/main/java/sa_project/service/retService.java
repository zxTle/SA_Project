package sa_project.service;

import sa_project.DatabaseConnection;
import sa_project.model.ProductDoc;
import sa_project.model.ProductsDocList;
import sa_project.model.RtForm;
import sa_project.model.RtFormList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class retService {
    private RtFormList returnList;
    private ProductsDocList products;

    public retService() {
        returnList = new RtFormList();
        products = new ProductsDocList();
    }

    private void readRtForm(String query) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBInvent = dbConnect.getConnection();
        Statement statement = connectDBInvent.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()){
            String rtNum = queryResult.getString("RT_no");
            String inNum = queryResult.getString("IN_no");
            String rtStatus = queryResult.getString("RT_status");
            RtForm retForm = new RtForm(rtNum,inNum,rtStatus);
            returnList.addRtForm(retForm);
        }
    }

    public RtFormList getReturnList(String query) throws SQLException {
        readRtForm(query);
        return returnList;
    }

    private void readProductList(String query) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBInvent = dbConnect.getConnection();
        Statement statement = connectDBInvent.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()){
            int itemNo = queryResult.getInt("RT_item_num");
            String prId = queryResult.getString("Product_id");
            String prName = queryResult.getString("Product_name");
            String des = queryResult.getString("Description");
            int qty = queryResult.getInt("RT_qty");
            String reason = queryResult.getString("RT_reason");
            ProductDoc product = new ProductDoc(itemNo,prId,prName,des,qty, "",0,0,reason);
            products.addProduct(product);
        }
    }
    public ProductsDocList getProductList(String query){
        try {
            products = new ProductsDocList();
            readProductList(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    public void updateRetStatus(RtForm retUpdate) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        String query = "UPDATE ret_forms SET RT_status = 'Received' WHERE RT_no = "+"'"+retUpdate.getRtNum()+"';";
        Statement statement = connectDBSales.createStatement();
        statement.executeUpdate(query);
    }
}
