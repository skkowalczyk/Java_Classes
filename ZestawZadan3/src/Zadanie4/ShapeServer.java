package Zadanie4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ShapeServer {
    private final List<Shape> shapes;
    private final ObjectMapper jsonMapper;
    private final XmlMapper xmlMapper;

    public ShapeServer(List<Shape> shapes) {
        this.shapes = shapes;
        this.jsonMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
    }

    public void start() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8080));
        serverChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        System.out.println("Serwer uruchomiony na http://localhost:8080/figures");
        System.out.println("Dostępne formaty:");
        System.out.println("- HTML: http://localhost:8080/figures");
        System.out.println("- JSON: http://localhost:8080/figures?format=json");
        System.out.println("- XML: http://localhost:8080/figures?format=xml");

        while (true) {
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();

                if (key.isAcceptable()) {
                    accept(selector, serverChannel);
                } else if (key.isReadable()) {
                    read(key, buffer);
                }
            }
        }
    }

    private void accept(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Nawiązano połączenie z klientem");
    }

    private void read(SelectionKey key, ByteBuffer buffer) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            clientChannel.close();
            System.out.println("Rozłączono");
            return;
        }

        buffer.flip();
        String request = new String(buffer.array(), 0, buffer.limit());
        String response = handleHttpRequest(request);
        clientChannel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
        clientChannel.close();
        buffer.clear();
    }

    private String handleHttpRequest(String request) {
        String[] lines = request.split("\r\n");
        if (lines.length > 0 && lines[0].startsWith("GET")) {
            String[] requestParts = lines[0].split(" ");
            if (requestParts.length < 2)
                return getNotFoundResponse();

            String fullUrl = requestParts[1];
            String url = fullUrl;
            String queryString = "";

            if (fullUrl.contains("?")) {
                String[] urlParts = fullUrl.split("\\?", 2);
                url = urlParts[0];
                queryString = urlParts[1];
            }

            Map<String, String> queryParams = parseQueryParams(queryString);
            String format = queryParams.getOrDefault("format", "html").toLowerCase();

            if (url.startsWith("/figures")) {
                try {
                    switch (format) {
                        case "json":
                            return getJsonResponse();
                        case "xml":
                            return getXmlResponse();
                        default:
                            return getHtmlResponse();
                    }
                } catch (IOException e) {
                    System.err.println("Błąd przy serializacji danych: " + e.getMessage());
                    return getServerErrorResponse();
                }
            }
        }

        return getNotFoundResponse();
    }

    private String getHtmlResponse() {
        StringBuilder responseBody = new StringBuilder();
        responseBody.append("<html><head><title>Figury geometryczne</title>");
        responseBody.append("</head><body>");

        responseBody.append("<h1>Figury geometryczne</h1>");
        responseBody.append("<div class='formats'>Format: ");
        responseBody.append("<a href='/figures'>HTML</a> | ");
        responseBody.append("<a href='/figures?format=json'>JSON</a> | ");
        responseBody.append("<a href='/figures?format=xml'>XML</a>");
        responseBody.append("</div>");

        responseBody.append("<table>");
        responseBody.append("<tr><th>Nazwa</th><th>Pole powierzchni</th><th>Obwód</th></tr>");

        for (Shape shape : shapes) {
            responseBody.append("<tr>");
            responseBody.append("<td>").append(shape.getName()).append("</td>");
            responseBody.append("<td>").append(String.format("%.2f", shape.calculateArea()))
                    .append("</td>");
            responseBody.append("<td>").append(String.format("%.2f", shape.calculatePerimeter()))
                    .append("</td>");
            responseBody.append("</tr>");
        }

        responseBody.append("</table></body></html>");

        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: " + responseBody.toString().getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "\r\n" +
                responseBody.toString();
    }

    private String getJsonResponse() throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        List<Map<String, Object>> shapesList = new ArrayList<>();

        for (Shape shape : shapes) {
            Map<String, Object> shapeData = new HashMap<>();
            shapeData.put("name", shape.getName());
            shapeData.put("area", shape.calculateArea());
            shapeData.put("perimeter", shape.calculatePerimeter());
            shapesList.add(shapeData);
        }

        responseData.put("shapes", shapesList);
        String jsonBody = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseData);

        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json; charset=UTF-8\r\n" +
                "Content-Length: " + jsonBody.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "\r\n" +
                jsonBody;
    }

    private String getXmlResponse() throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        List<Map<String, Object>> shapesList = new ArrayList<>();

        for (Shape shape : shapes) {
            Map<String, Object> shapeData = new HashMap<>();
            shapeData.put("name", shape.getName());
            shapeData.put("area", shape.calculateArea());
            shapeData.put("perimeter", shape.calculatePerimeter());
            shapesList.add(shapeData);
        }

        responseData.put("shapes", shapesList);
        String xmlBody = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseData);

        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/xml; charset=UTF-8\r\n" +
                "Content-Length: " + xmlBody.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "\r\n" +
                xmlBody;
    }

    private String getNotFoundResponse() {
        return "HTTP/1.1 404 Not Found\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
    }

    private String getServerErrorResponse() {
        return "HTTP/1.1 500 Internal Server Error\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
    }

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return params;
        }

        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                params.put(pair[0], pair[1]);
            } else {
                params.put(pair[0], "");
            }
        }
        return params;
    }
}
