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
        	
            menuAdmin(sc);
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
            System.out.println("Archivo usuarios.txt no encontrado.");
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

//carga los datos de los pcs desde pcs.txt
public static ArrayList<PC> cargarPCs() {
	ArrayList<PC> lista = new ArrayList<PC>();
	
	try {
		
		File archivo = new File("pcs.txt");
		Scanner lector = new Scanner(archivo);
		
		while (lector.hasNextLine()) {
			String linea = lector.nextLine().trim();
			if (linea.isEmpty()) 
			{
				continue; // saltar lineas vacias
			}
			
			String[] datos = linea.split("\\|"); //separadores que tiene el archivo pcs.txt
			if (datos.length >= 3)
			{
				String id = datos[0].trim();
				String ip = datos[1].trim();
				String so = datos[2].trim();
				lista.add(new PC(id,ip,so));
				
			}
		}
		
		lector.close();
	} catch (FileNotFoundException e) {
		System.out.println("archivo pcs.txt no encontrado");
	}
	
	return lista;
}




//menu de usuario
private static void menuUsuario(Scanner sc, Usuario usuarioLogueado) {
 int op;
 
 do {
     System.out.println("\n=== MENU USUARIO ===");
     System.out.println("1) Ver lista de PCs");
     System.out.println("2) Escanear un PC");
     System.out.println("3) Ver total de puertos abiertos");
     System.out.println("4) Ordenar PCs por IP");
     System.out.println("0) Salir");
     System.out.print("Opcion: ");
     
     String in = sc.nextLine().trim();
     if(in.isEmpty())
     {
         op = -1;
         continue; // salta entradas vacias
     }
     
     try {
         op = Integer.parseInt(in);
     } catch (NumberFormatException e)
     {
         op = -1;
     }
     
     switch (op) {
     case 1:
         
         ArrayList<PC> pcs = cargarPCs();  //muestra los pcs subidos al sistema
         if(pcs.isEmpty())
         {
             System.out.println("no hay pcs cargados en el sistema");
         } else {
             System.out.println("\n--- LISTA DE PCs ---");
             for (PC pc : pcs) 
             {
                 System.out.println(pc.getId());
             }
         }
         break;
         
     case 2:
         break;
     
     case 3:
         verTotalPuertosAbiertos(); //muestra todos los puertos abiertos en la red
         break;
         
     case 4:
         ordenarPCsPorIp(); //ordena las direcciones ip segun su categoria
         break;
         
     case 0:
         System.out.println("Saliendo del sistema...");
         System.out.println("Adios...");
         break;
     default:
         System.out.println("Opcion no valida");
     }

     
 } while (op != 0);
}
private static void menuAdmin(Scanner sc)
{
	int op;
		
		do {
			System.out.println("\n=== MENU ADMINISTRADOR ===");
			System.out.println("1) Ver lista completa de PCs");
			System.out.println("2) Agregar PC");
			System.out.println("3) Eliminar PC");
			System.out.println("4) Clasificar PCs por nivel de riesgo");
			System.out.println("0) Salir");
			System.out.println("Opcion: ");
			
			String input = sc.nextLine().trim();
			if (input.isEmpty())
			{
				op = -1;
				continue; // salta entradas vacias
			}
			
			try {
				op = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				op = -1;
			}
			
			switch (op) {
			case 1:
				verListaPcs(); //muestra la lista completa de los pcs del sistema junto a su info
				break;
				
			case 2:
				agregarPC(sc); //agrega un nuevo pc al sistema junto a su info y sus puertos
				break;
				
			case 3:
				eliminarPC(sc); //elimina un pc del sistema junto a los puertos asociados
				break;
				
			case 4:
				clasificarPCsRiesgo(); //clasifica los pcs del sistema segun su nivel de riesgo y vulnerabilidades
				break;
				
			case 0:
				System.out.println("Saliendo del sistema...");
				System.out.println("Adios...");
				break;
			
			default:
				System.out.println("Opcion no valida");
			}
			
		} while (op != 0);
		
}

//metodos del menu de admin

//despliega un listado completo de los pcs registrados en el sistema junto a su informacion
private static void verListaPcs() {
	ArrayList<PC> pcs = cargarPCs();
	ArrayList<Puerto> puertos = cargarPuertos(pcs);
	ArrayList<Vulnerabilidad> vulnerabilidades = cargarVulnerabilidades();
	asociarVulnerabilidades(puertos, vulnerabilidades);
	
	if (pcs.isEmpty())
	{
		System.out.println("no se encuentran PCs cargados en el sistema");
	} else {
		System.out.println("\n--- Listado completo de PCs registrados ---");
		for (PC pc : pcs)
		{
			pc.mostrarInfoCompleta();
			System.out.println("-----------------------");
		}
	}
	
}

//relaciona el puerto expuesto con la vulnerabilidad que posee
public static void asociarVulnerabilidades(ArrayList<Puerto> puertos, ArrayList<Vulnerabilidad> vulnerabilidades)
{
  for (Puerto puerto : puertos)
  {
      for (Vulnerabilidad vulnerabilidad : vulnerabilidades)
      {
          if (puerto.getNumero() == vulnerabilidad.getPuerto())
          {
              puerto.agregarVulnerabilidad(vulnerabilidad);
          }
      }
  }
}

