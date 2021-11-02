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
    private String prToIn;
    private InForm inFormResult;

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
            InForm inForm = new InForm(inNo,prNo,inDate,empId,empName);
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
            ProductDoc product = new ProductDoc(itemNo,pdId,pdName,des,qty, "",inventory,itemForecast,"");
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

    public InForm getInNoFromPrNo(String inputPrNo) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBInvent = dbConnect.getConnection();
        Statement statement = connectDBInvent.createStatement();
        ResultSet queryResult = statement.executeQuery("SELECT IN_no FROM in_forms WHERE PR_no = " + inputPrNo + ";");
        while (queryResult.next()){
            String inNo = queryResult.getString("IN_no");
            String inDate = queryResult.getString("IN_date");
            String empId = queryResult.getString("Emp_id");
            String prNo = queryResult.getString("PR_no");
            String empName = queryResult.getString("Emp_name");
            inFormResult = new InForm(inNo,prNo,inDate,empId,empName);
//            inList.addInList(inForm);
        }

        return inFormResult;
    }

    public void addInForm(InForm inForm) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        Statement statement = connectDBSales.createStatement();
        String inNo ="'" + inForm.getInNum()+"'" ;
        String inDate = "'" +inForm.getInDate()+"'" ;
        String prNo = "'" +inForm.getPrNum()+"'" ;
        String empId = "'" +inForm.getEmpId()+"'" ;
        String toInsert ="("+inNo+","+inDate+","+prNo+","+empId+")";
        String query = "INSERT INTO in_forms (IN_no, IN_date, PR_no, Emp_id) VALUES " +toInsert;
        statement.executeUpdate(query);
    }

    public void addInList(String inNo,ProductsDocList products) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        Statement statement = connectDBSales.createStatement();
        String query = "INSERT INTO in_product_list (IN_item_num, RQ_no, Product_id, RQ_qty) VALUES "+ products.toInsert(inNo);
        statement.executeUpdate(query);
    }



}
