package ar.utn.dds.tpa.monitoreo;

import java.util.ArrayList;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


import ar.utn.dds.tpa.monitoreo.Estado;
public class TelegramBot extends TelegramLongPollingBot {

    private String apiEndpoint;

    public TelegramBot(String telegramToken, String apiEndpoint) {
        super(telegramToken);
        this.apiEndpoint = apiEndpoint;
    }

    // Esta función se invocará cuando nuestro bot reciba un mensaje

    @Override
    public void onUpdateReceived(Update update) {

        // Se obtiene el id de chat del usuario
        Long chatId = update.getMessage().getChatId();

        // Se obtiene el mensaje escrito por el usuario
        final String msjOpElegida = update.getMessage().getText().toUpperCase();

        // Se parte en 3 el mensaje escrito por el usuario: CLAVE CANTIDAD CRITERIO
        String[] msjPartido = msjOpElegida.split(" ");

        IncidenciasService service = new IncidenciasService(apiEndpoint);

        ArrayList<String> msj = invocarService (msjPartido, service);

        // Se crea un objeto mensaje
        SendMessage responseMsg = new SendMessage();
        responseMsg.setChatId(chatId.toString());

        if (!msj.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String mensaje : msj) {
                sb.append(mensaje).append("\n");
            }
            responseMsg.setText(sb.toString());
        } else {
            responseMsg.setText("Sin incidencias");
        }

        try {
            // Se envía el mensaje
            execute(responseMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
        String apiEndpoint = System.getenv("API_ENDPOINT");
        if (apiEndpoint == null) {
            apiEndpoint = "http://localhost:8080";
        }
        try {
            // Se registra el bot
            //telegramBotsApi.registerBot(new TelegramBot());
            telegramBotsApi.registerBot(new TelegramBot(System.getenv("TOKEN_BOT"), apiEndpoint));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> invocarService(String[] msjPartido, IncidenciasService service  ) {
        String clave = msjPartido[0];
        String criterio;
        ArrayList<String> msj = new ArrayList<>();
        if (clave.equalsIgnoreCase("ULTIMAS") || clave.equalsIgnoreCase("RECIENTES")
                || clave.equalsIgnoreCase("ESTADO") || clave.equalsIgnoreCase("LUGAR")) {
            try {
                int cantidad = Integer.parseInt(msjPartido[1]);

                switch (clave) {
                    case "ULTIMAS":
                        msj = service.obtenerUltimasNReportadas(cantidad);
                        break;

                    case "RECIENTES":
                        msj = service.obtenerUltimasNPorFecha(cantidad);
                        break;

                    case "ESTADO":
                        criterio = msjPartido[2];
                        msj = service.obtenerUltimasNPorEstado(cantidad, Enum.valueOf(Estado.class, criterio));
                        break;

                    case "LUGAR":
                        criterio = msjPartido[2];
                        msj = service.filtrarPorLugar(criterio);
                        break;
                }
            } catch (NumberFormatException e) {
                msj.add("Por favor ingrese un mensaje válido.");
            } catch (IllegalArgumentException e) {
                msj.add("Por favor ingrese un estado válido: REPORTADO, ASIGNADO, CONFIRMADO, DESESTIMADO, ENPROGRESO, SOLUCIONADO.");
            } catch (Exception e) {
                msj.add("Por favor ingrese un mensaje válido.");
            }
        } else {
            msj.add("Ingrese la palabra clave seguido la cantidad y de ser necesario el filtro que desee (CLAVE CANTIDAD CRITERIO): " + "\n"
                    + "\n - ULTIMAS genera la lista con las N incidencias mas viejas (ULTIMAS N)."
                    + "\n - RECIENTES genera la lista con las N incidencias mas recientes (RECIENTES N)."
                    + "\n - ESTADO genera la lista con las ultimas N incidencias de un estado especifico (ESTADO N estado)."
                    + "\n - LUGAR genera la lista con las ultimas N incidencias de un lugar especifico (LUGAR N lugar)." + "\n"
                    + "\n \n Posibles estados: REPORTADO, ASIGNADO, CONFIRMADO, DESESTIMADO, ENPROGRESO, SOLUCIONADO. "
            );
        }

        return msj;
    }
}
