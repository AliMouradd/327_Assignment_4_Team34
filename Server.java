import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server {

    public static void main(String args[]){
        DatagramSocket aSocket = null;
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a port number: ");
            int port = scanner.nextInt();
            if (port < 0 || port > 65535) {
                throw new IOException("Port needs to be in the range of 1 and 65535");
            }
            aSocket = new DatagramSocket(port);
            byte[] buffer = new byte[1000];
            while(true){
                DatagramPacket request  = new DatagramPacket(buffer,  buffer.length);
                aSocket.receive(request);
                String word = new String(request.getData(), StandardCharsets.UTF_8);
                System.out.println("Received message from Client: " + word);
                byte[] editedByte = word.toUpperCase().getBytes();
                DatagramPacket reply = new DatagramPacket(editedByte,
                        request.getLength(),  request.getAddress(),   request.getPort());
                aSocket.send(reply);
            }
        }
        catch (SocketException e)
        {
            System.out.println("Socket:  " + e.getMessage());
        }
        catch (IOException  e)
        {
            System.out.println("IO:  " + e.getMessage());
        }
        finally
        {
            if(aSocket != null) aSocket.close();
        }
    }
}