package battleship.jeu;


import battleship.tir.IntPair;
import battleship.Jouer;

import java.util.Random;
import battleship.enums.EtatChamp;
import battleship.enums.Orientation;
import battleship.tir.Tir;


public class GameBoard {
    private EtatChamp[][] Grille;
    private int tailleGrille;
    private int Restants;
    public Navire[] listeNavires;
    public int nbNavires;

    public GameBoard(int size) {
        setSize(size);
        int wholeShipCount;
        wholeShipCount = Jouer.porteAvionCount + Jouer.sousMarinCount + Jouer.patrouilleCount + Jouer.destroyerCount;
        listeNavires = new Navire[wholeShipCount];
        Restants = 0;
        nbNavires = 0;
    }

    public void miseAJourResultat(Tir tir) {
        int ligne = tir.getLigne();
        int colonne = tir.getColonne();

        Grille[ligne][colonne] = tir.getResult();

        boolean repete, repeteEncore = false;

        if (tir.getResult() == EtatChamp.coulé) {

            int lowSearchRow, lowSearchColumn, highSearchRow, highSearchColumn;
            int rowSecondSide = 0, columnSecondSide = 0;
            do {
            	repete = false;
                if (ligne != 0)
                    lowSearchRow = ligne - 1;
                else
                    lowSearchRow = 0;
                if (colonne != 0)
                    lowSearchColumn = colonne - 1;
                else
                    lowSearchColumn = 0;
                if (ligne != tailleGrille)
                    highSearchRow = ligne + 1;
                else
                    highSearchRow = ligne;
                if (colonne != tailleGrille)
                    highSearchColumn = colonne + 1;
                else
                    highSearchColumn = colonne;

                for (int i = lowSearchRow; i <= highSearchRow; i++)
                    for (int j = lowSearchColumn; j <= highSearchColumn; j++) {
                        if (Grille[i][j] == EtatChamp.enEtat)
                            Grille[i][j] = EtatChamp.raté;
                        if (Grille[i][j] == EtatChamp.détruit) {
                            Grille[i][j] = EtatChamp.coulé;
                            if (repete == false) {
                                repeteEncore = true;
                                rowSecondSide = i;
                                columnSecondSide = j;
                            }
                            repete = true;
                            ligne = i;
                            colonne = j;
                        }

                    }
            } while (repete);

            while (repeteEncore) {
                repeteEncore = false;
                if (rowSecondSide != 0)
                    lowSearchRow = rowSecondSide - 1;
                else
                    lowSearchRow = 0;
                if (columnSecondSide != 0)
                    lowSearchColumn = columnSecondSide - 1;
                else
                    lowSearchColumn = 0;
                if (rowSecondSide != tailleGrille)
                    highSearchRow = rowSecondSide + 1;
                else
                    highSearchRow = rowSecondSide;
                if (columnSecondSide != tailleGrille)
                    highSearchColumn = columnSecondSide + 1;
                else
                    highSearchColumn = columnSecondSide;

                for (int i = lowSearchRow; i <= highSearchRow; i++)
                    for (int j = lowSearchColumn; j <= highSearchColumn; j++) {
                        if (Grille[i][j] == EtatChamp.enEtat)
                            Grille[i][j] = EtatChamp.raté;
                        if (Grille[i][j] == EtatChamp.détruit) {
                            Grille[i][j] = EtatChamp.coulé;
                            repeteEncore = true;
                            rowSecondSide = i;
                            columnSecondSide = j;
                        }

                    }
            }
        }
    }

