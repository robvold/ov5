import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080, 0, InetAddress.getByName(null));
        while(true) {
            Socket connection = serverSocket.accept();

            InputStreamReader readConnection = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(readConnection);
            PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);
            StringBuilder body = new StringBuilder();


            while ((reader.readLine()).length() != 0);
            while (reader.ready()) {
                char x = (char) reader.read();
                body.append(x);
            }

            //Fikser strengen mottatt fra frontenden:
            String temp = body.toString();
            temp = temp.replace("\"", "\\\"");
            temp = temp.replace("\n", "\\n");
            body = new StringBuilder(temp);
            body.insert(0, "\"");
            body.append("\"");


            String code = "docker run --rm ov " + body.toString();
            Process p = Runtime.getRuntime().exec(code);
            StringBuilder output = new StringBuilder();
            output.append("HTTP/1.0 200 OK \r\nContent-Type: text/html; charset=UTF-8\r\nAccess-Control-Allow-Origin:*\r\n\r\n");

            System.out.println("------input:");
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while ((s = b.readLine()) != null) {
                System.out.println(s);
                output.append(s);
            }

            System.out.println("------Error:");
            BufferedReader c = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String t;
            while ((t = c.readLine()) != null) {
                System.out.println(t);
                output.append(t);
            }
            System.out.println("------Output:");
            System.out.println(output.toString());

            writer.print(output.toString());
            writer.flush();
            reader.close();
            connection.close();
        }
    }
}