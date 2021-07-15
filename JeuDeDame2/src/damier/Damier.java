package damier;

import java.util.LinkedList;
import java.util.Set;

public class Damier {
	private Piece damier[][];
	private boolean tour;
	private TreeCoups coupsPossible;
	private ListeCoups coupsCourant;
	private boolean coupsEnCours;
	private Coord selection;
	private Piece gagnant;
	private String nomPartie;
	private LinkedList<Deplacement> listeCoupsJoues;
	private int index;
	public Damier(String nom){
		int i,j;
		tour=true;
		damier=new Piece[10][10];
		coupsCourant=new ListeCoups();
		coupsEnCours=false;
		selection=null;
		gagnant=Piece.Vide;
		nomPartie=nom;
		index=0;
		listeCoupsJoues=new LinkedList<Deplacement>();
		for(i=0;i<10;i++) {
			for(j=0;j<10;j++) {
				damier[i][j]=Piece.Vide;
			}
		}
		/*this.damier[3][6]=Piece.PionBlanc;

		this.damier[0][3]=Piece.PionNoir;

		this.damier[2][5]=Piece.PionNoir;
		this.damier[4][5]=Piece.PionNoir;*/
		for(i=0;i<10;i++) {
			for(j=0;j<4;j++) {
				if((i+j)%2==1) {
					this.damier[i][j]=Piece.PionNoir;
				}
			}
			for(j=6;j<10;j++) {
				if((i+j)%2==1) {
					this.damier[i][j]=Piece.PionBlanc;
				}
			}
		}
		this.update();
	}
	public boolean isDrag() {
		return this.coupsCourant.isEmpty() || this.index>0;
	}
	public Coups joue(Coord coord) {//renvoie null Si un coups du tour du joueur à dejà été joué et que les coordonnées entrées ne correspondent à aucun prochain coups
									//renvoie une liste vide si il n'y a pas de coups possible pour la case selectionnée
									//sinon renvoie la liste des coups pour la case selectionnée
		Coups retour=new Coups();
		if(this.coupsCourant.isEmpty()) {
			this.coupsCourant.addAll(this.coupsPossible.get(coord));
			selection=coord;
			for (Coups c : this.coupsCourant) {
				retour.add(c.get(index));
			}
		}
		else {
			if(this.coupsCourant.contient(coord,index)) {
				this.deplacer(selection, coord);
				selection=coord;
				for (Coups c : this.coupsCourant) {
					retour.add(c.get(index));
				}
			}
			else {
				if(this.coupsEnCours) {
					retour=null;
				}
				else {
					this.coupsCourant.clear();
					this.coupsCourant.addAll(this.coupsPossible.get(coord));
					selection=coord;
					for (Coups c : this.coupsCourant) {
						retour.add(c.get(index));
					}
				}
			}
		}
		return retour;
	}
	private void deplacer(Coord coord1, Coord coord2) {
		Direction versCoord2=coord1.vers(coord2);
		Coord temp=new Coord(coord1.direction(versCoord2));
		while(!temp.equals(coord2) && this.get(temp).vide()) {//boucle infinie si coord1 et coord2 ne sont pas sur la même diagonale
			temp=temp.direction(versCoord2);
		}
		this.set(coord2, this.get(coord1));
		this.set(coord1, Piece.Vide);
		if(temp.equals(coord2)) {
			this.listeCoupsJoues.add(new Deplacement(coord1,coord2,this.get(coord2)));
		}
		else {
			this.listeCoupsJoues.add(new Prise(coord1,this.get(coord2),temp,this.get(temp),coord2));
			this.set(temp, Piece.Vide);
		}
		//this.coupsCourant.jouer(coord2);
		index++;
		if(this.coupsCourant.nbreDeCoups()<=index) {
			index=0;
			this.coupsCourant.clear();
			this.coupsEnCours=false;
			this.tour=!this.tour;
			this.update();
		}
		else {
			this.coupsEnCours=true;
		}
	}
	public Piece get(int x, int y) {
		return damier[x][y];
	}
	public Piece get(Coord coord) {
		return damier[coord.getX()][coord.getY()];
	}
	private void set(Coord coord, Piece p) {
		this.damier[coord.getX()][coord.getY()]=p;
	}
	
