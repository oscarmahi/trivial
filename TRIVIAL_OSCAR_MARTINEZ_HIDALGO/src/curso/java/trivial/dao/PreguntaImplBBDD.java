package curso.java.trivial.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import curso.java.trivial.ClaseLog;
import curso.java.trivial.auxiliares.Auxiliar;
import curso.java.trivial.auxiliares.Constantes;
import curso.java.trivial.model.Pregunta;
import curso.java.trivial.model.Record;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Clase que implementa las interfaces de Pregunta y Record en BBDD
 * @author oscar
 *
 */
public class PreguntaImplBBDD implements IPregunta, IRecord {

	/**
	 * Metodo que devuelve la lista con las preguntas
	 */
	@Override
	public ArrayList<Pregunta> obtenerListaPreguntas() throws SQLException {
		
		ArrayList<Pregunta> listaPreguntas = new ArrayList<Pregunta>();
		
		//System.out.println("Consultar registros:");
		ClaseLog.miLog.debug("Consultar registros:");
		
		String query = "SELECT * FROM preguntas";
		ClaseLog.miLog.debug(query);
		
		//System.out.println(query);
		
		Connection conexion = Conexion.getConexion();

		PreparedStatement ps = conexion.prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Pregunta pregunta = new Pregunta(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6));

			listaPreguntas.add(pregunta);
		}
		rs.close();
		ps.close();
		return listaPreguntas;		
		
	}

	/**
	 * Metodo para añadir una pregunta
	 */
	@Override
	public void addPregunta(Pregunta pregunta) throws SQLException {
		
		String query = "INSERT INTO preguntas (cuestion, respuesta1, respuesta2, respuesta3, correcta) VALUES (?, ?, ?, ?, ?)";
		
		//System.out.println("Insertar registro");
		ClaseLog.miLog.debug("Insertar registro");

		Connection conexion = Conexion.getConexion();		
		PreparedStatement ps = conexion.prepareStatement(query);

		ps.setString(1, pregunta.getCuestion());
		ps.setString(2, pregunta.getRespuesta1());
		ps.setString(3, pregunta.getRespuesta2());
		ps.setString(4, pregunta.getRespuesta3());
		ps.setInt(5, pregunta.getCorrecta());
		
		int resultado = ps.executeUpdate();

		if (resultado == 0)
			ClaseLog.miLog.debug("No se ha podido insertar la pregunta");
			System.out.println("No se ha podido insertar la pregunta");
		conexion.commit();

		ps.close();				
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
			ClaseLog.miLog.debug("Importacion cancelada");
		}	
	}

	/**
	 * Complemento a addpreguntaexcel
	 * @param nombreFichero
	 */
	private void añadirPreguntas(String nombreFichero) {
		File fichero = new File(nombreFichero);
		try {
			Workbook w = Workbook.getWorkbook(fichero);
			// Se lee la primera hoja de la excel
			Sheet sheet = w.getSheet(0);
			leerExcel(sheet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * Complemento a addpreguntaexcel
	 * @param sheet
	 * @throws SQLException
	 */
	private void leerExcel(Sheet sheet) throws SQLException {

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
	
	public void anadirPreguntaExcel(ArrayList<Pregunta> preguntas) throws SQLException {
		for (int i = 0; i < preguntas.size(); i++) {
			addPregunta(preguntas.get(i));
		}
	}	
	
	
	/**
	 * Metodo para obtener la lista de los records
	 */
	@Override
	public ArrayList<Record> obtenerListaRecords() {
		
		ArrayList<Record> record = new ArrayList<Record>();
		// llamo al dao para que me devuelva los datos
		try {
			record =listarRecords(record);
		} catch (SQLException e) {
			ClaseLog.miLog.debug("Excepcion");
			e.printStackTrace();
		}
		// con el array con los datos lo muestro por pantalla
		
		System.out.println(Auxiliar.rb.getString("servicio.listado"));
		System.out.println(Auxiliar.rb.getString("servicio.fecharecord")+"---------------------NICK--------"+Auxiliar.rb.getString("servicio.puntos"));

		for (int i = 0; i < record.size(); i++) {
			System.out.println(
					record.get(i).getFecha() + " - " + record.get(i).getNick() + " - 	" + record.get(i).getPuntos());
		}		
		return record;
	}

	/**
	 * Metodo par alistar los records por pantalla
	 * @param records
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Record> listarRecords(ArrayList<Record> records) throws SQLException {
		//System.out.println("Consultar registros:");
		ClaseLog.miLog.debug("Consultar registros:");
		
		String query = "SELECT * FROM records";
		
		//System.out.println(query);
		
		Connection conexion = Conexion.getConexion();

		PreparedStatement ps = conexion.prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Record record = new Record(rs.getInt(3),rs.getString(1),rs.getString(2));

			records.add(record);
		}
		rs.close();
		ps.close();
		return records;	
	}	
	
	/**
	 * Metodo para grabar los records
	 */
	@Override
	public String grabarEnFicheroRecords(int puntos) throws SQLException {
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
	 * Metodo que usa grabarEnFicheroRecords
	 * @param record
	 * @throws SQLException
	 */
	public void addRecord(Record record) throws SQLException {
		int puntosRecord = 0;
		
		puntosRecord = existeRecord(record);
		// mirar si existe el record
		if (puntosRecord == 0) {
			//no existe el record, asi que lo inserto
			
			String query = "INSERT INTO records (nick, fecha, puntos) VALUES (?, ?, ?)";
			
			//System.out.println("Insertar registro");
			ClaseLog.miLog.debug("Insertar registro");
			
			Connection conexion = Conexion.getConexion();		
			PreparedStatement ps = conexion.prepareStatement(query);

			ps.setString(1, record.getNick());
			ps.setString(2, record.getFecha());
			ps.setInt(3, record.getPuntos());
			
			int resultado = ps.executeUpdate();

			if (resultado == 0) {
				ClaseLog.miLog.debug("No grabado");
				System.out.println(Auxiliar.rb.getString("record.nonuevo"));
			}
			
			conexion.commit();
			ps.close();		
		}else {
			if (puntosRecord < record.getPuntos()) {
				//si es menor actualizo los puntos en la BBDD
				//System.out.println("Actualizar registro:");
				ClaseLog.miLog.debug("Actualizar registro");
				String query = "UPDATE records SET puntos = ? WHERE nick = ?";

				Connection conexion = Conexion.getConexion();
				PreparedStatement ps = conexion.prepareStatement(query);
				ps.setInt(1, record.getPuntos());
				ps.setString(2, record.getNick());
				
				int resultado = ps.executeUpdate();
				
				if (resultado == 0)
					ClaseLog.miLog.debug("grabado");
					System.out.println(Auxiliar.rb.getString("record.nuevo"));
				conexion.commit();
			}else {
				ClaseLog.miLog.debug("No grabado");
				System.out.println(Auxiliar.rb.getString("record.nonuevo"));
			}
		}
	}
	
	
	/**
	 * Metodo que devuelve si un nick existe en record para ver si lo grabo o lo actualizo
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	public int existeRecord(Record record) throws SQLException {
		int puntos = 0; 
		//System.out.println("Consultar registros de record:");
		
		ClaseLog.miLog.debug("Consultar registros de record:\"");
		String query = "SELECT puntos FROM records WHERE nick like ?";
		Connection conexion = Conexion.getConexion();	
		PreparedStatement ps = conexion.prepareStatement(query);
		ps.setString(1, record.getNick());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			puntos = rs.getInt(1); 

		}
		rs.close();
		ps.close();
		
		return puntos;
	}	
	

}
