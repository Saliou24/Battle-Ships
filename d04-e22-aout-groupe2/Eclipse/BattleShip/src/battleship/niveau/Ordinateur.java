package battleship.niveau;

import battleship.jeu.GameBoard;
import battleship.jeu.Joueur;

public abstract class Ordinateur extends Joueur {
	public Ordinateur (int size) {
        tailleGrille = size;
        grilleOpposant = new GameBoard(size);
        setMaGrille();
    }

	public abstract void shoot();

}

