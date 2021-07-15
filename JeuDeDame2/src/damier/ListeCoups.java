package damier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ListeCoups extends LinkedList<Coups>{
	public boolean add(Coups c) {
		if(this.isEmpty()) {
			return super.add(c);
		}
		else {
			if(this.get(0).size()<c.size()) {
				this.clear();
				return super.add(c);
			}
			else {
				if(this.get(0).size()==c.size()) {
					return super.add(c);
				}
				else {
					return false;
				}
			}
		}
	}
	public boolean addAll(Collection <? extends Coups> l) {
		boolean retour = false;
		if(l!=null) {
			for(Coups c : l) {
				retour=this.add(c);
			}
		}
		return retour;
	}
	
	public ListeCoups ajouter(Coord coord) {
		if(this.isEmpty()) {
			this.add(new Coups(coord));
		}
		else {
			for(Coups c : this) {
				c.ajouter(coord);
			}
		}
		return this;
	}
	public ListeCoups affiche() {
		System.out.print("|");
		for(Coups c : this) {
			c.affiche();
			System.out.print("|");
		}
		return this;
	}
	
	public int nbreDeCoups() {
		if(this.isEmpty()) {
			return 0;
		}
		else {
			return this.get(0).size();
		}
	}
	public boolean contient(Coord coord, int index) {
		int i=0;
		boolean contient=false;
		if(this.nbreDeCoups()>0) {
			while(i<this.size() && !contient) {
				contient=this.get(i).get(index).equals(coord);
				i++;
			}
		}
		return contient;
	}
	public void jouer(Coord coord) {
		int i=0;
		while(i<this.size()){
			if(this.get(i).getFirst().equals(coord)) {
				this.get(i).removeFirst();
			}
			else {
				this.remove(i);
				i--;
			}
			i++;
		}
	}
	@Override
	public Coups[] toArray() {
		// TODO Auto-generated method stub
		Coups tab[]=new Coups[this.size()];
		int i=0;
		for(Coups c : this) {
			tab[i]=c;
			i++;
		}
		return tab;
	}
	@Override
	public ListeCoups clone() {
		ListeCoups l=new ListeCoups();
		for(Coups c : this) {
			l.add(c);
		}
		return l;
	}
	public Coups alea() {
		if(this.isEmpty()) {
			return null;
		}
		else {
			return this.get((int)(this.size()*Math.random()));
		}
	}
	
}
