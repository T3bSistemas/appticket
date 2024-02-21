package appticket.controladores;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import appticket.beans.DetalleTickets;
import appticket.beans.ExisteFactura;
import appticket.beans.GenerarFactura;
import appticket.beans.Solicitudes;
import appticket.beans.Ticket;
import appticket.modelos.Mregion;
import appticket.modelos.Mticket;
import appticket.utilidades.Utilidades;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/t3b-fact-ticket")
public class facturacion {
	
	@Autowired
	Mregion rg;
	
	@Autowired
	Mticket mt;
	
	@GetMapping({"/",""})
	public String inicio(){	
		return "Facturacion T3B Ticket V1.0";
	}
	
	@PostMapping("/getRegion")
	public Ticket getRegion(@RequestBody(required = true) Ticket ticket){	
		Utilidades u = new Utilidades();
		if( u.isNullNumber(ticket.getTienda()+"")) {
			if(!u.isNull(ticket.getFechaCompra())) {
				String region = rg.getRegion(1, ticket.getTienda(), ticket.getFechaCompra());
				if(region != null && !region.equals("")) {
					ticket.setRegion(region);
					return ticket;
				}			
			}
		}		
		return null;
	}
	
	@PostMapping("/getTicket")
	public Ticket getTicket(@RequestBody(required = true) Ticket ticket){
		ticket =  mt.getTicket(ticket);
		if(ticket != null) {
			ticket = mt.getTicketDetalle(ticket);
			return ticket;						
		}	
		return null;
	}
	
	@PostMapping("/agregarTicket")
	public Ticket agregarTicket(@RequestBody(required = true) Ticket ticket){	
		Utilidades u = new Utilidades();
		if( u.isNullNumber(ticket.getTienda()+"")) {
			if(!u.isNull(ticket.getFechaCompra())) {
				String region = rg.getRegion(1, ticket.getTienda(), ticket.getFechaCompra());
				if(region != null && !region.equals("")) {
					ticket.setRegion(region);
					try {
						RestTemplate 					restTemplate	= new RestTemplate();
						HttpEntity<Ticket> 			    request 		= new HttpEntity<Ticket>(ticket);
						ResponseEntity<ExisteFactura>	response 		= restTemplate.exchange("https://appdashboard3b.azurewebsites.net/t3b-fact-das/existeFactura", HttpMethod.POST, request, new ParameterizedTypeReference<ExisteFactura>(){});
						//ResponseEntity<ExisteFactura>	response 		= restTemplate.exchange("http://localhost:8082/t3b-fact-das/existeFactura", HttpMethod.POST, request, new ParameterizedTypeReference<ExisteFactura>(){});
						ExisteFactura					respuesta		= response.getBody();
						if(respuesta.getExiste() == 0) {
							ticket =  mt.getTicket(respuesta.getTicket());
							if(ticket != null) {
								if(ticket.getTipoPago() != null && !ticket.getTipoPago().equals("")) {
									ticket = mt.getTicketDetalle(ticket);
								}
								return ticket;
							}		
						}else {
							return respuesta.getTicket();
						}						
					} catch (Exception e) {
						e.printStackTrace();						
					}
				}else {
					return ticket;
				}
			}
		}		
		return null;
	}
	
