package Taller2_POO;

public class Usuario {
	
	 private String nombre;
	 private String password; 
	 private String rol;
	 
	public Usuario(String nombre, String password, String rol) 
	{
			this.nombre = nombre;
			this.password = password;
			this.rol = rol;
	}
	
	//getters y setters
	 public String getNombre() {
		return nombre;
	 }
	 public void setNombre(String nombre) {
		this.nombre = nombre;
	 }
	 public String getPassword() {
		return password;
	 }
	 public void setPassword(String password) {
		this.password = password;
	 }
	 public String getRol() {
		return rol;
	 }
	 public void setRol(String rol) {
		this.rol = rol;
	 }

}
