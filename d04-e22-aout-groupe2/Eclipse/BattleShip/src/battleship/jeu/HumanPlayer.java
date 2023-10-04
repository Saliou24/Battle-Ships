package battleship.jeu;

public  class HumanPlayer extends Joueur {

    private Deploiement deploiement;

    public HumanPlayer(int size) {
        tailleGrille = size;
        grilleOpposant = new GameBoard(size);
        deploiement = new Deploiement(size);
        maGrille = deploiement.returnBoard();
    }

    public void shoot() {
    }

	

}