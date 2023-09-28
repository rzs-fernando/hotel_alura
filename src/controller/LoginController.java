package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Usuario;
import views.Login;
import views.MenuUsuario;

public class LoginController implements ActionListener{
	
	private Login view;

	public LoginController(Login view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String nombre = view.getNombre();
		String passw = view.getPassword();
		
		if(Usuario.userValidation(nombre,passw)){
			MenuUsuario menu = new MenuUsuario();
			menu.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(view, "Usuario o Contraseña no válido");
		}
		
	}
	
	
}
