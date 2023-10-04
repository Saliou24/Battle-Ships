package battleship;

import java.awt.Dimension;

import battleship.jeu.ChangeView;
import battleship.menu.MainMenu;
import battleship.menu.Menu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import battleship.enums.Mode;
import battleship.jeu.Client;
import battleship.jeu.Joueur;
import battleship.jeu.NewGame;
import battleship.jeu.Server;

public class Jouer {
	public static JPanel firstPanel;
	public static JPanel secondPanel;
	public static JPanel thirdPanel;
	public static JPanel fourthPanel;
	public static JFrame frame;
	public static Menu currentMenu;
	public static int patrouilleCount = 1;
	public static int sousMarinCount = 1;
	public static int porteAvionCount = 1;
	public static int destroyerCount = 1;
	public static Joueur Joueur1;
	public static Joueur Joueur2;
	public static Mode mode;
	public static NewGame currentGame;
	public static ChangeView changer;
	public static boolean monTour;
	public static Server serwer;
	public static Client client;

	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                frame = new JFrame("BattleShips");
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.setMinimumSize(new Dimension(1024, 600));
	                frame.setResizable(true);
	                frame.setVisible(true);
	               
	                
	          
	                currentMenu = new MainMenu();
	            }
	        });

	    }
	
	
}