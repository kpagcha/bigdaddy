package es.uca.gii.csi.bigdaddy.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test extends Entidad {
	private String _s;
	private int _i;
	private float _f;
	private boolean _b;
	private Date _d;

	public Test() {
		super(1, "test");
	}
	
	private Test(String s, int i, float f, boolean b, Date d) {
		super(1, "test");
		_s = s;
		_i = i;
		_f = f;
		_b = b;
		_d = d;
	}
	
	public String toString() {
		return "Text: " + _s + "; Integer: " + _i + "; Float: " + _f + "; Boolean: " + _b + "; Date: " + _d; 
	}
	
	public static List<Test> Select(String s, Integer i, Double d, Boolean b, Date date) throws Exception {
		List<Test> aResultadoBusqueda = new ArrayList<Test>();
		
		Connection con = null;
		ResultSet rs = null;
		
		try {
			Data.LoadDriver();
			
			con = Data.Connection();
			
			String sSelect = "select `text`, `integer`, `real`, `boolean`, `date` from bigdaddy.test " +
					Where(
						new String[] { "test.text", "test.integer", "test.real", "test.boolean", "test.date" },
						new EType[] { EType.Text, EType.Integer, EType.Real, EType.Boolean, EType.Date },
						new Object[] { s, i, d, b, date });
			
			System.out.println(sSelect);
					
			rs = con.createStatement().executeQuery(sSelect);
			
			while (rs.next()) {
				Test test = new Test(rs.getString("text"), rs.getInt("integer"), rs.getFloat("real"), rs.getBoolean("boolean"), rs.getDate("date"));
				aResultadoBusqueda.add(test);
			}
			
			return aResultadoBusqueda;
		} finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
}
