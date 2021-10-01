package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import model.Cliente;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Cadastro extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCpf;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cadastro frame = new Cadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cadastro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 422, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(10, 11, 48, 14);
		contentPane.add(lblNewLabel);

		txtNome = new JTextField();
		txtNome.setBounds(10, 25, 150, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("CPF:");
		lblNewLabel_1.setBounds(188, 11, 48, 14);
		contentPane.add(lblNewLabel_1);

		txtCpf = new JTextField();
		txtCpf.setBounds(188, 25, 96, 20);
		contentPane.add(txtCpf);
		txtCpf.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Botão salvar
				salvar();
			}
		});
		btnSalvar.setBounds(319, 24, 89, 23);
		contentPane.add(btnSalvar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 135, 398, 128);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "CPF" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		scrollPane.setViewportView(table);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//botao atualizar
				atualizarCliente();
				
			}
		});
		btnAtualizar.setBounds(319, 58, 89, 23);
		contentPane.add(btnAtualizar);
		
		JButton btnDeletar = new JButton("Deletar");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Botao deletar
				deletarCliente();
				
			}
		});
		btnDeletar.setBounds(319, 92, 89, 23);
		contentPane.add(btnDeletar);
		refreshTB();
	}

	// Metodos
	
	public void atualizarCliente() {

		boolean status;
		Cliente cliente = new Cliente();
		Controller banco = new Controller();

		cliente.setId((int) table.getValueAt(table.getSelectedRow(), 0));

		cliente.setNome(txtNome.getText());
		cliente.setCpf(txtCpf.getText());


		status = banco.updateCadastro(cliente);

		if (status) {

			cleanFields();
			refreshTB();
			JOptionPane.showMessageDialog(null, "Cliente " + cliente.getNome() + "  foi atulizado", "Cadastro",
					JOptionPane.INFORMATION_MESSAGE);

		} else {

			JOptionPane.showMessageDialog(null, "ERRO ao tentar atualizar o cliente " + cliente.getNome(), "ERRO",
					JOptionPane.INFORMATION_MESSAGE);

		}

	}
	
	public void deletarCliente() {

		int confirm = JOptionPane.showConfirmDialog(null,
				"Deseja apagar o cliente " + table.getValueAt(table.getSelectedRow(), 1), "Aviso",
				JOptionPane.YES_NO_OPTION);

		if (confirm == 0) { // The ISSUE is here

			Cliente cliente = new Cliente();
			Controller banco = new Controller();

			cliente.setId((int) table.getValueAt(table.getSelectedRow(), 0));

			banco.deletarCadastro(cliente);

			cleanFields();
			refreshTB();

		}

	}
	
	public void salvar() {

		Cliente cliente = new Cliente();
		Controller banco = new Controller();
		Boolean status;

		cliente.setNome(txtNome.getText());
		cliente.setCpf(txtCpf.getText());

		status = banco.addCastro(cliente);

		refreshTB();

		if (status) {

			cleanFields();

			JOptionPane.showMessageDialog(null, "Cliente " + cliente.getNome() + " salvo no banco de dados", "Cadastro",
					JOptionPane.INFORMATION_MESSAGE);

		} else {

			JOptionPane.showMessageDialog(null,
					"ERRO ao tentar salvar o cliente " + cliente.getNome() + " no banco de dados", "Cadastro",
					JOptionPane.INFORMATION_MESSAGE);

		}

	}
	
	public void refreshTB() {
		
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();

		modelo.setNumRows(0);

		Controller banco = new Controller();

		for (Cliente c : banco.buscarClientes("")) {

			modelo.addRow(new Object[] { c.getId(), c.getNome(), c.getCpf()});

		}
		
	}

	public void cleanFields() {

		txtNome.setText("");
		txtCpf.setText("");

	}
}
