package es.uca.gii.csi.bigdaddy.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstatusSocial extends Entidad {
	private String _sNombre;
	
	/**
	 * @param iId identificador del EstatusSocial en la tabla correspondiente en la base de datos
	 * @param connection conexión con la base de datos
	 * @throws Exception si no existe un registro con la id iId
	 */
	public EstatusSocial(int iId, Connection connection) throws Exception {
		super(iId, "estatussocial");
		Initialize(iId, connection);
	}
	
	/**
	 * @param iId identificador del Conde en la tabla correspondiente en la base de datos
	 * @throws Exception si no existe un registro con la id iId
	 */
	public EstatusSocial(int iId) throws Exception {
		super(iId, "estatussocial");
		
		Connection con = null;
		try {
			Data.LoadDriver();
			con = Data.Connection();
					
			Initialize(iId, con);
		} finally {
			if (con != null) con.close();
		}
	}
	
	private EstatusSocial(int iId, String sNombre) {
		super(iId, "estatussocial");
		_sNombre = sNombre;
	}
	
	/**
	 * Construye una instancia de EstatusSocial con los datos obtenidos de la base de datos
	 * @param iId
	 * @param connection
	 * @throws Exception
	 */
	private void Initialize(int iId, Connection connection) throws Exception {	
		ResultSet rs = null;
		
		try {
			String sConsulta = "select nombre from bigdaddy.estatussocial where id = " + iId;
			rs = connection.createStatement().executeQuery(sConsulta);
			
			if (rs.next()) {
				_sNombre = rs.getString("nombre");
			} else {
				throw new Exception("El registro con la id=" + iId + " no existe");
			}
		} finally {
			if (rs != null) rs.close();
		}
	}
	
	public String getNombre() {
		return _sNombre;
	}
	
	public void setNombre(String sNombre) {
		_sNombre = sNombre;
	}
	
	public String toString() {
		return _sNombre;
	}
	
	/**
	 * @return lista con todos los registros de la tabla estatussocial ordenados por nombre de forma ascendente
	 * @throws Exception si hay un fallo con la base de datos
	 */
	public static List<EstatusSocial> Select() throws Exception {
		List<EstatusSocial> aResultadoBusqueda = new ArrayList<EstatusSocial>();
		
		Connection con = null;
		ResultSet rs = null;
		
		try {
			Data.LoadDriver();
			
			con = Data.Connection();
			
			String sSelect = "select id, nombre from bigdaddy.estatussocial order by nombre asc";
					
			rs = con.createStatement().executeQuery(sSelect);
			
			while (rs.next()) {
				EstatusSocial estatusSocial = new EstatusSocial(rs.getInt("id"), rs.getString("nombre"));
				aResultadoBusqueda.add(estatusSocial);
			}
			
			return aResultadoBusqueda;
		} finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
}
