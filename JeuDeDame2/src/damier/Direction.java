package damier;

public enum Direction {
	NO,NE,SO,SE;
	
	public Direction oppose() {
		switch(this) {
			case NO:
				return SE;
			case NE:
				return SO;
			case SE:
				return NO;
			case SO:
				return NE;
			default :
				return null;
		}
	}
	
	public Direction[] sauf() {
		Direction tab[];
		switch(this) {
			case NO:
				tab=new Direction[3];
				tab[0]=NE;
				tab[1]=SO;
				tab[2]=SE;
				break;
			case NE:
				tab=new Direction[3];
				tab[0]=NO;
				tab[1]=SO;
				tab[2]=SE;
				break;
			case SE:
				tab=new Direction[3];
				tab[0]=NO;
				tab[1]=NE;
				tab[2]=SO;
				break;
			case SO:
				tab=new Direction[3];
				tab[0]=NO;
				tab[1]=NE;
				tab[2]=SE;
				break;
			default :
				tab=new Direction[4];
				tab[0]=NO;
				tab[1]=NE;
				tab[2]=SO;
				tab[3]=SE;
				break;
		}
		return tab;
	}
}
