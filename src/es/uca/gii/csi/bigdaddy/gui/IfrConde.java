package es.uca.gii.csi.bigdaddy.gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import es.uca.gii.csi.bigdaddy.data.Conde;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IfrConde extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtDinastia;
	private JTextField txtOrdenDinastico;
	private JButton butGuardar;
	
	private Conde _conde = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IfrConde frame = new IfrConde();
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
	public IfrConde() {
		setClosable(true);
		setResizable(true);
		setTitle("Conde");
		setBounds(100, 100, 650, 400);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("428px"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblNombre = new JLabel("Nombre");
		getContentPane().add(lblNombre, "2, 2, fill, top");
		
		txtNombre = new JTextField();
		getContentPane().add(txtNombre, "2, 4, default, top");
		txtNombre.setColumns(10);
		
		JLabel lblDinasta = new JLabel("Dinast\u00EDa");
		getContentPane().add(lblDinasta, "2, 6, fill, top");
		
		txtDinastia = new JTextField();
		getContentPane().add(txtDinastia, "2, 8, fill, top");
		txtDinastia.setColumns(10);
		
		JLabel lblOrdenDinstico = new JLabel("Orden din\u00E1stico");
		getContentPane().add(lblOrdenDinstico, "2, 10, fill, top");
		
		txtOrdenDinastico = new JTextField();
		getContentPane().add(txtOrdenDinastico, "2, 12, fill, top");
		txtOrdenDinastico.setColumns(10);
		
		butGuardar = new JButton("Guardar");
		butGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String sNombre = txtNombre.getText();
					String sDinastia = txtDinastia.getText();
					int iOrdenDinastico = Integer.parseInt(txtOrdenDinastico.getText());
					
					if (_conde == null) {
						_conde = Conde.Create(sNombre, sDinastia, iOrdenDinastico);
					} else {
						_conde.setNombre(sNombre);
						_conde.setDinastia(sDinastia);
						_conde.setOrdenDinastico(iOrdenDinastico);
						_conde.Update();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Se ha producido un error en la base de datos");
				}
			}
		});
		getContentPane().add(butGuardar, "2, 14, left, default");

	}

}
