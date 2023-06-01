package ar.utn.dds.tpa.monitoreo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class ManejoIncidencias implements RepoIncidencia{

    public void save(Incidencia nuevaIncidencia) {
    	incidencias.add(nuevaIncidencia);
    }

     public List<Incidencia> findByEstado(Estado estado) {
		return  incidencias.stream().filter(incidencia -> incidencia.getEstado().equals(estado)).collect(Collectors.toList());
     }
     
    public Integer countIncidencias() {
        return incidencias.size();
    }

	public ArrayList<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void VaciarIncidenciasCargadas() {
		incidencias.clear();
	}
	
	private CriteriosFiltroIncidencias criterio;
    

	public CriteriosFiltroIncidencias getCriterio() {
		return criterio;
	}
	public void setCriterio(CriteriosFiltroIncidencias criterio) {
		this.criterio = criterio;
	}
}