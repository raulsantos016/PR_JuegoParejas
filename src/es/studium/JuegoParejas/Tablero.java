package es.studium.JuegoParejas;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Tablero extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	JButton btnTotal[] = new JButton[16]; // Botones
	Carta cartasTotal[] = new Carta[16]; // Cartas
	Carta cartaBase = new Carta(0, "escudo.png", 0); // Carta Base
	int par = 0;
	int puntos = 50;

	public JMenuBar mnuPrincipal;
	public JMenu mnuPartidaNueva; 
	public JMenu mnuRanking;
	public JMenu mnuAyuda; 
	public JMenuItem mniPartidaNueva;
	public JMenuItem mniRanking;
	public JMenuItem mniAyuda;

	public Tablero() {
		iniciarVentanaJuego();
	}

	public void iniciarVentanaJuego() { // Interfaz
		
		this.getContentPane().setBackground(new Color(49, 127, 69));
		this.setSize(520, 600);
		this.setTitle("Adivinar Parejas - Equipos de Fútbol");
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource("escudo.png")).getImage());

		mnuPrincipal = new JMenuBar();
		setJMenuBar(mnuPrincipal);

		mnuPartidaNueva = new JMenu("Partida Nueva");
		mniPartidaNueva = new JMenuItem("Comenzar Partida Nueva");
		mnuPrincipal.add(mnuPartidaNueva);
		mnuPartidaNueva.add(mniPartidaNueva);
		mniPartidaNueva.addActionListener(this);

		mnuRanking = new JMenu("Top 10");
		mniRanking = new JMenuItem("Consultar Top 10");
		mnuPrincipal.add(mnuRanking);
		mnuRanking.add(mniRanking);
		mniRanking.addActionListener(this);

		mnuAyuda = new JMenu("Ayuda");
		mniAyuda = new JMenuItem("Consultar Ayuda");
		mnuPrincipal.add(mnuAyuda);
		mnuAyuda.add(mniAyuda);
		mniAyuda.addActionListener(this);

		JTextField txtPuntos;
		txtPuntos = new JTextField("Puntos: 50");
		txtPuntos.setEditable(false);
		this.add(txtPuntos);
		txtPuntos.setBounds(220, 450, 65, 30);

		int contador = 0;

		for (int i = 0; i < 4; i++) { // Columnas
			
			for (int j = 0; j < 4; j++) { // Filas
				
				JButton btnCarta = new JButton("", new ImageIcon(this.getClass().getResource("escudo.png")));
				btnCarta.addActionListener(this);
				btnCarta.setBounds((i + 1) * 85, (j + 1) * 85, 80, 80);
				btnCarta.setName(contador + "");
				btnTotal[contador] = btnCarta;
				contador++;
				this.add(btnCarta);

				// Labels en la interfaz

				JLabel lblTitulo = new JLabel("Adivinar Parejas:");
				lblTitulo.setFont(new java.awt.Font("Comic Sans MS", 1, 22));
				lblTitulo.setBounds(170, 10, 500, 30);
				this.add(lblTitulo);

				JLabel lblSubtitulo = new JLabel("Equipos de Fútbol");
				lblSubtitulo.setFont(new java.awt.Font("Comic Sans MS", 2, 18));
				lblSubtitulo.setBounds(180, 40, 500, 30);
				this.add(lblSubtitulo);
			}
		}
		
		mezclarCartas();
		
	}

	public void mezclarCartas() { // Método para mezclar las cartas
		
		int contadorMezclarCartas = 0;
		
		for (int i = 1; i <= 8; i++) {
			
			Carta carta1 = new Carta(i, i + ".png", contadorMezclarCartas);
			Carta carta2 = new Carta(i, i + ".png", contadorMezclarCartas + 1);

			cartasTotal[contadorMezclarCartas] = carta1;
			contadorMezclarCartas++;
			cartasTotal[contadorMezclarCartas] = carta2;
			contadorMezclarCartas++;
		}

		int rellenar = 0; 
		Carta cartasBase[] = new Carta[16];

		for (int i = 0; i < cartasBase.length; i++) {
			cartasBase[i] = new Carta(0, "escudo.png", -1);
		}

		while (rellenar <= 15) {
			
			int aleatorio = ((int) (Math.random() * 16)); // Número aleatorio del 0 al 15
			
			if (buscarNumero(aleatorio, cartasBase)) {
				
				cartasBase[rellenar] = cartasTotal[aleatorio];
				cartasBase[rellenar].btn = btnTotal[rellenar];
				rellenar++;
			}
		}
		cartasTotal = cartasBase;
	}

	public boolean buscarNumero(int numRandom, Carta c[]) {
		
		int contadorBuscarNumero = 0;
		for (Carta c1 : c) {
			
			if (numRandom == c1.posicion) {
				contadorBuscarNumero++;
			}
		}
		return (contadorBuscarNumero < 1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == mniAyuda) {
			
			String ruta = "C:\\Users\\Usuario\\Desktop\\RAÚL\\PR\\PRACTICA 2 A\\MANUAL DE USUARIO FINAL.pdf";
			try {
				
				File archivo = new File(ruta);
				if (archivo.exists()) {
					
					if (Desktop.isDesktopSupported()) {
						
						Desktop.getDesktop().open(archivo);
					} else {
						
						System.out.println("Error: la función Desktop no funciona en este sistema.");
					}
				} else {
					
					System.out.println("Error: el archivo no existe: " + ruta);
				}
				
			} catch (IOException ex) {
				
				ex.printStackTrace();
			}

		} else if (e.getSource() == mniPartidaNueva) {
			new MenuPrincipal();
			
		} else if (e.getSource() == mniRanking) {
			new Ranking();
		}

		// Jugabilidad

		for (int i = 0; i < btnTotal.length; i++) {

			if (cartasTotal[i].btn == e.getSource() && cartasTotal[i].revelada == false) {
				cartasTotal[i].btn.setIcon(cartasTotal[i].img);

				if (par == 0) { // Carta 1 que muestra el usuario
					cartasTotal[i].revelada = true;
					cartaBase = cartasTotal[i];
					par = 1;

				} else { // Carta 2 que muestra el usuario
					
					par = 0;

					if (cartasTotal[i].valor == cartaBase.valor) { // Si ambas cartas coinciden

						cartasTotal[i].revelada = true; // Se muestran definitivamente ambas cartas
						boolean victoriaFinal = true;

						for (Carta elemento : cartasTotal) {

							if (elemento.revelada == false) { // Si aún no se ha finalizado la partida

								victoriaFinal = false;
								break;

							}
						}

						if (victoriaFinal) { // Si se ha finalizado la partida con victoria

							cartasTotal[i].btn.update(cartasTotal[i].btn.getGraphics());
							Icon iconoGanador = new ImageIcon(getClass().getResource("trofeo.png"));
							JOptionPane.showMessageDialog(this, "Has conseguido " + puntos + " puntos.", "!ENHORABUENA!", JOptionPane.PLAIN_MESSAGE, iconoGanador);
							String nombreJugador = JOptionPane.showInputDialog(this, "Introduce tu nombre para el ranking");
							Connection con = conectar();
							int respuesta = altaJugador(con, nombreJugador, puntos);
							if (respuesta == 0) 
							{
								System.out.println("Alta Correcta");
							} 
							else 
							{
								System.out.println("Error: Alta Incorrecta");
							}
							desconectar(con);

						} else { // Si se acierta una pareja

							cartasTotal[i].btn.update(cartasTotal[i].btn.getGraphics());
							JTextField txtPuntos;
							txtPuntos = new JTextField("Puntos: " + puntos);
							txtPuntos.setEditable(false);
							this.add(txtPuntos);
							txtPuntos.setBounds(220, 450, 65, 30);
						}

					} else {
						try { // Si el usuario falla
							
							cartasTotal[i].btn.update(cartasTotal[i].btn.getGraphics());
							Thread.sleep(500);
							tapar(i);
							puntos = puntos - 1;

							JTextField txtPuntos;
							txtPuntos = new JTextField("Puntos: " + puntos);
							txtPuntos.setEditable(false);
							this.add(txtPuntos);
							txtPuntos.setBounds(220, 450, 65, 30);

							if (puntos == 0) {

								JOptionPane.showMessageDialog(this, "Has perdido todos tus puntos", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
								this.setVisible(false);
								new MenuPrincipal();
							}
						} catch (Exception e2) {
							System.out.println(e2);
						}
					}
				}
			}
		}
	}

	public void tapar(int a) { // Método para tapar las cartas
		cartasTotal[a].btn.setIcon(new ImageIcon(this.getClass().getResource("escudo.png"))); 
		cartasTotal[Integer.valueOf(cartaBase.btn.getName())].revelada = false; 
		cartasTotal[Integer.valueOf(cartaBase.btn.getName())].btn.setIcon(new ImageIcon(this.getClass().getResource("escudo.png")));
	}

	public Connection conectar() { // Conectar la base de datos
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

	public int altaJugador(Connection con, String nombre, int puntos) {
		int respuesta = 0;
		try {
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO jugadores VALUES (null, '" + nombre + "', '" + puntos + "')";
			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		} catch (SQLException ex) {
			System.out.println("Error en la sentencia INSERT");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
	}

	public void desconectar(Connection con) { // Desconectar la base de datos
		try {
			con.close();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		new Tablero().setVisible(true);
	}
}