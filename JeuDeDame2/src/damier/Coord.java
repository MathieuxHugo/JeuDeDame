package damier;

public class Coord implements Comparable<Object>{
	private int x;
	private int y;
	public Coord(int x, int y) {
		this.x=x;
		this.y=y;
	}
	public Coord(String c) {
		String[] s=c.split(",");
		this.x=s[0].charAt(2)-'0';
		this.y=s[1].charAt(2)-'0';
	}
	public Coord(Coord c) {
		this.x=c.getX();
		this.y=c.getY();
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public String toString() {
		return "x="+x+",y="+y;
	}
	
	public boolean test(Direction direction) {
		switch(direction) {
			case NO:
				return this.x>0 && this.y>0;
			case NE:
				return this.x<9 && this.y>0;
			case SO:
				return this.x>0 && this.y<9;
			case SE:
				return this.x<9 && this.y<9;
			default:
				return false;
		}
	}
	
	public boolean testPrise(Direction direction) {
		switch(direction) {
		case NO:
			return this.NO().test(direction);
		case NE:
			return this.NE().test(direction);
		case SO:
			return this.SO().test(direction);
		case SE:
			return this.SE().test(direction);
		default:
			return false;
		}
	}
	public Coord direction(Direction direction) {
		switch(direction) {
		case NO:
			return this.NO();
		case NE:
			return this.NE();
		case SO:
			return this.SO();
		case SE:
			return this.SE();
		default:
			return null;
		}
	}
	public Direction vers(Coord vers) {
		if(this.y>vers.getY()) {
			if(this.x>vers.getX()) {
				return Direction.NO;
			}
			else {
				return Direction.NE;
			}
		}
		else {
			if(this.x>vers.getX()) {
				return Direction.SO;
			}
			else {
				return Direction.SE;
			}
		}
	}
	public Coord prise(Direction direction) {
		return this.direction(direction).direction(direction);
	}
	public boolean equals(Coord c) {
		return this.x==c.getX() && this.y==c.getY();
	}
	public Coord NO() {
		return new Coord(this.x-1,this.y-1);
	}
	public Coord SO() {
		return new Coord(this.x-1,this.y+1);
	}
	public Coord NE() {
		return new Coord(this.x+1,this.y-1);
	}
	public Coord SE() {
		return new Coord(this.x+1,this.y+1);
	}
	public boolean test() {
		return this.x>=0 && this.x<=9 && this.y>=0 && this.y<=9;
	}
	@Override
	public int compareTo(Object arg0) {
		Coord c = (Coord)arg0;
		return this.x+this.y*10-(c.getX()+c.getY()*10);
	}
	
	@Override
	public boolean equals(Object obj) {
		Coord c=(Coord)obj;
		return this.x==c.getX() && this.y==c.getY();
	}
	public int manouri() {
		return (this.x/2)+1+this.y*5;
	}
	public boolean dansPlateau() {
		return x>=0 && x<10 && y>=0 && y<10;
	}
}
