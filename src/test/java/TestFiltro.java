
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.utn.dds.tpa.monitoreo.CriterioLugar;
import ar.utn.dds.tpa.monitoreo.CriteriosFiltroIncidencias;
import ar.utn.dds.tpa.monitoreo.Estado;
import ar.utn.dds.tpa.monitoreo.FiltroUltimasNCumplenEstado;
import ar.utn.dds.tpa.monitoreo.FiltroUltimasNLaMasVieja;
import ar.utn.dds.tpa.monitoreo.FiltroUltimasNReportadas;
import ar.utn.dds.tpa.monitoreo.Incidencia;
import ar.utn.dds.tpa.monitoreo.ManejoCsv;
import ar.utn.dds.tpa.monitoreo.ManejoIncidencias;
import ar.utn.dds.tpa.monitoreo.Operador;

public class TestFiltro {


	Operador raulVasquez = new Operador("Raul Vasquez");
	Incidencia nuevaIncidencia = new Incidencia("1234-12", "02052005", "El colectivo XX no se acomoda correctamente en las paradas",
			Estado.ASIGNADO, raulVasquez, "Martin Facundo", "05052010", "Necesitamos que se indique la patente", "CABALLITO");

	Incidencia nuevaIncidencia2 = new Incidencia("1234-12", "02052018", "El colectivo XX no se acomoda correctamente en las paradas",
			Estado.CONFIRMADO, raulVasquez, "Martin Facundo", "05052022", "Necesitamos que se indique la patente", "PALERMO");

	Incidencia nuevaIncidencia3 = new Incidencia("1234-12", "02052010", "El colectivo XX no se acomoda correctamente en las paradas",
			Estado.DESESTIMADO, raulVasquez, "Martin Facundo", "05052015", "Necesitamos que se indique la patente", "LUGANO");

	Incidencia nuevaIncidencia4 = new Incidencia("1234-12", "02052000", "El colectivo XX no se acomoda correctamente en las paradas",
			Estado.CONFIRMADO, raulVasquez, "Martin Facundo", "05052002", "Necesitamos que se indique la patente", "ONCE");

	Incidencia nuevaIncidencia5 = new Incidencia("1234-12", "02052010", "El colectivo XX no se acomoda correctamente en las paradas",
			Estado.DESESTIMADO, raulVasquez, "Martin Facundo", "05052015", "Necesitamos que se indique la patente", "LUGANO");

	Incidencia nuevaIncidencia6 = new Incidencia("1234-12", "02051999", "El colectivo XX no se acomoda correctamente en las paradas",
			Estado.ASIGNADO, raulVasquez, "Martin Facundo", "05052002", "Necesitamos que se indique la patente", "RETIRO");


	ManejoCsv manejoCsv = new ManejoCsv();
	ManejoIncidencias manejoIncidencias = new ManejoIncidencias();

	@BeforeEach
	public void init() {
		manejoIncidencias.save(nuevaIncidencia);
		manejoIncidencias.save(nuevaIncidencia2);
		manejoIncidencias.save(nuevaIncidencia3);
		manejoIncidencias.save(nuevaIncidencia4);
		manejoIncidencias.save(nuevaIncidencia5);
		manejoIncidencias.save(nuevaIncidencia6);

	}

	@AfterEach
	public void setUpAfter() {
		manejoIncidencias.VaciarIncidenciasCargadas();
	}

	@Test
	public void filtroUltimasNReportadas(){


		CriteriosFiltroIncidencias filtro = new FiltroUltimasNReportadas(3);

		ArrayList<Incidencia> incidenciasFiltradas = filtro.listaFiltrada(manejoIncidencias.getIncidencias());

		ArrayList<Incidencia> incidenciasPrueba = new  ArrayList<Incidencia>() ;
		incidenciasPrueba.add(nuevaIncidencia6);
		incidenciasPrueba.add(nuevaIncidencia5);
		incidenciasPrueba.add(nuevaIncidencia4);

		assertEquals(incidenciasPrueba, incidenciasFiltradas );
	}

	@Test
	public void filtroUltimasNCumplenEstado(){

		CriteriosFiltroIncidencias filtro = new FiltroUltimasNCumplenEstado(4, Estado.DESESTIMADO);

		ArrayList<Incidencia> incidenciasFiltradas = filtro.listaFiltrada(manejoIncidencias.getIncidencias());

		ArrayList<Incidencia> incidenciasPrueba = new  ArrayList<Incidencia>() ;
		incidenciasPrueba.add(nuevaIncidencia3);
		incidenciasPrueba.add(nuevaIncidencia5);

		assertEquals(incidenciasPrueba, incidenciasFiltradas );
	}

	@Test
	public void filtroUltimasNCumplenEstadoPredicado(){
		CriteriosFiltroIncidencias filtro = new FiltroUltimasNCumplenEstado(4, Estado.DESESTIMADO);

		ArrayList<Incidencia> incidenciasFiltradas = filtro.listaFiltrada(manejoIncidencias.getIncidencias());

		Predicate<Incidencia> p1 = inc -> inc.getEstado().equals(Estado.DESESTIMADO);

		Assertions.assertTrue(incidenciasFiltradas.stream().allMatch(p1));
	}

	@Test
	public void FiltroUltimasNLaMasVieja(){

		CriteriosFiltroIncidencias filtro = new FiltroUltimasNLaMasVieja(2);

		ArrayList<Incidencia> incidenciasFiltradas = filtro.listaFiltrada(manejoIncidencias.getIncidencias());

		ArrayList<Incidencia> incidenciasPrueba = new  ArrayList<Incidencia>() ;
		incidenciasPrueba.add(nuevaIncidencia4);
		incidenciasPrueba.add(nuevaIncidencia6);

		assertEquals(incidenciasPrueba, incidenciasFiltradas );
	}

	@Test
	public void IncidenciasDeUnLugar(){

		CriteriosFiltroIncidencias filtro = new CriterioLugar("LUGANO");

		ArrayList<Incidencia> incidenciasFiltradas = filtro.listaFiltrada(manejoIncidencias.getIncidencias());

		ArrayList<Incidencia> incidenciasPrueba = new  ArrayList<Incidencia>() ;
		incidenciasPrueba.add(nuevaIncidencia3);
		incidenciasPrueba.add(nuevaIncidencia5);

		assertEquals(incidenciasPrueba, incidenciasFiltradas );
	}
}