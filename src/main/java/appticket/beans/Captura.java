package appticket.beans;

public class Captura {
	private String 			fechaCompra;
	private int 			tienda;
	private String 			caja;
	private String 			ticket;
	private Double 			importe;
	private String 			region;
	private PftConexiones 	conexion;
	
	public Captura() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Captura(String fechaCompra, int tienda, String caja, String ticket, Double importe, String region,
			PftConexiones conexion) {
		super();
		this.fechaCompra = fechaCompra;
		this.tienda = tienda;
		this.caja = caja;
		this.ticket = ticket;
		this.importe = importe;
		this.region = region;
		this.conexion = conexion;
	}

	public String getFechaCompra() {
		return fechaCompra;
	}

	public int getTienda() {
		return tienda;
	}

	public String getCaja() {
		return caja;
	}

	public String getTicket() {
		return ticket;
	}

	public Double getImporte() {
		return importe;
	}

	public String getRegion() {
		return region;
	}

	public PftConexiones getConexion() {
		return conexion;
	}

	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public void setTienda(int tienda) {
		this.tienda = tienda;
	}

	public void setCaja(String caja) {
		this.caja = caja;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setConexion(PftConexiones conexion) {
		this.conexion = conexion;
	}

	@Override
	public String toString() {
		return "Captura [fechaCompra=" + fechaCompra + ", tienda=" + tienda + ", caja=" + caja + ", ticket=" + ticket
				+ ", importe=" + importe + ", region=" + region + ", conexion=" + conexion + "]";
	}	
}
