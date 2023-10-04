package battleship.tir;

import battleship.enums.EtatChamp;
import java.io.Serializable;

public class Tir implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ligne;
    private int colonne;
    private EtatChamp result;
    private boolean estPret;

    public Tir(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public int getLigne() {
        return this.ligne;
    }

    public int getColonne() {
        return this.colonne;
    }

    public void setResult(EtatChamp result) {
        this.result = result;
    }

    public EtatChamp getResult() {
        return this.result;
    }

	public boolean isEstPret() {
		return estPret;
	}

	public void setEstPret(boolean estPret) {
		this.estPret = estPret;
	}


}