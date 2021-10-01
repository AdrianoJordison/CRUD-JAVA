package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

	// C
	public boolean create(Cliente cliente) {

		boolean status;
		String sql = "INSERT INTO client (nome, cpf) VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);

			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getCpf());

			pstm.execute();

			status = true;

		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		} finally {

			try {

				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

		return status;

	}
	// R

	public List<Cliente> read(String search) {

		String sql;

		if (search == "") {

			sql = "SELECT * FROM client";

		} else {

			sql = "SELECT * FROM client WHERE nome LIKE '%" + search + "%'";

		}

		List<Cliente> clientes = new ArrayList<Cliente>();

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rst = null;

		try {

			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			rst = pstm.executeQuery();

			while (rst.next()) {

				Cliente cliente = new Cliente();

				cliente.setId(rst.getInt("id"));
				cliente.setNome(rst.getString("nome"));
				cliente.setCpf(rst.getString("cpf"));

				clientes.add(cliente);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {

				if (rst != null) {
					rst.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

		return clientes;
	}

	// U

	public boolean update(Cliente cliente) {

		boolean status;

		String sql = "UPDATE client SET nome = ? , cpf = ? WHERE id = ?";
		// id, nome, cpf
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);

			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getCpf());

			pstm.setInt(3, cliente.getId());

			pstm.execute();

			status = true;

		} catch (Exception e) {

			status = false;
			e.printStackTrace();

		} finally {

			try {

				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

		return status;

	}

	// D

	public boolean delete(Cliente cliente) {

		boolean status;

		String sql = "DELETE FROM client WHERE id = ?";

		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);

			pstm.setInt(1, cliente.getId());

			pstm.execute();

			status = true;

		} catch (Exception e) {

			status = false;
			e.printStackTrace();

		} finally {

			try {

				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

		return status;

	}

}
