package sa_project.service;

import sa_project.DatabaseConnection;
import sa_project.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class inService {
    private InList inList;
    private ProductsDocList products;

    public inService() {
        inList = new InList();
        products = new ProductsDocList();
    }

    private void readInForm(String query) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBInvent = dbConnect.getConnection();
        Statement statement = connectDBInvent.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()){
            String inNo = queryResult.getString("IN_no");
            String inDate = queryResult.getString("IN_date");
            String empId = queryResult.getString("Emp_id");
            String prNo = queryResult.getString("PR_no");
            String empName = queryResult.getString("Emp_name");
            InForm inForm = new InForm(inNo,inDate,prNo,empId);
            inList.addInList(inForm);
        }
    }

    private void readProductList(String query) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        Statement statement = connectDBSales.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()){
            int itemNo = queryResult.getInt("IN_item_num");
            String pdId = queryResult.getString("Product_id");
            String pdName = queryResult.getString("Product_name");
            String des = queryResult.getString("Description");
            int qty = queryResult.getInt("IN_qty");
            int inventory = queryResult.getInt("Qty_onhand");
            int itemForecast = queryResult.getInt("amount");
            ProductDoc product = new ProductDoc(itemNo,pdId,pdName,des,qty, "",inventory,itemForecast);
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

    public InList getAllInForm(String query) throws SQLException {
        readInForm(query);
        return inList;
    }

//    public


}
