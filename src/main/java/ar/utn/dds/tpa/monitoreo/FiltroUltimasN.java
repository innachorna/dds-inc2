package ar.utn.dds.tpa.monitoreo;

import java.util.ArrayList;

public class FiltroUltimasN implements CriteriosFiltroIncidencias {
	
	private int ultimasN;
	
	
	public FiltroUltimasN(int ultimasN) {
		super();
		this.ultimasN = ultimasN;
	}



	@Override
	public ArrayList<Incidencia> listaFiltrada(ArrayList<Incidencia> incidencias) {

		ArrayList<Incidencia> nuevaLista = new ArrayList<Incidencia>();
		nuevaLista = incidencias;
		
			while(ultimasN!=nuevaLista.size()) {
				nuevaLista.remove(0);
			}
			
			return nuevaLista;
	}

}
