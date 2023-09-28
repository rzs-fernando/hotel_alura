package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import factory.ConnectionFactory;

public class Usuario {

	private String nombre;
	private String password;

	public Usuario(String nombre, String password) {
		this.nombre = nombre;
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Validación de Usuarios
	public static boolean userValidation(String nombre, String password) {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = factory.connectDB();
			statement = connection
					.prepareStatement("SELECT * FROM usuarios " + "WHERE nombre=? AND usuario_password =?");
			statement.setString(1, nombre);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			return resultSet.next();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
