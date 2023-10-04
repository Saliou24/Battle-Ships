package battleship.jeu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import battleship.enums.Mode;
import battleship.menu.MainMenu;
import battleship.tir.MyButton;
import battleship.tir.Tir;
import battleship.enums.EtatChamp;
import battleship.Jouer;

public class NewGame {
    protected JButton buttonMenu;
    protected int tailleGrille;
    protected JLabel[][] myLabels;
    protected JLabel[][] opponentLabels;
    protected MyButton[][] OpposantButtons;
    protected MyButton[][] myButtons;
    protected JLabel leftShipLabel;
    protected int naviresCoules;
    protected JLabel name;

    @SuppressWarnings("removal")
	public NewGame() {
        this.naviresCoules = 0;
        this.setPanels();
        this.tailleGrille = Jouer.Joueur1.tailleGrille;
        myButtons = new MyButton[tailleGrille][tailleGrille];
        OpposantButtons = new MyButton[tailleGrille][tailleGrille];
        myLabels = new JLabel[2][tailleGrille];
        opponentLabels = new JLabel[2][tailleGrille];

        for (int i = 0; i < tailleGrille; i++)
            for (int j = 0; j < tailleGrille; j++) {
                myButtons[i][j] = new MyButton("  ");
                myButtons[i][j].setSize(myButtons[i][j].getPreferredSize());
                myButtons[i][j].setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
                myButtons[i][j].setEnabled(false);

                OpposantButtons[i][j] = new MyButton("  ");
                OpposantButtons[i][j].setSize(OpposantButtons[i][j].getPreferredSize());
                OpposantButtons[i][j].setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
                OpposantButtons[i][j].ligne = i;
                OpposantButtons[i][j].colonne = j;

                OpposantButtons[i][j].addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent arg0) {
                        MyButton tempButton = (MyButton) arg0.getSource();
                        int ligne = tempButton.ligne;
                        int colonne = tempButton.colonne;
                        Tir tir;
                        if (Jouer.Joueur1.grilleOpposant.canIShootThere(ligne, colonne)) {
                            tir = new Tir(ligne, colonne);
                            tir.setResult(Jouer.Joueur2.maGrille.resolveShoot(tir));
                            Jouer.Joueur1.miseAJourResultat(tir);

                            if (tir.getResult() != EtatChamp.raté) {
                                if (Jouer.Joueur2.estMort()) {
                                    paintBoards();
                                    for (int i = 0; i < tailleGrille; i++)
                                        for (int j = 0; j < tailleGrille; j++)
                                            OpposantButtons[i][j].setEnabled(false);
                                    JOptionPane.showMessageDialog(null, "TU AS GAGNÉ ");
                                }
                            } else {
                                Jouer.Joueur2.shoot();
                                if (Jouer.Joueur1.estMort()) {
                                    paintBoards();
                                    for (int i = 0; i < tailleGrille; i++)
                                        for (int j = 0; j < tailleGrille; j++)
                                            OpposantButtons[i][j].setEnabled(false);
                                    JOptionPane.showMessageDialog(null, "TU AS PERDU");
                                }
                            }
                            if (tir.getResult() == EtatChamp.coulé)
                                naviresCoules = naviresCoules + 1;
                        }
                        paintBoards();
                    }

                });

                char lettre = (char) (i + 'a');
                String string = new StringBuilder().append(lettre).toString();

                myLabels[0][i] = new JLabel(string);
                myLabels[1][i] = new JLabel(new Integer(i + 1).toString());

                opponentLabels[0][i] = new JLabel(string);
                opponentLabels[1][i] = new JLabel(new Integer(i + 1).toString());
            }
        paintBoards();
    }

    protected void setPanels() {

        Jouer.firstPanel.setVisible(false);
        Jouer.secondPanel.setVisible(false);
        Jouer.thirdPanel.setVisible(false);
        Jouer.fourthPanel.setVisible(false);

        Jouer.firstPanel = new JPanel(new GridBagLayout());
        Jouer.secondPanel = new JPanel(new GridBagLayout());
        Jouer.thirdPanel = new JPanel(new GridBagLayout());
        Jouer.fourthPanel = new JPanel(new GridBagLayout());

        Jouer.firstPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        Jouer.secondPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        Jouer.thirdPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        Jouer.fourthPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        Jouer.frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints frameLayout = new GridBagConstraints();

        frameLayout.gridwidth = 2;
        frameLayout.fill = GridBagConstraints.BOTH;
        frameLayout.weightx = 0.1;
        frameLayout.weighty = 0.1;
        Jouer.frame.getContentPane().add(Jouer.firstPanel, frameLayout);
        frameLayout.weightx = 1.0;
        frameLayout.weighty = 1.0;
        frameLayout.gridy = 1;
        frameLayout.gridwidth = 1;
        Jouer.frame.getContentPane().add(Jouer.secondPanel, frameLayout);
        frameLayout.gridx = 1;
        Jouer.frame.getContentPane().add(Jouer.thirdPanel, frameLayout);
        frameLayout.weightx = 0.1;
        frameLayout.weighty = 0.1;
        frameLayout.gridwidth = 2;
        frameLayout.gridy = 2;
        frameLayout.gridx = 0;
        Jouer.frame.getContentPane().add(Jouer.fourthPanel, frameLayout);
        Jouer.frame.repaint();
        name = new JLabel();
        if (Jouer.mode == Mode.soloCLASSIQUE)
            name.setText("Singleplayer Easy");
        if (Jouer.mode == Mode.soloAVANCE)
            name.setText("Singleplayer Ultra Hard");
        if (Jouer.mode == Mode.hotSeat1)
            name.setText("Singleplayer " + Jouer.currentMenu.textFieldList[0].getText());
        if (Jouer.mode == Mode.hotSeat2)
            name.setText("Singleplayer " + Jouer.currentMenu.textFieldList[1].getText());
        if (Jouer.mode == Mode.onlineClient || Jouer.mode == Mode.onlineServer)
            name.setText("Multiplayer Online");
        buttonMenu = new JButton("Exit");
        name.setFont(new Font("SansSerif", Font.PLAIN, 20));
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.WEST;
        layout.weightx = 1.0;
        layout.weighty = 1.0;
        layout.insets = new Insets(5, 5, 5, 5);
        Jouer.firstPanel.add(name, layout);
        layout.anchor = GridBagConstraints.EAST;
        layout.gridx = 5;
        Jouer.fourthPanel.add(buttonMenu, layout);

        buttonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int pick = JOptionPane.showConfirmDialog(
                        null, "Voulez-vous vraiment quitter ?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION);
                if (pick == JOptionPane.YES_OPTION) {
                    Jouer.firstPanel.setVisible(false);
                    Jouer.secondPanel.setVisible(false);
                    Jouer.thirdPanel.setVisible(false);
                    Jouer.fourthPanel.setVisible(false);
                    Jouer.currentMenu = new MainMenu();
                    Jouer.frame.repaint();
                }
            }
        });

        GridBagConstraints bottomLayout = new GridBagConstraints();
        bottomLayout.weightx = 1.0;
        bottomLayout.weighty = 1.0;
        bottomLayout.insets = new Insets(5, 5, 5, 10);
        bottomLayout.anchor = GridBagConstraints.EAST;
        bottomLayout.gridx = 0;

        @SuppressWarnings("removal")
		String counter = new Integer(Jouer.Joueur1.maGrille.nbNavires - naviresCoules).toString();
        leftShipLabel = new JLabel("Navires partis : " + counter);
        Jouer.fourthPanel.add(leftShipLabel, bottomLayout);
    }

    protected void paintBoards() {
        Jouer.secondPanel.removeAll();
        Jouer.secondPanel.repaint();
        Jouer.thirdPanel.removeAll();
        Jouer.thirdPanel.repaint();

        @SuppressWarnings("removal")
		String counter = new Integer(Jouer.Joueur1.maGrille.nbNavires - naviresCoules).toString();
        leftShipLabel.setText("Navires partis : " + counter);

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(1, 1, 1, 1);
        layout.anchor = GridBagConstraints.CENTER;
        layout.fill = GridBagConstraints.BOTH;
        for (int i = 1; i < tailleGrille + 1; i++) {
            layout.gridx = i;
            Jouer.secondPanel.add(myLabels[1][i - 1], layout);
            Jouer.thirdPanel.add(opponentLabels[1][i - 1], layout);
        }
        layout.gridx = 0;
        for (int i = 1; i < tailleGrille + 1; i++) {
            layout.gridy = i + 1;
            Jouer.secondPanel.add(myLabels[0][i - 1], layout);
            Jouer.thirdPanel.add(opponentLabels[0][i - 1], layout);
        }
        if (Jouer.mode != Mode.hotSeat2)
            for (int i = 0; i < tailleGrille; i++)
                for (int j = 0; j < tailleGrille; j++) {
                    layout.gridx = j + 1;
                    layout.gridy = i + 2;
                    if (Jouer.Joueur1.maGrille.getEtatChamp(i, j) == EtatChamp.touché)
                        myButtons[i][j].setBackground(new Color(0, 255, 0));
                    else if (Jouer.Joueur1.maGrille.getEtatChamp(i, j) == EtatChamp.raté)
                        myButtons[i][j].setText("o");
                    else if (Jouer.Joueur1.maGrille.getEtatChamp(i, j) == EtatChamp.détruit)
                        myButtons[i][j].setBackground(new Color(255, 0, 0));
                    else if (Jouer.Joueur1.maGrille.getEtatChamp(i, j) == EtatChamp.coulé)
                        myButtons[i][j].setBackground(new Color(0, 0, 0));
                    Jouer.secondPanel.add(myButtons[i][j], layout);

                    if (Jouer.Joueur1.grilleOpposant.getEtatChamp(i, j) == EtatChamp.raté)
                        OpposantButtons[i][j].setText("o");
                    else if (Jouer.Joueur1.grilleOpposant.getEtatChamp(i, j) == EtatChamp.détruit)
                        OpposantButtons[i][j].setBackground(new Color(255, 0, 0));
                    else if (Jouer.Joueur1.grilleOpposant.getEtatChamp(i, j) == EtatChamp.coulé)
                        OpposantButtons[i][j].setBackground(new Color(0, 0, 0));

                    if (Jouer.mode == Mode.onlineClient || Jouer.mode == Mode.onlineServer)
                        if (Jouer.monTour == false)
                            OpposantButtons[i][j].setEnabled(false);
                        else
                            OpposantButtons[i][j].setEnabled(true);

                    Jouer.thirdPanel.add(OpposantButtons[i][j], layout);
                }
        else
            for (int i = 0; i < tailleGrille; i++)
                for (int j = 0; j < tailleGrille; j++) {
                    layout.gridx = j + 1;
                    layout.gridy = i + 2;
                    if (Jouer.Joueur2.maGrille.getEtatChamp(i, j) == EtatChamp.touché)
                        myButtons[i][j].setBackground(new Color(0, 255, 0));
                    else if (Jouer.Joueur2.maGrille.getEtatChamp(i, j) == EtatChamp.raté)
                        myButtons[i][j].setText("o");
                    else if (Jouer.Joueur2.maGrille.getEtatChamp(i, j) == EtatChamp.détruit)
                        myButtons[i][j].setBackground(new Color(255, 0, 0));
                    else if (Jouer.Joueur2.maGrille.getEtatChamp(i, j) == EtatChamp.coulé)
                        myButtons[i][j].setBackground(new Color(0, 0, 0));
                    Jouer.secondPanel.add(myButtons[i][j], layout);

                    if (Jouer.Joueur2.grilleOpposant.getEtatChamp(i, j) == EtatChamp.raté)
                        OpposantButtons[i][j].setText("o");
                    else if (Jouer.Joueur2.grilleOpposant.getEtatChamp(i, j) == EtatChamp.détruit)
                        OpposantButtons[i][j].setBackground(new Color(255, 0, 0));
                    else if (Jouer.Joueur2.grilleOpposant.getEtatChamp(i, j) == EtatChamp.coulé)
                        OpposantButtons[i][j].setBackground(new Color(0, 0, 0));
                    Jouer.thirdPanel.add(OpposantButtons[i][j], layout);
                }
    }

}