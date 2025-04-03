import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerHTTP {

    public static final int PORT = 8081;
    static final String CONTEXT = "/pracownicy";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext(CONTEXT, new PracownicyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Serwer HTTP uruchomiony na porcie " + PORT + ". Sprawdź http://localhost:" + PORT + CONTEXT);
    }

    static class PracownicyHandler implements HttpHandler {
        private String contentType;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                Map<String, String> params = parseQueryParams(query);
                List<Pracownik> pracownicy = getListaPracownikow();
                String format = params.getOrDefault("format", "json");
                String response;
                String contextType;

                if ("xml".equalsIgnoreCase(format)) {
                    response = contextToXml(pracownicy);
                    contextType = "application/xml";
                } else {
                    response = convertToJson(pracownicy);
                    contextType = "application/json";
                }

                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, response.getBytes().length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }

        private String convertToJson(List<Pracownik> pracownicy) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(pracownicy);
        }

        private String contextToXml(List<Pracownik> pracownicy) throws JsonProcessingException {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.writeValueAsString(pracownicy);
        }

        private Map<String, String> parseQueryParams(String query) {
            if (query == null || query.isEmpty()) {
                return Map.of();
            }

            return List.of(query.split("&")).stream().map(param -> param.split("=")).filter(paramArr -> paramArr.length == 2).collect(Collectors.toMap(paramArr -> paramArr[0], paramArr -> paramArr[1]));
        }

        private List<Pracownik> getListaPracownikow() {
            List<Pracownik> pracownicy = new ArrayList<>();
            pracownicy.add(new Pracownik("Jan", "Kowalski", "Programista"));
            pracownicy.add(new Pracownik("Sebastian", "Kowalczyk", "Wsparcie IT"));
            pracownicy.add(new Pracownik("Adam", "Bułka", "Administrator"));
            return pracownicy;
        }
    }
}
