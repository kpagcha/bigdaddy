package es.uca.gii.csi.bigdaddy.gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import es.uca.gii.csi.bigdaddy.data.EstatusSocial;

public class EstatusSocialListModel extends AbstractListModel<EstatusSocial> implements ComboBoxModel<EstatusSocial> {
	private static final long serialVersionUID = 1L;
	private List<EstatusSocial> _aData; 
	private Object _selection = null;
	
	public EstatusSocialListModel(List<EstatusSocial> aData) {
		_aData = aData;
	}
	
	@Override
	public EstatusSocial getElementAt(int iPosition) {
		return _aData.get(iPosition);
	}

	@Override
	public int getSize() {
		return _aData.size();
	}

	@Override
	public Object getSelectedItem() {
		return _selection;
	}

	@Override
	public void setSelectedItem(Object o) {
		EstatusSocial estatusSocial = (EstatusSocial)o;
		for (EstatusSocial e : _aData) {
			if (e.getNombre().equals(estatusSocial.getNombre()))
				_selection = e;
		}
	}

}
