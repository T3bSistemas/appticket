package appticket.utilidades;

public class Utilidades {
	
	public boolean isNull(String data) {
		try {
			if(data != null) {
				if(!data.equals("")) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public String getNull(String data) {
		try {
			if(data != null) {
				if(!data.equals("")) {
					return  data.trim();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean isNullNumber(String data) {
		try {
			Integer.parseInt(data+"");
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static void main(String arg[]) {
		Utilidades u = new Utilidades();
		String o = null;
		System.out.println(u.isNullNumber(o));
	}
}
