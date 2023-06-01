package ar.utn.dds.tpa.monitoreo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManejoCsv implements RepoIncidencia{
	
    public void cargarCSV(File nuevoCSV) {
    	
    	FileReader fr = null;
    	BufferedReader br = null;
		int contador=0;
		int lineasConError=0;
		
    	try {
    		fr = new FileReader(nuevoCSV);
    		br = new BufferedReader(fr);
    		
    		String linea;
    		int nroDeLinea=0;
    		
    		System.out.println("Analizamos Incidencia: ");
    		
    		while((linea=br.readLine())!=null) {
    			nroDeLinea++;
    			String arreglo []= linea.split("\t");

    			if(!(arreglo[0].isEmpty()) && !(arreglo[1].isEmpty()) && !(arreglo[2].isEmpty()) && !(arreglo[3].isEmpty())&&
    					!(arreglo[0].isBlank()) && !(arreglo[1].isBlank()) && !(arreglo[2].isBlank()) && !(arreglo[3].isBlank())) {

    				Incidencia nuevaIncidencia = new Incidencia();
    				nuevaIncidencia.setCodigoDeCatalogo(arreglo[0]);
    				nuevaIncidencia.setDescripcion(arreglo[2]);
    				nuevaIncidencia.setEstado(Estado.valueOf(arreglo[3]));
    				nuevaIncidencia.setOperador(new Operador(arreglo[4]));
    				nuevaIncidencia.setUsuario(arreglo[5]);
    				nuevaIncidencia.setMotivoRechazo(arreglo[7]);

    				incidencias.add(nuevaIncidencia);
    				contador++;
    				
    				System.err.println("La incidencia de la fila N°"+nroDeLinea+" no tiene errores.");
    			}
    			else {
					System.err.println("La incidencia de la fila N°"+nroDeLinea+" tiene errores.");
					lineasConError++;
				}
    			
    		
    		}
    		
    		
    	}catch(Exception ex) {
    		
    		
    	}
    	
    	finally {
    		try {
    			if(fr!= null) {
    				fr.close();
    			}
    			
    			if(br!=null) {
    				br.close();
    			}
    			
    			
    		}
    		catch(Exception ex) {
    			ex.printStackTrace();
    		}
    	}
    	
    	System.out.println("Se cargaron "+ contador +" Incidencias y hubo "+ lineasConError +" lineas con error.");
    }
    

	public void validar(String pathCsv) throws FileNotFoundException {
		Path path = Paths.get(pathCsv);
		if (!Files.exists(path)) {
			throw new FileNotFoundException(pathCsv);
		}
	}
	
}
