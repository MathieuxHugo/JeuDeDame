package entreeSortie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Scanner;

import damier.Coord;
import damier.Damier;
import damier.Deplacement;

public class Sauvegarde {
	private String nomDossier;
	private File parent;
	private LinkedList<String> listeNomFichier;
	public Sauvegarde(String nom) throws ErreurCreation{
		parent=new File("Sauvegarde");
		listeNomFichier=new LinkedList<String>();
		if(parent.exists()) {
			for(String n : parent.list()) {
				this.listeNomFichier.add(n);
			}
		}
		else {
			if(!parent.mkdir()) {
				throw new ErreurCreation();
			}
		}
		this.nomDossier=nom;
	}
	public void sauvegarder(Damier d) throws IOException {
		File f =new File(parent,d.getNomPartie());
		f.createNewFile();
		if(!this.listeNomFichier.contains(f.getName())) {
			this.listeNomFichier.add(f.getName());
		}
		PrintWriter w=new PrintWriter(f);
		for(Deplacement deplacement : d.getListeCoupsJoues()) {
			w.print(deplacement.getDepart()+"\n");
			w.print(deplacement.getArrivee()+"\n");
		}
		w.close();
	}
	
	public Damier charger(String n) throws FileNotFoundException {
		File f=new File(this.parent,n);
		Damier d=new Damier(n);
		Coord temp;
		if(f.exists() && f.canRead()) {
			Scanner scanner=new Scanner(f);
			while(scanner.hasNextLine()) {
				try {
					temp=new Coord(scanner.nextLine());
					d.joue(temp);
				}
				catch(java.lang.StringIndexOutOfBoundsException e){
					scanner.close();
					return null;
				}
			}
			scanner.close();
		}
		return d;
	}
	public LinkedList<String> getListeNomFichier() {
		return listeNomFichier;
	}
	public boolean delete(String n) {
		File f=new File(this.parent,n);
		return f.delete();
	}
}
