package battleship.jeu;

import battleship.enums.Mode;
import battleship.menu.MainMenu;
import battleship.Jouer;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {
    public Socket socket;
    private int port;
    private String ip;

    public Client() {
        ip = Jouer.currentMenu.textFieldList[0].getText();
        port = Integer.parseInt(Jouer.currentMenu.textFieldList[1].getText());
        Jouer.mode = Mode.onlineClient;
        try {
            socket = new Socket(ip, port);
            JOptionPane.showConfirmDialog(Jouer.frame, "Connecte avec le serveur!",
                                          "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

            Jouer.Joueur1 = (Joueur) new HumanPlayer(10);
        } catch (IOException e) {
            closeKlientConnection();
        }
    }

    public void closeKlientConnection() {
        JOptionPane.showConfirmDialog(Jouer.frame, "connexion impossible avec le serveur!",
                                      "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

        Jouer.currentMenu = new MainMenu();
        Jouer.frame.repaint();
    }
}