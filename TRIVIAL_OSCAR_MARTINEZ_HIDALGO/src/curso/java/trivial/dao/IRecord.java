package curso.java.trivial.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import curso.java.trivial.model.Record;

/**
 * Clase para la interfaz de Records
 * @author oscar
 *
 */
public interface IRecord {
	
	public ArrayList<Record> obtenerListaRecords();
	
	public String grabarEnFicheroRecords(int puntos) throws SQLException;

}
