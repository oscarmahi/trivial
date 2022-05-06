package curso.java.trivial.model;

/**
 * Clase POJO de Record y para usar compareTo par aordenar la lista
 * @author oscar
 *
 */
public class Record implements Comparable<Record>{
	
	private int puntos;
	private String nick;
	private String fecha;
	
	public Record(int puntos, String nick, String fecha) {
		this.puntos = puntos;
		this.nick = nick;
		this.fecha = fecha;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public int compareTo(Record o) {	//lo voy a ordenar de menor a mayor
		if (o.getPuntos()>puntos) {
			return 1; 					//se pondria aqui -1 para ordenarlo de menor a mayor
		}else if (o.getPuntos()==puntos) {
			return 0;
		}else {
			return -1;  				//se pondria aqui 1 para ordenarlo de menor a mayor
		}
	}	

}
