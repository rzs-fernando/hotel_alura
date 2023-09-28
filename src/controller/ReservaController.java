package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import model.Reserva;

public class ReservaController {
	
	private ReservaDAO reservaDAO;

	public ReservaController() {
		Connection connection = new ConnectionFactory().connectDB();
		this.reservaDAO = new ReservaDAO(connection);
	}
	
	public void guardar(Reserva reserva) {
		this.reservaDAO.guardarReserva(reserva);
	}
	
	public List<Reserva> mostrar() {
		return this.reservaDAO.reservasLista();
	}
	
	public List<Reserva> buscarId(String id) {
		return this.reservaDAO.busquedaID(id);
	}
	
	public void actualizarReserva(LocalDate fechaEnt, LocalDate fechaSal, String valor, String formaPago, Integer id) {
		this.reservaDAO.actualizar(fechaEnt, fechaSal, valor, formaPago, id);
	}
	
	public void eliminarReserva(Integer id) {
		this.reservaDAO.eliminar(id);
	}
	
}
