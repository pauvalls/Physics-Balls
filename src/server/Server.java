/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.ServerSocket;
import java.net.Socket;
import physicballs.Space;

/**
 *
 * @author Liam-Portatil
 */
public class Server {

    private static final int PORT = 1234;

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        new Server();
//    }

    public Server(Space parent) {
//        new ServerWindows().setVisible(true);
        
        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            Socket clientSock;
            String cliAddr;

            while (true) {
                System.out.println("Waiting for a client...");
                clientSock = serverSock.accept();
                cliAddr = clientSock.getInetAddress().getHostAddress();
                new ClientThread(clientSock, cliAddr, parent).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }  // end of ThreadedScoreServer()

}
