package battleship.jeu;

import battleship.enums.Mode;
import battleship.tir.MyButton;
import battleship.tir.Tir;
import battleship.enums.EtatChamp;
import battleship.Jouer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class HotSeatView extends NewGame {

    @SuppressWarnings("removal")
	HotSeatView(int monCoule, int tonCoule) {
        this.naviresCoules = monCoule;
        setPanels();
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
                        Tir tir = null;
                        if (Jouer.mode == Mode.hotSeat1)
                            if (Jouer.Joueur1.grilleOpposant.canIShootThere(ligne, colonne)) {

                                tir = new Tir(ligne, colonne);
                                tir.setResult(Jouer.Joueur2.maGrille.resolveShoot(tir));
                                Jouer.Joueur1.miseAJourResultat(tir);

                                if (tir.getResult() != EtatChamp.raté)
                                    if (Jouer.Joueur2.estMort()) {
                                        paintBoards();
                                        for (int i = 0; i < tailleGrille; i++)
                                            for (int j = 0; j < tailleGrille; j++)
                                                OpposantButtons[i][j].setEnabled(false);
                                        JOptionPane.showMessageDialog(null, Jouer.currentMenu.textFieldList[0].getText() + " GAGNÉ ");
                                    }
                                if (tir.getResult() == EtatChamp.coulé)
                                    naviresCoules = naviresCoules + 1;
                            }
                        if (Jouer.mode == Mode.hotSeat2)
                            if (Jouer.Joueur2.grilleOpposant.canIShootThere(ligne, colonne)) {

                                tir = new Tir(ligne, colonne);
                                tir.setResult(Jouer.Joueur1.maGrille.resolveShoot(tir));
                                Jouer.Joueur2.miseAJourResultat(tir);

                                if (tir.getResult() != EtatChamp.raté)
                                    if (Jouer.Joueur1.estMort()) {
                                        paintBoards();
                                        for (int i = 0; i < tailleGrille; i++)
                                            for (int j = 0; j < tailleGrille; j++)
                                                OpposantButtons[i][j].setEnabled(false);
                                        JOptionPane.showMessageDialog(null, Jouer.currentMenu.textFieldList[1].getText() + " GAGNÉ");
                                    }
                                if (tir.getResult() == EtatChamp.coulé)
                                    naviresCoules = naviresCoules + 1;
                            }
                        paintBoards();
                        if (tir != null && tir.getResult() == EtatChamp.raté)
                            Jouer.changer = new ChangeView(tonCoule, naviresCoules);

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
}