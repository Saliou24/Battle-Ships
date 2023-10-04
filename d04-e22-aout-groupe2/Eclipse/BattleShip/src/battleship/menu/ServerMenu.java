package battleship.menu;

import battleship.enums.Mode;
import battleship.jeu.Server;
import battleship.Jouer;
import java.awt.event.ActionEvent;


public class ServerMenu extends Menu  {


    private static String[] buttons = {"Connexion", "Retour"};
    private static String[] textFields = {"Port"};

    public ServerMenu() {
        super(buttons, textFields);
        EventHandler menuHandler = new MenuEventHandler();
        for (int i = 0; i < 2; i++)
            menuButtonList[i].addActionListener(menuHandler);
    }

    class MenuEventHandler extends Menu.EventHandler {

        public void actionPerformed(ActionEvent event) {
            Jouer.currentMenu.menuPanel.setVisible(false);
            if (event.getSource() == Menu.menuButtonList[0]) {
                Jouer.mode = Mode.onlineServer;
                Jouer.serwer = new Server();
            } else if (event.getSource() == Menu.menuButtonList[1])
                Jouer.currentMenu = new MultiPlayerMenu();

            Jouer.frame.invalidate();
            Jouer.frame.validate();
        }
    }

}
