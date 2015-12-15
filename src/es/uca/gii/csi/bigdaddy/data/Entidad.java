package es.uca.gii.csi.bigdaddy.data;

import java.sql.Connection;

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
	 * Elimina un registro de _sTabla
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
}
