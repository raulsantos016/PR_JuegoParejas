package es.studium.JuegoParejas;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Ranking extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JTextArea txtTop10;

	public Ranking() {
		this.setIconImage(new ImageIcon(getClass().getResource("escudo.png")).getImage());
		JLabel lblTop10 = new JLabel("TOP 10");
		lblTop10.setFont(new java.awt.Font("Cambria", 1, 30));
		lblTop10.setBounds(140, 10, 200, 40);
		this.add(lblTop10);

		JPanel mejoresPartidas = new JPanel();

		txtTop10 = new JTextArea();
		txtTop10.setBounds(50, 70, 300, 300);
		add(txtTop10);
		setTitle("Mejores partidas");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		this.add(mejoresPartidas);
		this.getContentPane().add(mejoresPartidas).setBackground(new Color(0, 128, 0));
		setVisible(true);

		txtTop10.setEditable(false);
		txtTop10.setOpaque(false);
		txtTop10.setFont(new java.awt.Font("Cambria", 1, 15));

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Connection con = conectar();
		rellenarTextArea(con, txtTop10);
		desconectar(con);
	}

	public Connection conectar() {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/memoria_maestra";
		String user = "adminParejas";
		String password = "Studium2023;";
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException ex) {
			System.out.println("ERROR: La dirección no es válida o el usuario y clave");
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		}
		return con;
	}

	public void rellenarTextArea(Connection con, JTextArea t) {
		String sqlSelect = "SELECT nombreJugador, puntosJugador FROM jugadores ORDER BY puntosJugador DESC LIMIT 10";
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				if (t.getText().length() == 0) {
					t.setText("Nombre: " + rs.getString("nombreJugador") + " | Puntos: " + rs.getString("puntosJugador"));
				} else {
					t.setText(t.getText() + "\n" + "Nombre: " + rs.getString("nombreJugador") + " | Puntos: "+ rs.getString("puntosJugador"));
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {

			System.out.println("ERROR en la sentencia SELECT");
			ex.printStackTrace();
		}
	}

	public void desconectar(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
	}
}
