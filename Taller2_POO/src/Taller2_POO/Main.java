package Taller2_POO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	
	private static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
        boolean logueado = false;
        String rol = "";

        do {
            System.out.println("\n=== LOGIN SECURENET ===");
            System.out.print("Usuario: ");
            String user = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();

            // 🔹 Aquí en vez del if largo, llamas a una función de verificación:
            rol = verificarUsuario(user, pass);

            if (!rol.equals("")) {
                logueado = true;
                System.out.println("Login exitoso como " + rol + "!");
            } else {
                System.out.println("Usuario o contraseña incorrecta.\n");
            }

        } while (!logueado);

        // Menú según el rol
        if (rol.equals("ADMIN")) {
            menuAdmin(sc);
        } else if (rol.equals("USUARIO")) {
        	
            menuUsuario(sc);
        }

        sc.close();
    }

	private static void menuUsuario() {
		// TODO Auto-generated method stub
		
	}

	private static void menuAdmin() {
		// TODO Auto-generated method stub
		
	}

	private static void leerVul() throws FileNotFoundException{
		File arch = new File("vulnerabilidades.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split("|");
			//System.out.println(linea); //testeo	
		}
	}

	private static void leerUsuarios() throws FileNotFoundException{
		File arch = new File("usuarios.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split(";");
			//System.out.println(linea); //testeo	
		}
	}

	private static void leerPuertos() throws FileNotFoundException{
		File arch = new File("puertos.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split("|");
			//System.out.println(linea); //testeo		
		}
	}

	private static void leerPcs() throws FileNotFoundException {
		File arch = new File("pcs.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split("|");
			//System.out.println(linea); //testeo		
		}	
	}

}
