package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import dao.HuespedDAO;
import factory.ConnectionFactory;
import model.Huespedes;

public class HuespedController {
	
	private HuespedDAO dao;

	public HuespedController() {
		Connection con = new ConnectionFactory().connectDB();
		this.dao = new HuespedDAO(con);
	}
	
	public void guardar(Huespedes huespedes) {
		this.dao.guardarHuesped(huespedes);
	}
	
	public List<Huespedes> mostrarHuesped(){
		return this.dao.mostrar();
	}
	
	public List<Huespedes> buscarHuesped(String id){
		return this.dao.buscarHuespedId(id);
	}
	
	public void eliminarHuesped(Integer idReserva) {
		this.dao.eliminarH(idReserva);
	}
	
	public void actualizarHuesped(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		this.dao.actualizarHpd(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
	}
	
}
