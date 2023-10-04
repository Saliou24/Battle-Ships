package battleship.jeu;


import battleship.enums.Mode;
import battleship.tir.MyButton;
import battleship.tir.Tir;
import battleship.enums.EtatChamp;
import battleship.Jouer;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class OnlineGame extends NewGame {

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    public Thread listener;

    @SuppressWarnings("removal")
	public OnlineGame() {
        this.naviresCoules = 0;
        if (Jouer.mode == Mode.onlineClient)
            Jouer.monTour = false;
        if (Jouer.mode == Mode.onlineServer)
            Jouer.monTour = true;

        if (Jouer.mode == Mode.onlineClient)
            try {
                outputStream = new ObjectOutputStream(Jouer.client.socket.getOutputStream());
                outputStream.flush();
                inputStream = new ObjectInputStream(Jouer.client.socket.getInputStream());
            } catch (IOException e) {
                Jouer.client.closeKlientConnection();
            }
        else
            try {
                outputStream = new ObjectOutputStream(Jouer.serwer.socket.getOutputStream());
                outputStream.flush();
                inputStream = new ObjectInputStream(Jouer.serwer.socket.getInputStream());
            } catch (IOException e) {
                Jouer.serwer.closeSerwerConnection();
            }
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
                        Tir tir;
                        if (Jouer.Joueur1.grilleOpposant.canIShootThere(ligne, colonne)) {
                            tir = new Tir(ligne, colonne);
                            try {
                                outputStream.writeObject(tir);
                                outputStream.flush();
                                tir = (Tir) inputStream.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Jouer.Joueur1.miseAJourResultat(tir);
                            if (tir.getResult() != EtatChamp.raté) {
                                if (tir.getResult() == EtatChamp.coulé)
                                    naviresCoules = naviresCoules + 1;
                                if (naviresCoules == 10) {
                                    Jouer.monTour = false;
                                    paintBoards();
                                    name.setText("Multijoueur Online, tu as perdu!");
                                    JOptionPane.showMessageDialog(null, "TU AS GAGNÉ ");
                                    return;
                                }
                            } else {
                                Jouer.monTour = false;
                                Responder();
                            }
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

    public void Responder() {
        Runnable listening = new Runnable() {
            public void run() {

                Tir tir = null;
                while (Jouer.monTour == false) {
                    try {
                        tir = (Tir) inputStream.readObject();
                        tir.setResult(Jouer.Joueur1.maGrille.resolveShoot(tir));
                        paintBoards();
                        Jouer.frame.repaint();
                        outputStream.writeObject(tir);
                        outputStream.flush();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (tir.getResult() != EtatChamp.raté) {
                        if (Jouer.Joueur1.estMort()) {
                            Jouer.monTour = false;
                            paintBoards();
                            name.setText("Multijoueur Online, Tu as perdu!");
                            JOptionPane.showMessageDialog(null, "TU AS PERDU");
                            return;
                        }
                    } else {
                        Jouer.monTour = true;
                        paintBoards();
                        Jouer.frame.repaint();
                    }
                }
            }
        };

        OnlineGame temp = (OnlineGame) Jouer.currentGame;
        temp.listener = new Thread(listening);
        temp.listener.start();

    }
}