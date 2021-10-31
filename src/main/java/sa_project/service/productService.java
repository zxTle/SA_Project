package sa_project.service;

import sa_project.DatabaseConnection;
import sa_project.model.Products;
import sa_project.model.ProductsList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class productService {

    private ProductsList productsList;

    public productService() {
        productsList = new ProductsList();
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

    public ProductsList getProductsList(String query) throws SQLException {
        readProducts(query);
        return productsList;
    }
}
