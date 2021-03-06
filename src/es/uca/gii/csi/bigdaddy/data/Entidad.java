package es.uca.gii.csi.bigdaddy.data;

import java.sql.Connection;
import java.util.Date;

public class Entidad {
	private final int _iId;
	private final String  _sTabla;
	private boolean _bIsDeleted = false;
	protected enum EType { Text, Integer, Real, Boolean, Date };
	
	protected Entidad(int iId, String sTabla) {
		_iId = iId;
		_sTabla = sTabla;
	}

	public int getId() {
		return _iId;
	}
	
	public boolean getIsDeleted() {
		return _bIsDeleted;
	}
	
	public void setIsDeleted(boolean bIsDeleted) {
		_bIsDeleted = bIsDeleted;
	}
	
	
	/**
	 * Actualiza un registro
	 * @param sQuery consulta con la instrucci�n SQL para actualizar
	 * @throws Exception
	 */
	protected void Update(String sQuery) throws Exception {
		if (getIsDeleted())
			throw new Exception("El registro no puede ser actualizado porque ha sido borrado");
	
		Connection con = null;
		
		try {
			Data.LoadDriver();
			
			con = Data.Connection();
			
			if (con.createStatement().executeUpdate(sQuery) == 0)
				throw new Exception("El registro no puede ser actualizado porque no existe.");
		} finally {
			if (con != null) con.close();
		}
	}
	
	/**
	 * Elimina un registro de la tabla _sTabla
	 */
	public void Delete() throws Exception {
		if (getIsDeleted())
			throw new Exception("El registro ya fue borrado");
		
		Connection con = null;
		
		try {
			Data.LoadDriver();

			con = Data.Connection();
			
			String sDelete = "delete from bigdaddy." + _sTabla +  " where id = " + getId();
			
			con.createStatement().executeUpdate(sDelete);
			setIsDeleted(true);
		} finally {
			if (con != null) con.close();
		}
	}
	
	/**
	 * @param asField nombre campos de la tabla que compondr�n la cla�sula where
	 * @param aiType tipos SQL de los campos
	 * @param aoValue valores de los campos; si uno de ellos es null, no se incluir� el campo en la consulta
	 * @return cadena con la cla�sula where formada, o cadena vac�a si todos los valores eran null
	 */
	protected static String Where(String[] asField, EType[] aType, Object[] aoValue) {
		StringBuilder sbWhere = new StringBuilder();
		
		int iLength = asField.length;
		for (int i = 0; i < iLength; i++) {
			Object o = aoValue[i];
			if (o != null) {
				switch (aType[i]) {
					case Text:
						sbWhere.append(asField[i] + " like " + Data.String2Sql((String)o, true, true));
						break;
					case Integer:
						sbWhere.append(asField[i] + " = " + ((Integer)o).intValue());
						break;
					case Real:
						double dValue = ((Double)o).doubleValue();
						double dThreshold = 0.001;
						sbWhere.append(asField[i] + " between " + (dValue - dValue * dThreshold) + 
								" and " + (dValue + dValue * dThreshold));
						break;
					case Boolean:
						sbWhere.append(asField[i] + " = " + ((Boolean)o).booleanValue());
						break;
					case Date:
						sbWhere.append(asField[i] + " = " + Data.Date2Sql((Date)o, true));
						break;
				}
				sbWhere.append(" and ");
			}
		}
		
		if (sbWhere.length() > 0)
			return "where " + sbWhere.substring(0, sbWhere.length() - 5);
		
		return "";
	}
}
