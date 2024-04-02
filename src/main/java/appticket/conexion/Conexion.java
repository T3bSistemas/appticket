package appticket.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import appticket.beans.PftConexiones;

@Service
public class Conexion {
	private static final Logger logger = LoggerFactory.getLogger(Conexion.class);
	
	@Autowired
	Environment env;
	
	TimeZone miZonaGMT		= TimeZone.getTimeZone("GMT");
	Calendar calendarioGMT	= new GregorianCalendar(miZonaGMT);
	Date	fecha 			= new Date();
    
	
	public Connection getConexionS(int sucursal){
		return (sucursal==1)?setConnectionSat():setConnectionYema();
		//return (sucursal==1)?setMMPConnection():setConnectionYema();
	}
	
	public Connection getConexionR(PftConexiones dto) throws ClassNotFoundException, SQLException{      
		return setConnectionReg(dto);        
    }  
	
	public Connection getConexionEcom() throws ClassNotFoundException, SQLException{      
		return setConnectionEcom();      
	} 
	 
	private Connection setConnectionReg(PftConexiones dto)   {
		Connection cons = null; 
		try {	
		    cons = DriverManager.getConnection(dto.getUrl().trim()+dto.getHost().trim()+":"+dto.getPuerto().trim()+"?ServiceName="+dto.getServicio(), "dba", env.getProperty("conexion.vtn.pass"));                          
		} catch (SQLException e) {
			logger.error("setConnectionReg- Conexion VTN: "+dto.getHost().trim()+" "+e.getMessage());
		} 
		return cons;
	}
	
	private Connection setConnectionEcom() throws ClassNotFoundException, SQLException  {
		Connection cons = null;                    
        try {  
        	cons = DriverManager.getConnection(env.getProperty("conexion.ecom.host"), env.getProperty("conexion.ecom.user"), password(false)); 
        } catch (SQLException ex) {
        	try {
        		cons = DriverManager.getConnection(env.getProperty("conexion.ecom.host"), env.getProperty("conexion.ecom.user"), password(true));
			} catch (Exception e) {
				logger.error("setConnectionEcom- Conexion ECOM: "+e.getMessage());
			}        	                                            
        }
        return cons;
    }

	public Connection setConnectionSat(){
		Connection cons = null;                     
        try {  	
        	cons = DriverManager.getConnection(env.getProperty("conexion.acrcloud.host"), env.getProperty("conexion.acrcloud.user"), password(false)); 
		} catch (SQLException e) {
        	try {
        		cons = DriverManager.getConnection(env.getProperty("conexion.acrcloud.host"), env.getProperty("conexion.acrcloud.user"),  password(true));                       
			} catch (SQLException ex) {				
				logger.error("setConnectionSat- Conexion AcrCloud: "+ex.getMessage());
			}    	                     
        }
        return cons;
    }
	
	public Connection setMMPConnection(){
		Connection cons = null;                     
        try {  	
        	cons = DriverManager.getConnection(env.getProperty("conexion.mmp.host"), env.getProperty("conexion.mmp.user"), env.getProperty("conexion.mmp.pass")); 
		} catch (SQLException e) {
			logger.error("setConnectionSat- Conexion MMP: "+e.getMessage());   	                     
        }
        return cons;
    }
	
	private Connection setConnectionYema(){    
		Connection cons = null;                      
        try {
        	cons = DriverManager.getConnection(env.getProperty("conexion.yema.host"), env.getProperty("conexion.yema.user"), env.getProperty("conexion.yema.pass")); 
		} catch (SQLException ex) {
        	try {
        		cons = DriverManager.getConnection(env.getProperty("conexion.yema.host"), env.getProperty("conexion.yema.user"), password(false)); 
			} catch (Exception e) {
				try {
					cons = DriverManager.getConnection(env.getProperty("conexion.yema.host"), env.getProperty("conexion.yema.user"), password(true)); 
				} catch (Exception e2) {
					logger.error("setConnectionSat- Conexion Yema: "+e2.getMessage());
				}			
			}
        	                                           
        }
        return cons;
    }
	
	public String password(boolean anterior){
		int ano = dateFechaToIntYear(fecha);
		try {	        
			switch (dateFechaToIntMonth(fecha)) {
            case 1:
            	return (anterior)?ano+"01z":(ano-1)+"12a";
            case 2:
            	return (anterior)?ano+"02x":(ano)+"01z";
            case 3:
            	return (anterior)?ano+"03y":(ano)+"02x";
            case 4:
            	return (anterior)?ano+"04o":(ano)+"03y";
            case 5:
            	return (anterior)?ano+"05m":(ano)+"04o";
            case 6:
            	return (anterior)?ano+"06l":(ano)+"05m";
            case 7:
            	return (anterior)?ano+"07g":(ano)+"06l";
            case 8:
            	return (anterior)?ano+"08f":(ano)+"07g";
            case 9:
            	return (anterior)?ano+"09e":(ano)+"08f";
            case 10:
            	return (anterior)?ano+"10d":(ano)+"09e";
            case 11:
            	return (anterior)?ano+"11b":(ano)+"10d";
            case 12:
            	return (anterior)?ano+"12a":(ano)+"11b";
            default:
            	return "";
			}
		} catch (Exception e) {
			logger.error("password- Obtener password: "+e.getMessage());
		}
		return "";
	}
	
	public int dateFechaToIntMonth(Date fecha){
		int month	=	0;
		try {		 
			calendarioGMT.setTime(fecha);
			month			= calendarioGMT.get(Calendar.MONTH)+1;
		} catch (Exception e) {
			logger.error("dateFechaToIntMonth- Obtener Formato Fecha: "+e.getMessage());
		}
		return month;
	}
	 
	public int dateFechaToIntYear(Date fecha){
		int year	=	0;
	    try {
	    	calendarioGMT.setTime(fecha);
		    year=calendarioGMT.get(Calendar.YEAR);
		} catch (Exception e) {
			logger.error("dateFechaToIntYear- Obtener Formato Fecha: "+e.getMessage());
		}	    
	    return year;
	}
}
