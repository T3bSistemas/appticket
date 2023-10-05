package appticket.modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.stereotype.Service;

import appticket.beans.DetalleTickets;
import appticket.beans.Domicilio;
import appticket.beans.Fclientes;
import appticket.beans.GenerarFactura;
import appticket.beans.Ticket;
import appticket.beans.TicketDetalle;
import appticket.conexion.Conexion;
import appticket.consultas.Cticket;
import appticket.interfaces.Iticket;
import appticket.utilidades.Utilidades;

@Service
public class Mticket implements Iticket{
	Conexion 			c 		= new Conexion();	
	Connection 			con		= null;
	PreparedStatement 	ps 		= null;
	ResultSet 			rs		= null;
	
	Connection 			con2	= null;
	PreparedStatement 	ps2 	= null;
	ResultSet 			rs2		= null;
	
	PreparedStatement 	ps3 	= null;
	ResultSet 			rs3		= null;
	
	@Override
	public Ticket getTicket(Ticket ticket) {
		try {					
			boolean ecom 	= ticket.getCaja().equals("99");
					con 	= (ecom)? c.getConexionEcom(): c.getConexionR(ticket.getConexion());			
			int     num		= 0;
			if(con != null) {
				ps 		= con.prepareStatement((ecom)?Cticket.ECMFNFACTURAECOMENCABEZADO.toString():Cticket.PVFPAGOSVENTASIN.toString());
				ps.setInt(	 1, ticket.getTienda());
				ps.setString(2, ticket.getFechaCompra());
				ps.setString(3, ticket.getCaja());
				ps.setString(4, ticket.getTicket());
				rs		= ps.executeQuery();
				while(rs.next()) {
					if(num == 0) {	
						Utilidades u = new Utilidades();
						ticket = new Ticket(rs.getString("fecha"), rs.getInt("tclave"), rs.getString("num_ticket"), rs.getString("caja"), rs.getDouble("total"), u.getNull(rs.getString("tipo_pago")), rs.getInt("idturno"), ticket.getConexion(), ticket.getRegion());
						num++;
					}else {
						return null;
					}					
				}		
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			cerrarConexion();
		}
		return ticket;
	}
	
	@Override
	public DetalleTickets getTicketDetalle(List<Ticket> tickets) {
		try {
			for (Ticket ticket : tickets) {											
				try {
					con 	= c.getConexionS(1);
					ps 		= con.prepareStatement(Cticket.FORMASPAGO.toString());
					ps.setInt(1, Integer.parseInt(ticket.getTipoPago()));
					rs		= ps.executeQuery();
					if(rs.next()) {
						String 	claveSAT = rs.getString("clave_sat");
								claveSAT = (claveSAT.length() == 1)?"0"+claveSAT:claveSAT;
						try {
							ps 	= con.prepareStatement(Cticket.SERIE.toString());
							ps.setInt(1, ticket.getTienda());
							rs	= ps.executeQuery();
							if(rs.next()) {
								String tdir			=	rs.getString("tdir");
								String tncrvendflag	=	rs.getString("tncrvendflag");
								String temail		=	rs.getString("temail");
								if(!isNull(tdir) && !isNull(tncrvendflag) && !isNull(temail)) {
									ticket.setTicketAdi(claveSAT, tdir, tncrvendflag, temail);
									try {
										boolean	ecom 	= ticket.getCaja().equals("99");
												con2 	= (ecom)? c.getConexionEcom(): c.getConexionR(ticket.getConexion());
												ps 		= con2.prepareStatement((ecom)?Cticket.ECMFNFACTURAECOMDETALLE.toString():Cticket.PVMOVIMIENTOSDETIN.toString());
										ps.setInt(	 1, ticket.getTienda());
										ps.setString(2, ticket.getFechaCompra());
										ps.setString(3, ticket.getCaja());
										ps.setString(4, ticket.getTicket());
										rs		= ps.executeQuery();																					
										while(rs.next()) {
											String  iclave		= rs.getString("iclave");
											Integer atmcant		= rs.getInt("atmcant");
											Double 	atmventa	= rs.getDouble("atmventa");
											if( atmcant > 0 && atmventa > 0d) {	
												try {
													ps2 	= con.prepareStatement(Cticket.IVAIEPS.toString());
													ps2.setString(1, iclave);
													rs2	= ps2.executeQuery();
													if(rs2.next()) {
														String CClaveUnidad 	= rs2.getString("c_ClaveUnidad");
														String CClaveProdServ	= rs2.getString("c_ClaveProdServ");
														ticket.AgrDetalle(new TicketDetalle(iclave, atmcant, atmventa, rs.getString("iv_clave"), rs.getString("ie_clave"), rs2.getString("idesc"), rs2.getString("iunidad"), CClaveUnidad.equals("") ?"H87":CClaveUnidad , CClaveProdServ.equals("") ?"01010101":CClaveProdServ, rs2.getString("iv_factor"), rs2.getString("ie_factor"), atmventa*rs2.getDouble("iv_factor"), atmventa*rs2.getDouble("ie_factor")));
													}else {
														return new DetalleTickets(9, "Problemas de datos, detalle ticket: IVA y IEPS Tda:"+ticket.getTienda()+" Tik:"+ticket.getTicket()+" "+iclave, null);
													}
												} catch (Exception e) {
													e.printStackTrace();
													return new DetalleTickets(8, "Problemas de datos, detalle ticket: IVA y IEPS Tda:"+ticket.getTienda()+" Tik:"+ticket.getTicket()+" "+iclave, null);
												}														
											}											
										}
										
										if(ticket.getDetalles() == null || ticket.getDetalles().size() == 0) {
											return new DetalleTickets(7, "Problemas de datos, detalle ticket: ATC Y ATV < 0 o IVA IEPS NULL  Tda:"+ticket.getTienda()+" Tik:"+ticket.getTicket(), null);
										}
									} catch (Exception e) {
										e.printStackTrace();	
										return new DetalleTickets(6, "Problemas de datos, detalle ticket: Tda:"+ticket.getTienda()+" Tik:"+ticket.getTicket(), null);
									}
								}else {
									return new DetalleTickets(5, "Problemas de datos, codigo postal y/o serie de factura: Tda:"+ticket.getTienda()+" Tik:"+ticket.getTicket(), null);
								}
							}else {
								return new DetalleTickets(4, "Problemas de datos, codigo postal y/o serie de factura: Tda:"+ticket.getTienda()+" Tik:"+ticket.getTicket(), null);
							}
						} catch (Exception e) {
							e.printStackTrace();
							return new DetalleTickets(3, "Problemas de datos, codigo postal y/o serie de factura: Tda:"+ticket.getTienda()+" Tik:"+ticket.getTicket(), null);
						}		
					}else {
						return new DetalleTickets(2, "Problemas de datos, forma de pago: Tda:"+ticket.getTienda()+" Tik:"+ticket.getTicket(), null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return new DetalleTickets(1, "Problemas de datos, forma de pago: Tda:"+ticket.getTienda()+" Tik:"+ticket.getTicket(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DetalleTickets(0, "", tickets);
	}
	
	public String validacionGeneraFactura(GenerarFactura genera) {
		try {
			if(genera != null) {
				if(genera.getFclientes()  != null) {
					if(genera.getTickets() != null) {
						if(genera.getTickets().size() > 0) {
							Fclientes f = genera.getFclientes();
							if(!isNull(f.getRfc())) {
								if(!isNull(f.getRazonSocial())) {
									if(!isNull(f.getCorreo())) {
										if(!isNull(f.getRegimenFiscal())) {
											if(!isNull(f.getUsoCFDI())) {
												if(f.getDomicilio() != null) {
													Domicilio d = f.getDomicilio();
													if(!isNull(d.getCp())) {
														return "";
													}else {
														return "Se requiere un CP en la Direccion";
													}
												}else {
													return "Se necesita un objeto de tipo Direccion";
												}
											}else {
												return "Se requiere un UsoCFDI";
											}
										}else {
											return "Se requiere un regimen Fiscal";
										}
									}else {
										return "Se requiere un Correo";
									}
								}else {
									return "Se rrquiere una Razon Social";
								}
							}else {
								return "Se rrquiere un RFC";
							}							
						}else {
							return "Se requiere por lo menos un Ticket para Facturar";
						}
					}else {
						return "Se requiere los Tickets a facturar";
					}
				}else {
					return "Se requieren los datos del Receptor";
				}
			}else {
				return "Se requieren los datos del cliente y de los tickets a Facturar";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error al validar los campos";
		}		
	}
	
	public boolean isNull(String data) {
		try {
			if(data != null) {
				if(!data.trim().equals("")) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void cerrarConexion() {
		try {
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			if(rs2 != null)
				rs2.close();
			if(ps2 != null)
				ps2.close();
			if(rs3 != null)
				rs3.close();
			if(ps3 != null)
				ps3.close();
			if(con != null)
				con.close();
			if(con2 != null)
				con2.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public static void main(String arg[]) {
		try {
			Integer claveSAT = null;
			System.out.println(new Utilidades().isNullNumber(claveSAT+""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
