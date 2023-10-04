package battleship.niveau;

import java.util.Random;
import battleship.Jouer;
import battleship.enums.EtatChamp;
import battleship.tir.Tir;

public  class Avance extends Ordinateur {
    private boolean lastWasGood;
    private int[][] probabilite;
    private int derniereLigne;
    private int derniereColonne;
    private int whatIsNeeded;
    private int modifirer;

    public Avance(int size) {
        super(size);
        Random generator = new Random();
        lastWasGood = false;
        probabilite = new int[size][size];
        setProbability();
        whatIsNeeded = 2;
        modifirer = generator.nextInt(2);
    }

    private void setProbability() {
        for (int i = 0; i < tailleGrille; i++)
            for (int j = 0; j < tailleGrille; j++)
                probabilite[i][j] = 0;
    }

    public void shoot() {

        Random generator = new Random();
        int ligne = 0, colonne = 0;
        Tir tir;
        int deadEndOuter = 0;
        do {
            int deadEnd = 0;
            do {
                if (deadEndOuter == 150 && modifirer == 1)
                    modifirer = 0;
                else if (deadEndOuter == 150 && modifirer == 0)
                    modifirer = 1;
                do {
                    if (deadEnd == 1000)
                        break;
                    ligne = generator.nextInt(tailleGrille);
                    colonne = generator.nextInt(tailleGrille);
                    deadEnd++;
                } while ((ligne + colonne) % whatIsNeeded == modifirer);
                if (deadEnd == 1000) {
                	ligne = generator.nextInt(tailleGrille);
                	colonne = generator.nextInt(tailleGrille);
                }

                if (lastWasGood) {
                    for (int i = 0; i < tailleGrille; i++)
                        for (int j = 0; j < tailleGrille; j++)
                            if (probabilite[i][j] == 1 && grilleOpposant.canIShootThere(i, j)) {
                            	ligne = i;
                            	colonne = j;
                            }
                    for (int i = 0; i < tailleGrille; i++)
                        for (int j = 0; j < tailleGrille; j++)
                            if (probabilite[i][j] == 2 && grilleOpposant.canIShootThere(i, j)) {
                            	ligne = i;
                            	colonne = j;
                            }
                }
                deadEndOuter++;
            } while (grilleOpposant.canIShootThere(ligne, colonne) == false);

            tir = new Tir(ligne, colonne);

            tir.setResult(Jouer.Joueur1.maGrille.resolveShoot(tir));
            miseAJourResultat(tir);

            if (tir.getResult() == EtatChamp.coulé) {
                setProbability();
                lastWasGood = false;
            }
            if (tir.getResult() == EtatChamp.détruit) {
                if (lastWasGood) {

                    if (ligne == derniereLigne) {
                        if (colonne - 1 >= 0 && grilleOpposant.canIShootThere(ligne, colonne - 1))
                            probabilite[ligne][colonne - 1] = 2;
                        if (colonne + 1 <= tailleGrille - 1 && grilleOpposant.canIShootThere(ligne, colonne + 1))
                            probabilite[ligne][colonne + 1] = 2;
                    }
                    if (colonne == derniereColonne) {
                        if (ligne - 1 >= 0 && grilleOpposant.canIShootThere(ligne - 1, colonne))
                            probabilite[ligne - 1][colonne] = 2;
                        if (ligne + 1 <= tailleGrille - 1 && grilleOpposant.canIShootThere(ligne + 1, colonne))
                            probabilite[ligne + 1][colonne] = 2;
                    }
                } else {
                    if (ligne != 0)
                        probabilite[ligne - 1][colonne] = 1;
                    if (ligne != tailleGrille - 1)
                        probabilite[ligne + 1][colonne] = 1;
                    if (colonne != 0)
                        probabilite[ligne][colonne - 1] = 1;
                    if (colonne != tailleGrille - 1)
                        probabilite[ligne][colonne + 1] = 1;
                }
                derniereLigne = ligne;
                derniereColonne = colonne;
                lastWasGood = true;
            }
        } while (tir.getResult() != EtatChamp.raté && Jouer.Joueur1.estMort() == false);

    }

}