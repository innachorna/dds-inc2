package ar.utn.dds.tpa.monitoreo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MonitoreoApp {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		
		// el main debe: cargar archivo csv por linea de comando que tome el archivo
		// como argumento de entrada y como salida muestre la cantidad de incidencias
		//cargadas y lineas con error
		/*
		 * 		-para separar campos usar tab
		 * 		-si un hay un error en alguna lunea se debe indicar por stderr
		 * 		 q la linea tiene un error y seguir con la siguiente
		 * 		
		 * */
		
		// Se debe implementar una Github Action que permita ejecutar las pruebas
		//unitarias cuando se realice un push a la rama main
		
		Scanner sc = new Scanner(System.in);

		System.out.println("Ingrese la direccion del csv a leer:"); 
		String csv = sc.nextLine(); 
		System.out.println("Se ingreso correctamente: " + csv);
		
		Path PathCsv = Paths.get(csv);
		if ( ! Files.exists( PathCsv )) {
			System.err.println("'" + csv + "' no cargo correctamente...");
			System.exit(1);
		}
		
		  // Le cargo al repo de incidencias las Incidencias --> Utilizo la Capa de fuente de datos
        File archivoCSV = new File(csv);
        ManejoCsv manejoCsv = new ManejoCsv();
        
        
		// Cargo el Archivo del Sistema de archivos --> Utilizo la Capa de fuente de datos
				
				try {
					manejoCsv.validar(csv);				    
					manejoCsv.cargarCSV(archivoCSV);
				} catch (IOException e) {
					System.err.println("error al cargar el directorio con documentos: ");
					e.printStackTrace();
					System.exit(2);
				}
	   
	
	}
}

