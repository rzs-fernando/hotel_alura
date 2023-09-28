package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Reserva;

public class ReservaDAO {

	private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}

	public void guardarReserva(Reserva reserva) {
		try {
			String sql = "INSERT INTO reservas(fecha_entrada,fecha_salida,valor,forma_de_pago) " + "VALUES(?,?,?,?)";
			try (PreparedStatement statement = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

				statement.setObject(1, reserva.getFechaEntrada());
				statement.setObject(2, reserva.getFechaSalida());
				statement.setString(3, reserva.getValor());
				statement.setString(4, reserva.getFormaPago());
				statement.executeUpdate();

				try (ResultSet rset = statement.getGeneratedKeys()) {
					while (rset.next()) {
						reserva.setId(rset.getInt(1));
					}
				}

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reserva> reservasLista() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id,fecha_entrada,fecha_salida,valor,forma_de_pago FROM reservas";

			try (PreparedStatement stm = con.prepareStatement(sql)) {
				stm.execute();

				datosReserva(reservas, stm);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reserva> busquedaID(String id) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id,fecha_entrada,fecha_salida,valor,forma_de_pago FROM reservas WHERE id= ?";

			try (PreparedStatement stm = con.prepareStatement(sql)) {
				stm.setString(1, id);
				stm.execute();

				datosReserva(reservas, stm);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void actualizar(LocalDate fechaEnt, LocalDate fechaSal, String valor, String formaPago, Integer id) {
		try (PreparedStatement pst = con.prepareStatement(
				"UPDATE reservas SET fecha_entrada=?, fecha_salida=?, valor=?, forma_de_pago=? WHERE id=?")) {
			pst.setObject(1, java.sql.Date.valueOf(fechaEnt));
			pst.setObject(2, java.sql.Date.valueOf(fechaSal));
			pst.setString(3, valor);
			pst.setString(4, formaPago);
			pst.setInt(5, id);
			pst.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void eliminar(Integer id) {
		try {
			Statement state = con.createStatement();
			state.execute("SET FOREIGN_KEY_CHECKS=0");
			PreparedStatement stm = con.prepareStatement("DELETE FROM reservas WHERE id=?"); 
			stm.setInt(1, id);
			stm.execute();
			state.execute("SET FOREIGN_KEY_CHECKS=1");
		}
		 catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void datosReserva(List<Reserva> reservas, PreparedStatement stmt) throws SQLException {
		try (ResultSet rset = stmt.getResultSet()) {
			while (rset.next()) {
				int id = rset.getInt("id");
				LocalDate fechaEnt = rset.getDate("fecha_entrada").toLocalDate().plusDays(0);
				LocalDate fechaSal = rset.getDate("fecha_salida").toLocalDate().plusDays(0);
				String valor = rset.getString("valor");
				String formaPago = rset.getString("forma_de_pago");

				Reserva reservaL = new Reserva(id, fechaEnt, fechaSal, valor, formaPago);
				reservas.add(reservaL);
			}
		}
	}

}
