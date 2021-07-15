package damier;

public class Deplacement {
	private Coord depart;
	private Coord arrivee;
	private Piece piece;
	public Deplacement(Coord d, Coord a, Piece p) {
		this.depart=d;
		this.arrivee=a;
		this.piece=p;
	}
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	public Coord getDepart() {
		return depart;
	}
	public void setDepart(Coord depart) {
		this.depart = depart;
	}
	public Coord getArrivee() {
		return arrivee;
	}
	public void setArrivee(Coord arrivee) {
		this.arrivee = arrivee;
	}
	public String toString() {
		return this.depart.manouri()+"-"+this.arrivee.manouri();
	}
}
