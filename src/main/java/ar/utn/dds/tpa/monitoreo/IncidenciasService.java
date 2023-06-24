package ar.utn.dds.tpa.monitoreo;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import ar.utn.dds.tpa.monitoreo.Estado;

public class IncidenciasService {
    private String apiEndpoint;
    ArrayList<String> msj = new ArrayList<>();

    public IncidenciasService(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public ArrayList<String> obtenerUltimasNReportadas(int n) {
        String url = "/incidencias/ultimas-n-reportadas?n=" + n;
        msj.add("Lista con las últimas N incidencias reportadas: ");
        msj.add(consultaAPI(url));
        return msj;
    }

    public ArrayList<String> obtenerUltimasNPorFecha(int n) {
        String url = "/incidencias/ultimas-n-por-fecha?n=" + n;
        msj.add("Lista con las ultimas N incidencias mas recientes: ");
        msj.add(consultaAPI(url));
        return msj;
    }

    public ArrayList<String> obtenerUltimasNPorEstado(int n, Estado estado) {
        String url = "/incidencias/ultimas-n-por-estado?n=" + n + "&estado=" + estado;
        msj.add("Lista con las ultimas N incidencias filtradas por estado: ");
        msj.add(consultaAPI(url));
        return msj;
    }

    public ArrayList<String> filtrarPorLugar( String lugar) {
        String url = "/incidencias/filtrar-por-lugar?lugar=" + lugar;
        msj.add("Lista con las ultimas N incidencias filtradas por lugar :");
        msj.add(consultaAPI(url));
        return msj;
    }

    private String consultaAPI(String url) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(this.apiEndpoint + url);

        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            //System.out.println(response);

            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}