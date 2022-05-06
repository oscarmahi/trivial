package curso.java.trivial.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import curso.java.trivial.model.Pregunta;

/**
 * Interface para la clase preguntas
 * @author oscar
 *
 */
public interface IPregunta {
	
	public ArrayList<Pregunta> obtenerListaPreguntas() throws SQLException;
	
	public void addPregunta(Pregunta preguunta) throws SQLException;
	
	public void addPreguntaExcel();


	
}
