import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    public static  void  main(String args[]){
        DatagramSocket aSocket  = null;
        try  {
            aSocket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter an IP Address: ");
            String address = scanner.nextLine();
            System.out.println("Please enter a port number: ");
            int port = scanner.nextInt();
            if (port < 1 || port > 65535) {
                throw new IOException("Port needs to be in the range of 1 and 65535");
            }
            scanner.nextLine();
            while (true) {
                System.out.println("Please enter a message to send to the server!");
                String message = scanner.nextLine();
                byte [] m = message.getBytes();
                InetAddress aHost = InetAddress.getByName(address);
                DatagramPacket request  = new DatagramPacket(m, m.length, aHost, port);
                aSocket.send(request);
                byte[] buffer = new byte[1000];
                DatagramPacket reply  =  new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                System.out.println("Reply: " + new String(reply.getData()));
            }
        }
        catch (SocketException e)
        {
            System.out.println("Socket: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("IO: " + e.getMessage());
        }
        finally
        {
            if(aSocket != null) aSocket.close();
        }
    }
}
