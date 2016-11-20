package entities_controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.jdbc.OracleDriver;

public class Conexion
{
  public static Connection getConnection() throws SQLException
  {
    String username = "is102621";
    String password = "jQPXnBbKRt";
    String thinConn = "jdbc:oracle:thin:@orion.javeriana.edu.co:1521:PUJDISOR";
    DriverManager.registerDriver( new OracleDriver() );
    Connection conn = DriverManager.getConnection( thinConn , username, password );
    conn.setAutoCommit( false );
    return conn;
 }
}
