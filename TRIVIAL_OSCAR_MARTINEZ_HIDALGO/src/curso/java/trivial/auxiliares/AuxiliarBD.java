package curso.java.trivial.auxiliares;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import curso.java.trivial.ClaseLog;

/**
 * Funciones auxiliares de BBDD para acceder alfichero de configuraion de la BBDD
 */
public class AuxiliarBD {
	private static Properties propertiesBD = null;
	public static ResourceBundle rbBD = null;

	private AuxiliarBD(String fichero) {
		propertiesBD = new Properties();
		try {
			propertiesBD.load(new FileInputStream(fichero));
		} catch (IOException ex) {
			ClaseLog.miLog.debug("NO existe el fichero de propiedades");
			System.out.println("NO existe el fichero de propiedades");
			ex.printStackTrace();
		}
	}

	/**
	 * Implementando Singleton
	 *
	 * @return
	 */
	public static AuxiliarBD getInstance(String fichero) {
		return new AuxiliarBD(fichero);
	}

	public static String getParametro(String parametro) {
		String ficheroConf = "./properties/bd.properties";

		if (propertiesBD == null) {
			AuxiliarBD.getInstance(ficheroConf);
		}

		String param = propertiesBD.getProperty(parametro);
		return param;
	}
}
