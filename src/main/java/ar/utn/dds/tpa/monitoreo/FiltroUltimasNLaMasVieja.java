package ar.utn.dds.tpa.monitoreo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class FiltroUltimasNLaMasVieja extends FiltroUltimasN implements CriteriosFiltroIncidencias {

	public FiltroUltimasNLaMasVieja(int ultimasN) {
		super(ultimasN);
		// TODO Auto-generated constructor stub
	}


	public int ocurreAntes(LocalDate fecha1,LocalDate fecha2) {
		if(fecha1.isBefore(fecha2)) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	@Override
	public ArrayList<Incidencia> listaFiltrada(ArrayList<Incidencia> incidencias) {
		
		Collections.sort(incidencias, (unaIncidencia, otraIncidencia) -> ocurreAntes(unaIncidencia.getFechaCreacion(),(otraIncidencia.getFechaCreacion())));

		
		ArrayList<Incidencia> nuevaLista = new ArrayList<Incidencia>();
		
		nuevaLista = super.listaFiltrada(incidencias);
		
		
		return nuevaLista;
	}
	
	
}