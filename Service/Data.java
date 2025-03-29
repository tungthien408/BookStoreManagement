package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data {
    private static Connection conn = null;

    // Tạo kết nối 
    public static Connection getConnection() throws SQLException{
        
        if(conn == null){
    
             conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/book","root" ,"");
        }
        else{}
        System.out.println("successful");
        return conn;
    }
    
    //Dong ket noi
    public static void closeConnection() throws SQLException{
        if(conn != null){
            conn.close();
        }
    }

    // Thuc hien truy van
    public static ResultSet execQuery(String query) throws SQLException{
        ResultSet rs = null;
        PreparedStatement connPreparedStatement = conn.prepareStatement(query);
        rs = connPreparedStatement.executeQuery();

        return rs;

    }
}
