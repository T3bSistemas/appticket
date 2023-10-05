package appticket.beans;

public class ExisteFactura {
	private int 			existe;
	private Ticket			ticket;
	
	public ExisteFactura() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExisteFactura(int existe, Ticket ticket) {
		super();
		this.existe = existe;
		this.ticket = ticket;
	}
	public int getExiste() {
		return existe;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setExiste(int existe) {
		this.existe = existe;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	@Override
	public String toString() {
		return "ExisteFactura [existe=" + existe + ", ticket=" + ticket + "]";
	}	
}
