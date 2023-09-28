package factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	public DataSource dataSource;
	
	public ConnectionFactory() {
		ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimeZone=true&serverTimeZone=UTC"); // url db
		pooledDataSource.setUser(""); // usuario
		pooledDataSource.setPassword(""); // password
		pooledDataSource.setMaxPoolSize(10);
		
		this.dataSource = pooledDataSource;
	}
	
	public Connection connectDB() {
		
		try {
			return this.dataSource.getConnection();
		} catch(SQLException e) {
			System.out.println("Ha ocurrido un error de conexión");
			throw new RuntimeException(e);
		}
	}
}