    private void setSize(int size) {
        tailleGrille = size - 1;
        Grille = new EtatChamp[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                Grille[i][j] = EtatChamp.enEtat;
    }

    public boolean ajouterDestroyer(int ligne, int colonne, Orientation orientation) {

        int lowRowNotAble, lowColumnNotAble, highRowNotAble, highColumnNotAble;

        if (orientation == Orientation.verticale) {
            if (ligne + 2 > tailleGrille || colonne > tailleGrille)
                return false;
            if (Grille[ligne][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne + 1][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne + 2][colonne] != EtatChamp.enEtat)
                return false;

            if (ligne + 2 == tailleGrille)
                highRowNotAble = ligne + 2;
            else
                highRowNotAble = ligne + 3;

            if (ligne == 0)
                lowRowNotAble = ligne;
            else
                lowRowNotAble = ligne - 1;

            if (colonne == tailleGrille)
                highColumnNotAble = colonne;
            else
                highColumnNotAble = colonne + 1;

            if (colonne == 0)
                lowColumnNotAble = colonne;
            else
                lowColumnNotAble = colonne - 1;

            Grille[ligne][colonne] = EtatChamp.touché;
            Grille[ligne + 1][colonne] = EtatChamp.touché;
            Grille[ligne + 2][colonne] = EtatChamp.touché;
        } else {

            if (ligne > tailleGrille || colonne + 2 > tailleGrille)
                return false;
            if (Grille[ligne][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne][colonne + 1] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne][colonne + 2] != EtatChamp.enEtat)
                return false;

            if (ligne == tailleGrille)
                highRowNotAble = ligne;
            else
                highRowNotAble = ligne + 1;

            if (ligne == 0)
                lowRowNotAble = ligne;
            else
                lowRowNotAble = ligne - 1;

            if (colonne + 2 == tailleGrille)
                highColumnNotAble = colonne + 2;
            else
                highColumnNotAble = colonne + 3;

            if (colonne == 0)
                lowColumnNotAble = colonne;
            else
                lowColumnNotAble = colonne - 1;

            Grille[ligne][colonne] = EtatChamp.touché;
            Grille[ligne][colonne + 1] = EtatChamp.touché;
            Grille[ligne][colonne + 2] = EtatChamp.touché;
        }

        for (int i = lowRowNotAble; i <= highRowNotAble; i++)
            for (int j = lowColumnNotAble; j <= highColumnNotAble; j++)
                if (Grille[i][j] != EtatChamp.touché)
                    Grille[i][j] = EtatChamp.pasEnEtat;

        listeNavires[nbNavires] = new Navire(new IntPair(ligne, colonne), 3, orientation);
        nbNavires++;
        Restants += 3;
        return true;
    }

    // Remember row and column value from 0, but displayed from 1
    // means: you have to use this methods with arguments 1 lower
    public boolean ajouterPatrouille(int ligne, int colonne, Orientation orientation) {

        int lowRowNotAble, lowColumnNotAble, highRowNotAble, highColumnNotAble;

        if (orientation == Orientation.verticale) {
            if (ligne + 1 > tailleGrille || colonne > tailleGrille)
                return false;
            if (Grille[ligne][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne + 1][colonne] != EtatChamp.enEtat)
                return false;

            if (ligne + 1 == tailleGrille)
                highRowNotAble = ligne + 1;
            else
                highRowNotAble = ligne + 2;

            if (ligne  == 0)
                lowRowNotAble = ligne;
            else
                lowRowNotAble = ligne - 1;

            if (ligne == tailleGrille)
                highColumnNotAble = colonne;
            else
                highColumnNotAble = colonne + 1;

            if (ligne == 0)
                lowColumnNotAble = colonne;
            else
                lowColumnNotAble = colonne - 1;

            Grille[ligne][colonne] = EtatChamp.touché;
            Grille[ligne + 1][colonne] = EtatChamp.touché;
        } else {

            if (ligne > tailleGrille || colonne + 1 > tailleGrille)
                return false;
            if (Grille[ligne][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne][colonne + 1] != EtatChamp.enEtat)
                return false;

            if (ligne == tailleGrille)
                highRowNotAble = ligne;
            else
                highRowNotAble = ligne + 1;

            if (ligne == 0)
                lowRowNotAble = ligne;
            else
                lowRowNotAble = ligne - 1;

            if (colonne + 1 == tailleGrille)
                highColumnNotAble = colonne + 1;
            else
                highColumnNotAble = colonne + 2;

            if (colonne == 0)
                lowColumnNotAble = colonne;
            else
                lowColumnNotAble = colonne - 1;

            Grille[ligne][colonne] = EtatChamp.touché;
            Grille[ligne][colonne + 1] = EtatChamp.touché;
        }

        for (int i = lowRowNotAble; i <= highRowNotAble; i++)
            for (int j = lowColumnNotAble; j <= highColumnNotAble; j++)
                if (Grille[i][j] != EtatChamp.touché)
                    Grille[i][j] = EtatChamp.pasEnEtat;

        listeNavires[nbNavires] = new Navire(new IntPair(ligne, colonne), 2, orientation);
        nbNavires++;

        Restants += 2;
        return true;
    }

