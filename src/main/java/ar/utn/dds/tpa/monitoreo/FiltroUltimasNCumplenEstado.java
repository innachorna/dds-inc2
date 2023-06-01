package ar.utn.dds.tpa.monitoreo;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FiltroUltimasNCumplenEstado extends FiltroUltimasN implements CriteriosFiltroIncidencias {

	Estado estado;
	
	public FiltroUltimasNCumplenEstado(int ultimasN, Estado estado) {
		super(ultimasN);
		this.estado = estado;
		// TODO Auto-generated constructor stub
	}

	//no estoy seguro si tengo que agarrar las ultimas N que cumplan el estado o agarrar las ultimas N y
	//ver cual de esas cumple el estado, opte por lo ultimo
	
	@Override
	public ArrayList<Incidencia> listaFiltrada(ArrayList<Incidencia> incidencias) {
		
		ArrayList<Incidencia> nuevaLista = new ArrayList<Incidencia>();
		nuevaLista = super.listaFiltrada(incidencias);
		
		return nuevaLista.stream().filter(incidencia -> incidencia.getEstado().equals(estado)).collect(Collectors.toCollection(ArrayList::new));
	}
	
	
}
