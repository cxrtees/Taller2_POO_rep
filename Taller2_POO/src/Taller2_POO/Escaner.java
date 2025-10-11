package Taller2_POO;

import java.util.Date;

public class Escaner {
	private PC pc;
	private Usuario usuario;
	private Date fecha;
	
	public Escaner(PC pc, Usuario usuario, Date fecha)
	{
		this.pc = pc;
		this.usuario = usuario;
		this.fecha = fecha;
	}
	
	//getters 
	public PC getPc() {
		return pc;
	}
	
	public Usuario getUsuario()
	{
		return usuario;
	}
	
	public Date getFecha()
	{
		return fecha;
	}
	
	//formatea los datos del resultado del escaner
	public String generarReporte()
	{
		String reporte = "";
		reporte += "=== Reporte de Escaneo ===\n";
		reporte += "PC: " + pc.getId() + "\n";
		reporte += "IP: " + pc.getIp() + "\n";
		reporte += "So: " + pc.getSo() + "\n";
		reporte += "Usuario: " + usuario.getNombre() + "\n";
		reporte += "Nivel de Riesgo: " + pc.calcularNivelRiesgo() + "\n";
		reporte += "Fecha: " + fecha.toString() + "\n";
		reporte += "Puertos abiertos:\n";
		
		for (Puerto puerto : pc.getPuertos())
		{
			if (puerto.estaAbierto())
			{
				reporte += " - Puerto " + puerto.getNumero() + "\n";
				for (Vulnerabilidad vulnerabilidad : puerto.getVulnerabilidades())
				{
					reporte += "--> * " + vulnerabilidad.getNombre() + " --> * " + vulnerabilidad.getDescripcion() + "\n"; 	
				}
			}
		}
		
		return reporte;
	}

}
