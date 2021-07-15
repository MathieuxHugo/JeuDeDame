package damier;

public class IA {
	private int profondeur;
	private boolean color;
	public IA(int niveau) {
		this.profondeur=2*niveau;
		this.color=true;
	}
	public IA(int niveau,boolean color) {
		this.profondeur=2*niveau;
		this.color=color;
	}
	
	public void joueFonctionnel(Damier d) {
		if(this.profondeur>0) {
			for(Coord c : this.meilleurCoup(d, true, 0)) {
				System.out.println("je joue : "+c);
				d.joue(c);
			}
		}
		else {
			if(d.gagnant().vide()) {
				Coord tab[]=d.pionDeplacable();
				int i =(int)Math.random()*tab.length;
				d.joue(tab[i]);
				Coups coup=d.getCoupsPossible().get(tab[i]).alea();
				for(Coord c : coup) {
					d.joue(c);
				}
			}
		}
	}
	
	public void joue2(Damier d) {
		if(this.profondeur>0) {
			for(Coord c : this.meilleurCoup2(d, color, 0)) {
				d.joue(c);
			}
		}
		else {
			if(d.gagnant().vide()) {
				Coord tab[]=d.pionDeplacable();
				int i =(int)Math.random()*tab.length;
				d.joue(tab[i]);
				Coups coup=d.getCoupsPossible().get(tab[i]).alea();
				for(Coord c : coup) {
					d.joue(c);
				}
			}
		}
	}
	
	public void joue(Damier d) {
		if(this.profondeur>0) {
			for(Coord c : this.meilleurCoupPotenitiel(d, true, 0)) {
				d.joue(c);
			}
		}
		else {
			if(d.gagnant().vide()) {
				Coord tab[]=d.pionDeplacable();
				int i =(int)Math.random()*tab.length;
				d.joue(tab[i]);
				Coups coup=d.getCoupsPossible().get(tab[i]).alea();
				for(Coord c : coup) {
					d.joue(c);
				}
			}
		}
	}
	
	
	
