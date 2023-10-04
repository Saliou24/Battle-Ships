package battleship.menu;

import battleship.enums.Mode;
import battleship.jeu.HumanPlayer;
import battleship.niveau.Classique;
import battleship.Jouer;
import battleship.niveau.Avance;

import java.awt.event.ActionEvent;


public class SinglePlayerMenu extends Menu {


    private static String[] buttons = {"CLASSIQUE", "AVANCE", "Retour"};
    private static String[] textFields = {};

    public SinglePlayerMenu() {
        super(buttons, textFields);
        EventHandler menuHandler = new MenuEventHandler();
        for (int i = 0; i < 3; i++)
            menuButtonList[i].addActionListener(menuHandler);
    }

    class MenuEventHandler extends Menu.EventHandler {

        public void actionPerformed(ActionEvent event) {
            Jouer.currentMenu.menuPanel.setVisible(false);
            if (event.getSource() == Menu.menuButtonList[0]) {
                Jouer.mode = Mode.soloCLASSIQUE;
                Jouer.Joueur2 = new Classique(10);
                Jouer.Joueur1 = new HumanPlayer(10);
    } else if (event.getSource() == Menu.menuButtonList[1]) {
                Jouer.mode = Mode.soloAVANCE;
                Jouer.Joueur2 = new Avance(10);
                Jouer.Joueur1 = new HumanPlayer(10);
            } else if (event.getSource() == Menu.menuButtonList[2])
                Jouer.currentMenu = new MainMenu();

            Jouer.frame.repaint();
        }
    }

}
