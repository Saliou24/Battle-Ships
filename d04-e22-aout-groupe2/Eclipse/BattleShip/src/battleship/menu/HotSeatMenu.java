package battleship.menu;

import battleship.enums.Mode;
import battleship.jeu.HumanPlayer;
import battleship.menu.Menu.EventHandler;
import battleship.Jouer;
import java.awt.event.ActionEvent;

public class HotSeatMenu extends Menu {


    private static String[] buttons = {"Commencer", "Retour"};
    private static String[] textFields = {"Nom Joueur 1", "Nom Joueur2"};

    public HotSeatMenu() {
        super(buttons, textFields);
        EventHandler menuHandler = new MenuEventHandler();
        for (int i = 0; i < 2; i++)
            menuButtonList[i].addActionListener(menuHandler);
    }

    class MenuEventHandler extends Menu.EventHandler {

        public void actionPerformed(ActionEvent event) {
            Jouer.currentMenu.menuPanel.setVisible(false);
            if (event.getSource() == Menu.menuButtonList[0]) {
                Jouer.mode = Mode.hotSeat1;
                Jouer.Joueur1 = new HumanPlayer(10);

            } else if (event.getSource() == Menu.menuButtonList[1])
                Jouer.currentMenu = new MultiPlayerMenu();

            Jouer.frame.invalidate();
            Jouer.frame.validate();
        }
    }

}
