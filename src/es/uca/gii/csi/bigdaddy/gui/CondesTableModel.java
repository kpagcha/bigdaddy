package es.uca.gii.csi.bigdaddy.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.uca.gii.csi.bigdaddy.data.Conde;

public class CondesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Conde> _aData;
	
	public CondesTableModel(List<Conde> aData) {
		_aData = aData;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return _aData.size();
	}

	@Override
	public Object getValueAt(int iRow, int iCol) {
		Conde conde = _aData.get(iRow);
		switch (iCol) {
			case 0:
				return conde.getNombre();
			case 1:
				return conde.getDinastia();
			case 2:
				return conde.getOrdenDinastico();
			case 3:
				return conde.getEstatusSocial().toString();
			default:
				throw new IllegalStateException("Error fatal");
		}
	}
	
	public Conde getData(int iRow) {
		return _aData.get(iRow);
	}

}
