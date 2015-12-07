package es.uca.gii.csi.bigdaddy.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Conde {
	private int _iId;
	private String _sNombre;
	private String _sDinastia;
	private int _iOrdenDinastico;
	private EstatusSocial _estatusSocial;
	private boolean _bIsDeleted = false;
	
	
	/**
	 * @param iId clave primaria de la tabla conde
	 * @throws Exception si falla la conexión con la base de datos
	 */
	public Conde(int iId) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		try {
			Data.LoadDriver();
			
			con = Data.Connection();
			
			String sConsulta = "select nombre, dinastia, ordenDinastico, id_EstatusSocial from bigdaddy.conde where id = " + iId;
			rs = con.createStatement().executeQuery(sConsulta);
			
			if (rs.next()) {
				_iId = iId;
				_sNombre = rs.getString(1);
				_sDinastia = rs.getString(2);
				_iOrdenDinastico = rs.getInt(3);
				_estatusSocial = new EstatusSocial(rs.getInt(4));
			} else {
				throw new Exception("El registro con la id=" + iId + " no existe");
			}
		} finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
	
	public int getId() {
		return _iId;
	}
	
	public String getNombre() {
		return _sNombre;
	}
	
	public void setNombre(String sNombre) {
		_sNombre = sNombre;
	}
	
	public String getDinastia() {
		return _sDinastia;
	}
	
	public void setDinastia(String sDinastia) {
		_sDinastia = sDinastia;
	}
	
	public int getOrdenDinastico() {
		return _iOrdenDinastico;
	}
	
	public void setOrdenDinastico(int iOrdenDinastico) {
		_iOrdenDinastico = iOrdenDinastico;
	}
	
	public EstatusSocial getEstatusSocial() {
		return _estatusSocial;
	}
	
	public void setEstatusSocial(EstatusSocial estatusSocial) {
		_estatusSocial = estatusSocial;
	}
	
	public boolean getIsDeleted() {
		return _bIsDeleted;
	}

	public String toString() {
		return super.toString() + "; Nombre: " + _sNombre + "; Dinastia: " + _sDinastia +
				"; Orden dinastico: " + _iOrdenDinastico + "; Estatus social: " + _estatusSocial.toString(); 
	}
	
	/**
	 * Crea una nueva entidad conde en la base de datos
	 * @param sNombre nombre
	 * @param sDinastia dinastía
	 * @param iOrdenDinastico orden dinástico
	 * @param estatusSocial estatus social
	 * @return Conde conde insertado en la base de datos
	 * @throws Exception si falla la conexión con la base de datos
	 */
	public static Conde Create(String sNombre, String sDinastia, int iOrdenDinastico, EstatusSocial estatusSocial) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		try {
			Data.LoadDriver();
			
			con = Data.Connection();
			
			String sInsert = "insert into bigdaddy.conde (nombre, dinastia, ordenDinastico, id_EstatusSocial) " +
					"values (" + Data.String2Sql(sNombre, true, false) + ", " +
					Data.String2Sql(sDinastia, true, false) + ", " +
					iOrdenDinastico + ", " + estatusSocial.getId() + ")";
			
			con.createStatement().executeUpdate(sInsert);
			
			return new Conde(Data.LastId(con));
		} finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
	
	/**
	 * Borra el registro de la entidad conde de la base de datos correspondiente
	 * a la instancia que invoca este método
	 * @throws Exception si el registro ya fue borrado o falla la ejecución de
	 * la sentencia en la base de datos
	 */
	public void Delete() throws Exception {
		if (_bIsDeleted)
			throw new Exception("El registro ya fue borrado");
		
		Connection con = null;
		
		try {
			Data.LoadDriver();

			con = Data.Connection();
			
			String sDelete = "delete from bigdaddy.conde where id = " + _iId;
			
			con.createStatement().executeUpdate(sDelete);
			_bIsDeleted = true;
		} finally {
			if (con != null) con.close();
		}
	} 
	
	/**
	 * Actualiza los campos del registro de la entidad conde de la base de datos
	 * correspondiente a la instancia que invoca este método con los valores actuales
	 * de los atributos
	 * @throws Exception si el registro ya fue borrado o falla la ejecución de 
	 * la sentencia en la base de datos
	 */
	public void Update() throws Exception {
		if (_bIsDeleted)
			throw new Exception("El registro no puede ser actualizado porque ha sido borrado");
	
		Connection con = null;
		
		try {
			Data.LoadDriver();
			
			con = Data.Connection();
			
			String sUpdate = "update bigdaddy.conde set " +
					"nombre = " + Data.String2Sql(_sNombre, true, false) + ", " +
					"dinastia = " + Data.String2Sql(_sDinastia, true, false) + ", " +
					"ordenDinastico = " + _iOrdenDinastico + ", " +
					"id_EstatusSocial = " + _estatusSocial.getId() + " " +
					"where id = " + _iId;
			
			if (con.createStatement().executeUpdate(sUpdate) == 0)
				throw new Exception("El registro no puede ser actualizado porque no existe.");
		} finally {
			if (con != null) con.close();
		}
	}
	
	/**
	 * Búsqueda de entidades Conde que cumplen las valores de los argumentos
	 * por los que se quiere filtrar
	 * @param sNombre si es null no se incluirá en la búsqueda
	 * @param sDinastia si es null no se incluirá en la búsqueda
	 * @param iOrdenDinastico si es null no se incluirá en la búsqueda
	 * @param sEstatusSocial nombre del estatus social; si es null no se incluirá en la búsqueda
	 * @return lista de entidad Conde
	 * @throws Exception si falla la consulta o la conexión con la base de datos
	 */
	public static List<Conde> Select(String sNombre, String sDinastia, Integer iOrdenDinastico, String sEstatusSocial) throws Exception {
		List<Conde> aResultadoBusqueda = new ArrayList<Conde>();
		
		Connection con = null;
		ResultSet rs = null;
		
		try {
			Data.LoadDriver();
			
			con = Data.Connection();
			
			String sSelect = "select conde.id from bigdaddy.conde inner join estatussocial on " +
					"conde.id_EstatusSocial = estatussocial.id " +
					Where(sNombre, sDinastia, iOrdenDinastico, sEstatusSocial);
					
			rs = con.createStatement().executeQuery(sSelect);
			
			while (rs.next()) {
				Conde conde = new Conde(rs.getInt(1));
				aResultadoBusqueda.add(conde);
			}
			
			return aResultadoBusqueda;
		} finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
	
	/**
	 * @param sNombre si es null no se incluirá en la búsqueda
	 * @param sDinastia si es null no se incluirá en la búsqueda
	 * @param iOrdenDinastico si es null no se incluirá en la búsqueda
	 * @param sEstatusSocial si es null no se incluiría en la búsqueda
	 * @return si todos los argumentos son null cadena vacía, de lo contrario,
	 * se devuelve la claúsula where formada con los campos a filtrar
	 */
	private static String Where(String sNombre, String sDinastia, Integer iOrdenDinastico, String sEstatusSocial) {
		StringBuilder sbWhere = new StringBuilder();
		
		if (sNombre != null)
			sbWhere.append("conde.nombre like " + Data.String2Sql(sNombre, true, true) + " and ");
		
		if (sDinastia != null)
			sbWhere.append("conde.dinastia like " + Data.String2Sql(sDinastia, true, true) + " and ");
		
		if (iOrdenDinastico != null)
			sbWhere.append("conde.ordenDinastico = " + iOrdenDinastico.intValue() + " and ");
		
		if (sEstatusSocial != null)
			sbWhere.append("estatussocial.nombre like " + Data.String2Sql(sEstatusSocial, true, true) + " and ");
		
		if (sbWhere.length() > 0)
			return "where " + sbWhere.substring(0, sbWhere.length()-5);
		
		return "";
	}
}
