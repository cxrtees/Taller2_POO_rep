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

}
