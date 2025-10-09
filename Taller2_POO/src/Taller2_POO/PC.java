package Taller2_POO;

public class PC {
	private String id;
	private String ip;
	private String so;

	public PC(String id, String ip, String so) {
		this.id = id;
		this.ip = ip;
		this.so = so;
	}

	public String getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getSo() {
		return so;
	}

	@Override
	public String toString() {
		return "PC [id=" + id + ", ip=" + ip + ", so=" + so + "]";
	}	
	public String obtenerClaseIp()
	{
		try {
			String[] partes = ip.split("\\.");
			int primerOcteto = Integer.parseInt(partes[0]); //primero de los 4 digitos de la direccion ip
			
			if(primerOcteto >= 0 && primerOcteto <= 127) //si el octeto esta entre 0 y 127 es clase A
			{
				return "Clase A";
				
			} else if (primerOcteto >= 128 && primerOcteto <= 191) //si esta entre 128 y 191 es clase B
			{
				return "Clase B";
				
			} else if (primerOcteto >= 192 && primerOcteto <= 223) //si esta entre 192 y 223 es clase C
			{
				return "Clase C";
				
			} else 
			{
				return "Otra";  
			}
		} catch (Exception e) {
			return "Error, clase desconocida";
		}
	}
}
