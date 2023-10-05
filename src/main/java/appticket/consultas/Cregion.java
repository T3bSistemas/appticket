package appticket.consultas;

public enum Cregion {
	FtiendasPorAlmacenDet,
	Ftiendas;
	
	public String toString() {
		switch(this) {
			case FtiendasPorAlmacenDet:
				return
				  "SELECT talmacen AS region\r\n"
				+ "FROM   ftiendas_por_almacen_det\r\n"
				+ "WHERE  fecha = ?\r\n"
				+ "       AND tclave = ? ";
			case Ftiendas:
				return
				  "SELECT talmacen AS region\r\n"
				+ "FROM   ftiendas f\r\n"
				+ "WHERE  f.tclave = ? ";
			default:
				return "";
		}
	}
}
