package damier;

import java.util.LinkedList;

public class Coups extends LinkedList<Coord> {
	private int score;
	private int potentiel;
	protected Coups(Coord coord) {
		super();
		this.add(coord);
	}
	protected Coups(Coord coord, int s) {
		super();
		this.add(coord);
		this.score=s;
	}
	protected Coups(int s) {
		super();
		this.score=s;
		this.potentiel=s;
	}
	protected Coups(int s, int p) {
		super();
		this.score=s;
		this.potentiel=p;
	}
	protected Coups() {
		super();
	}
	protected Coups ajouter(Coord coord) {
		this.push(coord);
		return this;
	}
	
	public boolean contient(Coord coord) {
		boolean retour=false;
		int i=0;
		while(!retour && i<this.size()) {
			retour=this.get(i).equals(coord);
			i++;
		}
		return retour;
	}
	public void affiche() {
		for(Coord c : this) {
			System.out.print(c+"-");
		}
	}
	public int getX(int i) {
		return this.get(i).getX();
	}
	public int getY(int i) {
		return this.get(i).getY();
	}
	@Override
	public Coord[] toArray() {
		Coord tab[]=new Coord[this.size()];
		int i=0;
		for(Coord c : this) {
			tab[i]=c;
			i++;
		}
		return tab;
	}
	@Override
	public Coups clone() {
		Coups c = new Coups();
		for(Coord coord : this) {
			c.add(coord);
		}
		return c;
	}
	
	public int getScore() {
		return this.score;
	}
	public void setScore(int s) {
		this.score=s;
	}
	public int getPotentiel() {
		return potentiel;
	}
	public void setPotentiel(int potentiel) {
		this.potentiel = potentiel;
	}
	
	
}