//carga los datos de las vulnerabilidades desde su txt
public static ArrayList<Vulnerabilidad> cargarVulnerabilidades() {
  ArrayList<Vulnerabilidad> lista = new ArrayList<Vulnerabilidad>();
  
  try {
      
      File archivo = new File("vulnerabilidades.txt");
      Scanner lector = new Scanner(archivo);
      
      while (lector.hasNextLine()) {
          String linea = lector.nextLine().trim();
          if (linea.isEmpty()) 
          {
              continue; // saltar lineas vacias
          }
          
          String[] datos = linea.split("\\|");
          if (datos.length >= 3)
          {
              int puerto = Integer.parseInt(datos[0].trim());
              String nombre = datos[1].trim();
              String descripcion = datos[2].trim();
              lista.add(new Vulnerabilidad(puerto,nombre,descripcion));
              
          }
      }
      
      lector.close();
  } catch (FileNotFoundException e) {
      System.out.println("archivo vulnerabilidades.txt no encontrado");
  }
  
  return lista;
}

//carga los puertos desde puertos.txt
public static ArrayList<Puerto> cargarPuertos(ArrayList<PC> pcs) {
  ArrayList<Puerto> lista = new ArrayList<Puerto>();
  
  try {
      
      File archivo = new File("puertos.txt");
      Scanner lector = new Scanner(archivo);
      
      while (lector.hasNextLine()) {
          String linea = lector.nextLine().trim();
          if (linea.isEmpty()) 
          {
              continue; // saltar lineas vacias
          }
          
          String[] datos = linea.split("\\|");
          if (datos.length >= 3)
          {
              String idPC = datos[0].trim();
              int numero = Integer.parseInt(datos[1].trim());
              String estado = datos[2].trim();
              
              //buscar pc asociado por id
              PC pcAsociado = null;
              for (PC pc : pcs)
              {
                  if (pc.getId().equals(idPC)) 
                  {
                      pcAsociado = pc;
                      break;
                  }  
              }
              
              if (pcAsociado != null)
              {
                  Puerto puerto = new Puerto(numero, estado, pcAsociado);
                  lista.add(puerto);
                  pcAsociado.agregarPuerto(puerto);
                  
              }					
          }
      }    
      lector.close();
  } catch (FileNotFoundException e) {
      System.out.println("archivo puertos.txt no encontrado");
  }
  
  return lista;
}

//muestra el total de puertos que se encuentran vulnerables al estar abiertos
private static void verTotalPuertosAbiertos() {
  ArrayList<PC> pcs = cargarPCs();
  ArrayList<Puerto> puertos = cargarPuertos(pcs);
  ArrayList<Vulnerabilidad> vulnerabilidades = cargarVulnerabilidades();
  asociarVulnerabilidades(puertos, vulnerabilidades);
  
  int total = 0;
  System.out.println("\n--- Puertos abiertos en la red ---");
  
  for (PC pc : pcs)
  {
      //se obtienen los puertos abiertos asociados a los pcs del sistema
      for (Puerto puerto : pc.getPuertos())
      {
          if (puerto.estaAbierto())
          {
              total++; //se cuenta el puerto abierto
              System.out.println("--> Pc: " + pc.getId() + " - Puerto: " + puerto.getNumero());
          
              //se obtienen las vulnerabilidades asociadas a los puertos abiertos
              for (Vulnerabilidad vulnerabilidad : puerto.getVulnerabilidades())
              {
                  System.out.println("--> Vulnerabilidad: " + vulnerabilidad.getNombre());
                  
              }
              
          }
          
      }
      
  }
  System.out.println("Cantidad total de puertos abiertos: " + total);
}

//ordena los pcs registrados en el sistema por categoria de su ip
private static void ordenarPCsPorIp() {
  ArrayList<PC> pcs = cargarPCs();
  ArrayList<PC> claseA = new ArrayList<>();
  ArrayList<PC> claseB = new ArrayList<>();
  ArrayList<PC> claseC = new ArrayList<>();
  ArrayList<PC> otraClase = new ArrayList<>();
  
  //se obtienen las clases de cada direccion de los pcs registrados en el sistema 
  for (PC pc : pcs)
  {
      String clase = pc.obtenerClaseIp();
      if (clase.equals("Clase A"))
      {
          claseA.add(pc);
      } else if (clase.equals("Clase B"))
      {
          claseB.add(pc);
      } else if (clase.equals("Clase C"))
      {
          claseC.add(pc);
      } else {
          otraClase.add(pc);
      }
      
  } 
  System.out.println("\n--- Pcs clase A ---");
  for (PC pc : claseA)
  {
      System.out.println(pc.getId());
  }
  
  System.out.println("\n--- Pcs clase B ---");
  for (PC pc : claseB)
  {
      System.out.println(pc.getId());
  }
  
  System.out.println("\n--- Pcs clase C ---");
  for (PC pc : claseC)
  {
      System.out.println(pc.getId());
  }
  
  if (!otraClase.isEmpty()) {
      System.out.println("\n--- Pcs de otras clases ---");
      for (PC pc : otraClase)
      {
          System.out.println(pc.getId());
      }    
  }
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

/*private static void leerUsuarios() throws FileNotFoundException{
		Scanner s = new Scanner(System.in);
		File arch = new File("usuarios.txt");
		s = new Scanner(arch);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split(";");
		}
	}*/

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