    // Remember row and column value from 0, but displayed from 1
    // means: you have to use this methods with arguments 1 lower
    public boolean ajouterSousMarin(int ligne, int colonne, Orientation orientation) {

        int lowRowNotAble, lowColumnNotAble, highRowNotAble, highColumnNotAble;

        if (orientation == Orientation.verticale) {
            if (ligne + 2 > tailleGrille || colonne > tailleGrille)
                return false;
            if (Grille[ligne][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne + 1][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne + 2][colonne] != EtatChamp.enEtat)
                return false;

            if (ligne + 2 == tailleGrille)
                highRowNotAble = ligne + 2;
            else
                highRowNotAble = ligne + 3;

            if (ligne == 0)
                lowRowNotAble = ligne;
            else
                lowRowNotAble = ligne - 1;

            if (colonne == tailleGrille)
                highColumnNotAble = colonne;
            else
                highColumnNotAble = colonne + 1;

            if (colonne == 0)
                lowColumnNotAble = colonne;
            else
                lowColumnNotAble = colonne - 1;

            Grille[ligne][colonne] = EtatChamp.touché;
            Grille[ligne + 1][colonne] = EtatChamp.touché;
            Grille[ligne + 2][colonne] = EtatChamp.touché;
        } else {

            if (ligne > tailleGrille || colonne + 2 > tailleGrille)
                return false;
            if (Grille[ligne][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne][colonne + 1] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne][colonne + 2] != EtatChamp.enEtat)
                return false;

            if (ligne == tailleGrille)
                highRowNotAble = ligne;
            else
                highRowNotAble = ligne + 1;

            if (ligne == 0)
                lowRowNotAble = ligne;
            else
                lowRowNotAble = ligne - 1;

            if (colonne + 2 == tailleGrille)
                highColumnNotAble = colonne + 2;
            else
                highColumnNotAble = colonne + 3;

            if (colonne == 0)
                lowColumnNotAble = colonne;
            else
                lowColumnNotAble = colonne - 1;

            Grille[ligne][colonne] = EtatChamp.touché;
            Grille[ligne][colonne + 1] = EtatChamp.touché;
            Grille[ligne][colonne + 2] = EtatChamp.touché;
        }

        for (int i = lowRowNotAble; i <= highRowNotAble; i++)
            for (int j = lowColumnNotAble; j <= highColumnNotAble; j++)
                if (Grille[i][j] != EtatChamp.touché)
                    Grille[i][j] = EtatChamp.pasEnEtat;

