package es.uca.gii.csi.bigdaddy.test;

import java.sql.Connection;
import java.sql.ResultSet;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import es.uca.gii.csi.bigdaddy.data.Data;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class DataTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	@Ignore
	@Test
	public void testTableAccess() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			String sql = "select count(*) from bigdaddy.conde";
			rs = con.createStatement().executeQuery(sql);
			
			int numeroElementos = 0;
			if (rs.next()) numeroElementos = rs.getInt(1);
			rs.close();
			
			Assert.assertEquals(numeroElementos, 2);
			
			sql = "select * from bigdaddy.conde";
			rs = con.createStatement().executeQuery(sql);
			
			while (rs.next()) {
				int i = 0;
				int id = rs.getInt(++i);
				String nombre = rs.getString(++i);
				String dinastia = rs.getString(++i);
				int ordenDinastico = rs.getInt(++i);
				String estatus = rs.getString(++i);
				
				System.out.println("id: " + id + ", nombre: " + nombre + ", dinastía: " + dinastia + ", orden dinástico: " + ordenDinastico + ", estatus social: " + estatus);
				Assert.assertEquals(i, rs.getMetaData().getColumnCount());
			}
		} finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
}
