package Taller2_POO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	
	private static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("asdadasdsad");
		System.out.println("hola");
		
		System.out.println("uwu owo ");
		leerPcs();
		leerPuertos();
		leerUsuarios();
		leerVul();
	}

	private static void leerVul() throws FileNotFoundException{
		File arch = new File("vulnerabilidades.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split("|");
			System.out.println(linea); //testeo	
		}
	}

	private static void leerUsuarios() throws FileNotFoundException{
		File arch = new File("usuarios.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split(";");
			System.out.println(linea); //testeo	
		}
	}

	private static void leerPuertos() throws FileNotFoundException{
		File arch = new File("puertos.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split("|");
			System.out.println(linea); //testeo		
		}
	}

	private static void leerPcs() throws FileNotFoundException {
		File arch = new File("pcs.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split("|");
			System.out.println(linea); //testeo		
		}	
	}

}
