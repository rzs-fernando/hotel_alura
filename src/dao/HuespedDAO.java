package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Huespedes;

public class HuespedDAO {

	private Connection con;

	public HuespedDAO(Connection con) {
		this.con = con;
	}

	public void guardarHuesped(Huespedes huespedes) {
		try {
			String sql = "INSERT INTO huespedes(nombre,apellido,fecha_nacimiento,nacionalidad,"
					+ "telefono,id_reserva) VALUES(?,?,?,?,?,?)";
			try (PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stm.setString(1, huespedes.getNombre());
				stm.setString(2, huespedes.getApellido());
				stm.setObject(3, huespedes.getFechaNacimiento());
				stm.setString(4, huespedes.getNacionalidad());
				stm.setString(5, huespedes.getTelefono());
				stm.setInt(6, huespedes.getIdReserva());
				stm.execute();
				try (ResultSet rset = stm.getGeneratedKeys()) {
					while (rset.next()) {
						huespedes.setId(rset.getInt(1));
					}
				}

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huespedes> mostrar() {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes";

			try (PreparedStatement stm = con.prepareStatement(sql)) {
				stm.execute();
				datosHuespedes(huespedes, stm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void datosHuespedes(List<Huespedes> huespedes, PreparedStatement stmt) throws SQLException {
		try (ResultSet rset = stmt.executeQuery()) {
			while (rset.next()) {
				int id = rset.getInt("id");
				String nombre = rset.getString("nombre");
				String apellido = rset.getString("apellido");
				LocalDate fechaNac = rset.getDate("fecha_nacimiento").toLocalDate().plusDays(0);
				String nacionalidad = rset.getString("nacionalidad");
				String telefono = rset.getString("telefono");
				int idReserva = rset.getInt("id_reserva");

				Huespedes huesped = new Huespedes(id, nombre, apellido, fechaNac, nacionalidad, telefono, idReserva);
				huespedes.add(huesped);

			}
		}
	}

	public List<Huespedes> buscarHuespedId(String id) {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE id=?";

			try (PreparedStatement stm = con.prepareStatement(sql)) {
				stm.setString(1, id);
				stm.execute();
				datosHuespedes(huespedes, stm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public void actualizarHpd(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva, Integer id) {
		try (PreparedStatement ppst = con.prepareStatement(
				"UPDATE huespedes SET nombre=?, apellido=?, fecha_nacimiento=?, nacionalidad=?, telefono=?, id_reserva=? WHERE id=?")) {
			ppst.setString(1, nombre);
			ppst.setString(2, apellido);
			ppst.setObject(3, fechaNacimiento);
			ppst.setString(4, nacionalidad);
			ppst.setString(5, telefono);
			ppst.setInt(6, idReserva);
			ppst.setInt(7, id);
			ppst.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void eliminarH(Integer id) {
		try (PreparedStatement prepst = con.prepareStatement("DELETE FROM huespedes WHERE id=?")) {
			prepst.setInt(1, id);
			prepst.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