	private void update() {
		this.coupsPossible=new TreeCoups();
		int i,j;
		for(i=0;i<10;i++) {
			if(this.damier[i][9]==Piece.PionNoir) {
				this.damier[i][9]=Piece.DameNoire;
			}
			if(this.damier[i][0]==Piece.PionBlanc) {
				this.damier[i][0]=Piece.DameBlanche;
			}
		}
		for(i=0; i<this.damier.length; i++) {
			for(j=0;j<this.damier[i].length;j++) {
				if(this.damier[i][j].tour(tour)) {
					this.coupsPossible.put(new Coord(i,j), this.prise(new Coord(i,j)));
				}
			}
		}
		if(this.coupsPossible.isEmpty()) {
			for(i=0; i<this.damier.length; i++) {
				for(j=0;j<this.damier[i].length;j++) {
					if(this.damier[i][j].tour(tour)) {
						this.coupsPossible.put(new Coord(i,j), this.deplacement(new Coord(i,j)));
					}
				}
			}
			if(this.coupsPossible.isEmpty()) {
				if(this.tour) {
					this.gagnant=Piece.PionNoir;
				}
				else {
					this.gagnant=Piece.PionBlanc;
				}
			}
		}
	}
	public void afficheCoupsPossible() {
		this.coupsPossible.affiche();
	}
	
	public Piece gagnant() {
		return this.gagnant;
	}
	private ListeCoups prisePion(Coord coord, Direction[] direction, Coups dejaPasse) {
		ListeCoups retour = new ListeCoups();
		for(Direction d : direction) {
			if(coord.testPrise(d) && this.get(coord.direction(d)).opposant(tour) && this.get(coord.prise(d)).vide() && !dejaPasse.contient(coord.direction(d))) {
				dejaPasse.add(coord.direction(d));
				retour.addAll(this.prisePion(coord.prise(d), d.oppose().sauf(),dejaPasse.clone()).ajouter(coord.prise(d)));
			}
		}
		return retour;
	}
	private ListeCoups apresPriseDame(Coord coord, Direction direction, Coups dejaPasse) {
		ListeCoups retour=new ListeCoups();
		Coord temp=new Coord(coord);
		while(temp.test() && this.get(temp).vide()) {
			retour.addAll(this.priseDame(temp, direction.oppose().sauf(), dejaPasse.clone()).ajouter(temp));
			temp=temp.direction(direction);
		}
		return retour;
	}
	private ListeCoups priseDame(Coord coord, Direction[] direction, Coups dejaPasse) {
		Coord temp;
		ListeCoups retour=new ListeCoups();
		for(Direction d : direction) {
			temp=new Coord(coord);
			while(temp.test(d) && this.get(temp.direction(d)).vide()) {
				temp=temp.direction(d);
			}
			if(temp.testPrise(d) && this.get(temp.direction(d)).opposant(tour) && this.get(temp.prise(d)).vide() && !dejaPasse.contient(temp.direction(d))) {
				dejaPasse.add(temp.direction(d));
				retour.addAll(this.apresPriseDame(temp.prise(d), d,dejaPasse.clone()));
			}
		}
		return retour;
	}
	private ListeCoups prise(Coord coord) {
		ListeCoups retour;
		Coups dejaPasse=new Coups();
		if(this.get(coord).pion()) {
			retour=prisePion(coord,Direction.values(),dejaPasse);
		}
		else {
			retour=priseDame(coord,Direction.values(),dejaPasse);
		}
		return retour;
	}
	private ListeCoups deplacementPion(Coord coord) {
		ListeCoups retour=new ListeCoups();
		if(this.get(coord).blanc()) {
			if(coord.test(Direction.NE) && this.get(coord.NE()).vide()) {
				retour.add(new Coups(coord.NE()));
			}
			if(coord.test(Direction.NO) && this.get(coord.NO()).vide()) {
				retour.add(new Coups(coord.NO()));
			}
		}
		else {
			if(coord.test(Direction.SE) && this.get(coord.SE()).vide()) {
				retour.add(new Coups(coord.SE()));
			}
			if(coord.test(Direction.SO) && this.get(coord.SO()).vide()) {
				retour.add(new Coups(coord.SO()));
			}
		}
		return retour;
	}
	private ListeCoups deplacementDame(Coord coord) {
		ListeCoups retour=new ListeCoups();
		Coord temp;
		for(Direction d : Direction.values()) {
			temp=new Coord(coord);
			while(temp.test(d) && this.get(temp.direction(d)).vide()) {
				retour.add(new Coups(temp.direction(d)));
				temp=temp.direction(d);
			}
		}
		return retour;
	}
	private ListeCoups deplacement(Coord coord) {
		ListeCoups retour;
		if(this.get(coord).pion()) {
			retour=deplacementPion(coord);
		}
		else {
			retour=deplacementDame(coord);
		}
		return retour;
	}
	public LinkedList<Deplacement> getListeCoupsJoues() {
		return listeCoupsJoues;
	}
	
