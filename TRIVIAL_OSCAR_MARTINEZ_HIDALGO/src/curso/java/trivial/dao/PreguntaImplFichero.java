package curso.java.trivial.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import curso.java.trivial.ClaseLog;
import curso.java.trivial.auxiliares.Auxiliar;
import curso.java.trivial.auxiliares.Constantes;
import curso.java.trivial.model.Pregunta;
import curso.java.trivial.model.Record;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Clase que implementa las interfaces de preguntas y records en ficheros
 * @author oscar
 *
 */
public class PreguntaImplFichero implements IPregunta, IRecord{
	
	/**
	 * Metodo que devuelve la lista de preguntas
	 */
	@Override
	public ArrayList<Pregunta> obtenerListaPreguntas() {
		
		ArrayList<Pregunta> listaPreguntasXml = new ArrayList<Pregunta>();
		
		// Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();

		File xmlFile = new File(Constantes.FICHERO);

		if (xmlFile.exists()) {
			try {
				// Se crea el documento a traves del archivo
				Document document = builder.build(xmlFile);
				// Se obtiene la raiz 'juego'
				Element rootNode = document.getRootElement();
				// Se obtiene el nombre del nodo raiz
				String nombreNodo = rootNode.getName();
				// Se obtiene la lista de hijos del nodo raiz
				List listaPreguntasAux = rootNode.getChildren("pregunta");
				// Se recorre la lista de hijos del nodo
				for (int i = 0; i < listaPreguntasAux.size(); i++) {
					// Se obtiene el elemento 'pregunta'
					Element cuestion = (Element) listaPreguntasAux.get(i);

					String nombre = cuestion.getChildText("texto");
					String respuesta1 = cuestion.getChildText("respuesta1");
					String respuesta2 = cuestion.getChildText("respuesta2");
					String respuesta3 = cuestion.getChildText("respuesta3");
					String correcta = cuestion.getChildText("correcta");
					int correctaI = Integer.parseInt(correcta);

					Pregunta pLeida = new Pregunta(nombre, respuesta1, respuesta2, respuesta3, correctaI);
					listaPreguntasXml.add(pLeida);
				}
			} catch (IOException e) {
				ClaseLog.miLog.debug("Error en llenarListaPreguntas");
				//System.out.println("Error en llenarListaPreguntas");
				e.printStackTrace();
			} catch (Exception e) {
				ClaseLog.miLog.debug("Error en llenarListaPreguntas");
				//System.out.println("Error en llenarListaPreguntas");
				e.printStackTrace();
			}
		}
		return listaPreguntasXml;


	}

