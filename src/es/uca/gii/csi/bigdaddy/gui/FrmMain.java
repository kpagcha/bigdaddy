package es.uca.gii.csi.bigdaddy.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain window = new FrmMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public FrmMain() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Gesti\u00F3n de personal del Castillo del Conde Dr\u00E1cula");
		frame.setBounds(100, 100, 650, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		frame.setJMenuBar(menuBar);
		
		JMenu mitNuevo = new JMenu("Nuevo");
		menuBar.add(mitNuevo);
		
		JMenuItem mitNuevoConde = new JMenuItem("Conde");
		mitNuevoConde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IfrConde ifrConde = new IfrConde(null);
				ifrConde.setBounds(20, 20, 470, 250);
				frame.getContentPane().add(ifrConde);
				ifrConde.setVisible(true);
			}
		});
		mitNuevo.add(mitNuevoConde);
		
		JMenu mitBuscar = new JMenu("Buscar");
		menuBar.add(mitBuscar);
		
		JMenuItem mitBuscarConde = new JMenuItem("Conde");
		mitBuscarConde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IfrCondes ifrCondes = new IfrCondes(frame);
				ifrCondes.setBounds(20, 20, 470, 210);
				frame.getContentPane().add(ifrCondes, 0);
				ifrCondes.setVisible(true);
			}
		});
		mitBuscar.add(mitBuscarConde);
		frame.getContentPane().setLayout(null);
	}

}
