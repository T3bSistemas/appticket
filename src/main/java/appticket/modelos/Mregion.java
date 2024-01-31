package appticket.modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Service;

import appticket.consultas.Cregion;
import appticket.interfaces.Iregion;
import appticket.utilidades.Utilidades;

@Service
public class Mregion implements Iregion{	
	public String getRegion(int sucursal, int tienda, String fecha) {	
		Connection con = null;
		PreparedStatement 	ps 	= null;
		ResultSet 			rs	= null;
		Utilidades 		    u   = new Utilidades();
		try {
			//Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
			//DriverManager.setLoginTimeout(10);
			con = DriverManager.getConnection("jdbc:sybase:Tds:201.161.87.102:2638?ServiceName=satback", "dba", "202401z");
			if (con != null) {
				boolean isNumber = (u.isNullNumber(tienda + ""));
				if ((!(u.isNull(fecha))) && isNumber) {
					ps = con.prepareStatement(Cregion.FtiendasPorAlmacenDet.toString());
					ps.setString(1, fecha);
					ps.setInt(2, tienda);
				} else {
					if (isNumber) {
						ps = con.prepareStatement(Cregion.Ftiendas.toString());
						ps.setInt(1, tienda);
					} else {
						return null;
					}
				}

				rs = ps.executeQuery();
				if (rs.next()) {
					return new Utilidades().getNull(rs.getString("region"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	
	
}
