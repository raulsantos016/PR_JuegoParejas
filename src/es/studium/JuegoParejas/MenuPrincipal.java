package es.studium.JuegoParejas;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuPrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton btnPartidaNueva = new JButton("Partida Nueva");
	JButton btnRanking = new JButton("Top 10");
	JButton btnAyuda = new JButton("Ayuda");

	JLabel lblArbitro = new JLabel();

	public MenuPrincipal() {

		this.setIconImage(new ImageIcon(getClass().getResource("escudo.png")).getImage());

		JPanel mnuPrincipal = new JPanel();

		setTitle("Menú Principal");
		this.setSize(400, 500);
		setLocationRelativeTo(null);

		mnuPrincipal.add(btnPartidaNueva);
		btnPartidaNueva.addActionListener(this);

		mnuPrincipal.add(btnRanking);
		btnRanking.addActionListener(this);

		mnuPrincipal.add(btnAyuda);
		btnAyuda.addActionListener(this);
		
		lblArbitro.setIcon(new ImageIcon("C:\\Users\\Usuario\\Desktop\\PR - Proyectos Eclipse\\AdivinarParejasJava-master\\AdivinarParejasJava-master\\src\\es\\studium\\JuegoParejas\\arbitro.png"));
		mnuPrincipal.add(lblArbitro);
		this.getContentPane().add(mnuPrincipal).setBackground(new Color(16, 144, 16));
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if (btnPartidaNueva == e.getSource()) {
			
			this.setVisible(false);
			new Tablero().setVisible(true);
			
		} else {
			
			if (btnRanking == e.getSource()) {
				new Ranking();
				
			} else {
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
			}
		}
	}

	public static void main(String[] args) {
		new MenuPrincipal();
	}
}
