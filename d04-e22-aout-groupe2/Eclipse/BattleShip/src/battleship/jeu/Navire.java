package battleship.jeu;

import battleship.tir.IntPair;
import battleship.enums.Orientation;
import battleship.tir.Tir;
import battleship.enums.EtatChamp;

public class Navire {
    private EtatChamp etatNavire;
    private final int tailleNavire;
    private final IntPair[] champBataille;
    private final EtatChamp[] etatPiecesNavires;

    public Navire(IntPair begining, int size, Orientation orientation) {
        tailleNavire = size;
        champBataille = new IntPair[tailleNavire];
        etatPiecesNavires = new EtatChamp[tailleNavire];
        etatNavire = EtatChamp.pasTouché;
        int ligne = begining.ligne;
        int colonne = begining.colonne;
        if (orientation == Orientation.horizontale)
            for (int i = 0; i < tailleNavire; i++) {
                champBataille[i] = new IntPair(ligne, colonne + i);
                etatPiecesNavires[i] = EtatChamp.pasTouché;
            }
        else
            for (int i = 0; i < tailleNavire; i++) {
                champBataille[i] = new IntPair(ligne + i, colonne);
                etatPiecesNavires[i] = EtatChamp.pasTouché;
            }

    }

    public EtatChamp getEtatNavire() {
        return etatNavire;
    }

    public boolean onThisShip(IntPair champ) {
        for (int i = 0; i < tailleNavire; i++)
            if (champBataille[i].ligne == champ.ligne && champBataille[i].colonne == champ.colonne)
                return true;
        return false;
    }

    public IntPair[] getChampBataille() {
        return champBataille;
    }

    public int getTailleNavire() {
        return tailleNavire;
    }

    public void tir(Tir tirAVerifier) {
        int ligneDuTir = tirAVerifier.getLigne();
        int colonneDuTir = tirAVerifier.getColonne();
        for (int i = 0; i < tailleNavire; i++) {
            if (ligneDuTir == champBataille[i].ligne && colonneDuTir == champBataille[i].colonne) {
                etatNavire = EtatChamp.détruit;
                etatPiecesNavires[i] = EtatChamp.détruit;
            };
        }
        int partiesDetruites = 0;

        for (int i = 0; i < tailleNavire; i++)
            if (etatPiecesNavires[i] == EtatChamp.détruit)
                partiesDetruites++;

        if (partiesDetruites == tailleNavire)
            etatNavire = EtatChamp.coulé;

    }

}