package sa_project.service;

import sa_project.DatabaseConnection;
import sa_project.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class productService {

    private ProductsList productsList;
    private CategoryList categoryList;

    public productService() {
        productsList = new ProductsList();
        categoryList = new CategoryList();
    }

    private void readProducts(String query) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        Statement statement = connectDBSales.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()) {
            String proId = queryResult.getString("Product_id");
            String proType = queryResult.getString("Product_type");
            String proName = queryResult.getString("Product_name");
            String description = queryResult.getString("Description");
            Products product = new Products(proId,proType,proName,description);
            productsList.addProduct(product);;
        }
    }

    private void readCategory(String query) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        Statement statement = connectDBSales.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()) {
            String categoryName = queryResult.getString("Ctg_name");
            String initialName = queryResult.getString("Initials");
            Category category = new Category(categoryName, initialName);
            categoryList.addCategory(category);
        }
    }
    public void addProductForm(ProductForm prForm) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        Statement statement = connectDBSales.createStatement();
        String prNo ="'" + prForm.getProductID()+"'" ;
        String prType = "'" +prForm.getProductType()+"'" ;
        String prName = "'" +prForm.getProductName()+"'" ;
        String prDes = "'" +prForm.getDescription()+"'" ;
        String toInsert ="("+prNo+","+prType+","+prName+","+prDes+",0,0,0)";
        String query = "INSERT INTO product_stocks (Product_id, Product_type, Product_name, Description, Qty_onhand, Total_qty_req, Qty_forecast) VALUES " +toInsert;
        statement.executeUpdate(query);
    }
    public void updateProductForm(String query) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBProduct = dbConnect.getConnection();
        Statement statement = connectDBProduct.createStatement();
        statement.executeUpdate(query);
    }

    public void updateQtyStockFormReq(ProductsDocList reqProduct) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection();
        Connection connectDBSales = dbConnect.getConnection();
        for(int i = 0;i<reqProduct.toList().size();i++){
            int qty = reqProduct.toList().get(i).getQuantity();
            String productId = "'"+reqProduct.toList().get(i).getProductId()+"';";
            String query = "UPDATE product_stocks SET Qty_onhand = Qty_onhand-"+qty+" WHERE Product_id = "+productId;
            Statement statement = connectDBSales.createStatement();
            statement.executeUpdate(query);
        }
    }

    public ProductsList getProductsList(String query) throws SQLException {
        readProducts(query);
        return productsList;
    }
    public CategoryList getCategoryList(String query) throws SQLException {
        readCategory(query);
        return categoryList;
    }
}
