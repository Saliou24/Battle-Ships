package battleship.menu;

import battleship.Jouer;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu {
	static public JButton[] menuButtonList;
    public JTextField[] textFieldList;
    protected int textFieldCount;
    protected int buttonCount;
    public JPanel menuPanel;
    protected GridBagConstraints layout;

    public Menu(String[] buttonNames, String[] textFieldNames) {
        Jouer.frame.getContentPane().setLayout(new BorderLayout());
        JLabel gameName = new JLabel("BATTLESHIPS");
        JLabel author = new JLabel("CRÉE PAR SALIOU  THIONGANE et "
        		                      + "WILLIAM FOSSI NGAMBU");
        gameName.setFont(new Font("Papyrus", Font.PLAIN, 40));
        author.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));

        buttonCount = buttonNames.length;
        menuButtonList = new JButton[buttonCount];

        textFieldCount = textFieldNames.length;
        if (textFieldCount > 0)
            textFieldList = new JTextField[textFieldCount];

        menuPanel = new JPanel(new GridBagLayout());
        layout = new GridBagConstraints();

        Jouer.frame.getContentPane().add(menuPanel, BorderLayout.NORTH);

        layout.gridx = 0;
        layout.gridy = 0;

        menuPanel.add(gameName, layout);

        layout.gridy++;
        layout.insets = new Insets(0, 0, 100, 0);

        menuPanel.add(author, layout);

        layout.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < textFieldCount; i++) {
            layout.gridy++;
            layout.insets = new Insets(0, 0, 20, 0);
            textFieldList[i] = new JTextField(textFieldNames[i]);
            textFieldList[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            menuPanel.add(textFieldList[i], layout);
        }

        layout.insets = new Insets(0, 0, 5, 0);

        for (int i = 0; i < buttonCount; i++) {

            layout.gridy++;
            menuButtonList[i] = new JButton(buttonNames[i]);
            menuButtonList[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            menuPanel.add(menuButtonList[i], layout);
        }

    }

    abstract protected class EventHandler implements ActionListener {

        abstract public void actionPerformed(ActionEvent event);

    }

}
