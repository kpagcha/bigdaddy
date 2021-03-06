package es.uca.gii.csi.bigdaddy.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import es.uca.gii.csi.bigdaddy.util.Config;

public class Data {
    public static String getPropertiesUrl() { return "./db.properties"; }
    public static Connection Connection() throws Exception {
        try {
            Properties properties = Config.Properties(getPropertiesUrl());
            return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
       }
       catch (Exception ee) { throw ee; }
	}
    
    public static void LoadDriver() 
        throws InstantiationException, IllegalAccessException, 
        ClassNotFoundException, IOException {
            Class.forName(Config.Properties(Data.getPropertiesUrl()
            ).getProperty("jdbc.driverClassName")).newInstance();
    }
    
    /**
     * @param s cadena que se transforma la cadena para que sea compatible con SQL
     * @param bAddQuotes a�adir comillas que envuelve la cadena
     * @param bAddWildcards a�adir porcentajes (%) que envuelven la cadena
     * @return String cadena transformada compatible con SQL
     */
    public static String String2Sql(String s, boolean bAddQuotes, boolean bAddWildcards) {
    	s = s.replace("'", "''");
    	
    	if (bAddWildcards)
    		s = "%" + s + "%";
    	
    	if (bAddQuotes)
    		s = "'" + s + "'";
    	
    	return s;
    }
    
    /**
     * @param b booleano que se transformar� a int
     * @return int entero resultado de la transformaci�n del booleano
     */
    public static int Boolean2Sql(boolean b) {
    	return b ? 1 : 0;
    }
    
    /**
     * @param dt fecha que se transformar� siguiendo el formato de cadenas de MySQL
     * @param bAddQuotes a�adir comillas que envuelve la cadena
     * @return String cadena transformada compatible con SQL 
     */
    public static String Date2Sql(Date dt, boolean bAddQuotes) {
    	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    	String s = sdf.format(dt);
    	
    	if (bAddQuotes)
    		s = "'" + s + "'";
    	
    	return s;
    }
    
    /**
     * @param con conexi�n actual
     * @return �ltimo id insertado con la conexi�n a la base de datos actual
     * @throws SQLException si falla la consulta
     * @throws IOException si falla la apertura o lectura del fichero de configuraci�n
     */
    public static int LastId(Connection con) throws IOException, SQLException {
    	ResultSet rs = null;
    	
    	try {
    		Properties properties = Config.Properties(getPropertiesUrl());
    		rs = con.createStatement().executeQuery(properties.getProperty("jdbc.lastIdSentence"));
    		
    		rs.next();
    		return rs.getInt(1);
    	} finally {
    		if (rs != null) rs.close();
    	}
    }
}