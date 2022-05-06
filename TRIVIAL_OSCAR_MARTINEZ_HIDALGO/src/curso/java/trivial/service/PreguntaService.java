package curso.java.trivial.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import curso.java.trivial.ClaseLog;
import curso.java.trivial.auxiliares.Auxiliar;
import curso.java.trivial.auxiliares.Constantes;
import curso.java.trivial.dao.FicheroTexto;
import curso.java.trivial.model.Pregunta;

public class PreguntaService {
	
	private static ArrayList<String> arrayLog = new ArrayList<String>();

	private static Scanner teclado = new Scanner(System.in);


	/**
	 * Metodo para mostrar por pantalla el fichero de Instrucciones
	 */
	public static void MostrarInstrucciones() {
		File file = new File(Constantes.FINSTRUCCIONES);

		BufferedReader br = null;
		String linea;
		try {
			br = new BufferedReader(new FileReader(file));
			System.out.println("");
			System.out.println("-----------------------------------------------------------------------------------------------------------");
			while ((linea = br.readLine()) != null) {
				System.out.println(linea);
			}
			br.close();
			System.out.println(Auxiliar.rb.getString("servicio.final"));
		} catch (FileNotFoundException e) {
			ClaseLog.miLog.debug("Error");
			System.out.println("Error al procesar el fichero de Instrucciones");
			e.printStackTrace();
		} catch (IOException e) {
			ClaseLog.miLog.debug("Error");
			System.out.println("Error al procesar el fichero de Instrucciones");
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				ClaseLog.miLog.debug("Error");
				System.out.println("Error al procesar el fichero de Instrucciones");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo para jugar
	 * @throws SQLException 
	 */
	public static int jugar(ArrayList<Pregunta> listaPreguntas)  throws SQLException {
		int correctaFichero;
		int correctaJugador;
		int puntos = 0;
		
		if (listaPreguntas.size() != 0) {
			// --------Proceso del juego preguna a pregunta con el array de preguntas
			for (int i = 0; i < listaPreguntas.size(); i++) {
				System.out.println("-------------------------------------------------------------------------------");
				System.out.println(Auxiliar.rb.getString("servicio.pregunta") + listaPreguntas.get(i).getCuestion());
				System.out.println(Auxiliar.rb.getString("servicio.opcion1") + listaPreguntas.get(i).getRespuesta1());
				System.out.println(Auxiliar.rb.getString("servicio.opcion2") + listaPreguntas.get(i).getRespuesta2());
				System.out.println(Auxiliar.rb.getString("servicio.opcion3") + listaPreguntas.get(i).getRespuesta3());
				correctaFichero = listaPreguntas.get(i).getCorrecta();
				// ----------------------------Escribo en el array para el fichero log-----------------------------------
				arrayLog.add(Auxiliar.rb.getString("servicio.pregunta") + listaPreguntas.get(i).getCuestion());
				switch (correctaFichero) {
				case 1:
					arrayLog.add(Auxiliar.rb.getString("servicio.respuestaok") + listaPreguntas.get(i).getRespuesta1());
					break;
				case 2:
					arrayLog.add(Auxiliar.rb.getString("servicio.respuestaok") + listaPreguntas.get(i).getRespuesta2());
					break;
				case 3:
					arrayLog.add(Auxiliar.rb.getString("servicio.respuestaok") + listaPreguntas.get(i).getRespuesta3());
					break;
				}
				// ------------------------------Seleccionar respuesta correcta------------------------------------------
				do {
					System.out.print(Auxiliar.rb.getString("servicio.seleccion"));
					correctaJugador = teclado.nextInt();
				} while ((correctaJugador < 1) || (correctaJugador > 3));

				if (correctaFichero == correctaJugador) {
					System.out.println(Auxiliar.rb.getString("servicio.ok"));
					puntos++;
				} else {
					System.out.println(Auxiliar.rb.getString("servicio.nook") + correctaFichero + "---------");
				}
				// ---------------------------------Escribo en array para el log----------------------------------------
				switch (correctaJugador) {
				case 1:
					arrayLog.add(Auxiliar.rb.getString("servicio.elegida") + listaPreguntas.get(i).getRespuesta1());
					break;
				case 2:
					arrayLog.add(Auxiliar.rb.getString("servicio.elegida") + listaPreguntas.get(i).getRespuesta2());
					break;
				case 3:
					arrayLog.add(Auxiliar.rb.getString("servicio.elegida") + listaPreguntas.get(i).getRespuesta3());
					break;
				}
				arrayLog.add("------------------------------------------------------------------------------");
				// -----------------------------------------------------------------------------------------------------
			}
			arrayLog.add(Auxiliar.rb.getString("servicio.fin") + puntos);
			System.out.println("*****************************************************************");
			System.out.println(Auxiliar.rb.getString("servicio.fin") + puntos);
			System.out.println("*****************************************************************");

		} else {
			System.out.println(Auxiliar.rb.getString("nohaypreguntas"));
		}
		return puntos;
	}


	/**
	 * Metodo para escribir en el fichero pdf
	 * 
	 * @param nickP
	 * @param puntosP
	 * @param log
	 */
	public static void grabarEnFicheroPdf(String nickP, int puntosP) {

		char opcion = '0';
		do {
			System.out.print(Auxiliar.rb.getString("servicio.puntuacion3"));
			opcion = teclado.next().charAt(0);
		} while ((opcion != 's') && (opcion != 'n'));

		teclado.nextLine();// reseteo el teclado

		if (opcion == 's') { // si quiere grabarla le pido el nick y lanzo el proceso de grabación del fichero
			if (nickP.equals("")) {
				do {
					System.out.print(Auxiliar.rb.getString("servicio.puntuacion4"));
					nickP = teclado.nextLine();
				} while ((nickP.indexOf('-') != -1) || (nickP.indexOf('*') != -1) || (nickP.indexOf('/') != -1)
						|| (nickP.equals("")));
			}
			FicheroTexto.generarPdf(nickP, arrayLog);
		}
	}


}
