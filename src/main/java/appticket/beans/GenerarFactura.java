package appticket.beans;

import java.util.List;


public class GenerarFactura {
	Fclientes fclientes;
	List<Ticket> tickets;
	public GenerarFactura() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GenerarFactura(Fclientes fclientes, List<Ticket> tickets) {
		super();
		this.fclientes = fclientes;
		this.tickets = tickets;
	}
	public Fclientes getFclientes() {
		return fclientes;
	}
	public List<Ticket> getTickets() {
		return tickets;
	}
	public void setFclientes(Fclientes fclientes) {
		this.fclientes = fclientes;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
}