        listeNavires[nbNavires] = new Navire(new IntPair(ligne, colonne), 3, orientation);
        nbNavires++;
        Restants += 3;
        return true;
    }

    // Remember row and column value from 0, but displayed from 1
    // means: you have to use this methods with arguments 1 lower
    public boolean ajouterPorteAvion(int ligne, int colonne, Orientation orientation) {

        int lowRowNotAble, lowColumnNotAble, highRowNotAble, highColumnNotAble;

        if (orientation == Orientation.verticale) {
            if (ligne + 3 > tailleGrille || colonne > tailleGrille)
                return false;
            if (Grille[ligne][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne + 1][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne + 2][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne + 3][colonne] != EtatChamp.enEtat)
                return false;

            if (ligne + 3 == tailleGrille)
                highRowNotAble = ligne + 3;
            else
                highRowNotAble = ligne + 4;

            if (ligne == 0)
                lowRowNotAble = ligne;
            else
                lowRowNotAble = ligne - 1;

            if (colonne == tailleGrille)
                highColumnNotAble = colonne;
            else
                highColumnNotAble = colonne + 1;

            if (colonne == 0)
                lowColumnNotAble = colonne;
            else
                lowColumnNotAble = colonne - 1;

            Grille[ligne][colonne] = EtatChamp.touché;
            Grille[ligne + 1][colonne] = EtatChamp.touché;
            Grille[ligne + 2][colonne] = EtatChamp.touché;
            Grille[ligne + 3][colonne] = EtatChamp.touché;
        } else {

            if (ligne > tailleGrille || colonne + 3 > tailleGrille)
                return false;
            if (Grille[ligne][colonne] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne][colonne + 1] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne][colonne + 2] != EtatChamp.enEtat)
                return false;
            if (Grille[ligne][colonne + 3] != EtatChamp.enEtat)
                return false;

            if (ligne == tailleGrille)
                highRowNotAble = ligne;
            else
                highRowNotAble = ligne + 1;

            if (ligne == 0)
                lowRowNotAble = ligne;
            else
                lowRowNotAble = ligne - 1;

            if (colonne + 3 == tailleGrille)
                highColumnNotAble = colonne + 3;
            else
                highColumnNotAble = colonne + 4;

            if (colonne == 0)
                lowColumnNotAble = colonne;
            else
                lowColumnNotAble = colonne - 1;

            Grille[ligne][colonne] = EtatChamp.touché;
            Grille[ligne][colonne + 1] = EtatChamp.touché;
            Grille[ligne][colonne + 2] = EtatChamp.touché;
            Grille[ligne][colonne + 3] = EtatChamp.touché;
        }

        for (int i = lowRowNotAble; i <= highRowNotAble; i++)
            for (int j = lowColumnNotAble; j <= highColumnNotAble; j++)
                if (Grille[i][j] != EtatChamp.touché)
                    Grille[i][j] = EtatChamp.pasEnEtat;

        listeNavires[nbNavires] = new Navire(new IntPair(ligne, colonne), 4, orientation);
        nbNavires++;
        Restants += 4;
        return true;
    }

    public EtatChamp resolveShoot(Tir tir) {

        int ligne = tir.getLigne();
        int colonne = tir.getColonne();
        EtatChamp result = EtatChamp.raté;

        if (Grille[ligne][colonne] == EtatChamp.touché) {
            Grille[ligne][colonne] = EtatChamp.détruit;
            Restants--;

        } else {
            Grille[ligne][colonne] = EtatChamp.raté;
            return EtatChamp.raté;
        }

        IntPair pair = new IntPair(ligne, colonne);
        for (int i = 0; i < nbNavires; i++) {
            listeNavires[i].tir(tir);
            if (listeNavires[i].onThisShip(pair))
                result = listeNavires[i].getEtatNavire();
        }
        tir.setResult(result);
        miseAJourResultat(tir);
        return result;
    }

    public boolean plusDeNavires() {
        if (Restants == 0)
            return true;
        return false;
    }

    public boolean canIShootThere(int ligne, int colonne) {
        if (Grille[ligne][colonne] == EtatChamp.raté || Grille[ligne][colonne] == EtatChamp.détruit || Grille[ligne][colonne] == EtatChamp.coulé)
            return false;
        else
            return true;
    }

    public EtatChamp getEtatChamp(int ligne, int colonne) {
        return this.Grille[ligne][colonne];
    }

    public void debug() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                System.out.print(Grille[i][j] + "\t");
            System.out.print("\n");
        }
    }

    public void setRandom() {
        int i, ligne, colonne;
        Orientation orientation = null;
        Random generator = new Random();

        for (i = 0; i < Jouer.porteAvionCount; i++)
            do {
            	ligne = generator.nextInt(tailleGrille);
            	colonne = generator.nextInt(tailleGrille);
                orientation = Orientation.values()[generator.nextInt(2)];
            } while (!ajouterPorteAvion(ligne, colonne, orientation));

        for (i = 0; i < Jouer.sousMarinCount; i++)
            do {
            	ligne = generator.nextInt(tailleGrille);
            	colonne = generator.nextInt(tailleGrille);
                orientation = Orientation.values()[generator.nextInt(2)];
            } while (!ajouterSousMarin(ligne, colonne, orientation));

        for (i = 0; i < Jouer.patrouilleCount; i++)
            do {
            	ligne = generator.nextInt(tailleGrille);
            	colonne = generator.nextInt(tailleGrille);
                orientation = Orientation.values()[generator.nextInt(2)];
            } while (!ajouterPatrouille(ligne, colonne, orientation));

        for (i = 0; i < Jouer.destroyerCount; i++)
            do {
            	ligne = generator.nextInt(tailleGrille);
            	colonne = generator.nextInt(tailleGrille);
            } while (!ajouterDestroyer(ligne, colonne,orientation));

    }

}