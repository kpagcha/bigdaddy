package es.uca.gii.csi.bigdaddy.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.uca.gii.csi.bigdaddy.data.Data;
import es.uca.gii.csi.bigdaddy.data.EstatusSocial;

public class EstatusSocialTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}
	
	@Test
	public void testConstructor() throws Exception {

		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			
			EstatusSocial estatusSocial = new EstatusSocial(1);
			
			String sConsulta = "select nombre from bigdaddy.estatussocial where id = 1";
			rs = con.createStatement().executeQuery(sConsulta);
			
			if (rs.next()) {
				Assert.assertEquals(rs.getString(1), estatusSocial.getNombre());
			}
			
			try {
				estatusSocial = new EstatusSocial(0);
			} catch(Exception e) {
				Assert.assertEquals("El registro con la id=0 no existe", e.getMessage());
			}
		} finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
	
	@Test
	public void testSelect() throws Exception {
		List<EstatusSocial> aResultadoBusqueda = EstatusSocial.Select();
		
		Assert.assertEquals(3, aResultadoBusqueda.size());
		
		EstatusSocial estatusSocial1 = aResultadoBusqueda.get(2);
		EstatusSocial estatusSocial2 = aResultadoBusqueda.get(1);
		
		Assert.assertEquals("Noble", estatusSocial1.getNombre());
		Assert.assertEquals("Monarca", estatusSocial2.getNombre());
	}
}
