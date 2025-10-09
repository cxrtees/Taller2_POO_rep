package Taller2_POO;

public class Vulnerabilidad {
	private int puerto;
	private String nombre;
	private String descripcion;
	
	
	public Vulnerabilidad(int puerto, String nombre, String descripcion)
	{
		this.puerto = puerto;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public int getPuerto()
	{
		return puerto;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public String getDescripcion()
	{
		return descripcion;
	}

}
