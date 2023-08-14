import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LiveServer {
    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(80);
        while (true) {
            Socket s = ss.accept();
            System.out.println(s.getRemoteSocketAddress());
            new Thread(() -> {
                try {
                    String filepath = "C:\\Users\\U S E R\\Desktop\\Programming journey\\VSCode\\lear2.html";
                    byte[] htmlBytes = Files.readAllBytes(Paths.get(filepath));

                    OutputStream os = s.getOutputStream();
                    os.write("HTTP/1.1 200 OK\r\n".getBytes());
                    os.write("Content-Type: text/html\r\n".getBytes());
                    os.write(("Content-Length: " + htmlBytes.length + "\r\n").getBytes());
                    os.write("\r\n".getBytes());
                    os.write(htmlBytes);
                    os.flush();

//                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
