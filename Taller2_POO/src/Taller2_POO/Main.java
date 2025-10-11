package Taller2_POO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;





public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		//login principal
		Scanner sc = new Scanner(System.in);
        boolean logueado = false;
        String rol = "";
        Usuario usuarioLogueado = null;

        do {
            System.out.println("\n=== LOGIN SECURENET ===");
            System.out.println("Usuario: ");
            String user = sc.nextLine();
            System.out.println("Contraseña: ");
            String pass = sc.nextLine();

            
            usuarioLogueado = verificarUsuario(user, pass,cargarUsuarios());

            if (usuarioLogueado != null) {
            	
                logueado = true;
                rol = usuarioLogueado.getRol();
                System.out.println("Login exitoso como " + rol + "!");
            } else {
                System.out.println("Usuario o contraseña incorrecta.\n");
            }

        } while (!logueado);

        // Menú según el rol de usuario logueado
        if (rol.equals("ADMIN")) {
        	
            menuAdmin(sc);
        } else if (rol.equals("USER")) {
        	
            menuUsuario(sc, usuarioLogueado);
        }

        sc.close();
    }
	
	
	//funciones de lectura 
	
	//carga los datos de usuarios.txt
	public static ArrayList<Usuario> cargarUsuarios() {
		ArrayList<Usuario> lista = new ArrayList<>();
		
		try {
			File archivo = new File("usuarios.txt");
			Scanner lector = new Scanner(archivo);
			
			while (lector.hasNextLine())
			{
				String linea = lector.nextLine().trim();
				if (linea.isEmpty())
				{
					continue; // saltar lineas vacias
				}
				
				String[] datos = linea.split(";");
				if (datos.length >= 3)
				{
					String nombre = datos[0].trim();
					String password = datos[1].trim();
					String rol = datos[2].trim();
					lista.add(new Usuario(nombre, password, rol));
				}
			}
			
			lector.close();
	
		} catch (FileNotFoundException e) {
			System.out.println("archivo usuarios.txt no encontrado");
		}
		
		return lista;
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
	
	
	//funciones de logueo
	
	//verifica la contraseña del login con la que registra el usuario
	private static Usuario verificarUsuario(String user, String pass, ArrayList<Usuario> usuarios) 
	{
		for (Usuario u : usuarios) {
			if (u.getNombre().equals(user) && hash(pass).equals(u.getPassword())) //compara si el usuario y el hash de la contraseña a loguear coinciden con la que tiene en el registro
			{
				return u;  //si es el usuario verificado devuelve sus datos
			}
		}
		return null; //no se encontro usuario o la contraseña era incorrecta
	}
	
	//hashea la contraseña usando el algoritmo sha-256
	private static String hash(String input) {
		try {
			
			MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = algoritmo.digest(input.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(hashBytes);
			
		} catch (Exception e)
		{
			return null;
		}
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
			 escanearPC(sc, usuarioLogueado);  //detalla los puertos e informacion relevante del pc buscado para escribirlo en txt
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
	
	
	//menu de administrador
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
	
	
	//clasifica a los pcs por su nivel de riesgo
	private static void clasificarPCsRiesgo()
	{
		ArrayList<PC> pcs = cargarPCs();
		ArrayList<Puerto> puertos = cargarPuertos(pcs);
		ArrayList<Vulnerabilidad> vulnerabilidades = cargarVulnerabilidades();
		asociarVulnerabilidades(puertos, vulnerabilidades);
		
		ArrayList<PC> riesgoAlto = new ArrayList<>();
		ArrayList<PC> riesgoMedio = new ArrayList<>();
		ArrayList<PC> riesgoBajo = new ArrayList<>();
		
		for (PC pc : pcs)
		{
			String riesgo = pc.calcularNivelRiesgo();
			if (riesgo.equals("ALTO"))
			{
				riesgoAlto.add(pc);
			} else if (riesgo.equals("MEDIO"))
			{
				riesgoMedio.add(pc);
			} else {
				riesgoBajo.add(pc);
			}
			
		}
	
		if (!riesgoAlto.isEmpty())
		{
			System.out.println("\n--- Riesgo Alto ---");
			for (PC pc : riesgoAlto)
			{
				System.out.println("-->" + pc.getId() + " - " + pc.getIp() + " - Cantidad de vulnerabilidades: " + pc.contarVulnerabilidades());
			}
		} else 
		{
			System.out.println("\n--- Riesgo Alto ---");
			System.out.println("... sin registros ...");
		}
		
		
		if (!riesgoMedio.isEmpty())
		{
			System.out.println("\n--- Riesgo Medio ---");
			for (PC pc : riesgoMedio)
			{
				System.out.println("-->" + pc.getId() + " - " + pc.getIp() + " - Cantidad de vulnerabilidades: " + pc.contarVulnerabilidades());
			}
		} else
		{
			System.out.println("\n--- Riesgo Medio ---");
			System.out.println("... sin registros ...");
		}
		
		
		if (!riesgoBajo.isEmpty())
		{
			System.out.println("\n--- Riesgo Bajo ---");
			for (PC pc : riesgoBajo)
			{
				System.out.println("-->" + pc.getId() + " - " + pc.getIp() + " - Cantidad de vulnerabilidades: " + pc.contarVulnerabilidades());
			}
		} else {
			System.out.println("\n--- Riesgo Bajo ---");
			System.out.println("... sin registros ...");
	
		}
	}
	
	
	//añade un nuevo pc al sistema junto con su informacion tecnica
	private static void agregarPC(Scanner sc) {
		ArrayList<PC> pcs = cargarPCs();
		System.out.println("Id del nuevo Pc a registrar: ");
		String id = sc.nextLine().trim();
		
		//ver si el id ya esta registrado en el sistema
		for (PC pc : pcs)
		{
			if (pc.getId().equals(id))
			{
				System.out.println("Error, se ha encontrado un Pc con el id entregado");
				return;
			}
		}
		
		System.out.println("Ip: ");
		String ip = sc.nextLine().trim();
		System.out.println("Sistema operativo: ");
		String so = sc.nextLine().trim();
		
		PC nuevoPC = new PC(id, ip, so);
		
	
		boolean opcion = false;
		while (!opcion)
		{
			System.out.println("Quiere agregar puertos al pc? (s/n): ");
			String respuesta = sc.nextLine().trim().toLowerCase(); //en caso de mayusculas 
			
			if (respuesta.equals("s") || respuesta.equals("si"))
			{
				opcion = true;
				boolean agregarPuertos = true;
				
				while (agregarPuertos) {
					System.out.println("Ingrese el numero del puerto: ");
					int numPuerto;
					
					try
					{
						numPuerto = Integer.parseInt(sc.nextLine().trim());
						
					} catch (NumberFormatException e)
					{
						System.out.println("Error, numero de puerto ingresado incorrecto");
						continue;
					}
					
					System.out.println("Estado del puerto (abierto/cerrado): ");
					String estado = sc.nextLine().trim().toLowerCase();
					
					//validacion de estado
					if (!estado.equals("abierto") && !estado.equals("cerrado"))
					{
						System.out.println("Opcion ingresada incorrecta, use (abierto) o (cerrado)");
						continue;
					}
					
					//crear y agregar puerto al pc
					Puerto puerto = new Puerto(numPuerto,estado,nuevoPC);
					nuevoPC.agregarPuerto(puerto);
					
					System.out.println("Puerto " + numPuerto + " agregado correctamente! ");
					
					System.out.println("Quiere agregar otro puerto? (s/n): ");
					String continuar = sc.nextLine().trim().toLowerCase();
					if (!continuar.equals("s") && !continuar.equals("si"))
					{
						agregarPuertos = false; //para salir
					}
				}
				
				//registrar los puertos asociados al pc en puertos.txt
				guardarPuertos(nuevoPC);	
			}
			else if (respuesta.equals("n") || respuesta.equals("no"))
			{
				opcion = true;
				System.out.println("no se agregaron puertos...");
			} else 
			{
				System.out.println("Error opcion ingresada invalida");
			}
		}
		pcs.add(nuevoPC);
		
		//agregar el pc registrado al sistema actualizando el archivo pcs.txt 
		guardarPCs(pcs);
		System.out.println("Pc agregado al sistema correctamente!");
		
	}
	
	
	private static void guardarPuertos(PC pc)
	{
		try
		{
			FileWriter writer = new FileWriter("puertos.txt", true);
			for (Puerto puerto : pc.getPuertos())
			{
				String linea = pc.getId() + "|" + puerto.getNumero() + "|" + puerto.getEstado();
				writer.write(linea + "\n");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Error al guardar el archivo puertos ->" + e.getMessage());
		}
		
	}


	//elimina el pc buscado por el id del sistema
	private static void eliminarPC(Scanner sc) {
		ArrayList<PC> pcs = cargarPCs();
		
		System.out.println("Id del Pc a eliminar: ");
		String id = sc.nextLine().trim();
		
		PC pcAEliminar = null;
		
		for (PC pc : pcs)
		{
			if (pc.getId().equals(id))
			{
				pcAEliminar = pc;
				break;
				
			}
		}
		
		if (pcAEliminar != null)
		{
			pcs.remove(pcAEliminar);
			//guardar la lista de pcs actualizada en pcs.txt
			guardarPCs(pcs);
			
			//eliminar puertos asociados al pc
			eliminarPuertos(id);
			
			System.out.println("Pc ha sido eliminado correctamente!");
		} else {
			System.out.println("No se ha encontrado el Pc con el Id: " + id);
		}
		
	}
	
	
	private static void eliminarPuertos(String idPc) {
		try {
			ArrayList<PC> pcs = cargarPCs();
			ArrayList<Puerto> puertos = cargarPuertos(pcs);
			
			//se filtran los puertos para dejar los que no estan asociados al pc eliminado
			ArrayList<Puerto> puertosRestantes = new ArrayList<>();
			for (Puerto puerto : puertos)
			{
				if (!puerto.getPcAsociado().getId().equals(idPc))
				{
					puertosRestantes.add(puerto);
				}
			}
			
			//actualizar puertos.txt para quitar los puertos eliminados
			FileWriter writer = new FileWriter("puertos.txt");
			for (Puerto puerto : puertosRestantes)
			{
				String linea = puerto.getPcAsociado().getId() + "|" + puerto.getNumero() + "|" + puerto.getEstado();
				writer.write(linea + "\n" );
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Error al eliminar puertos: " + e.getMessage());
		}
		
	}


	//actualiza el archivo pcs.txt cuando se agrega o elimina un pc del sistema 
	public static void guardarPCs(ArrayList<PC> pcs)
	{
		try {
			FileWriter writer = new FileWriter("pcs.txt");
			for (PC pc : pcs)
			{
				String linea = pc.getId() + "|" + pc.getIp() + "|" + pc.getSo();
				writer.write(linea + "\n");
			}
			writer.close();
		}catch(IOException e) {
			System.out.println("Error al guardar el archivo pcs ->" + e.getMessage());
			
		}
	}
	
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
	
	
	
	
	
	//metodos del menu de usuario
	
	//escanea el pc escogido generando un registro de su informacion y la del usuario que realizo el escaner
	private static void escanearPC(Scanner sc, Usuario usuarioLogueado) {
		ArrayList<PC> pcs = cargarPCs();
		ArrayList<Puerto> puertos = cargarPuertos(pcs);
		ArrayList<Vulnerabilidad> vulnerabilidades = cargarVulnerabilidades();
		asociarVulnerabilidades(puertos, vulnerabilidades);
		
		System.out.println("Ingrese el Id del pc a escanear: ");
		String id = sc.nextLine().trim();
		
		PC pcEscaneado = null;
		for (PC pc : pcs)
		{
			if (pc.getId().equals(id))
			{
				pcEscaneado = pc;
				break;
			}
			
		}
		
		if (pcEscaneado != null)
		{
			//se crea objeto escaneo con el formato del registro para escribirlo en reportes.txt
			Escaner escaneo = new Escaner(pcEscaneado, usuarioLogueado, new Date());
			
			//se imprime el reporte por consola antes de guardarlo en el txt
			System.out.println("==========================\n");
			String reporte = escaneo.generarReporte();
			System.out.println(reporte);
			System.out.println("==========================\n");
			
			guardarReporte(escaneo);
			System.out.println("escaneo guardado en reportes.txt");
			
		} else {
			System.out.println("no se encontro el PC con el id: " + id);
		}
		
	}
	
	//guarda el informe solicitado del escaneo de los pcs en el archivo reportes.txt
	private static void guardarReporte(Escaner escaneo)
	{
		try {
			FileWriter writer = new FileWriter("reportes.txt", true);
			String reporte = escaneo.generarReporte();
			writer.write(reporte);
			writer.write("\n================\n\n");
			writer.close();
		}catch (IOException e) {
			System.out.println("Error al guardar reporte.txt: " + e.getMessage());
		}
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
	  
	  if (total == 0)
	  {
		System.out.println("no se encontraron puertos abiertos en la red");
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
}
	
