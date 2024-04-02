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
				return "SELECT p.fecha, p.tclave, p.num_ticket, p.caja, ( p.importe_rec - p.importe_cambio ) AS total, p.fp_clave AS tipo_pago, p.idturno FROM pv_fpagos_ventas_in p WHERE p.tclave = ? AND p.fecha = ? AND p.caja = ? AND p.num_ticket = ? ";
			case ECMFNFACTURAECOMENCABEZADO:
				return "call dba.ECM_FN_FACTURAECOMENCABEZADO(?, ?, ?, ?)";
			case ECMFNFACTURAECOMDETALLE:
				return "call dba.ECM_FN_FACTURAECOMDETALLE(?, ?, ?, ?)";
			case PVMOVIMIENTOSDETIN:
				return "SELECT fecha, tclave, num_ticket, caja, iclave, Sum(atmcant) AS atmcant, Sum(atmventa) AS atmventa, iv_clave, ie_clave, atmdesc, gclave, lclave FROM pv_movimientos_det_in WHERE tclave = ? AND fecha = ? AND caja = ? AND num_ticket = ? GROUP BY fecha, tclave, iclave, num_ticket, caja, iclave, iv_clave, ie_clave, atmdesc, gclave, lclave ORDER BY iclave ";
			case IVAIEPS:
				return "SELECT a.idesc, a.iunidad, a.iventa, Isnull(a.c_claveunidad, '') AS c_ClaveUnidad, Isnull(a.c_claveprodserv, '') AS c_ClaveProdServ, i.iv_factor, ie_factor FROM dba.farticulos a, dba.fiva i, dba.fieps e WHERE a.iclave = ? AND a.iv_clave = i.iv_clave AND a.ie_clave = e.ie_clave ";
			case FORMASPAGO:
				return "SELECT clave_sat FROM dba.formas_pago f WHERE f.fp_clave = ? ";
			case SERIE:
				return "SELECT tcp AS tdir, tncrvendflag, temail FROM dba.ftiendas t WHERE t.tclave = ? ";
			default:
				return "";
		}
	}
}
