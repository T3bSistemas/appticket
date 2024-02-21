package appticket.beans;

public class Domicilio {
	private String calle;
	private String colonia;
	private String cp;
	private String estado;
	private String munAlc;
	private String numExt;
	private String numInt;
	private String pais;
	public Domicilio() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Domicilio(String calle, String colonia, String cp, String estado, String munAlc, String numExt,
			String numInt, String pais) {
		super();
		this.calle = calle;
		this.colonia = colonia;
		this.cp = cp;
		this.estado = estado;
		this.munAlc = munAlc;
		this.numExt = numExt;
		this.numInt = numInt;
		this.pais = pais;
	}
	public String getCalle() {
		return calle;
	}
	public String getColonia() {
		return colonia;
	}
	public String getCp() {
		return cp;
	}
	public String getEstado() {
		return estado;
	}
	public String getMunAlc() {
		return munAlc;
	}
	public String getNumExt() {
		return numExt;
	}
	public String getNumInt() {
		return numInt;
	}
	public String getPais() {
		return pais;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setMunAlc(String munAlc) {
		this.munAlc = munAlc;
	}
	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}
	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	@Override
	public String toString() {
		return "{calle:" + calle + ", colonia:" + colonia + ", cp:" + cp + ", estado:" + estado + ", munAlc:"
				+ munAlc + ", numExt:" + numExt + ", numInt:" + numInt + ", pais:" + pais + "}";
	}
}
