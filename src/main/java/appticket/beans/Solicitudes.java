package appticket.beans;

public class Solicitudes {
	private int 	status_sol;
	private String 	rfc;
	private String 	tclave;
	private String 	num_caja;
	private String 	num_ticket;
	private String 	fecha_compra;
	private String 	fecha_sol;
	private String 	descripcion;
	private String 	num_factura;
	private int 	folio_sol;
	private String 	tipo;
	private int 	solicitud_id;
	private String 	sucursal;
	
	public Solicitudes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Solicitudes(int status_sol, String rfc, String tclave, String num_caja, String num_ticket,
			String fecha_compra, String fecha_sol, String descripcion, String num_factura, int folio_sol, String tipo,
			int solicitud_id, String sucursal) {
		super();
		this.status_sol = status_sol;
		this.rfc = rfc;
		this.tclave = tclave;
		this.num_caja = num_caja;
		this.num_ticket = num_ticket;
		this.fecha_compra = fecha_compra;
		this.fecha_sol = fecha_sol;
		this.descripcion = descripcion;
		this.num_factura = num_factura;
		this.folio_sol = folio_sol;
		this.tipo = tipo;
		this.solicitud_id = solicitud_id;
		this.sucursal = sucursal;
	}

	public int getStatus_sol() {
		return status_sol;
	}

	public String getRfc() {
		return rfc;
	}

	public String getTclave() {
		return tclave;
	}

	public String getNum_caja() {
		return num_caja;
	}

	public String getNum_ticket() {
		return num_ticket;
	}

	public String getFecha_compra() {
		return fecha_compra;
	}

	public String getFecha_sol() {
		return fecha_sol;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getNum_factura() {
		return num_factura;
	}

	public int getFolio_sol() {
		return folio_sol;
	}

	public String getTipo() {
		return tipo;
	}

	public int getSolicitud_id() {
		return solicitud_id;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setStatus_sol(int status_sol) {
		this.status_sol = status_sol;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public void setTclave(String tclave) {
		this.tclave = tclave;
	}

	public void setNum_caja(String num_caja) {
		this.num_caja = num_caja;
	}

	public void setNum_ticket(String num_ticket) {
		this.num_ticket = num_ticket;
	}

	public void setFecha_compra(String fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

	public void setFecha_sol(String fecha_sol) {
		this.fecha_sol = fecha_sol;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setNum_factura(String num_factura) {
		this.num_factura = num_factura;
	}

	public void setFolio_sol(int folio_sol) {
		this.folio_sol = folio_sol;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setSolicitud_id(int solicitud_id) {
		this.solicitud_id = solicitud_id;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	
	@Override
	public String toString() {
		return "Solicitudes [status_sol=" + status_sol + ", rfc=" + rfc + ", tclave=" + tclave + ", num_caja="
				+ num_caja + ", num_ticket=" + num_ticket + ", fecha_compra=" + fecha_compra + ", fecha_sol="
				+ fecha_sol + ", descripcion=" + descripcion + ", num_factura=" + num_factura + ", folio_sol="
				+ folio_sol + ", tipo=" + tipo + ", solicitud_id=" + solicitud_id + ", sucursal=" + sucursal + "]";
	}
}
