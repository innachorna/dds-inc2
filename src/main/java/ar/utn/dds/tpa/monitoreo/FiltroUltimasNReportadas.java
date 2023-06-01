package ar.utn.dds.tpa.monitoreo;

import java.util.ArrayList;
import java.util.Collections;

public class FiltroUltimasNReportadas extends FiltroUltimasN implements CriteriosFiltroIncidencias {

	public FiltroUltimasNReportadas(int ultimasN) {
		super(ultimasN);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ArrayList<Incidencia> listaFiltrada(ArrayList<Incidencia> incidencias) {
		
		ArrayList<Incidencia> nuevaLista = new ArrayList<Incidencia>();
		nuevaLista = super.listaFiltrada(incidencias);
		
		Collections.reverse(nuevaLista);
		
		return nuevaLista;
		
		
	}

}
