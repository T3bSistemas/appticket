package appticket.utilidades;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import appticket.beans.Domicilio;
import appticket.beans.Fclientes;
import appticket.beans.Ticket;

@Service
public class Utilidades {
	private static final Logger logger = LoggerFactory.getLogger(Utilidades.class);
	
	public boolean isNull(String data) {
		try {
			if(data != null) {
				if(!data.equals("")) {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("isNull- validacion Null ("+data+") :"+e.getMessage());
		}
		return true;
	}
	
	public String getNull(String data) {
		try {
			if(data != null) {
				if(!data.equals("")) {
					return  data.trim();
				}
			}
		} catch (Exception e) {
			logger.error("getNull- validacion getNull ("+data+") :"+e.getMessage());
		}
		return "";
	}
	
	public boolean isNullNumber(String data) {
		try {
			Integer.parseInt(data+"");
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public boolean isNullDouble(String data) {
		try {
			Double.parseDouble(data+"");
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidDate(String d, String dateFormat) {
	  DateFormat df = new SimpleDateFormat(dateFormat);
	  df.setLenient(false);
	  try {
	    df.parse(d);
	  } catch (ParseException e) {
	    return false;
	  }
	  return true;
	}
	
	
	public String validarFormatoTicket(Ticket ticket) {
		try {
			if(ticket != null) {
				if(!isNull(ticket.getFechaCompra())) {
					if(isValidDate(ticket.getFechaCompra(), "yyyy-MM-dd")) {
						if(ticket.getTienda() != null && isNullNumber(ticket.getTienda()+"") && ticket.getTienda() > 0 && ticket.getTienda() < 99999) {
							if(!isNull(ticket.getTicket())) {
								if(!isNull(ticket.getCaja())) {
									if(ticket.getCaja().length() == 2) {
										if(ticket.getTotal() != null && isNullDouble(ticket.getTotal()+"") && ticket.getTotal() > 0) {
											return "";
										}else {
											return "El campo total:double no debe ser null  y  debe de ser mayor a 0";
										}
									}else {
										return "El campo caja:string tiene solo 2 caracteres en formato 01,02..10";
									}								
								}else {
									return "El campo caja:string no puede ser null o vacio";
								}
							}else {
								return "El campo ticket:string no puede ser null o vacio";
							}
						}else {
							return "El campo tienda:integer no debe ser null  y  debe de ser mayor a 0 y menor a 99999";
						}
					}else {
						return "El campo fechaCompra:string tiene que tener el formato YYYY-MM-DD";
					}				
				}else {
					return "El campo fechaCompra:string no puede ser null o vacio";
				}
			} else {
				return "Se requiere un formato valido {fechaCompra:string, tienda:integer, ticket:string, caja:string, total:double}";
			}
		} catch (Exception e) {			
			return "Error al validar los campos de Ticket";
		}	
	}
	
	public String validarFormatoTickets(List<Ticket> tickets) {
		try {
			if(tickets != null) {
				if(tickets.size() > 0) {
					for (int i=0;i<tickets.size();i++) {	
						Ticket ticket = tickets.get(i);
						if(ticket != null) {
							String validarFormatoTicket = validarFormatoTicket(ticket);
							if(!validarFormatoTicket.equals("")) {
								return "En la posicion ["+i+"]: "+validarFormatoTicket;
							}
						}else {
							return "En la posicion ["+i+"]: Se requiere un formato valido {fechaCompra:string, tienda:integer, ticket:string, caja:string, total:double}";
						}				
					}
					return "";
				}else {
					return "Se requiere una lista de tickets";
				}
			}else {
				return "Se requiere un formato valido tickets:[Object]}";
			}		
		} catch (Exception e) {
			return "Error al validar los campose lista de Tickets";
		}		
	}
	
	
	public String validarDatosCliente(Fclientes f) {
		try {
			if(f != null) {
				if(!isNull(f.getRfc())) {
					Pattern pattern = Pattern.compile("^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?$", Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(f.getRfc());
				    boolean matchFound = matcher.find();
				    if(matchFound) {
				    	if(!isNull(f.getRazonSocial())) {
							if(!isNull(f.getCorreo())) {
								Pattern pattern2    = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
								Matcher matcher2    = pattern2.matcher(f.getCorreo());
							    boolean matchFound2 = matcher2.find();
							    if(matchFound2) {
							    	if(!isNull(f.getCorreo2())) {
							    		Pattern pattern3    = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
										Matcher matcher3    = pattern3.matcher(f.getCorreo2());
									    boolean matchFound3 = matcher3.find();
									    if(!matchFound3) {
									    	return "Válida tu Correo 2 sea correcto";
									    }
							    	}								    	
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
							    	return "Válida tu Correo sea correcto";
							    }
							}else {
								return "Se requiere un Correo";
							}
						}else {
							return "Se rrquiere una Razon Social";
						}
				    }else {
				    	return "Válida tu RFC sea correcto";
				    }	
				}else {
					return "Se requiere un RFC";
				}	
			}else {
				return "Se requiere un formato valido {rfc: string,razonSocial: string,correo: string,correo2: string,regimenFiscal: string,usoCFDI: string,domicilio:{cp: string}}";
			}						
		} catch (Exception e) {
			return "Error al validar los campos en validarDatosCliente";
		}		
	}
}
