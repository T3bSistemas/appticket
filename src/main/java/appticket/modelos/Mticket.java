package appticket.modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import appticket.beans.Ticket;
import appticket.beans.TicketDetalle;
import appticket.conexion.Conexion;
import appticket.consultas.Cticket;
import appticket.interfaces.Iticket;
import appticket.utilidades.Utilidades;

@Service
public class Mticket implements Iticket{
	private static final Logger logger = LoggerFactory.getLogger(Mticket.class);
	@Autowired
	Conexion c;	
	
	@Autowired
	Utilidades ut;
	
	@Override
	public Ticket getTicket(Ticket ticket) {
		Connection 		    con = null;
		PreparedStatement 	ps  = null;
		ResultSet 			rs  = null;
		PreparedStatement 	ps2  = null;
		ResultSet 			rs2  = null;
		try {					
			boolean ecom 	= ticket.getCaja().equals("99");
				    con 	= (ecom)? c.getConexionEcom(): c.getConexionR(ticket.getConexion());	
			if(con != null) {
				ps 		= con.prepareStatement((ecom)?Cticket.ECMFNFACTURAECOMENCABEZADO.toString():Cticket.PVFPAGOSVENTASIN.toString());
				ps.setInt(	 1, ticket.getTienda());
				ps.setString(2, ticket.getFechaCompra());
				ps.setString(3, ticket.getCaja());
				ps.setString(4, ticket.getTicket());
				rs		= ps.executeQuery();
				ticket.setTotal(0.0);
				while(rs.next()) {
					if(ticket.getTotal() == 0.0) {	
						ticket = new Ticket(rs.getString("fecha"), rs.getInt("tclave"), rs.getString("num_ticket"), rs.getString("caja"), rs.getDouble("total"), ut.getNull(rs.getString("tipo_pago")), rs.getInt("idturno"), ticket.getConexion(), ticket.getRegion(), ticket.getFclientes());
					}else {
						ticket.setTotal(ticket.getTotal()+rs.getDouble("total"));
					}					
				}	
				
				if(ticket.getTotal() > 0) {
					if(con != null) {
						List<TicketDetalle> detalle = new ArrayList<TicketDetalle>();
						ps2 		= con.prepareStatement((ecom)?Cticket.ECMFNFACTURAECOMDETALLE.toString():Cticket.PVMOVIMIENTOSDETIN.toString());
						ps2.setInt(	 1, ticket.getTienda());
						ps2.setString(2, ticket.getFechaCompra());
						ps2.setString(3, ticket.getCaja());
						ps2.setString(4, ticket.getTicket());
						rs2		= ps2.executeQuery();																					
						while(rs2.next()) {				
							detalle.add(new TicketDetalle(rs2.getString("iclave"), rs2.getInt("atmcant"), rs2.getDouble("atmventa"), rs2.getString("iv_clave"), rs2.getString("ie_clave"), (ecom)?"":rs2.getString("atmdesc"), (ecom)?"":rs2.getString("gclave"), (ecom)?"":rs2.getString("lclave")));
						}
						ticket.setDetalle(detalle);
					}
				}
			}else {
				return null;
			}
		} catch (Exception e) {
			logger.error("getTicket- Consultar Ticket VTN: "+e.getMessage());	
			return null;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(con != null)
					con.close();
			} catch (Exception e2) {
				logger.error("cerrarConexion- Cerrar Conexion: "+e2.getMessage());
			}
		}
		return ticket;
	}
}
