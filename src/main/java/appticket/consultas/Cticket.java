package appticket.consultas;

public enum Cticket {
	PVFPAGOSVENTASIN,
	ECMFNFACTURAECOMENCABEZADO,
	ECMFNFACTURAECOMDETALLE,
	PVMOVIMIENTOSDETIN,
	IVAIEPS,
	FORMASPAGO,
	SERIE;
	
	public String toString() {
		switch(this) {
			case PVFPAGOSVENTASIN:
				return
				  "SELECT p.fecha,\r\n"
				+ "       p.tclave,\r\n"
				+ "       p.num_ticket,\r\n"
				+ "       p.caja,\r\n"
				+ "       ( p.importe_rec - p.importe_cambio ) AS total,\r\n"
				+ "       p.fp_clave                           AS tipo_pago,\r\n"
				+ "       p.idturno\r\n"
				+ "FROM   pv_fpagos_ventas_in p\r\n"
				+ "WHERE  p.tclave = ? \r\n"
				+ "       AND p.fecha = ?\r\n"
				+ "       AND p.caja = ?\r\n"
				+ "       AND p.num_ticket = ? ";
			case ECMFNFACTURAECOMENCABEZADO:
				return
				  "call dba.ECM_FN_FACTURAECOMENCABEZADO(?, ?, ?, ?)";
			case ECMFNFACTURAECOMDETALLE:
				return
				  "call dba.ECM_FN_FACTURAECOMDETALLE(?, ?, ?, ?)";
			case PVMOVIMIENTOSDETIN:
				return
				   "SELECT fecha,\r\n"
				 + "       tclave,\r\n"
				 + "       num_ticket,\r\n"
				 + "       caja,\r\n"
				 + "       iclave,\r\n"
				 + "       Sum(atmcant)  AS atmcant,\r\n"
				 + "       Sum(atmventa) AS atmventa,\r\n"
				 + "       iv_clave,\r\n"
				 + "       ie_clave\r\n"
				 + "FROM   pv_movimientos_det_in\r\n"
				 + "WHERE  tclave = ?\r\n"
				 + "       AND fecha = ?\r\n"
				 + "       AND caja = ?\r\n"
				 + "       AND num_ticket = ?\r\n"
				 + "GROUP  BY fecha,\r\n"
				 + "          tclave,\r\n"
				 + "          iclave,\r\n"
				 + "          num_ticket,\r\n"
				 + "          caja,\r\n"
				 + "          iclave,\r\n"
				 + "          iv_clave,\r\n"
				 + "          ie_clave\r\n"
				 + "ORDER  BY iclave ";
			case IVAIEPS:
				return
				"SELECT   a.idesc,\r\n"
				+ "       a.iunidad,\r\n"
				+ "       a.iventa,\r\n"
				+ "       Isnull(a.c_claveunidad, '')   AS c_ClaveUnidad,\r\n"
				+ "       Isnull(a.c_claveprodserv, '') AS c_ClaveProdServ,\r\n"
				+ "       i.iv_factor,\r\n"
				+ "       ie_factor\r\n"
				+ "FROM   farticulos a,\r\n"
				+ "       fiva i,\r\n"
				+ "       fieps e\r\n"
				+ "WHERE  a.iclave = ?\r\n"
				+ "       AND a.iv_clave = i.iv_clave\r\n"
				+ "       AND a.ie_clave = e.ie_clave ";
			case FORMASPAGO:
				return
				  "SELECT clave_sat\r\n"
				+ "FROM   formas_pago f\r\n"
				+ "WHERE  f.fp_clave = ? ";
			case SERIE:
				return
				  "SELECT tcp AS tdir,\r\n"
				+ "       tncrvendflag,\r\n"
				+ "       temail\r\n"
				+ "FROM   ftiendas t\r\n"
				+ "WHERE  t.tclave = ? ";
			default:
				return "";
		}
	}
}
