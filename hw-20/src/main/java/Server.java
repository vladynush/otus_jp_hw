import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(Configuration.executorsNum);

    public void start() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(Configuration.port)) {
            System.out.println("Started server at port: " + Configuration.port);
            while (true) {
                threadPool.submit(new SocketHandler(serverSocket.accept()));
            }
        }
    }

    private static class SocketHandler implements Runnable {

        Socket socket;

        public SocketHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                Request request = handleRequestMiddleware(br);
                String response = RequestsHandler.handleRequest(request);
                pw.println(response);
                pw.flush();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Fatal exception!");
                }
            }
        }

        private Request handleRequestMiddleware(BufferedReader reader) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while (!(line = reader.readLine()).equals(Configuration.EOF)) {
                stringBuilder.append(line).append("\n");
            }
            String request = stringBuilder.toString().replaceAll("[\\n\\r]+", "");
            String startLine = (request.split("\n")[0]);
            String httpMethod = startLine.split(" ")[0];

            if (!httpMethod.equals("GET")) {
                throw new RuntimeException(
                        String.format("Server supports only GET requests, but got %s", httpMethod)
                );
            }
            String resourcePath = startLine.split(" ")[1]; // take path from "GET /path HTTP/1.1"

            return new Request(resourcePath);
        }
    }
}