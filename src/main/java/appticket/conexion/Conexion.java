package appticket.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import appticket.beans.PftConexiones;

public class Conexion {
	TimeZone miZonaGMT		= TimeZone.getTimeZone("GMT");
	Calendar calendarioGMT	= new GregorianCalendar(miZonaGMT);
	Date	fecha 			= new Date();
    
	
	public Connection getConexionS(int sucursal){
		return (sucursal==1)?setConnectionSat():setConnectionYema();
	}
	
	public Connection getConexionR(PftConexiones dto) throws ClassNotFoundException, SQLException{      
		return setConnectionReg(dto);        
    }  
	
	public Connection getConexionEcom() throws ClassNotFoundException, SQLException{      
		return setConnectionEcom();      
	} 
	 
	private Connection setConnectionReg(PftConexiones dto)  {
		Connection cons = null; 
		try {	
		    cons = DriverManager.getConnection(dto.getUrl().trim()+dto.getHost().trim()+":"+dto.getPuerto().trim()+"?ServiceName="+dto.getServicio(), "dba", "5575349");                          
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return cons;
	}
	
	private Connection setConnectionEcom() throws ClassNotFoundException, SQLException  {
		Connection cons = null;                    
        try {  
        	cons = DriverManager.getConnection("jdbc:sybase:Tds:201.161.87.114:2638?ServiceName=master", "dba", password(false)); 
        } catch (SQLException ex) {
        	cons = DriverManager.getConnection("jdbc:sybase:Tds:201.161.87.114:2638?ServiceName=master", "dba", password(true));                                            
        }
        return cons;
    }

	public Connection setConnectionSat(){
		Connection cons = null;                     
        try {  	
        	cons = DriverManager.getConnection("jdbc:sybase:Tds:201.161.87.102:2638?ServiceName=satback", "dba", password(false)); 
		} catch (SQLException e) {
        	try {
        		cons = DriverManager.getConnection("jdbc:sybase:Tds:201.161.87.102:2638?ServiceName=satback", "dba",  password(true));                       
			} catch (SQLException ex) {				
				ex.printStackTrace();
			}    	                     
        }
        return cons;
    }
	
	private Connection setConnectionYema(){    
		Connection cons = null;                      
        try {
        	cons = DriverManager.getConnection("jdbc:sybase:Tds:20.29.81.92:2638?ServiceName=yemacore", "dba", "202212aa"); 
		} catch (SQLException ex) {
        	try {
        		cons = DriverManager.getConnection("jdbc:sybase:Tds:20.29.81.92:2638?ServiceName=yemacore", "dba", password(false)); 
			} catch (Exception e) {
				try {
					cons = DriverManager.getConnection("jdbc:sybase:Tds:20.29.81.92:2638?ServiceName=yemacore", "dba", password(true)); 
				} catch (Exception e2) {
					e2.printStackTrace();
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
			e.printStackTrace();
		}
		return "";
	}
	
	public int dateFechaToIntMonth(Date fecha){
		int month	=	0;
		try {		 
			calendarioGMT.setTime(fecha);
			month			= calendarioGMT.get(Calendar.MONTH)+1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return month;
	}
	 
	public int dateFechaToIntYear(Date fecha){
		int year	=	0;
	    try {
	    	calendarioGMT.setTime(fecha);
		    year=calendarioGMT.get(Calendar.YEAR);
		} catch (Exception e) {
			e.printStackTrace();
		}	    
	    return year;
	}
}
