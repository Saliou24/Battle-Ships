package battleship.jeu;

import battleship.tir.Tir;

public abstract class Joueur {
    public GameBoard maGrille;
    protected GameBoard grilleOpposant;
    protected int tailleGrille;

    protected void setMaGrille() {
        maGrille = new GameBoard(tailleGrille);
        maGrille.setRandom();

    }

    public abstract void shoot();

    public void debug() {
        maGrille.debug();
    }

    protected void MiseAJourResultat(Tir tir) {
		miseAJourResultat(tir);
	}

	protected void miseAJourResultat(Tir tir) {
    	grilleOpposant.miseAJourResultat(tir);
    }

    public boolean estMort() {
        return maGrille.plusDeNavires();
    }

	

}