package battleship.jeu;


import battleship.enums.Mode;
import battleship.menu.MainMenu;
import battleship.Jouer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server {
    private ServerSocket serverSocket;
    public Socket socket;
    private int port;

    public Server() {
        port = Integer.parseInt(Jouer.currentMenu.textFieldList[0].getText());
        Jouer.mode = Mode.onlineServer;
        initializeServer();
        try {
            if (serverSocket != null) {
                socket = serverSocket.accept();
                JOptionPane.showConfirmDialog(Jouer.frame, "Connected with opponent!",
                                              "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

                Jouer.Joueur1 = (Joueur) new HumanPlayer(10);

            }
        } catch (NullPointerException | IOException e) {
            closeSerwerConnection();
        }
    }

    private void initializeServer() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeSerwerConnection() {
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
        }
        JOptionPane.showConfirmDialog(Jouer.frame, "impossible de te connecter avec l'opposant!",
                                      "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

        Jouer.currentMenu = new MainMenu();
        Jouer.frame.repaint();
    }
}