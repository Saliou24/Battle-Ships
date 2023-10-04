package battleship.niveau;

import java.util.Random;
import battleship.Jouer;
import battleship.enums.EtatChamp;
import battleship.tir.Tir;

public class Classique extends Ordinateur {

    public Classique(int size) {
        super(size);
    }

    public void shoot() {

        Random generator = new Random();
        int ligne, colonne;
        Tir tir;

        do {
            do {
            	ligne = generator.nextInt(tailleGrille);
            	colonne = generator.nextInt(tailleGrille);
            } while (grilleOpposant.canIShootThere(ligne, colonne) == false);

            tir = new Tir(ligne, colonne);

            tir.setResult(Jouer.Joueur1.maGrille.resolveShoot(tir));
            miseAJourResultat(tir);
        } while (tir.getResult() != EtatChamp.raté && Jouer.Joueur1.estMort() == false);
    }

	
	
}