package damier;

import java.util.TreeMap;

public class TreeCoups extends TreeMap<Coord, ListeCoups> {
	private int nbreCoups;
	
	public TreeCoups() {
		super();
		this.nbreCoups=1;
	}
	public void affiche() {
		for(Coord c : this.keySet()) {
			System.out.print("Coordonnée : "+c +" -> ");
			this.get(c).affiche();
		}
	}
	public ListeCoups put(Coord k, ListeCoups v) {
		if(this.nbreCoups < v.nbreDeCoups()) {
			this.clear();
			this.nbreCoups=v.nbreDeCoups();
			return super.put(k, v);
		}
		else {
			if(this.nbreCoups==v.nbreDeCoups()) {
				return super.put(k, v);
			}
			else {
				return null;
			}
		}
	}
	@Override
	public TreeCoups clone() {
		TreeCoups t= new TreeCoups();
		for(Coord k : this.keySet()) 
			t.put(k, this.get(k));
		return t;
	}
	
	
}