	/**
	 * Metodo para añadir una pregunta
	 */
	@Override
	public void addPregunta(Pregunta pregunta) {
		
		ArrayList<Pregunta> listaPreguntasAux = new ArrayList<Pregunta>();
		String nombre;
		
		String respuesta1;
		String respuesta2;
		String respuesta3;
		String correcta;
		int correctaI;

		// Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();

		File xmlFile = new File(Constantes.FICHERO);

		if (xmlFile.exists()) { // si el fichero existe añado la
								// ppregunta-------------------------------------------
			try {
				// Se crea el documento a traves del archivo
				Document document = builder.build(xmlFile);
				// Se obtiene la raiz 'juego'
				Element rootNode = document.getRootElement();
				// Se obtiene el nombre del nodo raiz
				String nombreNodo = rootNode.getName();
				// Se obtiene la lista de hijos del nodo raiz
				List listaPreguntas = rootNode.getChildren("pregunta");
				// Se recorre la lista de hijos del nodo
				for (int i = 0; i < listaPreguntas.size(); i++) {
					// Se obtiene el elemento 'pregunta'
					Element cuestion = (Element) listaPreguntas.get(i);

					nombre = cuestion.getChildText("texto");
					respuesta1 = cuestion.getChildText("respuesta1");
					respuesta2 = cuestion.getChildText("respuesta2");
					respuesta3 = cuestion.getChildText("respuesta3");
					correcta = cuestion.getChildText("correcta");
					correctaI = Integer.parseInt(correcta);

					Pregunta pLeida = new Pregunta(nombre, respuesta1, respuesta2, respuesta3, correctaI);
					listaPreguntasAux.add(pLeida);
				}

			} catch (IOException e) {
				ClaseLog.miLog.debug("Error");
				e.printStackTrace();
			} catch (Exception e) {
				ClaseLog.miLog.debug("Error");
				e.printStackTrace();
			}

			// ahora escribimos en el fichero de destino con la nueva pregunta
			String docNuevoStr = "";
			try {
				Document docNuevo = new Document();
				Element nodoRaiz = new Element("juego");
				docNuevo.addContent(nodoRaiz);

				for (int i = 0; i < listaPreguntasAux.size(); i++) {
					Element nodoPregunta = new Element("pregunta");
					nodoRaiz.addContent(nodoPregunta);

					Element nodoTexto = new Element("texto");
					nodoTexto.setText(listaPreguntasAux.get(i).getCuestion());
					nodoPregunta.addContent(nodoTexto);

					Element nodoRespuesta1 = new Element("respuesta1");
					nodoRespuesta1.setText(listaPreguntasAux.get(i).getRespuesta1());
					nodoPregunta.addContent(nodoRespuesta1);

					Element nodoRespuesta2 = new Element("respuesta2");
					nodoRespuesta2.setText(listaPreguntasAux.get(i).getRespuesta2());
					nodoPregunta.addContent(nodoRespuesta2);

					Element nodoRespuesta3 = new Element("respuesta3");
					nodoRespuesta3.setText(listaPreguntasAux.get(i).getRespuesta3());
					nodoPregunta.addContent(nodoRespuesta3);

					Element nodoCorrecta = new Element("correcta");
					correcta = (listaPreguntasAux.get(i).getCorrecta() + "");
					nodoCorrecta.setText(correcta);
					nodoPregunta.addContent(nodoCorrecta);
				}
				// ahora añado como ultimo nodo la pregunta a añadir
				Element nodoPregunta = new Element("pregunta");
				nodoRaiz.addContent(nodoPregunta);

				Element nodoTexto = new Element("texto");
				nodoTexto.setText(pregunta.getCuestion());
				nodoPregunta.addContent(nodoTexto);

				Element nodoRespuesta1 = new Element("respuesta1");
				nodoRespuesta1.setText(pregunta.getRespuesta1());
				nodoPregunta.addContent(nodoRespuesta1);

				Element nodoRespuesta2 = new Element("respuesta2");
				nodoRespuesta2.setText(pregunta.getRespuesta2());
				nodoPregunta.addContent(nodoRespuesta2);

				Element nodoRespuesta3 = new Element("respuesta3");
				nodoRespuesta3.setText(pregunta.getRespuesta3());
				nodoPregunta.addContent(nodoRespuesta3);

				Element nodoCorrecta = new Element("correcta");
				correcta = pregunta.getCorrecta() + "";
				nodoCorrecta.setText(correcta);
				nodoPregunta.addContent(nodoCorrecta);

				Format format = Format.getPrettyFormat();
				// Creamos el serializador con el formato deseado
				XMLOutputter xmloutputter = new XMLOutputter(format);
				// Serializamos nuestro nuevo document
				docNuevoStr = xmloutputter.outputString(docNuevo);

			} catch (Exception e) {
				ClaseLog.miLog.debug("Error");
				e.printStackTrace();
			}
			// Para llevar el XML a fichero
			File ficheroAux = new File(Constantes.FICHEROAUX);
			FileWriter fichero = null;
			try {
				fichero = new FileWriter(ficheroAux);
				PrintWriter pw = new PrintWriter(fichero);
				pw.println(docNuevoStr);
				fichero.close();

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("No existe el fichero");
				ClaseLog.miLog.debug("No existe el fichero");
			}

			// borrar fichero origen y renombrar el aux como el
			// origen-----------------------------------------
			xmlFile.delete();
			ficheroAux.renameTo(xmlFile);
			System.out.println(Auxiliar.rb.getString("pregunta.nuevaxml"));
		} else { // si el fichero no existe le creo con la pregunta que me
					// pasan------------------------------------

			// ahora escribimos en el fichero de destino con la nueva pregunta
			String docNuevoStr = "";
			try {
				Document docNuevo = new Document();
				Element nodoRaiz = new Element("juego");
				docNuevo.addContent(nodoRaiz);

				// ahora añado como ultimo nodo la pregunta a añadir
				Element nodoPregunta = new Element("pregunta");
				nodoRaiz.addContent(nodoPregunta);

				Element nodoTexto = new Element("texto");
				nodoTexto.setText(pregunta.getCuestion());
				nodoPregunta.addContent(nodoTexto);

				Element nodoRespuesta1 = new Element("respuesta1");
				nodoRespuesta1.setText(pregunta.getRespuesta1());
				nodoPregunta.addContent(nodoRespuesta1);

				Element nodoRespuesta2 = new Element("respuesta2");
				nodoRespuesta2.setText(pregunta.getRespuesta2());
				nodoPregunta.addContent(nodoRespuesta2);

				Element nodoRespuesta3 = new Element("respuesta3");
				nodoRespuesta3.setText(pregunta.getRespuesta3());
				nodoPregunta.addContent(nodoRespuesta3);

				Element nodoCorrecta = new Element("correcta");
				correcta = pregunta.getCorrecta() + "";
				nodoCorrecta.setText(correcta);
				nodoPregunta.addContent(nodoCorrecta);

				Format format = Format.getPrettyFormat();
				// Creamos el serializador con el formato deseado
				XMLOutputter xmloutputter = new XMLOutputter(format);
				// Serializamos nuestro nuevo document
				docNuevoStr = xmloutputter.outputString(docNuevo);

			} catch (Exception e) {
				ClaseLog.miLog.debug("Error");
				e.printStackTrace();
			}
			// Para llevar el XML a fichero
			File ficheroAux = new File(Constantes.FICHERO);
			FileWriter fichero = null;
			try {
				fichero = new FileWriter(ficheroAux);
				PrintWriter pw = new PrintWriter(fichero);
				pw.println(docNuevoStr);
				fichero.close();

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("No existe el fichero");
				ClaseLog.miLog.debug("No existe el fichero");
			}

			System.out.println(Auxiliar.rb.getString("pregunta.nuevaxml"));
		}

	}

