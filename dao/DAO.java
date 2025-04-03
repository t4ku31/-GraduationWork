package dao;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DAO {
    // DBの参照先パスを格納
    DataSource ds = null;

    // DBと接続するためのメソッド
    public Connection getConnection() throws Exception {
        if (ds == null) {
            InitialContext ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/team1");
            if(ds == null) {
                throw new Exception("DataSource lookup failed for 'java:/comp/env/jdbc/team1'");
            }
        }
        Connection con = ds.getConnection();
        if (con == null) {
            throw new Exception("DataSource.getConnection() returned null");
        }
        return con;
    }
}
