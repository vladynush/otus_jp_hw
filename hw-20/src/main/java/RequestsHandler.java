import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RequestsHandler {

    private final static String contentType = "Content-Type: text/html; charset=utf-8\n";
    private final static String head = "<head></head>\n";

    public static String handleRequest(Request req) {
        String filePath = Configuration.uriToFileMapping.get(req.uri());
        if (filePath != null) {
            return buildOkHttpResponse(readFileToString(filePath));
        } else {
            return "HTTP/1.1 404 Not Found\n";
        }
    }

    private static String readFileToString(String fileName) {
        try {
            byte[] encodedBytes = Files.readAllBytes(Paths.get(fileName));
            return new String(encodedBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static String buildOkHttpResponse(String text) {
        String respStart = "HTTP/1.1 200 OK\n" + contentType + "\n" + head + "<body>\n";
        String respBody = Arrays
                .stream(text.split("\n"))
                .map(line -> "<p>" + line + "</p>")
                .collect(Collectors.joining("\n"));
        String respEnd = "</body>";
        return respStart + respBody + respEnd + Configuration.EOF;
    }

}