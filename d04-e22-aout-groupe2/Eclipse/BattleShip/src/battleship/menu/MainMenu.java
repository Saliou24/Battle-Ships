package battleship.menu;

import battleship.Jouer;
import battleship.menu.Menu.EventHandler;

import java.awt.event.ActionEvent;

public class MainMenu extends Menu{


    private static String[] buttons = {"Joueur VS IA", "Mode Multijoueur", "Quitter"};
    private static String[] textFields = {};

    public MainMenu() {
        super(buttons, textFields);
        EventHandler menuHandler = new MenuEventHandler();
        for (int i = 0; i < 3; i++)
            menuButtonList[i].addActionListener(menuHandler);
    }

    class MenuEventHandler extends Menu.EventHandler {

        public void actionPerformed(ActionEvent event) {
            Jouer.currentMenu.menuPanel.setVisible(false);
            if (event.getSource() == Menu.menuButtonList[0])
                Jouer.currentMenu = new SinglePlayerMenu();
            else if (event.getSource() == Menu.menuButtonList[1])
                Jouer.currentMenu = new MultiPlayerMenu();
            else if (event.getSource() == Menu.menuButtonList[2])
                if (Jouer.frame.isDisplayable())
                    Jouer.frame.dispose();

            Jouer.frame.invalidate();
            Jouer.frame.validate();
        }
    }


}
