package es.uca.gii.csi.bigdaddy.data;

import java.sql.Connection;
import java.sql.Types;

public class Entidad {
	private final int _iId;
	private final String  _sTabla;
	private boolean _bIsDeleted = false;
	
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
	 * @param sQuery consulta con la instrucción SQL para actualizar
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
	 * @param asFields nombre campos de la tabla que compondrán la claúsula where
	 * @param aiTypes tipos SQL de los campos
	 * @param aoValues valores de los campos; si uno de ellos es null, no se incluirá el campo en la consulta
	 * @return cadena con la claúsula where formada, o cadena vacía si todos los valores eran null
	 */
	protected static String Where(String[] asFields, int[] aiTypes, Object[] aoValues) {
		StringBuilder sbWhere = new StringBuilder();
		
		for (int i = 0; i < asFields.length; i++) {
			Object o = aoValues[i];
			if (aoValues[i] != null) {
				if (aiTypes[i] == Types.VARCHAR)
					sbWhere.append(asFields[i] + " like " + Data.String2Sql((String)o, true, true));
				else
					sbWhere.append(asFields[i] + " = " + o.getClass().cast(o));
				sbWhere.append(" and ");
			}
		}
		
		if (sbWhere.length() > 0)
			return "where " + sbWhere.substring(0, sbWhere.length()-5);
		
		return "";
	}
}
