package appticket.beans;

import java.util.ArrayList;
import java.util.List;

public class ListRespuesta {
	List<Respuesta> respuesta;

	public ListRespuesta() {
		respuesta = new ArrayList<Respuesta>(); 
	}

	public ListRespuesta(List<Respuesta> respuesta) {
		super();
		this.respuesta = respuesta;
	}

	public List<Respuesta> getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(List<Respuesta> respuesta) {
		this.respuesta = respuesta;
	}

	@Override
	public String toString() {
		return "ListRespuesta [respuesta=" + respuesta + "]";
	}
}
