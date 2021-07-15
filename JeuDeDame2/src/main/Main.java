package main;


import java.io.FileNotFoundException;
import java.io.IOException;

import damier.Coord;
import damier.Coups;
import damier.Damier;
import damier.IA;
import damier.Piece;
import entreeSortie.ErreurCreation;
import entreeSortie.Sauvegarde;
import interfaceGraphique.Fenetre;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fenetre f= new Fenetre();
		//f.montreToi();
		/*Damier d=new Damier("prout");
		IA ordinateur1=new IA(4,true);
		IA ordinateur2=new IA(3,false);
		int cpt=0;
		long t;
		t=System.currentTimeMillis();
		d.affiche();
		//System.out.println("\n voilà"+ordinateur1.meilleurCoup(d, true, 0));
		while(cpt<250 && d.gagnant().vide()) {
			System.out.println("cpt="+cpt);
			//d.affiche();
			ordinateur1.joue(d);
			ordinateur2.joue(d);
			d.affiche();
			cpt++;
		}
		System.out.println("\nLes "+d.gagnant()+" ont gagné!!!");*/
		
	}

}
