package interfaceGraphique;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import damier.*;

public class Plateau extends JPanel implements MouseListener,MouseMotionListener,ActionListener,ComponentListener{
	private int c, w0, h0;
	private ActionListener a;
	private Damier damier;
	private JTextArea l;
	private IA ordinateur;
	private boolean joueur;
	private MouseEvent click;
	private LinkedList<Coord> dernierCoups;
	public Plateau(ActionListener a) {
		super();
		this.a=a;
		this.ordinateur=null;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setLayout(null);
		this.setBackground(Color.DARK_GRAY);
		this.click=null;
		this.dernierCoups=new LinkedList<Coord>();
		l=new JTextArea();
		l.setEditable(false);
		l.setBackground(Color.DARK_GRAY);
		l.setLineWrap(true);
	}
	
	
	public void setContreOrdinateur(int niveau, boolean joueur) {
		this.joueur=joueur;
		this.ordinateur=new IA(niveau,!joueur);
		if(!joueur==damier.isTour()) {
			this.ordinateur.joue(damier);
		}
	}
	
	public void setJoueurContreJoueur() {
		this.ordinateur=null;
	}
	
	private void addButton() {
		JButton b = new JButton("Retour");
		JButton sauvegarder=new JButton("Sauvegarder La Partie");
		JButton revenir = new JButton("<");
		revenir.setFont(new Font("bouttonRevenir", Font.PLAIN, 30));
		l.setFont(new Font("Label", Font.PLAIN, 20));
		l.setForeground(Color.BLACK);
		b.addActionListener(a);
		b.setActionCommand("MENU");
		sauvegarder.addActionListener(a);
		sauvegarder.setActionCommand("SAUVEGARDER");
		revenir.addActionListener(this);
		int w=0,h=0;
		if(w0!=0) {
			w=(2*w0)/3;
			h=this.getHeight()/10;
			b.setBounds(w0/6, h, w, h);
			sauvegarder.setBounds(w0/6, 3*h, w, h);
			l.setBounds(w0+(c*21)/2,c*3,w0-c/2,c*5);
			revenir.setBounds(w0+c*10+w0/6,h,w,h);
		}
		else {
			System.out.println("w0=0");
			w=this.getWidth()/10;
			h=h0/2;
			b.setBounds(w, h/4, w, h);
			sauvegarder.setBounds(3*w, h/4, w, h);
			l.setBounds(c*3,h0+(c*21)/2,c*5,h0-c/2);
			revenir.setBounds(w,h0+c*10+h0/6,w,h);
		}
		this.add(b);
		this.add(sauvegarder);
		this.add(revenir);
		this.add(l);
	}
	public void affichageCoups() {
		String text=this.getDamier().getNomPartie()+"("+this.getDamier().score()+") : ";
		for(Deplacement d : this.damier.getListeCoupsJoues()) {
			text=text+d.toString()+" | ";
		}
		l.setText(text);
	}
	public void traceCase(int x, int y, Graphics g) {
		Color joliVert=new Color(70,220,70);
		if((x+y)%2==0) {
			g.setColor(Color.white);
			g.fillRect(w0+x*c, h0+y*c, c, c);
		}
		else {
			switch(damier.get(x, y)) {
			case PionBlanc:
				if(this.dernierCoups.contains(new Coord(x,y))) {
					g.setColor(joliVert);
				}
				else {
					g.setColor(Color.black);
				}
				g.fillRect(w0+x*c, h0+y*c, c, c);
				tracePion(x,y,g,true);
				break;
			case PionNoir:
				if(this.dernierCoups.contains(new Coord(x,y))) {
					g.setColor(joliVert);
				}
				else {
					g.setColor(Color.black);
				}
				g.fillRect(w0+x*c, h0+y*c, c, c);
				tracePion(x,y,g,false);
				break;
			case DameBlanche:
				if(this.dernierCoups.contains(new Coord(x,y))) {
					g.setColor(joliVert);
				}
				else {
					g.setColor(Color.black);
				}
				g.fillRect(w0+x*c, h0+y*c, c, c);
				this.traceDame(x, y, g, true);
				break;
			case DameNoire:
				if(this.dernierCoups.contains(new Coord(x,y))) {
					g.setColor(joliVert);
				}
				else {
					g.setColor(Color.black);
				}
				g.fillRect(w0+x*c, h0+y*c, c, c);
				this.traceDame(x, y, g, false);
				break;
			case Vide:
				if(this.dernierCoups.contains(new Coord(x,y))) {
					g.setColor(new Color(50,100,50));
				}
				else {
					g.setColor(Color.black);
				}
				g.fillRect(w0+x*c, h0+y*c, c, c);
				break;
			}
		}
	}
	
