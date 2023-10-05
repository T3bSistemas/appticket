package appticket.beans;

public class Respuesta {
	private String 	codigo;
	private Integer tienda;
	private String 	pdf;
	private String 	xml;
	
	public Respuesta() {
		super();
		// TODO Auto-generated constructor stub
	}	
	public Respuesta(String codigo, Integer tienda, String pdf, String xml) {
		super();
		this.codigo = codigo;
		this.tienda = tienda;
		this.pdf = pdf;
		this.xml = xml;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getPdf() {
		return pdf;
	}
	public String getXml() {
		return xml;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public Integer getTienda() {
		return tienda;
	}
	public void setTienda(Integer tienda) {
		this.tienda = tienda;
	}
	@Override
	public String toString() {
		return "Respuesta [codigo=" + codigo + ", tienda=" + tienda + ", pdf=" + pdf + ", xml=" + xml + "]";
	}
}
