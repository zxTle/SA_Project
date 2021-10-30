package sa_project.service;

import sa_project.DatabaseConnection;
import sa_project.model.ProductDoc;
import sa_project.model.ProductsDocList;
import sa_project.model.ReqForm;
import sa_project.model.ReqList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class reqService {
    private ReqList reqForms;
    private ProductsDocList products;
    private String query;
    public reqService() {
        this.query = "";
    }

    private void readReqFormData(String query) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        Statement statement = connectDBSales.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()){
            String rqNumber = queryResult.getString("RQ_no");
            String rqDate = queryResult.getString("RQ_date");
            String rqDueDate = queryResult.getString("RQ_due_date");
            String deliveriedDate = queryResult.getString("Deliveried_date");
            String rqStatus = queryResult.getString("RQ_status");
            String orderNum = queryResult.getString("OR_no");
            String empId = queryResult.getString("Emp_id");
            String empName = queryResult.getString("Emp_name");
            ReqForm req = new ReqForm(rqNumber,rqDate,rqDueDate,deliveriedDate,rqStatus,orderNum,empId,empName);
            reqForms.addReqList(req);
        }
    }
    public ReqList getRqList(String query){
        try {
            reqForms = new ReqList();
            readReqFormData(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reqForms;
    }

    private void readProductList(String query) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        Statement statement = connectDBSales.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()){
            int itemNo = queryResult.getInt("RQ_item_num");
            String prId = queryResult.getString("Product_id");
            String prName = queryResult.getString("Product_name");
            String des = queryResult.getString("Description");
            int qty = queryResult.getInt("RQ_qty");
            ProductDoc product = new ProductDoc(itemNo,prId,prName,des,qty);
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
}
