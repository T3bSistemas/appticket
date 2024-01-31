package appticket.interfaces;

import java.util.List;

import appticket.beans.DetalleTickets;
import appticket.beans.GenerarFactura;
import appticket.beans.Ticket;


public interface Iticket {
	public Ticket 			getTicket(Ticket ticket);
	public Ticket 	        getTicketDetalle(Ticket tickets);
	public DetalleTickets 	getTicketsDetalles(List<Ticket> tickets);
	public String 			validacionGeneraFactura(GenerarFactura genera);
	public boolean 			isNull(String data);
	public void 			cerrarConexion();
	
}
