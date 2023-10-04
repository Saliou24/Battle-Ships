package battleship.menu;

import battleship.enums.Mode;
import battleship.jeu.Client;
import battleship.Jouer;
import java.awt.event.ActionEvent;

public class ClientMenu extends Menu {

    private static String[] buttons = {"Connexion", "Retour"};
    private static String[] textFields = {"Adresse IP", "Port"};

    public ClientMenu() {
        super(buttons, textFields);
        EventHandler menuHandler = new MenuEventHandler();
        for (int i = 0; i < 2; i++)
            menuButtonList[i].addActionListener(menuHandler);
    }

    class MenuEventHandler extends Menu.EventHandler {

        public void actionPerformed(ActionEvent event) {
            Jouer.currentMenu.menuPanel.setVisible(false);
            if (event.getSource() == Menu.menuButtonList[0]) {
                Jouer.mode = Mode.onlineClient;
                Jouer.client = new Client();
            } else if (event.getSource() == Menu.menuButtonList[1])
                Jouer.currentMenu = new MultiPlayerMenu();

            Jouer.frame.invalidate();
            Jouer.frame.validate();
        }
    }

}