package damier;

public enum Piece {
	PionBlanc,
	DameBlanche,
	PionNoir,
	DameNoire,
	Vide;
	public boolean blanc() {
		return this==PionBlanc || this==DameBlanche;
	}
	public boolean noir() {
		return this==PionNoir || this==DameNoire;
	}
	public boolean dame() {
		return this==DameNoire || this==DameBlanche;
	}
	public boolean pion() {
		return this==PionNoir || this==PionBlanc;
	}
	public boolean vide() {
		return this==Vide;
	}
	public boolean opposant(boolean tour) {
		if(tour) {
			return this.noir();
		}
		else {
			return this.blanc();
		}
	}
	public boolean tour(boolean tour) {
		if(tour) {
			return this.blanc();
		}
		else {
			return this.noir();
		}
	}
	public boolean memeCouleur(Piece p) {
		if(this.blanc()) {
			return p.blanc();
		}
		else {
			if(this.noir()) {
				return p.noir();
			}
			else {
				return p.vide();
			}
		}
	}
	
	public String toString() {
		if(this.vide()) {
			return "Vide";
		}
		else {
			if(this.noir()) {
				return "Noirs";
			}
			else {
				return "Blancs";
			}
		}
	}
	
}