	public String getNomPartie() {
		return nomPartie;
	}


	public void setNomPartie(String nomPartie) {
		this.nomPartie = nomPartie;
	}

	protected void revenirDun() {
		if(!this.listeCoupsJoues.isEmpty()) {
			this.coupsCourant.clear();
			if(this.listeCoupsJoues.getLast().getClass()==Prise.class) {
				Prise p=(Prise)this.listeCoupsJoues.removeLast();
				this.set(p.getArrivee(), Piece.Vide);
				this.set(p.getDepart(), p.getPiece());
				this.set(p.getPrise(), p.getPiecePrise());
				if(this.listeCoupsJoues.getLast().getPiece().memeCouleur(p.getPiece())) {
					this.coupsEnCours=true;
				}
				else {
					this.coupsEnCours=false;
					this.update();
				}
				if(p.getPiece().blanc()) {
					this.tour=true;
				}
				else {
					this.tour=false;
				}
			}
			else {
				Deplacement d=this.listeCoupsJoues.removeLast();;
				this.set(d.getArrivee(),Piece.Vide);
				this.set(d.getDepart(), d.getPiece());
				this.tour=!tour;
				this.update();
			}
		}
	}
	
	public void revenir() {
		index=0;
		this.gagnant=Piece.Vide;
		if(!this.listeCoupsJoues.isEmpty()) {
			if(this.listeCoupsJoues.getLast().getClass()==Prise.class) {
				Prise p=(Prise)this.listeCoupsJoues.removeLast();
				this.set(p.getArrivee(), Piece.Vide);
				this.set(p.getDepart(), p.getPiece());
				this.set(p.getPrise(), p.getPiecePrise());
				while(!this.listeCoupsJoues.isEmpty() && this.listeCoupsJoues.getLast().getPiece().memeCouleur(p.getPiece())) {
					p=(Prise)this.listeCoupsJoues.removeLast();
					this.set(p.getArrivee(), Piece.Vide);
					this.set(p.getDepart(), p.getPiece());
					this.set(p.getPrise(), p.getPiecePrise());
				}
				if(!this.coupsEnCours) {
					this.tour=!tour;
				}
				this.coupsEnCours=false;
				this.update();
			}
			else {
				Deplacement d=this.listeCoupsJoues.removeLast();;
				this.set(d.getArrivee(),Piece.Vide);
				this.set(d.getDepart(), d.getPiece());
				this.tour=!tour;
				this.update();
			}
		}
	}
	
	
	public TreeCoups getCoupsPossible() {
		return coupsPossible;
	}
	public Coord[] pionDeplacable() {
		Coord[] tab=new Coord[this.coupsPossible.size()];
		int i=0;
		for(Coord c : this.coupsPossible.keySet()) {
			tab[i]=c;
			i++;
		}
		return tab;
	}