	public void traceMouv(int x, int y, Graphics g) {
		g.setColor(new Color(40,200,40));
		switch(damier.get(x, y)) {
		case PionBlanc:
			g.fillRect(w0+x*c, h0+y*c, c, c);
			tracePion(x,y,g,true);
			break;
		case PionNoir:
			g.fillRect(w0+x*c, h0+y*c, c, c);
			tracePion(x,y,g,false);
			break;
		case DameBlanche:
			g.fillRect(w0+x*c, h0+y*c, c, c);
			this.traceDame(x, y, g, true);
			break;
		case DameNoire:
			g.fillRect(w0+x*c, h0+y*c, c, c);
			this.traceDame(x, y, g, false);
			break;
		case Vide:
			g.setColor(new Color(40,80,40));
			g.fillRect(w0+x*c, h0+y*c, c, c);
			break;
		}
	}
	public void tracePion(int x,int y,Graphics g, boolean blanc) {
		Color couleur2;
		if(blanc) {
			g.setColor(Color.LIGHT_GRAY);
			couleur2=Color.BLACK;
		}
		else {
			g.setColor(Color.BLACK);
			couleur2=Color.LIGHT_GRAY;
		}
		int taille=c/10;
		g.fillOval(w0+x*c+taille, h0+y*c+taille, c-2*taille, c-2*taille);
		g.setColor(couleur2);
		for(int i=1; i<5;i++) {
			g.drawOval(w0+x*c+i*taille, h0+y*c+i*taille, c-2*i*taille, c-2*i*taille);
			g.drawOval(w0+x*c+i*taille+1, h0+y*c+i*taille+1, c-2*i*taille-2, c-2*i*taille-2);
		}
	}
	public void updateArea(Point p,Graphics g) {
		int i,j;
		for(i=((p.x-w0)/c)-1;i<=((p.x-w0)/c)+1;i++) {
			if(i<10 && i>=0) {
				for(j=((p.y-h0)/c)-1;j<=((p.y-h0)/c)+1;j++) {
					if(j<10 && j>=0) {
						this.traceCase(i, j, g);
					}
				}
			}
		}
	}
	
	public void traceDame(int x,int y,Graphics g, boolean blanc) {
		if(blanc) {
			g.setColor(Color.LIGHT_GRAY);
		}
		else {
			g.setColor(Color.DARK_GRAY);
		}
		this.tracePion(x, y, g, blanc);
		this.couronne(x, y, g);
	}
	public void couronne(int x, int y, Graphics g) {
		g.setColor(new Color(175,169,26));
		Polygon p = new Polygon();
		int div=3, div6=div*6;
		int x0=w0+x*c+c/div,y0=h0+y*c+c/div;
		p.addPoint(x0, y0);
		p.addPoint(x0+2*c/div6, y0+3*c/div6);
		p.addPoint(x0+3*c/div6, y0);
		p.addPoint(x0+4*c/div6, y0+3*c/div6);
		p.addPoint(x0+6*c/div6, y0);
		p.addPoint(x0+c/div, y0+c/div);
		p.addPoint(x0, y0+c/div);
		g.fillPolygon(p);
		g.setColor(Color.BLACK);
		g.drawPolygon(p);
	}
	public void couronne(Point point, Graphics g) {
		g.setColor(new Color(175,169,26));
		Polygon p = new Polygon();
		int div=3, div6=div*6;
		int x0=point.x-c/(2*div),y0=point.y-c/(2*div);
		p.addPoint(x0, y0);
		p.addPoint(x0+2*c/div6, y0+3*c/div6);
		p.addPoint(x0+3*c/div6, y0);
		p.addPoint(x0+4*c/div6, y0+3*c/div6);
		p.addPoint(x0+6*c/div6, y0);
		p.addPoint(x0+c/div, y0+c/div);
		p.addPoint(x0, y0+c/div);
		g.fillPolygon(p);
		g.setColor(Color.BLACK);
		g.drawPolygon(p);
	}

