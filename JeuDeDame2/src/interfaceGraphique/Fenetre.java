package interfaceGraphique;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import damier.Damier;
import entreeSortie.Sauvegarde;

public class Fenetre extends JFrame implements ActionListener{
		private JPanel cardHolder;
	    private CardLayout cards;
	    private Plateau plateau;
	    private Sauvegarde sauvegarde;
	    private final String NOUVELLE_PARTIE="Nouvelle Partie";
	    private final String CHARGER_PARTIE="Charger une Partie";
	    private final String PLATEAU="PLATEAU";
	    private final String MENU="MENU";
	    private final String QUITTER="Quitter";
	    private final String ORDINATEUR="Ordinateur";
	    private final String CHARGER_ORDINATEUR="Charger une Partie contre l'ordinateur";
		public Fenetre() {
			super();
			long t=System.currentTimeMillis();
			this.setTitle("Jeu de Dame");
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setMinimumSize(new Dimension(500,500));
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setContentPane(createPane());
			this.sauvegarde=null;
			cards.first(cardHolder);
		}
		private JPanel createPane() {
			cardHolder=new JPanel();
			cardHolder.setVisible(true);
			cards=new CardLayout();
			cardHolder.setLayout(cards);
			plateau=new Plateau(this);
			JPanel menu = createMenu();
			cardHolder.add(menu,MENU);
			cardHolder.add(plateau,PLATEAU);
			return cardHolder;
		}
		