	@PostMapping("/generarFactura")
	public List<Ticket> generarFactura(@RequestBody(required = true) GenerarFactura genera){
		String 			validacion 	= mt.validacionGeneraFactura(genera);
		if(validacion.equals("")) {
			DetalleTickets dtTick = mt.getTicketsDetalles(genera.getTickets());
			List<Ticket> tickets  = dtTick.getTickets();			
			if(tickets != null && tickets.size() == genera.getTickets().size()) {
				genera.setTickets(tickets);	
				try {
					RestTemplate 					restTemplate	= new RestTemplate();
					HttpEntity<GenerarFactura> 		request 		= new HttpEntity<GenerarFactura>(genera);
					ResponseEntity<List<Ticket>> 	response 		= restTemplate.exchange("https://appfactura.azurewebsites.net/t3b-facturacion/generarFactura", HttpMethod.POST, request, new ParameterizedTypeReference<List<Ticket>>(){});
					//ResponseEntity<List<Ticket>> 	response 		= restTemplate.exchange("http://localhost:8083/t3b-facturacion/generarFactura", HttpMethod.POST, request, new ParameterizedTypeReference<List<Ticket>>(){});
													tickets 		= response.getBody();	
					if(tickets.size() > 0) {
						boolean segir = true;
						for (Ticket ticket : tickets) {
							if(ticket.getFolio() == null || ticket.getFolio().equals("")){
								segir= false;
								break;
							}
						}	
						List<Solicitudes> solicitudes = new ArrayList<Solicitudes>();
						if(segir) {
							genera.setTickets(tickets);	
							try {
								restTemplate	= new RestTemplate();
								HttpEntity<GenerarFactura> 		requestG 		= new HttpEntity<GenerarFactura>(genera);
								ResponseEntity<Boolean> 	    responseG 		= restTemplate.exchange("https://appdashboard3b.azurewebsites.net/t3b-fact-das/guardarFactura", HttpMethod.POST, requestG, new ParameterizedTypeReference<Boolean>(){});
								//ResponseEntity<Boolean> 	    responseG 		= restTemplate.exchange("http://localhost:8082/t3b-fact-das/guardarFactura", HttpMethod.POST, requestG, new ParameterizedTypeReference<Boolean>(){});
								if(responseG.getBody()) {
									for (Ticket ticket : tickets) {
											solicitudes.add(new Solicitudes(1, genera.getFclientes().getRfc(), ticket.getTienda()+"", ticket.getCaja(), ticket.getTicket(), ticket.getFechaCompra(),"", "Facturada", ticket.getFolio()+" "+ticket.getTncrvendflag(), 0, "FINALIZADA", 0, "T3B"));
									}
									if(solicitudes.size() > 0) {
										try {
											restTemplate	= new RestTemplate();
											HttpEntity<List<Solicitudes>> 	requestS 		= new HttpEntity<List<Solicitudes>>(solicitudes);
											restTemplate.exchange("https://appdashboard3b.azurewebsites.net/t3b-fact-das/guardarSolicitud", HttpMethod.POST, requestS, new ParameterizedTypeReference<Boolean>(){});
											//restTemplate.exchange("http://localhost:8082/t3b-fact-das/guardarSolicitud", HttpMethod.POST, requestS, new ParameterizedTypeReference<Boolean>(){});
										} catch (Exception e) {
											e.printStackTrace();
										}																
									}
									return tickets;	
								}
							} catch (Exception e) {
								e.printStackTrace();
							}							
						} else {	
							for (Ticket ticket : tickets) {
								if(ticket.getXml() != null && !ticket.getXml().equals("")) {
									int 	status_sol 	= 0;
									String 	tipo		= "";
									if(ticket.getXml().contains("nombre del receptor") ) {
										status_sol = 3;
										tipo="NOM REC";
										ticket.setXml("Favor de validar que la Razón Social o nombre se encuentre registrado tal y como aparece en su Constancia de Situación Fiscal actualizada");
									}else if(ticket.getXml().contains("DomicilioFiscalReceptor") ) {
										status_sol = 3;
										tipo="CP INVALIDO";
										ticket.setXml("Error de captura, favor de validar que el Código Postal se encuentre registrado tal y como aparece en su Constancia de Situación Fiscal actualizada");
									}else if(ticket.getXml().contains("regimenfiscal") ) {
										status_sol = 3;
										tipo="CP INVALIDO";
										ticket.setXml("Error de Captura, favor de validar que el Régimen Fiscal se encuentre registrado tal y como aparece en su Constancia de Situación Fiscal actualizada");
									}else if(ticket.getXml().contains("RFC") || ticket.getXml().contains("inscritos") || ticket.getXml().contains("XML") ) {
										status_sol = 3;
										tipo="RFC NO EXISTE";
									}else if(ticket.getXml().contains("c_UsoCFDI") ) {
										status_sol = 3;
										tipo="CFDI INC";
									}else if(ticket.getXml().contains("RegimenFiscalR") ) {
										status_sol = 3;
										tipo="REGMFISC";
									}else if(ticket.getXml().contains("LugarExpedicion") ){
										status_sol = 3;
										tipo="CP INVALIDO";
									}else if(ticket.getXml().contains("ValorUnitario") ){
										status_sol = 3;
										tipo="VENTA CERO";
									}else {
										status_sol = 0;
										tipo="INDETERMINADA";
									}
									solicitudes.add(new Solicitudes(status_sol, genera.getFclientes().getRfc(), ticket.getTienda()+"", ticket.getCaja(), ticket.getTicket(), ticket.getFechaCompra(),"", ticket.getXml(), "0", 0, tipo, 0, "T3B"));
								}								
							}
							
							if(solicitudes.size() > 0) {
								try {
									restTemplate	= new RestTemplate();
									HttpEntity<List<Solicitudes>> 	requestS 		= new HttpEntity<List<Solicitudes>>(solicitudes);
									restTemplate.exchange("https://appdashboard3b.azurewebsites.net/t3b-fact-das/guardarSolicitud", HttpMethod.POST, requestS, new ParameterizedTypeReference<Boolean>(){});
									//restTemplate.exchange("http://localhost:8082/t3b-fact-das/guardarSolicitud", HttpMethod.POST, requestS, new ParameterizedTypeReference<Boolean>(){});
								} catch (Exception e) {
									e.printStackTrace();
								}																
							}
							return tickets;	
						}
						return tickets;
						
					}								
				} catch (Exception e) {
					e.printStackTrace();
				}					
			}else {
				if(dtTick.getCodigo() > 0) {
					List<Solicitudes> solicitudes = new ArrayList<Solicitudes>();
					for (Ticket ticket : genera.getTickets()) {
						solicitudes.add(new Solicitudes(0, genera.getFclientes().getRfc(), ticket.getTienda()+"", ticket.getCaja(), ticket.getTicket(), ticket.getFechaCompra(),"", dtTick.getDescripcion(), "0", 0, "INFO. INCOMPLETA", 0, "T3B"));
					}
					if(solicitudes.size() > 0) {
						RestTemplate 					restTemplate	= new RestTemplate();
						HttpEntity<List<Solicitudes>> 	request 		= new HttpEntity<List<Solicitudes>>(solicitudes);
						restTemplate.exchange("https://appdashboard3b.azurewebsites.net/t3b-fact-das/guardarSolicitud", HttpMethod.POST, request, new ParameterizedTypeReference<Boolean>(){});
						//restTemplate.exchange("http://localhost:8082/t3b-fact-das/guardarSolicitud", HttpMethod.POST, request, new ParameterizedTypeReference<Boolean>(){});
					}
				}		
			}
		}
		return null;
	}
}
