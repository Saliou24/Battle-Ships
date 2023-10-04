package battleship.menu;


import battleship.Jouer;

import java.awt.event.ActionEvent;

public class MultiPlayerMenu extends Menu {

    private static String[] buttons = {"Serveur", "Client", "Hot Seat", "Back"};
    private static String[] textFields = {};

    public MultiPlayerMenu() {
        super(buttons, textFields);
        EventHandler menuHandler = new MenuEventHandler();
        for (int i = 0; i < 4; i++)
            menuButtonList[i].addActionListener(menuHandler);
    }

    class MenuEventHandler extends Menu.EventHandler {

        public void actionPerformed(ActionEvent event) {
            Jouer.currentMenu.menuPanel.setVisible(false);
            if (event.getSource() == Menu.menuButtonList[0])
                Jouer.currentMenu = new ServerMenu();
            else if (event.getSource() == Menu.menuButtonList[1])
               Jouer.currentMenu = new ClientMenu();
            else if (event.getSource() == Menu.menuButtonList[2])
                Jouer.currentMenu = new HotSeatMenu();
            else if (event.getSource() == Menu.menuButtonList[3])
                Jouer.currentMenu = new MainMenu();

            Jouer.frame.invalidate();
            Jouer.frame.validate();
        }
    }

}