	public int score() {
		int i,j, cpt=0;
		if(this.gagnant.vide()) {
			for(i=0;i<10;i++) {
				for(j=(i+1)%2;j<10;j=j+2) {
					if(this.damier[j][i].noir()) {
						if(this.damier[j][i].dame()) {
							cpt=cpt-2;
						}
						cpt--;
					}
					else {
						if(this.damier[j][i].blanc()) {
							if(this.damier[j][i].dame()) {
								cpt=cpt+2;
							}
							cpt++;
						}
					}
				}
			}
		}
		else {
			if(this.gagnant.blanc()) {
				cpt=Integer.MAX_VALUE;
			}
			else {
				cpt=Integer.MIN_VALUE;
			}
		}
		return cpt;
	}
	public int score(boolean couleur) {
		int i,j, cptNoir=0, cptBlanc=0;
		if(this.gagnant.vide()) {
			for(i=0;i<10;i++) {
				for(j=(i+1)%2;j<10;j=j+2) {
					if(this.damier[j][i].noir()) {
						if(this.damier[j][i].dame()) {
							cptNoir=cptNoir+2;
						}
						cptNoir++;
					}
					else {
						if(this.damier[j][i].blanc()) {
							if(this.damier[j][i].dame()) {
								cptBlanc=cptBlanc+2;
							}
							cptBlanc++;
						}
					}
				}
			}
		}
		else {
			if(this.gagnant.blanc()) {
				if(couleur) {
					return 10000;
				}
				else {
					return -10000;
				}
			}
			else {
				if(!couleur) {
					return 10000;
				}
				else {
					return -10000;
				}
			}
		}
		if(couleur) {
			return cptBlanc-cptNoir;
		}
		else {
			return cptNoir-cptBlanc;
		}
	}
	public void affiche() {
		int i,j;
		for(i=0;i<10;i++) {
			for(j=0;j<10;j++) {
				switch(this.damier[j][i]){
				case DameBlanche:
					System.out.print(" DB");
					break;
				case DameNoire:
					System.out.print(" DN");
					break;
				case PionBlanc:
					System.out.print(" B ");
					break;
				case PionNoir:
					System.out.print(" N ");
					break;
				case Vide:
					System.out.print(" - ");
					break;
				default:
					break;
				}
			}
			System.out.println();
		}
		System.out.println("CoupsEnCours="+this.coupsEnCours);
		System.out.println("NomPartie="+this.nomPartie);
		System.out.println("tour="+this.tour);
		System.out.print("CoupCourant :");
		this.coupsCourant.affiche();
		System.out.println("\nCoupsPossible :");
		this.coupsPossible.affiche();
		System.out.println("\ngagnant="+this.gagnant);
		System.out.print("Coups Joués : ");
		for(Deplacement deplace : this.listeCoupsJoues) {
			System.out.print(deplace+"|");
		}
		//System.out.println("\n"+this.selection);
	}


	public Damier(Damier d) {
		this.damier = d.damier.clone();
		this.tour = d.tour;
		this.coupsPossible = d.coupsPossible.clone();
		this.coupsCourant = d.coupsCourant.clone();
		this.coupsEnCours = d.coupsEnCours;
		this.selection = d.selection;
		this.gagnant = Piece.Vide;
		this.nomPartie = d.nomPartie;
		this.listeCoupsJoues = (LinkedList<Deplacement>)d.listeCoupsJoues.clone();
	}


	public boolean isTour() {
		return tour;
	}
	
	public boolean isTour(Piece p) {
		return (tour && p.blanc()) || (!tour && p.noir());
	}
	
	
}
