import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        threadPool.submit(sendSocketRequest("GET /bubble HTTP/1.1"));
        threadPool.submit(sendSocketRequest("GET /dc HTTP/1.1"));
        threadPool.submit(sendSocketRequest("GET /marvel HTTP/1.1"));
        threadPool.submit(sendSocketRequest("PUT /marvel HTTP/1.1"));
        threadPool.shutdown();
    }


    private static Runnable sendSocketRequest(String uri) {
        return () -> {
            try (Socket socket = new Socket(Configuration.host, Configuration.port);
                 PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                pw.println(uri + "\n" + Configuration.EOF);
                pw.flush();
                String line;
                while (!(line = br.readLine()).equals(Configuration.EOF)) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}