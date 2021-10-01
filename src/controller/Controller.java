package controller;

import java.util.List;

import model.Cliente;
import model.ClienteDao;

public class Controller {
	
	public boolean addCastro(Cliente cliente) {
		
		boolean status;
		
		ClienteDao banco = new ClienteDao();
		
		status = banco.create(cliente);
		
		return status;
		
	}
	
	public boolean updateCadastro(Cliente cliente) {
		
		ClienteDao banco = new ClienteDao();
		return banco.update(cliente); 
		
	}
	
	public boolean deletarCadastro(Cliente cliente) {
		
		ClienteDao banco = new ClienteDao();
		return banco.delete(cliente);
		
	}
	
	public List<Cliente> buscarClientes(String search) {
		
		ClienteDao cdao = new ClienteDao();
		return cdao.read(search);	
	}
	

	

}
