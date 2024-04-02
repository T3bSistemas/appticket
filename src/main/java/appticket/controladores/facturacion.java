package appticket.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import appticket.beans.Ticket;
import appticket.modelos.Mticket;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/t3b-fact-ticket")
public class facturacion {
	private static final Logger logger = LoggerFactory.getLogger(facturacion.class);
	
	@Autowired
	Mticket mt;
	
	@GetMapping({"/",""})
	public String inicio(){	
		return "Facturacion T3B Ticket V1.1";
	}
	
	@PostMapping("/getTicket")
	public Ticket getTicket(@RequestBody(required = false) Ticket ticket){	
		try {
			ticket =  mt.getTicket(ticket);	
		} catch (Exception e) {
			logger.error("getTicket- Erro en Servicio Obtener en - VTN");
		}		
		return ticket;
	}
}
