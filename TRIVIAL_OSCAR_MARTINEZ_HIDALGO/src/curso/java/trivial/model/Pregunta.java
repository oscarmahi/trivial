package curso.java.trivial.model;

/**
 * Clase POJO de Pregunta
 * @author oscar
 *
 */
public class Pregunta{
	
	private String cuestion;
	private String respuesta1;
	private String respuesta2;
	private String respuesta3;
	private int correcta;

	public Pregunta() {
		
	}
	
	public Pregunta(String cuestion, String respuesta1, String respuesta2, String respuesta3, int correcta) {
		this.cuestion = cuestion;
		this.respuesta1 = respuesta1;
		this.respuesta2 = respuesta2;
		this.respuesta3 = respuesta3;
		this.correcta = correcta;
	}

	public String getCuestion() {
		return cuestion;
	}

	public void setCuestion(String cuestion) {
		this.cuestion = cuestion;
	}

	public String getRespuesta1() {
		return respuesta1;
	}

	public void setRespuesta1(String respuesta1) {
		this.respuesta1 = respuesta1;
	}

	public String getRespuesta2() {
		return respuesta2;
	}

	public void setRespuesta2(String respuesta2) {
		this.respuesta2 = respuesta2;
	}

	public String getRespuesta3() {
		return respuesta3;
	}

	public void setRespuesta3(String respuesta3) {
		this.respuesta3 = respuesta3;
	}

	public int getCorrecta() {
		return correcta;
	}

	public void setCorrecta(int correcta) {
		this.correcta = correcta;
	}

}
