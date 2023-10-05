package appticket.beans;

import java.util.List;

public class DetalleTickets {
	private Integer 		codigo;
	private String 			descripcion;
	private List<Ticket>  	tickets;
	public DetalleTickets() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DetalleTickets(Integer codigo, String descripcion, List<Ticket> tickets) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.tickets = tickets;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public List<Ticket> getTickets() {
		return tickets;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	@Override
	public String toString() {
		return "DetalleTickets [codigo=" + codigo + ", descripcion=" + descripcion + ", tickets=" + tickets + "]";
	}
	
	
}
