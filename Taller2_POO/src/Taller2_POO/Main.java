package Taller2_POO;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class Main {

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

            
            rol = verificarUsuario(user, pass,cargarUsuarios());

            if (!rol.equals("")) {
            	
                logueado = true;
                System.out.println("Login exitoso como " + rol + "!");
            } else {
                System.out.println("Usuario o contraseña incorrecta.\n");
            }

        } while (!logueado);

        // Menú según el rol
        if (rol.equals("ADMIN")) {
        	
            menuAdmin();
        } else if (rol.equals("USUARIO")) {
        	
            menuUsuario();
        }

        sc.close();
    }
	
public static ArrayList<Usuario> cargarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();

        try {
            File archivo = new File("usuarios.txt");
            Scanner lector = new Scanner(archivo);

            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    String nombre = datos[0].trim();
                    String password = datos[1].trim();
                    String rol = datos[2].trim();
                    lista.add(new Usuario(nombre, password, rol));
                }
            }

            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ Archivo usuarios.txt no encontrado.");
        }

        return lista;
    }
public static String verificarUsuario(String user, String pass,ArrayList<Usuario> usuarios) {
	
	for (Usuario u : usuarios) {
        if (u.getNombre().equals(user) && hash(pass).equals(hash(u.getPassword()))) {
            return u.getRol();
        }
    }
    return ""; // no se encontró usuario o contraseña incorrecta
}
public static String hash(String input) {
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hashBytes);
    } catch (Exception e) {
        return null;
    }
}

public static ArrayList<PC> cargarPCs() {
	ArrayList<PC> lista = new ArrayList<PC>();
	
	try {
		File archivo = new File("pcs.txt");
		Scanner lector = new Scanner(archivo);
		
		while (lector.hasNextLine()) {
			String linea = lector.nextLine().trim();
			if (linea.isEmpty()) continue;
			
			// Para soportar separadores como coma, punto y coma
			String[] datos = linea.split("[,;]");
			if (datos.length >= 3) {
				String id = datos[0].trim();
				String ip = datos[1].trim();
				String so = datos[2].trim();
				lista.add(new PC(id, ip, so));
			}
		}
		lector.close();
	}	catch (FileNotFoundException e) {
		System.out.println("Archivo pcs.txt no encontrado");
	}	
	return lista;
}



private static void menuUsuario() {
	Scanner sc = new Scanner(System.in);
	int op;
	
	do {
		System.out.println("\n=== MENÚ USUARIO ===");
		System.out.println("1) Ver lista de PCs");
		System.out.println("0) Salir");
		System.out.print("Opción: ");
		String in = sc.nextLine().trim();
		if (in.isEmpty()) { op = -1; continue;}
		
		try {
			op = Integer.parseInt(in);
		} catch (NumberFormatException e) {
			op = -1;
		}
		
		switch (op) {
		case 1:
			ArrayList<PC> pcs = cargarPCs();
			if (pcs.isEmpty()) {
				System.out.println("No hay PCs cargados");
			} else {
				System.out.println("\n--- LISTA DE PCs ---");
				for (PC pc : pcs) {
					System.out.println(pc.toString());
				}
			}
			break;
		case 0:
			System.out.println("Saliendo del Menú Usuario...");
			break;
		default:
			System.out.println("Opción no válida");
		}
	} while (op != 0);
}

private static void menuAdmin() {
		
		}

private static void leerVul() throws FileNotFoundException{
		Scanner s = new Scanner(System.in);
		File arch = new File("vulnerabilidades.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split("|");
		}
	}

private static void leerUsuarios() throws FileNotFoundException{
		Scanner s = new Scanner(System.in);
		File arch = new File("usuarios.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split(";");
		}
	}

private static void leerPuertos() throws FileNotFoundException{
		Scanner s = new Scanner(System.in);
		File arch = new File("puertos.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split("|");
		}
	}

private static void leerPcs() throws FileNotFoundException {
		Scanner s = new Scanner(System.in);
		File arch = new File("pcs.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split("|");
		}	
	}

}
