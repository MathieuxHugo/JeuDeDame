package damier;

public class Prise extends Deplacement {
	private Coord prise;
	private Piece piecePrise;
	public Prise(Coord d, Piece pd, Coord p,Piece pp, Coord a) {
		super(d,a,pd);
		this.prise=p;
		this.piecePrise=pp;
	}
	public Coord getPrise() {
		return prise;
	}
	public void setPrise(Coord prise) {
		this.prise = prise;
	}
	public Piece getPiecePrise() {
		return piecePrise;
	}
	public void setPiecePrise(Piece piecePrise) {
		this.piecePrise = piecePrise;
	}
	public String toString() {
		return this.getDepart().manouri()+"x"+this.getArrivee().manouri();
	}
}
