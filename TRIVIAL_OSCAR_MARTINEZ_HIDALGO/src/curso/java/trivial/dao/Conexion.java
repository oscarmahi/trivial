package curso.java.trivial.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import curso.java.trivial.ClaseLog;
import curso.java.trivial.auxiliares.AuxiliarBD;

/**
 * Clase para acceder a la BBDD
 * @author oscar
 *
 */
public class Conexion {
	
	static String bd = AuxiliarBD.getParametro("bd.nombre");
	static String login = AuxiliarBD.getParametro("bd.login");
	static String password = AuxiliarBD.getParametro("bd.pass");
	static String host = AuxiliarBD.getParametro("bd.host"); //127.0.0.1
	
	static Connection conexion; //atributo para guardar el objeto Connection
	
    public static Connection getConexion() {
	    if (conexion == null) {
	    	crearConexion();
	    }
	    return conexion;
    }
    
    // devuelve true si se ha creado correctamente
    private static boolean crearConexion() {
	    try {
	        //cargo el driver
	        Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("com.mysql.cj.jdbc.Driver");
	        
	    	String url = "jdbc:mysql://";
	    	conexion = DriverManager.getConnection(url + host + "/"+ bd, login, password);
	    	
	    	conexion.setAutoCommit(false);
	    } catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
	    }
	    catch (Exception e) {
	    	System.out.println(e);
	    	return false;
	    }
	    return true;
    }

    public static void desconectar(){
    	try {
    		conexion.close();
            conexion = null;
            ClaseLog.miLog.debug("La conexion a la base de datos \" + bd + \" ha terminado");
            System.out.println("La conexion a la base de datos " + bd + " ha terminado");
    	
    	} catch (SQLException e) {
    		System.out.println("Error al cerrar la conexion");
        }
    }
   
    public static void main(String[] args) {
    	Connection c = Conexion.getConexion();
    	System.out.println(c);
    }

}
