package middlefeastdes.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    String url ="jdbc:mysql://127.0.0.1/middlefeast";
    String login = "root";
    String pwd = "";
    public static Database db;
    public Connection con;
    private Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url, login, pwd);
            System.out.println("connexion etablie");
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection  getConnection()
    {
        return con;
    }
    public static Database getInstance()
    {if(db==null)
        db=new Database();
        return db;
    }
}
