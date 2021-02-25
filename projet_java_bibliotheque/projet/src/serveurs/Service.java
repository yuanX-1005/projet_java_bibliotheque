package serveurs;

import java.util.List;

import appli.Abonne;
//import appli.Document;
import documents.Documents;

public abstract class Service implements Runnable{
	
	// ressource partage 
	
	protected static List<Documents> lesDocs;
	protected static List<Abonne> lesAbonnes;
	
	// obtenir les docs et les abonnées
	public static void setlesDocsEtAbonne(List<Documents> lesDocus, List<Abonne> lesAbonnes) {
		Service.lesDocs = lesDocus;
		Service.lesAbonnes = lesAbonnes;
	}
	
	// reccupère le doc et verifie l'existance du doc à partir du numero entré
	protected static Documents getDoc(int noDoc) {
		for(Documents d : lesDocs)
			if(d.numero() == noDoc)
				return d;
		return null;
	}
	
	// reccupère le doc et verifie l'existance de l'abonne à partir du numero entré
	protected static Abonne getAbonne(int noAbonne) {
		for(Abonne a : lesAbonnes)
			if(a.getNumero() == noAbonne)
				return a;
		return null;
	}
}
