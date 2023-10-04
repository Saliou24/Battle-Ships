package battleship.jeu;

import battleship.enums.Mode;
import battleship.Jouer;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChangeView {
    private JPanel panel;

    public ChangeView(int monCoule, int toncoule) {

        Jouer.firstPanel.setVisible(false);
        Jouer.secondPanel.setVisible(false);
        Jouer.thirdPanel.setVisible(false);
        Jouer.fourthPanel.setVisible(false);
        if (Jouer.mode == Mode.hotSeat1)
            Jouer.mode = Mode.hotSeat2;
        else
            Jouer.mode = Mode.hotSeat1;

        JLabel missed = new JLabel("Vous avez rate!");
        JLabel text = new JLabel();
        if (Jouer.mode == Mode.hotSeat2)
            text.setText("Joueur: " + Jouer.currentMenu.textFieldList[1].getText() + " tour");
        else
            text.setText("Joueur: " + Jouer.currentMenu.textFieldList[0].getText() + " tour");
        text.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        missed.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints frameLayout = new GridBagConstraints();

        frameLayout.gridwidth = 2;
        frameLayout.fill = GridBagConstraints.BOTH;

        Jouer.frame.getContentPane().add(panel, frameLayout);

        panel.add(missed, frameLayout);

        frameLayout.gridy = 1;

        panel.add(text, frameLayout);

        frameLayout.gridy = 2;

        JButton button = new JButton("OK");

        panel.add(button, frameLayout);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                panel.setVisible(false);
                Jouer.currentGame = (NewGame) new HotSeatView(monCoule, toncoule);

            }

        });
    }
}