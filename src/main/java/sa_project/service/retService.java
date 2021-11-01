package sa_project.service;

import sa_project.DatabaseConnection;
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
}
