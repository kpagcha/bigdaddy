package es.uca.gii.csi.bigdaddy.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import es.uca.gii.csi.bigdaddy.data.Conde;
import es.uca.gii.csi.bigdaddy.data.Data;
import es.uca.gii.csi.bigdaddy.data.EstatusSocial;

import org.junit.Assert;

public class CondeTest {

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
			
			Conde conde = new Conde(1);
			
			String sConsulta = "select nombre, dinastia, ordenDinastico from bigdaddy.conde where id = 1";
			rs = con.createStatement().executeQuery(sConsulta);
			
			if (rs.next()) {
				Assert.assertEquals(rs.getString(1), conde.getNombre());
				Assert.assertEquals(rs.getString(2), conde.getDinastia());
				Assert.assertEquals(rs.getInt(3), conde.getOrdenDinastico());
			}
			
			try {
				conde = new Conde(0);
			} catch(Exception e) {
				Assert.assertEquals("El registro con la id=0 no existe", e.getMessage());
			}
		} finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
	
	
	@Test
	public void testCreate() throws Exception {
		String sNombre = "Boris";
		String sDinastia = "Putin";
		int iOrdenDinastico = 9;
		EstatusSocial estatusSocial = new EstatusSocial(1);
		
		Conde conde = Conde.Create(sNombre, sDinastia, iOrdenDinastico, estatusSocial);
		
		Assert.assertEquals(sNombre, conde.getNombre());
		Assert.assertEquals(sDinastia, conde.getDinastia());
		Assert.assertEquals(iOrdenDinastico, conde.getOrdenDinastico());
		
		conde.Delete();
	}
	
	@Test
	public void testSelect() throws Exception {
		List<Conde> aResultadoBusqueda1 = Conde.Select(null, null, null, null);
		
		Assert.assertEquals(2, aResultadoBusqueda1.size());
		
		Conde conde1 = aResultadoBusqueda1.get(0);
		Conde conde2 = aResultadoBusqueda1.get(1);
		
		Assert.assertEquals("Vladimir", conde1.getNombre());
		Assert.assertEquals("Dimitri", conde2.getNombre());
		
		List<Conde> aResultadoBusqueda2 = Conde.Select("adimi", null, 4, null);
		
		Assert.assertEquals(1, aResultadoBusqueda2.size());
		
		Conde conde3 = aResultadoBusqueda2.get(0);
		
		Assert.assertEquals("Vladimir", conde3.getNombre());
		Assert.assertEquals("Dracula", conde3.getDinastia());
		Assert.assertEquals(4, conde3.getOrdenDinastico());
		
		List<Conde> aResultadoBusqueda3 = Conde.Select("Jesulin", "de Ubrique", 1, null);
		
		Assert.assertEquals(0, aResultadoBusqueda3.size());
		
		List<Conde> aResultadoBusqueda4 = Conde.Select(null, null, null, "Noble");
		
		Assert.assertEquals(2, aResultadoBusqueda4.size());
		
		Conde conde4 = aResultadoBusqueda4.get(0);
		Conde conde5 = aResultadoBusqueda4.get(1);
		
		Assert.assertEquals("Vladimir", conde4.getNombre());
		Assert.assertEquals("Dimitri", conde5.getNombre());
	}
	
	@Test
	public void testUpdate() throws Exception {
		Conde conde = Conde.Create("Igor", "Dracula", 3, new EstatusSocial(1));
		
		int iId = conde.getId();
		String sNombre = "Vladimir Vladimirovich";
		String sDinastia = "Morozhenoe";
		int iOrdenDinastico = 1;
		EstatusSocial estatusSocial = new EstatusSocial(3);
		
		conde.setNombre(sNombre);
		conde.setDinastia(sDinastia);
		conde.setOrdenDinastico(iOrdenDinastico);
		conde.setEstatusSocial(estatusSocial);
		
		conde.Update();
		
		conde = new Conde(iId);
		
		Assert.assertEquals(sNombre, conde.getNombre());
		Assert.assertEquals(sDinastia, conde.getDinastia());
		Assert.assertEquals(iOrdenDinastico, conde.getOrdenDinastico());
		Assert.assertEquals("Monarca", conde.getEstatusSocial().getNombre());
			
		conde.Delete();
		
		conde = Conde.Create("Vladimir", "Putin", 1, new EstatusSocial(1));
		conde.Delete();
		try {
			conde.Update();
		} catch(Exception e) {
			Assert.assertEquals("El registro no puede ser actualizado porque ha sido borrado", e.getMessage());
		}
	}
	
	@Test
	public void testDelete() throws Exception {
		String sNombre = "Anton";
		String sDinastia = "Burov";
		int iOrdenDinastico = 4;
		EstatusSocial estatusSocial = new EstatusSocial(3);
		
		Conde conde = Conde.Create(sNombre, sDinastia, iOrdenDinastico, estatusSocial);
		
		conde.Delete();
		
		Assert.assertEquals(true, conde.getIsDeleted());
		
		List<Conde> aResultadoBusqueda = Conde.Select(sNombre, sDinastia, iOrdenDinastico, estatusSocial.getNombre());

		Assert.assertEquals(0, aResultadoBusqueda.size());
		
		try {
			conde.Delete();
		} catch(Exception e) {
			Assert.assertEquals("El registro ya fue borrado", e.getMessage());
		}
	}
}