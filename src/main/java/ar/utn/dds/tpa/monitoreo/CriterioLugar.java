package ar.utn.dds.tpa.monitoreo;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CriterioLugar implements CriteriosFiltroIncidencias {

	private String lugar;
	
	public CriterioLugar(String i) {
		super();
		this.lugar = i;
	}

	@Override
	public ArrayList<Incidencia> listaFiltrada(ArrayList<Incidencia> incidencias) {
		return incidencias.stream().filter(incidencia -> incidencia.getLugar().equals(lugar)).collect(Collectors.toCollection(ArrayList::new));
	}

}
