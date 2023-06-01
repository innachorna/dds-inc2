import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.utn.dds.tpa.monitoreo.Estado;
import ar.utn.dds.tpa.monitoreo.Incidencia;
import ar.utn.dds.tpa.monitoreo.ManejoCsv;
import ar.utn.dds.tpa.monitoreo.Operador;
import ar.utn.dds.tpa.monitoreo.RepoIncidencia;
import ar.utn.dds.tpa.monitoreo.ManejoIncidencias;
import java.io.File;

public class TestCSV {
                                        

	File csvBueno = new File("src/test/resources/csvBien.csv");
	File csvConErrores = new File("src/test/resources/csvConErrores.csv");
	File csvConUnError = new File("src/test/resources/csvConUnError.csv");
	Operador raulVasquez = new Operador("Raul Vasquez");
	Incidencia nuevaIncidencia = new Incidencia("1234-12", "02052000", "El colectivo XX no se acomoda correctamente en las paradas", 
			Estado.DESESTIMADO, raulVasquez, "Martin Facundo", "05052022", "Necesitamos que se indique la patente", "lugar");
	
	ManejoCsv manejoCsv = new ManejoCsv();
	ManejoIncidencias manejoIncidencias = new ManejoIncidencias();

	
	@Test
	  public void siSeCambiaElEstadoSigueConcidiendo(){

		nuevaIncidencia.setEstado(Estado.ASIGNADO);
		
		assertEquals(Estado.ASIGNADO, nuevaIncidencia.getEstado() );
	  }
	
	@Test
	public void csvCargaBien() {
		manejoCsv.cargarCSV(csvBueno);
		assertEquals(5, manejoIncidencias.countIncidencias());	//se cargan los 4 registros
		manejoIncidencias.VaciarIncidenciasCargadas();
		
}
	
	
	@Test
		public void csvConErroresCargaMal() {
		manejoCsv.cargarCSV(csvConErrores);
		assertEquals(2,manejoIncidencias.countIncidencias());	//de los 4 registros, 2 estan escritas erroneamente por lo cual solo carga los otros 2
		manejoIncidencias.VaciarIncidenciasCargadas();
	}

	@Test
	public void findByEstado() {
		manejoCsv.cargarCSV(csvBueno);
		assertEquals(2, (manejoIncidencias.findByEstado(Estado.REPORTADO)).size());	//hay 2 incidencias con estado REPORTADO
		manejoIncidencias.VaciarIncidenciasCargadas();
	}

	@Test
	public void csvConSoloUnErrorCargaMal() {
		manejoCsv.cargarCSV(csvConUnError);
		assertEquals(4,manejoIncidencias.countIncidencias());	//de los 5 registros, 1 solo esta escrito mal entonces cargan solo 3
		manejoIncidencias.VaciarIncidenciasCargadas();
	
}

	
	
}
