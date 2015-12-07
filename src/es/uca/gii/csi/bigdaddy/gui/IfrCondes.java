package es.uca.gii.csi.bigdaddy.gui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi.bigdaddy.data.Conde;
import es.uca.gii.csi.bigdaddy.data.EstatusSocial;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class IfrCondes extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtDinastia;
	private JTextField txtOrdenDinastico;
	private JTable table;
	private Container pnlParent;
	private JComboBox<EstatusSocial> cmbEstatusSocial;

	/**
	 * Create the frame.
	 */
	public IfrCondes(Container frame) {
		pnlParent = frame;
		
		setResizable(true);
		setClosable(true);
		setTitle("Condes");
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNombre = new JLabel("Nombre");
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblDinastia = new JLabel("Dinast\u00EDa");
		panel.add(lblDinastia);
		
		txtDinastia = new JTextField();
		txtDinastia.setText("");
		panel.add(txtDinastia);
		txtDinastia.setColumns(10);
		
		JLabel lblOrdenDinastico = new JLabel("Orden din\u00E1stico");
		panel.add(lblOrdenDinastico);
		
		txtOrdenDinastico = new JTextField();
		panel.add(txtOrdenDinastico);
		txtOrdenDinastico.setColumns(10);
		
		JButton butBuscar = new JButton("Buscar");
		butBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String sNombre = txtNombre.getText();
					String sDinastia = txtDinastia.getText();
					String sOrdenDinastico = txtOrdenDinastico.getText();
					Object oSelection = cmbEstatusSocial.getModel().getSelectedItem();
					String sEstatusSocial = oSelection == null ? null :  oSelection.toString();
					
					if (sNombre.isEmpty())
						sNombre = null;
					if (sDinastia.isEmpty())
						sDinastia = null;
					Integer iOrdenDinastico = sOrdenDinastico.isEmpty() ? null : Integer.parseInt(sOrdenDinastico);
					
					table.setModel(new CondesTableModel(Conde.Select(sNombre, sDinastia, iOrdenDinastico, sEstatusSocial)));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error en la búsqueda");
				}
			}
		});
		
		JLabel lblEstatusSocial = new JLabel("Estatus social");
		panel.add(lblEstatusSocial);
		
		cmbEstatusSocial = new JComboBox<EstatusSocial>();
		cmbEstatusSocial.setEditable(true);
		try {
			cmbEstatusSocial.setModel(new EstatusSocialListModel(EstatusSocial.Select()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Se ha producido un error en la base de datos. No se han podido cargar las opciones");
		}
		panel.add(cmbEstatusSocial);
		panel.add(butBuscar);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					int iRow = ((JTable)arg0.getSource()).getSelectedRow();
					
					Conde conde = ((CondesTableModel)table.getModel()).getData(iRow);
					
					if (conde != null) {
						IfrConde ifrConde = new IfrConde(conde);
						ifrConde.setBounds(20, 20, 470, 250);
						pnlParent.add(ifrConde, 0);
						ifrConde.setVisible(true);
					}
				}
			}
		});
		table.setTableHeader(null);
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		
	}

}
