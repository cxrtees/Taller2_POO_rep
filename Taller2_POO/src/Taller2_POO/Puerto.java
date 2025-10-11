package Taller2_POO;

import java.util.ArrayList;

public class Puerto {
	private int numero;
	private String estado;
	private PC pcAsociado;
	private ArrayList<Vulnerabilidad> vulnerabilidades;
	
	public Puerto(int numero, String estado, PC pcAsociado)
	{
		this.numero = numero;
		this.estado = estado;
		this.pcAsociado = pcAsociado;
		this.vulnerabilidades = new ArrayList<>();
	}
	
	//getters
	public int getNumero()
	{
		return numero;
	}
	
	public String getEstado()
	{
		return estado;
	}
	
	public PC getPcAsociado()
	{
		return pcAsociado;
	}
	
	public ArrayList<Vulnerabilidad> getVulnerabilidades()
	{
		return vulnerabilidades;
	}
	
	//metodo para ver si un puerto esta abierto
	public boolean estaAbierto()
	{
		return "Abierto".equalsIgnoreCase(estado);
	}
	
	//metodo para guardar las vulnerabilidades asociadas al puerto
	public void agregarVulnerabilidad(Vulnerabilidad vulnerabilidad)
	{
		vulnerabilidades.add(vulnerabilidad);
	}
	
}


