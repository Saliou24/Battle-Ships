package battleship.jeu;

import battleship.enums.Orientation;
import battleship.menu.MainMenu;
import battleship.tir.MyButton;
import battleship.enums.Mode;
import battleship.enums.EtatChamp;
import battleship.Jouer;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Deploiement {

	private int tailleGrille;
	private MyButton buttons[][];
	private JLabel labels[][];
	private JButton buttonOK;
	private JButton buttonMenu;
	private GameBoard board;
	private JRadioButton PorteAvion;
	private JRadioButton sousMarin;
	private JRadioButton destroyer;
	private JRadioButton bateauPatrouille;
	private JComboBox<String> orient;
	private int nbPatrouille = 0;
	private int nbSousMarin = 0;
	private int nbPorteAvion = 0;
	private int nbDestroyeur = 0;
	private JButton randomBoard;

	@SuppressWarnings("removal")
	public Deploiement(int size) {
		Jouer.currentMenu.menuPanel.setVisible(false);
		if (Jouer.mode == Mode.hotSeat2) {
			Jouer.firstPanel.setVisible(false);
			Jouer.secondPanel.setVisible(false);
			Jouer.thirdPanel.setVisible(false);
			Jouer.fourthPanel.setVisible(false);
		}
		tailleGrille = size;
		board = new GameBoard(tailleGrille);
		setPanels();
		buttons = new MyButton[tailleGrille][tailleGrille];
		labels = new JLabel[2][tailleGrille];
		for (int i = 0; i < tailleGrille; i++)
			for (int j = 0; j < tailleGrille; j++) {
				buttons[i][j] = new MyButton(" ");
				buttons[i][j].ligne = i;
				buttons[i][j].colonne = j;
				buttons[i][j].setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
				buttons[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						MyButton tempButton = (MyButton) arg0.getSource();
						Orientation orientation;
						if (orient.getSelectedIndex() == 0)
							orientation = Orientation.verticale;
						else
							orientation = Orientation.horizontale;
						if (PorteAvion.isSelected()) {
							if (board.ajouterPorteAvion(tempButton.ligne, tempButton.colonne, orientation)) {
								nbPorteAvion++;
								randomBoard.setEnabled(false);
							}
							if (nbPorteAvion == Jouer.porteAvionCount) {
								PorteAvion.setEnabled(false);
								PorteAvion.setSelected(false);

								if (nbPatrouille != Jouer.patrouilleCount)
									bateauPatrouille.setSelected(true);
								if (nbSousMarin != Jouer.sousMarinCount)
									sousMarin.setSelected(true);
								if (nbDestroyeur != Jouer.destroyerCount)
									destroyer.setSelected(true);
							}
						}

						else if (destroyer.isSelected()) {
							if (board.ajouterDestroyer(tempButton.ligne, tempButton.colonne, orientation)) {
								nbDestroyeur++;
								randomBoard.setEnabled(false);
							}
							if (nbDestroyeur == Jouer.destroyerCount) {
								destroyer.setEnabled(false);
								destroyer.setSelected(false);

								if (nbPatrouille != Jouer.patrouilleCount)
									bateauPatrouille.setSelected(true);
								if (nbSousMarin != Jouer.sousMarinCount)
									sousMarin.setSelected(true);
								if (nbPorteAvion != Jouer.porteAvionCount)
									PorteAvion.setSelected(true);
							}

						}

						else if (sousMarin.isSelected()) {
							if (board.ajouterSousMarin(tempButton.ligne, tempButton.colonne, orientation)) {
								nbSousMarin++;
								randomBoard.setEnabled(false);
							}
							if (nbSousMarin == Jouer.sousMarinCount) {
								sousMarin.setEnabled(false);
								sousMarin.setSelected(false);

								if (nbPatrouille != Jouer.patrouilleCount)
									bateauPatrouille.setSelected(true);
								if (nbPorteAvion != Jouer.porteAvionCount)
									PorteAvion.setSelected(true);
								if (nbDestroyeur != Jouer.destroyerCount)
									destroyer.setSelected(true);
							}
						}

						else if (bateauPatrouille.isSelected()) {
							if (board.ajouterPatrouille(tempButton.ligne, tempButton.colonne, orientation)) {
								nbPatrouille++;
								randomBoard.setEnabled(false);
							}
							if (nbPatrouille == Jouer.patrouilleCount) {
								bateauPatrouille.setEnabled(false);
								bateauPatrouille.setSelected(false);

								if (nbSousMarin != Jouer.sousMarinCount)
									sousMarin.setSelected(true);
								if (nbPorteAvion != Jouer.porteAvionCount)
									PorteAvion.setSelected(true);
								if (nbDestroyeur != Jouer.destroyerCount)
									destroyer.setSelected(true);
							}

						}

						int having = nbPatrouille + nbSousMarin + nbPorteAvion + nbDestroyeur;
						int required = Jouer.porteAvionCount + Jouer.sousMarinCount + Jouer.patrouilleCount
								+ Jouer.destroyerCount;
						if (having == required) {
							buttonOK.setEnabled(true);
							for (int i = 0; i < tailleGrille; i++)
								for (int j = 0; j < tailleGrille; j++)
									buttons[i][j].setEnabled(false);
						}
						paintButtons();
					}

				});

				char letter = (char) (i + 'a');
				String string = new StringBuilder().append(letter).toString();
				labels[0][i] = new JLabel(string);
				labels[1][i] = new JLabel(new Integer(i + 1).toString());
			}
		paintButtons();
	}

	private void setPanels() {
		Jouer.firstPanel = new JPanel(new GridBagLayout());
		Jouer.secondPanel = new JPanel(new GridBagLayout());
		Jouer.thirdPanel = new JPanel(new GridBagLayout());
		Jouer.fourthPanel = new JPanel(new GridBagLayout());

		Jouer.firstPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		Jouer.secondPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		Jouer.thirdPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		Jouer.fourthPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		Jouer.frame.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints frameLayout = new GridBagConstraints();

		frameLayout.gridwidth = 2;
		frameLayout.fill = GridBagConstraints.BOTH;
		frameLayout.weightx = 0.1;
		frameLayout.weighty = 0.1;
		Jouer.frame.getContentPane().add(Jouer.firstPanel, frameLayout);
		frameLayout.weightx = 1.0;
		frameLayout.weighty = 1.0;
		frameLayout.gridy = 1;
		frameLayout.gridwidth = 1;
		Jouer.frame.getContentPane().add(Jouer.secondPanel, frameLayout);
		frameLayout.gridx = 1;
		Jouer.frame.getContentPane().add(Jouer.thirdPanel, frameLayout);
		frameLayout.weightx = 0.1;
		frameLayout.weighty = 0.1;
		frameLayout.gridwidth = 2;
		frameLayout.gridy = 2;
		frameLayout.gridx = 0;
		Jouer.frame.getContentPane().add(Jouer.fourthPanel, frameLayout);
		Jouer.frame.repaint();

		JLabel name = new JLabel("Deploiement");
		if (Jouer.mode == Mode.hotSeat1)
			name.setText("Deploiement " + Jouer.currentMenu.textFieldList[0].getText());
		if (Jouer.mode == Mode.hotSeat2)
			name.setText("Deploiement " + Jouer.currentMenu.textFieldList[1].getText());
		buttonOK = new JButton("OK");
		buttonOK.setEnabled(false);
		buttonMenu = new JButton("Exit");
		name.setFont(new Font("SansSerif", Font.PLAIN, 20));
		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.WEST;
		layout.weightx = 1.0;
		layout.weighty = 1.0;
		layout.insets = new Insets(5, 5, 5, 5);
		Jouer.firstPanel.add(name, layout);
		layout.anchor = GridBagConstraints.EAST;
		Jouer.fourthPanel.add(buttonOK, layout);
		layout.weightx = 0;
		layout.weighty = 0;
		layout.gridx = 1;
		Jouer.fourthPanel.add(buttonMenu, layout);

		buttonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int pick = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (pick == JOptionPane.YES_OPTION) {
					if (Jouer.mode == Mode.onlineServer)
						Jouer.serwer.closeSerwerConnection();
					if (Jouer.mode == Mode.onlineClient)
						Jouer.client.closeKlientConnection();
					Jouer.firstPanel.setVisible(false);
					Jouer.secondPanel.setVisible(false);
					Jouer.thirdPanel.setVisible(false);
					Jouer.fourthPanel.setVisible(false);
					Jouer.currentMenu = new MainMenu();
					Jouer.frame.repaint();
				}
			}
		});

		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Jouer.mode == Mode.hotSeat1) {
					Jouer.mode = Mode.hotSeat2;
					Jouer.Joueur2 = new HumanPlayer(10);
				} else if (Jouer.mode == Mode.soloCLASSIQUE || Jouer.mode == Mode.soloAVANCE)
					Jouer.currentGame = new NewGame();
				else if (Jouer.mode == Mode.hotSeat2)
					Jouer.currentGame = (NewGame) new HotSeatView(0, 0);
				else if (Jouer.mode == Mode.onlineClient) {
					Jouer.currentGame = (NewGame) new OnlineGame();
					OnlineGame temp = (OnlineGame) Jouer.currentGame;
					temp.Responder();
				} else if (Jouer.mode == Mode.onlineServer)
					Jouer.currentGame = (NewGame) new OnlineGame();
			}
		});

		// Right Panel
		randomBoard = new JButton("Get Random Deploiement");
		randomBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				board.setRandom();
				randomBoard.setEnabled(false);
				nbPorteAvion = Jouer.porteAvionCount;
				nbSousMarin = Jouer.sousMarinCount;
				nbPatrouille = Jouer.patrouilleCount;
				nbDestroyeur = Jouer.destroyerCount;
				buttonOK.setEnabled(true);
				for (int i = 0; i < tailleGrille; i++)
					for (int j = 0; j < tailleGrille; j++)
						buttons[i][j].setEnabled(false);

				PorteAvion.setEnabled(false);
				sousMarin.setEnabled(false);
				bateauPatrouille.setEnabled(false);

				paintButtons();
			}

		});

		ButtonGroup shipChoose = new ButtonGroup();
		PorteAvion = new JRadioButton("PORTE-AVION (" + Jouer.porteAvionCount + ")", true);
		sousMarin = new JRadioButton("SOUS-MARIN (" + Jouer.sousMarinCount + ")", false);
		destroyer = new JRadioButton("DESTROYER (" + Jouer.destroyerCount + ")", false);
		bateauPatrouille = new JRadioButton("PATROUILLE (" + Jouer.patrouilleCount + ")", false);
		shipChoose = new ButtonGroup();

		shipChoose.add(PorteAvion);
		shipChoose.add(sousMarin);
		shipChoose.add(destroyer);
		shipChoose.add(bateauPatrouille);
		layout.gridx = 0;
		layout.gridy = 0;
		layout.weightx = 1;
		layout.weighty = 1;
		layout.gridwidth = 1;
		layout.gridheight = 1;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;
		Jouer.thirdPanel.add(PorteAvion, layout);
		layout.gridy = 1;
		Jouer.thirdPanel.add(sousMarin, layout);
		layout.gridy = 2;
		Jouer.thirdPanel.add(bateauPatrouille, layout);
		layout.gridy = 3;
		Jouer.thirdPanel.add(destroyer, layout);
		String[] names = { "Vertical", "Horizontal" };
		orient = new JComboBox<String>(names);
		layout.gridy = 4;
		Jouer.thirdPanel.add(orient, layout);
		layout.gridy = 5;
		layout.insets = new Insets(0, 0, 200, 0);
		Jouer.thirdPanel.add(randomBoard, layout);

	}

	private void paintButtons() {
		Jouer.secondPanel.removeAll();
		Jouer.secondPanel.repaint();
		GridBagConstraints layout = new GridBagConstraints();
		layout.insets = new Insets(1, 1, 1, 1);
		layout.anchor = GridBagConstraints.CENTER;
		layout.fill = GridBagConstraints.BOTH;

		for (int i = 1; i < tailleGrille + 1; i++) {
			layout.gridy = i;
			Jouer.secondPanel.add(labels[1][i - 1], layout);
		}
		layout.gridy = 0;
		for (int i = 1; i < tailleGrille + 1; i++) {
			layout.gridx = i;
			Jouer.secondPanel.add(labels[0][i - 1], layout);
		}

		for (int i = 0; i < tailleGrille; i++)
			for (int j = 0; j < tailleGrille; j++) {
				layout.gridx = j + 1;
				layout.gridy = i + 1;
				if (board.getEtatChamp(i, j) == EtatChamp.touché)
					buttons[i][j].setBackground(new Color(0, 255, 0));
				Jouer.secondPanel.add(buttons[i][j], layout);
			}
	}

	public GameBoard returnBoard() {
		return this.board;
	}

}