	public Coups meilleurCoup(Damier d, boolean couleur, int index) {
		Coups retour, temp;
		Coups coupsPossible[];
		if(index<this.profondeur && d.gagnant().vide()) {
			if(couleur) {
				retour = new Coups(Integer.MIN_VALUE);
				for(Coord c : d.pionDeplacable()) {
					coupsPossible=d.getCoupsPossible().get(c).toArray();
					for(Coups coup : coupsPossible) {
						temp=new Coups(c);
						d.joue(c);
						for(Coord coord : coup.toArray()) {
							temp.add(coord);
							d.joue(coord);
						}
						temp.setScore(this.meilleurCoup(d, !couleur, index+1).getScore());
						if(retour.getScore()<temp.getScore()) {
							retour=temp;
						}
						d.revenir();
					}
				}
			}
			else {
				retour = new Coups(Integer.MAX_VALUE);
				for(Coord c : d.pionDeplacable()) {
					coupsPossible=d.getCoupsPossible().get(c).toArray();
					for(Coups coup : coupsPossible) {
						temp=new Coups(c);
						d.joue(c);
						for(Coord coord : coup.toArray()) {
							temp.add(coord);
							d.joue(coord);
						}
						temp.setScore(this.meilleurCoup(d, !couleur, index+1).getScore());
						if(retour.getScore()>temp.getScore()) {
							retour=temp;
						}
						d.revenir();
					}
				}
			}
			
			
		}
		else {
			//System.out.println("profondeur atteinte");
			retour=new Coups(d.score(color));
			/*if(d.gagnant().vide()) {
				retour=new Coups(d.score(couleur));
			}
			else {
				if(d.gagnant().blanc()) {
					retour=new Coups(d.score()-index);
				}
				else {
					retour=new Coups(d.score()+index);
				}
			}*/
		}
		return retour;
	}
	public Coups meilleurCoupPotenitiel(Damier d, boolean couleur, int index) {
		Coups retour, temp;
		Coups coupsPossible[];
		int potentiel=Integer.MIN_VALUE;
		if(index<this.profondeur && d.gagnant().vide()) {
			if(couleur) {
				retour = new Coups(Integer.MIN_VALUE);
				for(Coord c : d.pionDeplacable()) {
					coupsPossible=d.getCoupsPossible().get(c).toArray();
					for(Coups coup : coupsPossible) {
						temp=new Coups(c);
						d.joue(c);
						for(Coord coord : coup.toArray()) {
							temp.add(coord);
							d.joue(coord);
						}
						temp.setScore(this.meilleurCoup(d, !couleur, index+1).getScore());
						if(retour.getScore()<=temp.getScore()) {
							if(retour.getScore()<temp.getScore() || temp.getPotentiel()>retour.getPotentiel()) {
								retour=temp;
							}
						}
						d.revenir();
					}
				}
			}
			else {
				retour = new Coups(Integer.MAX_VALUE);
				for(Coord c : d.pionDeplacable()) {
					coupsPossible=d.getCoupsPossible().get(c).toArray();
					for(Coups coup : coupsPossible) {
						temp=new Coups(c);
						d.joue(c);
						for(Coord coord : coup.toArray()) {
							temp.add(coord);
							d.joue(coord);
						}
						temp.setScore(this.meilleurCoup(d, !couleur, index+1).getScore());
						if(retour.getScore()>temp.getScore()) {
							retour=temp;
						}
						if(potentiel<temp.getScore()) {
							potentiel=temp.getScore();
						}
						d.revenir();
					}
				}
				retour.setPotentiel(potentiel);
			}
			
			
		}
		else {
			//System.out.println("profondeur atteinte");
			retour=new Coups(d.score(color));
			/*if(d.gagnant().vide()) {
				retour=new Coups(d.score(couleur));
			}
			else {
				if(d.gagnant().blanc()) {
					retour=new Coups(d.score()-index);
				}
				else {
					retour=new Coups(d.score()+index);
				}
			}*/
		}
		return retour;
	}
	public Coups meilleurCoup2(Damier d, boolean couleur, int index) {
		Coups retour, temp;
		Coups coupsPossible[];
		if(index<this.profondeur && d.gagnant().vide()) {
			retour = new Coups(Integer.MIN_VALUE);
			for(Coord c : d.pionDeplacable()) {
				coupsPossible=d.getCoupsPossible().get(c).toArray();
				for(Coups coup : coupsPossible) {
					temp=new Coups(c);
					d.joue(c);
					for(Coord coord : coup.toArray()) {
						temp.add(coord);
						d.joue(coord);
					}
					temp.setScore(this.meilleurCoup(d, couleur, index+1).getScore());
					if(retour.getScore()<temp.getScore()) {
						retour=temp;
					}
					d.revenir();
				}
			}
			
		}
		else {
			//System.out.println("profondeur atteinte");
			retour=new Coups(d.score(couleur));
			/*if(d.gagnant().vide()) {
				retour=new Coups(d.score(couleur));
			}
			else {
				if(d.gagnant().blanc()) {
					retour=new Coups(d.score()-index);
				}
				else {
					retour=new Coups(d.score()+index);
				}
			}*/
		}
		return retour;
	}
}

	/*
	public Coups meilleurCoup(Damier d, boolean couleur) {
		Coups retour = null;
		Coups temp;
		int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
		if(couleur) {
			for(Coord c : d.pionDeplacable()) {
				temp=parcoursBlanc(d,c,0);
				if(temp.getScore()>max) {
					retour=temp;
					max=temp.getScore();
				}
			}
		}
		else {
			for(Coord c : d.pionDeplacable()) {
				temp=parcoursBlanc(d,c,0);
				if(temp.getScore()<min) {
					retour=temp;
					max=temp.getScore();
				}
			}
		}
		return retour;
	}
	
	private Coups parcoursBlanc(Damier d, Coord c,int index) {
		Coups listeCoups=d.joue(c);
		Coups retour=null;
		Coups temp;
		int max=Integer.MIN_VALUE;
		if(listeCoups.isEmpty()) {
			retour=new Coups(c,meilleurNoir(d,index));
		}
		else {
			for(Coord coord : listeCoups) {
				temp=this.parcoursBlanc(d, coord,index).ajouter(c);
				if(temp.getScore()>max) {
					retour=temp;
					max=retour.getScore();
				}
			}
		}
		d.revenirDun();
		return retour;
	}
	
	private Coups parcoursNoir(Damier d, Coord c, int index) {
		Coups listeCoups=d.joue(c);
		Coups retour=null;
		Coups temp;
		int min=Integer.MAX_VALUE;
		if(listeCoups.isEmpty()) {
			retour=new Coups(c,meilleurBlanc(d,index));
		}
		else {
			for(Coord coord : listeCoups) {
				temp=this.parcoursNoir(d, coord,index).ajouter(c);
				if(temp.getScore()<min) {
					retour=temp;
					min=retour.getScore();
				}
			}
		}
		d.revenirDun();
		return retour;
	}
	
	private int meilleurBlanc(Damier d,int index) {
		int retour=Integer.MIN_VALUE,temp;
		index=index+1;//Augementation de la profondeur de 1
		if(d.gagnant().vide()) {
			if(index==this.profondeur) {
				retour = d.score();
			}
			else {
				for(Coord c : d.pionDeplacable()) {
					temp=this.parcoursBlanc(d, c,index).getScore();
					if(temp>retour) {
						retour=temp;
					}
				}
			}
		}
		else {
			if(d.gagnant().noir()) {
				retour=Integer.MIN_VALUE+index;
			}
			else {
				retour=Integer.MAX_VALUE-index;
			}
		}
		return retour;
	}
}
	/*
	private int meilleurNoir(Damier d,int index) {
		int retour=Integer.MAX_VALUE,temp;
		index=index+1;//Augementation de la profondeur de 1
		if(d.gagnant().vide()) {
			if(index==this.profondeur) {
				retour = d.score();
			}
			else {
				for(Coord c : d.pionDeplacable()) {
					temp=this.parcoursNoir(d, c,index).getScore();
					if(temp<retour) {
						retour=temp;
					}
				}
			}
		}
		else {
			if(d.gagnant().noir()) {//je rajoute ou j'enleve index pour faire remonter le resultat qui arrive à la profondeur la plus basse
				retour=Integer.MIN_VALUE+index;
			}
			else {
				retour=Integer.MAX_VALUE-index;
			}
		}
		return retour;
	}*/



