package curso.java.trivial;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import curso.java.trivial.auxiliares.Auxiliar;

public class Menu {
	
	/**
	 * Clase Menu para mostrar un menu de opciones
	 */

	private static Scanner teclado;
	
	/**
	 * Metodo para mostrar un menu de opciones.
	 * @return
	 */
	public static int menuDeOpciones() {
		
		//-----------------------------------Para el tema del idioma-------------------------------------------------------------
		String linea1 = "---------------------"+Auxiliar.rb.getString("menu.de.opciones")+"--------------------";
		String linea2 = "1.- "+Auxiliar.rb.getString("Jugar");
		String linea3 = "2.- "+Auxiliar.rb.getString("anadir.pregunta");
		String linea4 = "3.- "+Auxiliar.rb.getString("importar.preguntas");
		String linea5 = "4.- "+Auxiliar.rb.getString("ver.records");
		String linea6 = "5.- "+Auxiliar.rb.getString("Instrucciones");
		String linea7 = "6.- "+Auxiliar.rb.getString("Salir");
		String linea8 = Auxiliar.rb.getString("Opcion")+": ";
		//--------------------------------------------------------------------------------------------------------------------------
		
		int opcion = 6;
		teclado = new Scanner(System.in);
		try {
			do {
				System.out.println("----------------------------------------------------------");
				System.out.println(linea1);
				System.out.println(linea2);
				System.out.println(linea3);
				System.out.println(linea4);
				System.out.println(linea5);
				System.out.println(linea6);
				System.out.println(linea7);
				System.out.print(linea8);
				opcion = teclado.nextInt();
			} while ((opcion < 1) || (opcion > 6));
		} catch (Exception e) {
			System.out.println("Error en la introduccion de opciones.");
		}
		return (opcion);
	}
}
