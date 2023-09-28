package test;

import java.sql.Connection;
import java.sql.SQLException;

import factory.ConnectionFactory;

public class PruebaConexion {
	
	public static void main(String[] args) throws SQLException {
		
		Connection con = new ConnectionFactory().connectDB();
		
		System.out.println("Conexion exitosa");
		
		con.close();
	}

}
