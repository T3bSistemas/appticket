package appticket.beans;

public class PftConexiones {
	private String tclave;
	private String host;
	private String puerto;
	private String servicio;
	private String base;
	private String driver;
	private String url;
	
	public PftConexiones() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PftConexiones(String tclave, String host, String puerto, String servicio, String base, String driver,
			String url) {
		super();
		this.tclave = tclave;
		this.host = host;
		this.puerto = puerto;
		this.servicio = servicio;
		this.base = base;
		this.driver = driver;
		this.url = url;
	}

	public String getTclave() {
		return tclave;
	}

	public String getHost() {
		return host;
	}

	public String getPuerto() {
		return puerto;
	}

	public String getServicio() {
		return servicio;
	}

	public String getBase() {
		return base;
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public void setTclave(String tclave) {
		this.tclave = tclave;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
