package ar.utn.dds.tpa.monitoreo;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.text.ParseException;
import java.util.ArrayList;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        // Esta función se invocará cuando nuestro bot reciba un mensaje
        // Se obtiene el mensaje escrito por el usuario
    	
        final String messageTextReceived = update.getMessage().getText();
        
        int cantIncidencias = Integer.parseInt(System.getenv("CANT_INCIDENCIAS"));
        
        Estado criterioEstado = Estado.valueOf(System.getenv("CRITERIO_ESTADO"));

        String lugar = System.getenv("LUGAR");

        // Se obtiene el id de chat del usuario
        Long chatId = update.getMessage().getChatId();

        // Procesa el repo
        ManejoIncidencias repo = new ManejoIncidencias();

        Incidencia nuevaIncidencia1 = null;
        nuevaIncidencia1 = new Incidencia("4371-23", "03092018", "Escalera mecanica no funciona en entrada de subte X",
		        Estado.REPORTADO, new Operador("Jorge Lopez"), "Emiliano Garcia", "12022020", "La altura de los escalones estan dentro de lo reglamentado", "1387");
        Incidencia nuevaIncidencia2 = null;
        nuevaIncidencia2 = new Incidencia("4372-29", "03062021", "El colectivo XX no se acomoda correctamente en las paradas",
		        Estado.SOLUCIONADO, new Operador("Raul Vasquez"), "Martin Facundo", "05122021", "Necesitamos que se indique la patente", "1389");
        Incidencia nuevaIncidencia3 = null;
        nuevaIncidencia3 = new Incidencia("4373-25", "02092008", "La rampa de la esquina Z entre calles 1 y 2 esta rota",
		        Estado.REPORTADO, new Operador("Jorge Lopez"), "Mariana Sanchez", "12022009", "Se verificó que está en buen estado", "1281");
        Incidencia nuevaIncidencia4 = null;
        nuevaIncidencia4 = new Incidencia("4374-26", "03092010", "La calle 5 esquina con calle 6 necesita semaforo para no videntes",
		        Estado.DESESTIMADO, new Operador("Nicolas Fernandez"), "Maria Insaurralde", "11042021", "No es posible la colocación", "1390");
        Incidencia nuevaIncidencia5 = null;
        nuevaIncidencia5 = new Incidencia("4371-24", "03092018", "Escalera mecanica no funciona en entrada de subte X",
		        Estado.DESESTIMADO, new Operador("Raul Vasquez"), "Maria Garcia", "12022020", "La altura de los escalones estan dentro de lo reglamentado", "1381");
        Incidencia nuevaIncidencia6 = null;
        nuevaIncidencia6 = new Incidencia("4381-25", "04102021", "El colectivo XX no se acomoda correctamente en las paradas",
		        Estado.SOLUCIONADO, new Operador("Raul Vasquez"), "Jorge Facundo Perez", "05032021", "  ", "1379");
        Incidencia nuevaIncidencia7 = null;
        nuevaIncidencia7 = new Incidencia("4371-28", "03092007", "La rampa de la esquina Z entre calles 1 y 2 esta rota",
		        Estado.DESESTIMADO, new Operador("Jorge Lopez"), "Mario Herrera", "12112009", "Se verificó que está en buen estado", "1387");
        Incidencia nuevaIncidencia8 = null;
        nuevaIncidencia8 = new Incidencia("4214-25", "03092010", "La calle 5 esquina con calle 6 necesita semaforo para no videntes",
		        Estado.REPORTADO, new Operador("Nicolas Fernandez"), "Jorge Facundo Perez", "16122020", "No es posible la colocación", "1315");
        Incidencia nuevaIncidencia9 = null;
        nuevaIncidencia9 = new Incidencia("4267-21", "03082012", "La calle 5 esquina con calle 6 necesita semaforo para no videntes",
		        Estado.DESESTIMADO, new Operador("Maria Cruz"), "Eugenia Lopez", "12012013", "No es posible la colocación", "1365");
        Incidencia nuevaIncidencia10 = null;
        nuevaIncidencia10 = new Incidencia("3214-21", "03012012", "La calle 5 esquina con calle 6 necesita semaforo para no videntes",
		        Estado.SOLUCIONADO, new Operador("Nicolas Fernandez"), "Maria Eugenia Sanchez", "16122013", " ", "1315");

        repo.save(nuevaIncidencia1);
        repo.save(nuevaIncidencia2);
        repo.save(nuevaIncidencia3);
        repo.save(nuevaIncidencia4);
        repo.save(nuevaIncidencia5);
        repo.save(nuevaIncidencia6);
        repo.save(nuevaIncidencia7);
        repo.save(nuevaIncidencia8);
        repo.save(nuevaIncidencia9);
        repo.save(nuevaIncidencia10);

        // 1. Se genera la lista con las ultimas N incidencias mas viejas
        CriteriosFiltroIncidencias ultimasNxMasVieja = new FiltroUltimasNLaMasVieja(cantIncidencias);
        
        repo.setCriterio(ultimasNxMasVieja);
        
        ArrayList<Incidencia> incidenciasFiltradas = ultimasNxMasVieja.listaFiltrada(repo.getIncidencias());

        ArrayList<String> msj = new ArrayList<>();
        msj.add("Lista con las ultimas N incidencias mas viejas");
        this.incidenciasToString(incidenciasFiltradas, msj);

        // 2. Se genera la lista con las ultimas N incidencias reportadas
        CriteriosFiltroIncidencias ultimasNreportadas = new FiltroUltimasNReportadas(cantIncidencias);
        repo.setCriterio(ultimasNreportadas);
        ArrayList<Incidencia> incidenciasFiltradas2 = ultimasNreportadas.listaFiltrada(repo.getIncidencias());

        msj.add("\n\nLista con las ultimas N incidencias reportadas");
        this.incidenciasToString(incidenciasFiltradas2, msj);

        // 3. Se genera la lista con las ultimas N incidencias de un estado especifico
        CriteriosFiltroIncidencias ultimasNEstado = new FiltroUltimasNCumplenEstado(cantIncidencias, criterioEstado);
        repo.setCriterio(ultimasNEstado);
        ArrayList<Incidencia> incidenciasFiltradas3 = ultimasNEstado.listaFiltrada(repo.getIncidencias());

        msj.add("\n\nLista con las ultimas N incidencias de un estado especifico");
        this.incidenciasToString(incidenciasFiltradas3, msj);

        // 4. Se genera la lista con incidencias de un lugar en particular
        CriteriosFiltroIncidencias incidenciasDeUnLugar = new CriterioLugar(lugar);
        repo.setCriterio(incidenciasDeUnLugar);
        ArrayList<Incidencia> incidenciasFiltradas4 = incidenciasDeUnLugar.listaFiltrada(repo.getIncidencias());

        msj.add("\n\nLista con incidencias de un lugar en particular");
        this.incidenciasToString(incidenciasFiltradas4, msj);

        // Se crea un objeto mensaje
        SendMessage responseMsg = new SendMessage();
        responseMsg.setChatId(chatId.toString());

        if(msj.isEmpty()) {
            responseMsg.setText("Sin incidencias");
        } else {
            responseMsg.setText(msj.toString());
        }

        try {
            // Se envía el mensaje
            execute(responseMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void incidenciasToString(ArrayList<Incidencia> incidenciasFiltradas, ArrayList<String> msj) {
        int i = 0;
        while(i < incidenciasFiltradas.size()){
            msj.add(incidenciasFiltradas.get(i).toString());
            i++;
        }
    }

    @Override
    public String getBotUsername() {
        // Se devuelve el nombre que dimos al bot al crearlo con el BotFather
        return System.getenv("NOMBRE_BOT");
    }
    @Override
    public String getBotToken() {
        // Se devuelve el token que nos generó el BotFather de nuestro bot
        return System.getenv("TOKEN_BOT");
    }
    public static void main(String[] args) throws TelegramApiException {
        // Se crea un nuevo Bot API
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            // Se registra el bot
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