	public void traceSelection(int x, int y, Graphics g) {
		g.setColor(Color.red);
		for(int i=0; i<5;i++) {
			g.drawRect(w0+x*c+i, h0+y*c+i, c-1-2*i, c-1-2*i);
		}
	}
	
	public void tracePossible(int x, int y, Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(w0+x*c+c/3, h0+y*c+c/3, c/3, c/3);
	}
	public void tracePlateau(Graphics g) {
		int i,j;
		for(i=0;i<10;i++) {
			for(j=0;j<10;j++) {
				this.traceCase(i, j, g);
			}
		}
		
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle r=g.getClipBounds();
		int height=this.getHeight(),width=this.getWidth();
		if(height>width) {
			c=width/10;
			w0=0;
			h0=(height-width)/2;
		}
		else {
			c=height/10;
			w0=(width-height)/2;
			h0=0;
		}
		this.removeAll();
		this.addButton();
		this.tracePlateau(g);
	}
	
	public Damier getDamier() {
		return damier;
	}

	public void setDamier(Damier damier) {
		this.damier = damier;
	}

	public void update() {
	}

	public void updateDernierCoup() {
		Iterator<Deplacement> iterateur;
		Deplacement deplacement;
		this.dernierCoups.clear();
		if(!this.damier.getListeCoupsJoues().isEmpty()) {//faire apparaitre les dernies coups
			iterateur=this.damier.getListeCoupsJoues().descendingIterator();
			deplacement=iterateur.next();
			while(this.damier.isTour(deplacement.getPiece()) && iterateur.hasNext()) {
				deplacement=iterateur.next();
			}
			if(iterateur.hasNext()) {
				do {
					this.dernierCoups.add(deplacement.getArrivee());
					this.dernierCoups.add(deplacement.getDepart());
					deplacement=iterateur.next();
				}while(!this.damier.isTour(deplacement.getPiece()) && iterateur.hasNext());
			}
			else {
				if(!this.damier.isTour(deplacement.getPiece())) {
					this.dernierCoups.add(deplacement.getArrivee());
					this.dernierCoups.add(deplacement.getDepart());
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int x,y;
		Coord coord;
		Coups coupsPossible;
		x=(e.getPoint().x-w0)/c;
		y=(e.getPoint().y-h0)/c;
		Graphics g=this.getGraphics();
		if((this.ordinateur==null || this.joueur==this.damier.isTour()) && x>=0 && x<10 && y>=0 && y<10) {
			coord=new Coord(x,y);
			//System.out.println("Coord : "+coord);
			coupsPossible=this.damier.joue(coord); 
			if(coupsPossible!=null) {
				//System.out.println("Bliip");
				this.updateDernierCoup();
				this.tracePlateau(g);
				if(!coupsPossible.isEmpty()) {
					this.traceSelection(x, y, g);
					for(Coord c : coupsPossible) {
						this.tracePossible(c.getX(), c.getY(), g);
					}
				}
			}
		}
		this.affichageCoups();
		if(this.ordinateur!=null && this.joueur!=this.damier.isTour() && damier.gagnant().vide()) {
			this.ordinateur.joue(damier);
			this.updateDernierCoup();
			this.tracePlateau(g);
			this.affichageCoups();
		}

		if(damier.gagnant()!=Piece.Vide) {
			String options[]= {"Oui","Non"};
			if(0==JOptionPane.showOptionDialog(this,"Les "+damier.gagnant()+" ont gagné!!! Voulez vous retourner au Menu?", "Victoire!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, 0)) {
				this.a.actionPerformed(new ActionEvent(this,0,"MENU"));
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//this.click=new Coord((e.getX()-w0)/c,(e.getY()-h0)/c);
		this.click=e;
	}
	public static double positif(double n) {
		if(n<0) {
			return -n;
		}
		else {
			return n;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		/*if(this.click.equals(new Coord((e.getX()-w0)/c,(e.getY()-h0)/c))) {
			this.mouseClicked(e);
		}*/
		int gap=c/2;
		int x,y;
		Coord coord;
		Coups coupsPossible;
		Graphics g=this.getGraphics();
		this.updateArea(e.getPoint(), this.getGraphics());
		if(positif(e.getX()-this.click.getX())>gap||positif(e.getY()-this.click.getY())>gap) {
			x=(this.click.getX()-w0)/c;
			y=(this.click.getY()-h0)/c;
			if((this.ordinateur==null || this.joueur==this.damier.isTour()) && x>=0 && x<10 && y>=0 && y<10) {
				coord=new Coord(x,y);
				//System.out.println("Coord : "+coord);
				coupsPossible=this.damier.joue(coord); 
				if(coupsPossible!=null) {
					//System.out.println("Bliip");
					this.updateDernierCoup();
					this.tracePlateau(g);
					if(!coupsPossible.isEmpty()) {
						this.traceSelection(x, y, g);
						for(Coord c : coupsPossible) {
							this.tracePossible(c.getX(), c.getY(), g);
						}
					}
				}
			}
			/*this.mouseClicked(this.click);
			this.mouseClicked(e);*/
		}
		else {
			this.mouseClicked(e);
		}
		this.click=null;
	}
	public void traceDrag(Point p,Graphics g, Piece piece) {
		Color couleur2;
		int i;
		int taille=c/10;
		if(!piece.vide() && p.x>w0+(c-2*taille)/2 && p.y>h0+(c-2*taille)/2 && p.x<w0+10*c-(c-2*taille)/2 && p.y<h0+10*c-(c-2*taille)/2) {
			this.updateArea(p, g);
			if(piece.blanc()) {
				g.setColor(Color.LIGHT_GRAY);
				couleur2=Color.BLACK;
			}
			else {
				g.setColor(Color.BLACK);
				couleur2=Color.LIGHT_GRAY;
			}
			g.fillOval(p.x-(c-2*taille)/2, p.y-(c-2*taille)/2, c-2*taille, c-2*taille);
			g.setColor(couleur2);
			for(i=1; i<5;i++) {
				g.drawOval(p.x-(c-2*i*taille)/2, p.y-(c-2*i*taille)/2, c-2*i*taille, c-2*i*taille);
				g.drawOval(p.x-(c-2*(i*taille+1))/2, p.y-(c-2*(i*taille+1))/2, c-2*i*taille-2, c-2*i*taille-2);
			}
			if(piece.dame()) {
				this.couronne(p, g);
			}
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		int x,y;
		x=(this.click.getX()-w0)/c;
		y=(this.click.getY()-h0)/c;
		this.traceDrag(e.getPoint(), this.getGraphics(), this.damier.get(x, y));
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(this.damier.isTour()==this.joueur) {
			this.damier.revenir();
		}
		this.damier.revenir();
		this.updateDernierCoup();
		this.tracePlateau(this.getGraphics());
		this.affichageCoups();
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		this.tracePlateau(this.getGraphics());
	}

}
