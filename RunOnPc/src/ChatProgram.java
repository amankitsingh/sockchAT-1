import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

class ChatProgram {
    static String otherIpAddress;
    static int otherPort;
    static String myIpAddress;
    static int myPort = 8080;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter host address:");
        otherIpAddress = scanner.nextLine();
        //scanner.close();
        System.out.print("Enter port:");
        //scanner=new Scanner(System.in);
        otherPort = scanner.nextInt();
        myIpAddress = getIpAddress();

        new Client(myIpAddress + "@" + myPort);
        new Server();
        System.out.println("\n\n\n==========Welcome to chat application...==========\n" + "type \"exit\" to quit");

        while (true) {
            String message = scanner.nextLine();
            if (message.compareTo("exit") == 0) {
                System.out.println("\n\n========Thank you for using...=======");
                System.exit(0);
            } else {
                new Client(message);
            }
        }

    }

    public static String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}