package appticket.beans;



public class Fclientes {
	private String 	rfc;
	private String  razonSocial;
	private String 	correo;
	private String 	correo2;
	private String 	regimenFiscal;
	private String 	usoCFDI;
	
	private Domicilio domicilio;

	
	public Fclientes() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Fclientes(String rfc, String razonSocial, String correo, String correo2, String regimenFiscal,
			String usoCFDI, Domicilio domicilio) {
		super();
		this.rfc = rfc;
		this.razonSocial = razonSocial;
		this.correo = correo;
		this.correo2 = correo2;
		this.regimenFiscal = regimenFiscal;
		this.usoCFDI = usoCFDI;
		this.domicilio = domicilio;
	}


	public String getRfc() {
		return rfc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public String getCorreo() {
		return correo;
	}

	public String getCorreo2() {
		return correo2;
	}

	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	public String getUsoCFDI() {
		return usoCFDI;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setCorreo2(String correo2) {
		this.correo2 = correo2;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
}
