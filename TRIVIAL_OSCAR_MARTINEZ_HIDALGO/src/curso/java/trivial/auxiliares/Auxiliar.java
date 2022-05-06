package curso.java.trivial.auxiliares;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import curso.java.trivial.ClaseLog;


/**
 * Funciones auxiliares
 */
public class Auxiliar {
	
    private static Properties properties = null;
    public static ResourceBundle rb = null;
        
    private Auxiliar(String fichero) {
        properties = new Properties();
        try {
        	properties.load(new FileInputStream(fichero));
        } catch (IOException ex) {
        	ClaseLog.miLog.debug("NO existe el fichero de propiedades");
        	System.out.println("NO existe el fichero de propiedades");
        	ex.printStackTrace();
        }
    }
    
    //Configuration
 
    /**
     * Implementando Singleton
     *
     * @return
     */
    public static Auxiliar getInstance(String fichero) {
        return new Auxiliar(fichero);
    }

	public static String getParametro(String parametro) {
		String ficheroConf = "./properties/persistencia.properties";
		
		if(properties==null) {
			Auxiliar.getInstance(ficheroConf);
		}
		
		String param = properties.getProperty(parametro);
		
		return param;
	}
	
	/**
	 * Metodo auxiliar para definir el tipo de idioma
	 * @return
	 */
	public static int idioma() {
		//miro a ver en el fichero de propiedades como esta el idioma
		int defecto = 0;
		
		String fichero = "./properties/idioma.properties";
		Properties properties = new Properties();
		
        try {
        	properties.load(new FileInputStream(fichero));
        } catch (IOException ex) {
        	ClaseLog.miLog.debug("NO existe el fichero de propiedades");
        	System.out.println("NO existe el fichero de propiedades");
        	ex.printStackTrace();
        }
        	
		String idiomaDefecto = properties.getProperty("idioma.defecto");

		if (idiomaDefecto.equals("en")) {
			defecto = 2;
			Locale locale = new Locale("en");
			rb = ResourceBundle.getBundle("idioma", locale);
		}else if (idiomaDefecto.equals("es")) {
			defecto = 1;
			Locale locale = new Locale("es");
			rb = ResourceBundle.getBundle("idioma", locale);
		}
		return defecto;
	}

	/**
	 * Metodo que cambia el idioma por defecto
	 * @param idioma
	 */
	public static void cambiarIdiomaDefecto(int idioma) {
		String linea="";
		if (idioma == 1) {
			linea = "idioma.defecto=es";
		}else if (idioma == 2) {
			linea = "idioma.defecto=en";
		}
		File file = new File("./properties/idioma.properties");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(linea);
			bw.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
