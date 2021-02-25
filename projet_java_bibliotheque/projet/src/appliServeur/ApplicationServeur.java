package appli;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import documents.DVDs;
import documents.Documents;
import serveurs.Serveur;
import serveurs.Service;

public class ApplicationServeur {
	
	private static final int PORTRES = 3000; // port pour une reservation 
	private static final int PORTEMP = 4000; // port pour un emprunt
	private static final int PORTRET = 5000; // port pour un retour
	private static int[] ports = {PORTRES,PORTEMP,PORTRET};
		
	public static void main(String[] args) {
		List<Documents> lesDocus = initDocs(); // initialisation des documents
		List<Abonne> lesAbonnes = initAbonnes(); // initialisation des abonnées
		Service.setlesDocsEtAbonne(lesDocus, lesAbonnes); 
		
		try {
			for(int port : ports) {
				new Thread(new Serveur(port)).start(); //lance les 3 serveurs
			}
		}
		catch(IOException e) {
			System.err.println("Un probleme est survenu lors de la création du serveur : " + e);
		}
	}

	// Ajout de documents
	private static List<Documents> initDocs() {
		List<Documents> docs = new ArrayList<Documents>();
		docs.add(new DVDs(9,"Harry Potter",true));
		docs.add(new DVDs(8,"Cendrillon",false));
		docs.add(new DVDs(7,"Train pour Busan",true));
		return docs;
	}
	
	// Ajout d'abonnées
	private static List<Abonne> initAbonnes(){
		List<Abonne> abonnes = new ArrayList<Abonne>();
		Calendar date = Calendar.getInstance();
		date.set(2001,6,8);
		Date dateNaissance = date.getTime();
		abonnes.add(new Abonne(1,"Anne",dateNaissance));
		date.set(2010,10,2);
		dateNaissance = date.getTime();
		abonnes.add(new Abonne(2,"Mona",dateNaissance));
		date.set(2000,3,25);
		dateNaissance = date.getTime();
		abonnes.add(new Abonne(3,"Lucas",dateNaissance));
		return abonnes;
	}
	
}