		private JPanel createMenu(){
			JPanel menu = new JPanel();
			menu.setVisible(true);
			menu.setLayout(null);
			menu.setBackground(Color.BLACK);
			JButton b= new JButton("Nouvelle Partie");
			JButton b1= new JButton("Charger une Partie");
			JButton b3= new JButton("Nouvelle Partie contre l'ordinateur");
			JButton b4= new JButton("Charger une Partie contre l'ordinateur");
			JButton b2= new JButton("Quitter");
			JLabel label= new JLabel("Menu Jeu de Dame");
			int w=this.getWidth()/3,h=this.getHeight()/10;
			Font fb=new Font("Button", Font.PLAIN, 20);
			b.setActionCommand(NOUVELLE_PARTIE);
			b.addActionListener(this);
			b.setBounds(w, 3*h/2, w, h);
			b1.setActionCommand(CHARGER_PARTIE);
			b1.addActionListener(this);
			b1.setBounds(w, 3*h, w, h);
			b3.setActionCommand(ORDINATEUR);
			b3.addActionListener(this);
			b3.setBounds(w, 9*h/2, w,h);
			b4.setActionCommand(CHARGER_ORDINATEUR);
			b4.addActionListener(this);
			b4.setBounds(w, 12*h/2, w,h);
			b2.setActionCommand(QUITTER);
			b2.addActionListener(this);
			b2.setBounds(w, 15*h/2, w,h);
			label.setBounds(w,h/2,w,h);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setFont(new Font("Label",Font.BOLD,30));
			menu.add(b);
			menu.add(b1);
			menu.add(b3);
			menu.add(b4);
			menu.add(b2);
			menu.add(label);
			return menu;
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		String nom;
		int reponse,niveau;
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case MENU:
			cards.show(cardHolder, MENU);
			break;
		case NOUVELLE_PARTIE:
			nom=JOptionPane.showInputDialog("Veuillez entrer le nom de votre partie", "Partie de Dame");
			if(nom!=null) {
				Damier d = new Damier(nom);
				plateau.setJoueurContreJoueur();
				plateau.setDamier(d);
				cards.show(cardHolder, PLATEAU);
			}
			break;
		case "SAUVEGARDER":
			if(this.sauvegarde==null) {
				try {
					this.sauvegarde=new Sauvegarde("sauvegarde des parties");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(this.plateau, "Le répertoire des pour stocker les parties n'a pas pu être créé.");
					break;
				}
			}
			try {
				nom=this.plateau.getDamier().getNomPartie();
				if(this.sauvegarde.getListeNomFichier().contains(nom)) {
					String options[]= {"Oui","Non"};
					reponse=JOptionPane.showOptionDialog(this.cardHolder,"Cette partie existe déjà, voulez vous l'écraser?", "Cette partie existe déjà!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, -1);
					if(1==reponse) {
						while(nom==null || this.sauvegarde.getListeNomFichier().contains(nom)) {
							nom= JOptionPane.showInputDialog("Veuillez entrer le nom de votre partie", "Partie de Dame");
						}
						this.plateau.getDamier().setNomPartie(nom);
					}
					else {
						if(reponse==-1) {
							System.out.println("La partie n'a pas été sauvegardée");
							JOptionPane.showMessageDialog(this.cardHolder, "La partie n'a pas été sauvegardée");
							break;
						}
					}
				}
				this.sauvegarde.sauvegarder(this.plateau.getDamier());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this.plateau, "Le fichier de sauvegarde n'a pas pu être créé.");
			}
			break;
		case CHARGER_PARTIE :
			if(this.sauvegarde==null) {
				try {
					this.sauvegarde=new Sauvegarde("sauvegarde des parties");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(this.plateau, "Le répertoire des pour stocker les parties n'a pas pu être créé.");
					break;
				}
			}
			try {
				nom=(String)JOptionPane.showInputDialog(this.cardHolder, "Veuillez selectionner la partie que vous voules charger.", "Charger une partie", JOptionPane.PLAIN_MESSAGE, null, this.sauvegarde.getListeNomFichier().toArray(), null);
				if(nom!=null){
					plateau.setDamier(this.sauvegarde.charger(nom));
					cards.show(cardHolder, PLATEAU);
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this.plateau, "Le fichier de sauvegarde n'a pas pu être créé.");
			}
			break;
		case CHARGER_ORDINATEUR :
			if(this.sauvegarde==null) {
				try {
					this.sauvegarde=new Sauvegarde("sauvegarde des parties");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(this.plateau, "Le répertoire des pour stocker les parties n'a pas pu être créé.");
					break;
				}
			}
			try {
				nom=(String)JOptionPane.showInputDialog(this.cardHolder, "Veuillez selectionner la partie que vous voules charger.", "Charger une partie", JOptionPane.PLAIN_MESSAGE, null, this.sauvegarde.getListeNomFichier().toArray(), null);
				if(nom!=null) {
					String options[]= {"Blanc","Noir"};
					reponse=JOptionPane.showOptionDialog(this.cardHolder,"Voulez vous jouer les blanc ou les noirs", "Blanc ou Noir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, -1);
					if(reponse==-1) {
						break;
					}
					else {
						String options2[]= {"Trés Facile","Facile","Moyen","Difficile"};
						switch((String)JOptionPane.showInputDialog(this.cardHolder, "Selectionner le niveau de l'ordinateur", "Niveau de difficulté", JOptionPane.PLAIN_MESSAGE, null, options2, null)) {
						case "Trés Facile":
							niveau=0;
							break;
						case "Facile":
							niveau=1;
							break;
						case "Moyen":
							niveau=2;
							break;
						case "Difficile":
							niveau=3;
							break;
						default:
							niveau=-1;
							break;
						}
						if(niveau!=-1) {
							Damier d = new Damier(nom);
							plateau.setDamier(this.sauvegarde.charger(nom));
							if(reponse==0) {
								plateau.setContreOrdinateur(niveau, true);
							}
							else {
								plateau.setContreOrdinateur(niveau, false);
							}
							cards.show(cardHolder, PLATEAU);
						}
						else {
							break;
						}
					}
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this.plateau, "Le fichier de sauvegarde n'a pas pu être créé.");
			}
			break;
		case ORDINATEUR :
			nom=JOptionPane.showInputDialog("Veuillez entrer le nom de votre partie", "Partie de Dame");
			if(nom!=null) {
				String options[]= {"Blanc","Noir"};
				reponse=JOptionPane.showOptionDialog(this.cardHolder,"Voulez vous jouer les blanc ou les noirs", "Blanc ou Noir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, -1);
				if(reponse==-1) {
					break;
				}
				else {
					String options2[]= {"Trés Facile","Facile","Moyen","Difficile"};
					switch((String)JOptionPane.showInputDialog(this.cardHolder, "Selectionner le niveau de l'ordinateur", "Niveau de difficulté", JOptionPane.PLAIN_MESSAGE, null, options2, null)) {
					case "Trés Facile":
						niveau=0;
						break;
					case "Facile":
						niveau=1;
						break;
					case "Moyen":
						niveau=2;
						break;
					case "Difficile":
						niveau=3;
						break;
					default:
						niveau=-1;
						break;
					}
					if(niveau!=-1) {
						Damier d = new Damier(nom);
						plateau.setDamier(d);
						if(reponse==0) {
							plateau.setContreOrdinateur(niveau, true);
						}
						else {
							plateau.setContreOrdinateur(niveau, false);
						}
						cards.show(cardHolder, PLATEAU);
					}
					else {
						break;
					}
				}
			}
			break;
		case QUITTER:
			System.exit(0);
			break;
		default:
			break;
		}
	}
	
	public void montreToi() {
		this.cards.show(cardHolder, MENU);
	}
	private static final long serialVersionUID = 1L;
}
