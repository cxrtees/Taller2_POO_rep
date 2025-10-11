package Taller2_POO;

import java.util.ArrayList;


public class PC {
	private String id;
	private String ip;
	private String so;
	private ArrayList<Puerto> puertos;
	
	public PC(String id, String ip, String so) {
		this.id = id;
		this.ip = ip;
		this.so = so;
		this.puertos = new ArrayList<>();
	}
// getters
	public String getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getSo() {
		return so;
	}

	public ArrayList<Puerto> getPuertos()
	{
		return puertos;
	}
	
	//se agrega un nuevo puerto a la lista de puertos asociados al pc
	public void agregarPuerto(Puerto puerto)
	{
		puertos.add(puerto);
	}
	
	
	//cuenta las vulnerabilidades de cada puerto 
	public int contarVulnerabilidades()
	{
		int contador = 0;
		for (Puerto puerto : puertos)
		{
			//contar las vulnerabilidades del puerto que esta expuesto
			if (puerto.estaAbierto())
			{
				contador += puerto.getVulnerabilidades().size();
			}
			
		}
		return contador;
	}
	
	//calcula el nivel de riesgo de los pcs segun su cantidad de vulnerabilidades
	public String calcularNivelRiesgo() 
	{
		int totalVulnerabilidades = contarVulnerabilidades();
		
		if(totalVulnerabilidades >= 3) //nivel de riesgo alto
		{
			return "ALTO";
			
		} else if (totalVulnerabilidades >= 1) //nivel de riesgo medio 
		{
			return "MEDIO";
			
		} else {
			return "BAJO";  //nivel de riesgo bajo
		}
		
	}
	
	
	//calcula la clase de la direccion ip segun el rango de sus primeros digitos
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
	
	//imprime los datos de Pc
	public void mostrarInfoCompleta()
	{
		System.out.println("-> Id: " + id);
		System.out.println("-> Ip: " + ip);
		System.out.println("-> So: " + so);
		System.out.println("-> Puertos: ");
		
		if (puertos.isEmpty())
		{
			System.out.println("-- no hay puertos asociados a este pc");
			
		} else
		{
		
			for (Puerto puerto : puertos) {
				System.out.println("-- Puerto " + puerto.getNumero() + ": " + puerto.getEstado());
				
				if (puerto.estaAbierto() && !puerto.getVulnerabilidades().isEmpty()) //muestra las vulnerabilidades de los puertos abiertos 
				{
					System.out.println("-- Vulnerabilidades: ");
					for (Vulnerabilidad vulnerabilidad : puerto.getVulnerabilidades())
					{
						System.out.println("--- * "+ vulnerabilidad.getNombre());
						
					}	
				}
			}
			
		}
			
	}
	
}
