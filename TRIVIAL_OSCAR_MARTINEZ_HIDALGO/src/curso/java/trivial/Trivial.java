package curso.java.trivial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import curso.java.trivial.auxiliares.Auxiliar;
import curso.java.trivial.dao.IPregunta;
import curso.java.trivial.dao.IRecord;
import curso.java.trivial.dao.PreguntaImplBBDD;
import curso.java.trivial.dao.PreguntaImplFichero;
import curso.java.trivial.model.Pregunta;
import curso.java.trivial.service.PreguntaService;

public class Trivial {

	/**
	 * Main del juego de trivial para .Miteris. Practica 1.3 de ADD
	 * Oscar Martinez Hidalgo
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {

		PropertyConfigurator.configure("./properties/log4j.properties");
		ClaseLog.miLog.debug("Inicio del programa");
		
		//apertura del fichero de propiedades para ver si la persistencia es fichero o bbdd
		String persiste = Auxiliar.getParametro("persistencia.acceso");
		//---------------------------------------------------------------------------------------------
		
		//Creo las variables para usar una interfaz u otra dependiendo del tipo de persistencia
		IPregunta daoPregunta = null;
		IRecord daoRecord = null;
		
		if (persiste.equals("BBDD")) {
			daoPregunta = new PreguntaImplBBDD();
			daoRecord = new PreguntaImplBBDD();
			
		}else if (persiste.equals("fichero")) {
			daoPregunta = new PreguntaImplFichero();
			daoRecord = new PreguntaImplFichero();
		}
		//----------------------------------------------------------------------------------------------
		//abro el fichero de propiedades de idioma para seleccionar el idioma por defecto
		int defecto = Auxiliar.idioma();
		//Esta es la parte de seleccion de idioma------------------------------------------------------ 
		int idioma =0;
		Scanner teclado = new Scanner(System.in);
		do {
			System.out.print(Auxiliar.rb.getString("idioma.main"));
			idioma = teclado.nextInt();
		} while ((idioma != 1) && (idioma != 2));

		
		if (idioma == 1) {		//español
			Locale locale = new Locale("es");
			Auxiliar.rb = ResourceBundle.getBundle("idioma", locale);
		}else if (idioma == 2) {	//ingles
			Locale locale = new Locale("en");
			Auxiliar.rb = ResourceBundle.getBundle("idioma", locale);
		}		
		
		if (defecto != idioma) {
			Auxiliar.cambiarIdiomaDefecto(idioma);
		}
		//-----------------------------------------------------------------------------------------------
		int opcion = 6;

		opcion = Menu.menuDeOpciones();
		while (opcion != 6) {
			switch (opcion) {
			case 1: 	//jugar
				ArrayList<Pregunta> preguntas = daoPregunta.obtenerListaPreguntas();
				int puntos = PreguntaService.jugar(preguntas);
				// una vez acabado el juego pregunto si quiere grabar su puntuacion--------------------------------------
				String nick = daoRecord.grabarEnFicheroRecords(puntos);
				//acceder al tema del pdf
				PreguntaService.grabarEnFicheroPdf(nick, puntos);
				opcion = Menu.menuDeOpciones();
				break;
			case 2:				//añadir pregunta. La pido por consola y la paso a los dao
				String respuesta1;
				String respuesta2;
				String respuesta3;
				int correctaT = 0;
				teclado.nextLine();
				System.out.println(Auxiliar.rb.getString("servicio.add"));
				System.out.print(Auxiliar.rb.getString("servicio.introduce"));
				String cuestionT = teclado.nextLine();
				System.out.print(Auxiliar.rb.getString("servicio.respuesta1"));
				respuesta1 = teclado.nextLine();
				System.out.print(Auxiliar.rb.getString("servicio.respuesta2"));
				respuesta2 = teclado.nextLine();
				System.out.print(Auxiliar.rb.getString("servicio.respuesta3"));
				respuesta3 = teclado.nextLine();
				do {
					System.out.print(Auxiliar.rb.getString("servicio.correcta"));
					correctaT = teclado.nextInt();
				} while ((correctaT < 1) || (correctaT > 3));

				Pregunta pregunta = new Pregunta(cuestionT, respuesta1, respuesta2, respuesta3, correctaT);						
				
				daoPregunta.addPregunta(pregunta);
				opcion = Menu.menuDeOpciones();
				break;
			case 3: 									//Importar preguntas
				daoPregunta.addPreguntaExcel();
				opcion = Menu.menuDeOpciones();
				break;
			case 4: 									//ver records ordenados por puntuacion
				daoRecord.obtenerListaRecords();
				opcion = Menu.menuDeOpciones();
				break;
			case 5:  									// instrucciones por pantalla. A partir de un fichero de texto.
				PreguntaService.MostrarInstrucciones();
				opcion = Menu.menuDeOpciones();
				break;
			}
		}
		ClaseLog.miLog.debug("Fin del programa");
		System.out.println("-----------------------------------FIN DE PROGRAMA-----------------------------");
	}

	
}