	/**
	 * Metodo para añadir preguntas de un fichero excel
	 */
	@Override
	public void addPreguntaExcel() {
		Scanner teclado = new Scanner(System.in);
		String nombre = "";
		// pedir el fichero
		System.out.println(Auxiliar.rb.getString("servicio.importarexcel"));
		System.out.print(Auxiliar.rb.getString("servicio.nombref"));
		nombre = teclado.nextLine();
		if (!nombre.equals("n")) {
			String nombreFichero = "./ficheros/" + nombre;
			// lanzar proceso en dao
			añadirPreguntas(nombreFichero);
		}else {
			System.out.println(Auxiliar.rb.getString("servicio.cancelado"));
		}
		
	}

	/**
	 * Metodo de apoyo  addPreguntaExcel
	 * @param nombreFichero
	 */
	private void añadirPreguntas(String nombreFichero) {
		File fichero = new File(nombreFichero);
		try {
			Workbook w = Workbook.getWorkbook(fichero);
			// Se lee la primera hoja de la excel
			Sheet sheet = w.getSheet(0);
			//leerExcel(sheet, persiste);
			leerExcel(sheet);
		} catch (Exception e) {
			ClaseLog.miLog.debug("Error");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo de apoyo  addPreguntaExcel
	 * @param nombreFichero
	 */
	private void leerExcel(Sheet sheet) {

		ArrayList<Pregunta> pregunta = new ArrayList<Pregunta>();
		
		String[] linea = new String[Constantes.NUMEROATRIBUTOSCLASEPREGUNTA];		//pongo 5 pq es el número de atributos de mi clase Pregunta pero ???????????????????
		for (int f = 0; f < sheet.getRows(); f++) {
			for (int c = 0; c < sheet.getColumns(); c++) {
				linea[c]  = sheet.getCell(c, f).getContents();
			}
			Pregunta pLeida = new Pregunta(linea[0], linea[1], linea[2], linea[3], Integer.parseInt(linea[4]));
			pregunta.add(pLeida);
		}
		
		anadirPreguntaExcel(pregunta);
	}


	/**
	 * Metodo de apoyo  addPreguntaExcel
	 * @param nombreFichero
	 */
	private void anadirPreguntaExcel(ArrayList<Pregunta> pregunta) {
		
		ArrayList<Pregunta> listaPreguntasAux = new ArrayList<Pregunta>();
		String nombre;
		String respuesta1;
		String respuesta2;
		String respuesta3;
		String correcta;
		int correctaI;

		// Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();

		File xmlFile = new File(Constantes.FICHERO);

		if (xmlFile.exists()) { // si el fichero existe añado la
								// ppregunta-------------------------------------------
			try {
				// Se crea el documento a traves del archivo
				Document document = builder.build(xmlFile);
				// Se obtiene la raiz 'juego'
				Element rootNode = document.getRootElement();
				// Se obtiene el nombre del nodo raiz
				String nombreNodo = rootNode.getName();
				// Se obtiene la lista de hijos del nodo raiz
				List listaPreguntas = rootNode.getChildren("pregunta");
				// Se recorre la lista de hijos del nodo
				for (int i = 0; i < listaPreguntas.size(); i++) {
					// Se obtiene el elemento 'pregunta'
					Element cuestion = (Element) listaPreguntas.get(i);

					nombre = cuestion.getChildText("texto");
					respuesta1 = cuestion.getChildText("respuesta1");
					respuesta2 = cuestion.getChildText("respuesta2");
					respuesta3 = cuestion.getChildText("respuesta3");
					correcta = cuestion.getChildText("correcta");
					correctaI = Integer.parseInt(correcta);

					Pregunta pLeida = new Pregunta(nombre, respuesta1, respuesta2, respuesta3, correctaI);
					listaPreguntasAux.add(pLeida);
				}

			} catch (IOException e) {
				ClaseLog.miLog.debug("Error");
				e.printStackTrace();
			} catch (Exception e) {
				ClaseLog.miLog.debug("Error");
				e.printStackTrace();
			}

			// ahora escribimos en el fichero de destino con las nuevas preguntas
			String docNuevoStr = "";
			try {
				Document docNuevo = new Document();
				Element nodoRaiz = new Element("juego");
				docNuevo.addContent(nodoRaiz);

				for (int i = 0; i < listaPreguntasAux.size(); i++) {
					Element nodoPregunta = new Element("pregunta");
					nodoRaiz.addContent(nodoPregunta);

					Element nodoTexto = new Element("texto");
					nodoTexto.setText(listaPreguntasAux.get(i).getCuestion());
					nodoPregunta.addContent(nodoTexto);

					Element nodoRespuesta1 = new Element("respuesta1");
					nodoRespuesta1.setText(listaPreguntasAux.get(i).getRespuesta1());
					nodoPregunta.addContent(nodoRespuesta1);

					Element nodoRespuesta2 = new Element("respuesta2");
					nodoRespuesta2.setText(listaPreguntasAux.get(i).getRespuesta2());
					nodoPregunta.addContent(nodoRespuesta2);

					Element nodoRespuesta3 = new Element("respuesta3");
					nodoRespuesta3.setText(listaPreguntasAux.get(i).getRespuesta3());
					nodoPregunta.addContent(nodoRespuesta3);

					Element nodoCorrecta = new Element("correcta");
					correcta = (listaPreguntasAux.get(i).getCorrecta() + "");
					nodoCorrecta.setText(correcta);
					nodoPregunta.addContent(nodoCorrecta);
				}
				// ahora añado como ultimos nodos las preguntas a añadir del fichero excel
				for (int i = 0; i < pregunta.size(); i++) {
					Element nodoPregunta = new Element("pregunta");
					nodoRaiz.addContent(nodoPregunta);

					Element nodoTexto = new Element("texto");
					nodoTexto.setText(pregunta.get(i).getCuestion());
					nodoPregunta.addContent(nodoTexto);

					Element nodoRespuesta1 = new Element("respuesta1");
					nodoRespuesta1.setText(pregunta.get(i).getRespuesta1());
					nodoPregunta.addContent(nodoRespuesta1);

					Element nodoRespuesta2 = new Element("respuesta2");
					nodoRespuesta2.setText(pregunta.get(i).getRespuesta2());
					nodoPregunta.addContent(nodoRespuesta2);

					Element nodoRespuesta3 = new Element("respuesta3");
					nodoRespuesta3.setText(pregunta.get(i).getRespuesta3());
					nodoPregunta.addContent(nodoRespuesta3);

					Element nodoCorrecta = new Element("correcta");
					correcta = pregunta.get(i).getCorrecta() + "";
					nodoCorrecta.setText(correcta);
					nodoPregunta.addContent(nodoCorrecta);
				}
				Format format = Format.getPrettyFormat();
				// Creamos el serializador con el formato deseado
				XMLOutputter xmloutputter = new XMLOutputter(format);
				// Serializamos nuestro nuevo document
				docNuevoStr = xmloutputter.outputString(docNuevo);

			} catch (Exception e) {
				ClaseLog.miLog.debug("Error");
				e.printStackTrace();
			}
			// Para llevar el XML a fichero
			File ficheroAux = new File(Constantes.FICHEROAUX);
			FileWriter fichero = null;
			try {
				fichero = new FileWriter(ficheroAux);
				PrintWriter pw = new PrintWriter(fichero);
				pw.println(docNuevoStr);
				fichero.close();

			} catch (IOException e) {
				e.printStackTrace();
				ClaseLog.miLog.debug("Error. No E fichero");
				System.out.println("No existe el fichero");
			}

			// borrar fichero origen y renombrar el aux como el
			// origen-----------------------------------------
			xmlFile.delete();
			ficheroAux.renameTo(xmlFile);
			System.out.println(Auxiliar.rb.getString("pregunta.importacion"));
		} else { // si el fichero no existe le creo con la pregunta que me pasan----------------------------------------------
			    // ahora escribimos en el fichero de destino con la nueva pregunta---------------------------------------------
			String docNuevoStr = "";
			try {
				Document docNuevo = new Document();
				Element nodoRaiz = new Element("juego");
				docNuevo.addContent(nodoRaiz);
				// ahora añado como nodos las preguntas a añadir del fichero excel
				for (int i = 0; i < pregunta.size(); i++) {
					Element nodoPregunta = new Element("pregunta");
					nodoRaiz.addContent(nodoPregunta);

					Element nodoTexto = new Element("texto");
					nodoTexto.setText(pregunta.get(i).getCuestion());
					nodoPregunta.addContent(nodoTexto);

					Element nodoRespuesta1 = new Element("respuesta1");
					nodoRespuesta1.setText(pregunta.get(i).getRespuesta1());
					nodoPregunta.addContent(nodoRespuesta1);

					Element nodoRespuesta2 = new Element("respuesta2");
					nodoRespuesta2.setText(pregunta.get(i).getRespuesta2());
					nodoPregunta.addContent(nodoRespuesta2);

					Element nodoRespuesta3 = new Element("respuesta3");
					nodoRespuesta3.setText(pregunta.get(i).getRespuesta3());
					nodoPregunta.addContent(nodoRespuesta3);

					Element nodoCorrecta = new Element("correcta");
					correcta = pregunta.get(i).getCorrecta() + "";
					nodoCorrecta.setText(correcta);
					nodoPregunta.addContent(nodoCorrecta);
				}

				Format format = Format.getPrettyFormat();
				// Creamos el serializador con el formato deseado
				XMLOutputter xmloutputter = new XMLOutputter(format);
				// Serializamos nuestro nuevo document
				docNuevoStr = xmloutputter.outputString(docNuevo);

			} catch (Exception e) {
				ClaseLog.miLog.debug("Error");
				e.printStackTrace();
			}
			// Para llevar el XML a fichero
			File ficheroAux = new File(Constantes.FICHERO);
			FileWriter fichero = null;
			try {
				fichero = new FileWriter(ficheroAux);
				PrintWriter pw = new PrintWriter(fichero);
				pw.println(docNuevoStr);
				fichero.close();

			} catch (IOException e) {
				e.printStackTrace();
				ClaseLog.miLog.debug("Error. No E fichero");
				System.out.println("No existe el fichero");
			}

			System.out.println(Auxiliar.rb.getString("pregunta.importacion"));
		}
		
	}

	/**
	 * Metodo que devuelve la lista de records
	 */
	@Override
	public ArrayList<Record> obtenerListaRecords() {
		ArrayList<Record> record = new ArrayList<Record>();
		// llamo al dao para que me devuelva los datos
		record =listarRecords(record);
		// con el array con los datos lo muestro por pantalla
		Collections.sort(record); // con esto ordeno la lista de objetos record
		System.out.println(Auxiliar.rb.getString("servicio.listado"));
		System.out.println(Auxiliar.rb.getString("servicio.fecharecord")+"---------------------NICK--------"+Auxiliar.rb.getString("servicio.puntos"));

		for (int i = 0; i < record.size(); i++) {
			System.out.println(
					record.get(i).getFecha() + " - " + record.get(i).getNick() + " - 	" + record.get(i).getPuntos());
		}		
		return record;
	}

	/**
	 * Metodo para mostrar por pantalla el fichero de redords
	 * @param record
	 * @return
	 */
	public static ArrayList<Record> listarRecords(ArrayList<Record> record) {
		String fichero = Constantes.FICHERORECORDS;
		String fechaR, nickR, puntosR;
		int puntosI;

		File file = new File(fichero);
		if (file.exists()) {
			// En caso de que exista, intentamos leer
			try {

				BufferedReader br = new BufferedReader(new FileReader(file));
				String linea = br.readLine();

				// se lee linea a linea
				// si es null es que ha llegado al final del fichero
				while (linea != null) {

					fechaR = linea.substring(7, linea.indexOf('-') - 5);
					nickR = linea.substring(linea.indexOf('-') + 3, linea.indexOf('/') - 1);
					puntosR = linea.substring(linea.indexOf('*') + 3, linea.length());
					puntosI = Integer.parseInt(puntosR);

					Record recordR = new Record(puntosI, nickR, fechaR);
					record.add(recordR);
					linea = br.readLine();
				}

				br.close();
			} catch (IOException errorDeFichero) {
				ClaseLog.miLog.debug("Error");
				System.out.println("Ha habido problemas: " + errorDeFichero.getMessage());
			} catch (Exception e) {
				ClaseLog.miLog.debug("Error");
				System.out.println("Ha habido problemas: " + e.getMessage());
			} finally {

			}
		} else {
			System.out.println(Auxiliar.rb.getString("record.vacio"));
		}

		return record;
	}

	/**
	 * Metodo para grabar los records
	 */
	@Override
	public String grabarEnFicheroRecords(int puntos) {
		char opcion = '0';
		String nickF = "";
		String fecha;
		Calendar calendar = new GregorianCalendar();
		Scanner teclado = new Scanner(System.in);
		
		do {
			System.out.print(Auxiliar.rb.getString("servicio.puntuacion1") + puntos + Auxiliar.rb.getString("servicio.puntuacion2"));
			opcion = teclado.next().charAt(0);
		} while ((opcion != 's') && (opcion != 'n'));

		teclado.nextLine();// reseteo el teclado

		if (opcion == 's') { // si quiere grabarla le pido el nick y lanzo el proceso de grabación del
								// fichero
			do {
				System.out.print(Auxiliar.rb.getString("servicio.nick"));
				nickF = teclado.nextLine();
			} while ((nickF.indexOf('-') != -1) || (nickF.indexOf('*') != -1) || (nickF.indexOf('/') != -1)
					|| (nickF.equals("")));

			fecha = calendar.getTime().toString();

			Record record = new Record(puntos, nickF, fecha);
			
			addRecord(record);
		}
		return nickF;
	}	

	
	
	/**
	 * Metodo de apoyo a grabarEnFichweroRecords
	 * @param record
	 */
	public static void addRecord(Record record) {
		Boolean encontrado = false;
		String linea, lineaAux;
		String nick, puntosR;
		int puntosI;
		File fileEscritura = new File(Constantes.FICHERORECORDSAUX);
		File fileLectura = new File(Constantes.FICHERORECORDS);

		if (!fileLectura.exists()) {
			creaFicheroRecord(record);
		} else {

			BufferedWriter bw = null;
			BufferedReader br = null;

			try {
				br = new BufferedReader(new FileReader(fileLectura));
				bw = new BufferedWriter(new FileWriter(fileEscritura, true));

				linea = br.readLine();

				// se lee linea a linea
				// si es null es que ha llegado al final del fichero
				while (linea != null) {
					nick = linea.substring(linea.indexOf('-') + 3, linea.indexOf('/') - 1);
					if (nick.equals(record.getNick())) {
						encontrado = true;
						puntosR = linea.substring(linea.indexOf('*') + 3, linea.length());
						puntosI = Integer.parseInt(puntosR);
						if (puntosI < record.getPuntos()) {
							linea = Auxiliar.rb.getString("record.fecha") + record.getFecha() + " NICK-: " + record.getNick() + " " + Auxiliar.rb.getString("record.puntos")
									+ record.getPuntos();
							System.out.println(Auxiliar.rb.getString("record.nuevo"));
						}else {
							System.out.println(Auxiliar.rb.getString("record.nonuevo"));
						}
					}
					bw.write(linea);
					bw.newLine();
					linea = br.readLine();
					if ((linea == null) && (!encontrado)) {                //llego al final del fichero y no lo he encontrado. Lo añado
						lineaAux = Auxiliar.rb.getString("record.fecha") + record.getFecha() + " NICK-: " + record.getNick() + " " + Auxiliar.rb.getString("record.puntos")
								+ record.getPuntos();
						bw.write(lineaAux);
						System.out.println(Auxiliar.rb.getString("record.nuevo"));
					}
				}
				br.close();
				bw.close();
			} catch (IOException errorDeFichero) {
				ClaseLog.miLog.debug("Error");
				System.out.println("Ha habido problemas: " + errorDeFichero.getMessage());
			} finally {
				try {
					br.close();
					bw.close();
				} catch (IOException e) {
					ClaseLog.miLog.debug("Error");
					System.out.println("Problemas al grabar el fichero de records.");
					e.printStackTrace();
				}
			}
			// renombrar ficheros
			fileLectura.delete();
			fileEscritura.renameTo(fileLectura);
		}
	}
	
	
	/**
	 * Metodo para crear el fichero de record si no existe
	 * @param record
	 */
	private static void creaFicheroRecord(Record record) {
		String linea = Auxiliar.rb.getString("record.fecha") + record.getFecha() + " NICK-: " + record.getNick() + " "+Auxiliar.rb.getString("record.puntos")
				+ record.getPuntos();

		File file = new File(Constantes.FICHERORECORDS);

		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(linea);
			bw.newLine();
			bw.close();
			System.out.println(Auxiliar.rb.getString("record.nuevo"));
		} catch (IOException errorDeFichero) {
			ClaseLog.miLog.debug("Error");
			System.out.println("Ha habido problemas: " + errorDeFichero.getMessage());
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				ClaseLog.miLog.debug("Error");
				System.out.println("Problemas al grabar el fichero de records.");
				e.printStackTrace();
			}
		}
	}	
	

}
