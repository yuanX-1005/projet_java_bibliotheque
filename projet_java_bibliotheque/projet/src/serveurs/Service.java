package serveurs;

import java.util.List;

import appli.Abonne;
//import appli.Document;
import documents.Documents;

public abstract class Service implements Runnable{
	
	// ressource partage 
	
	protected static List<Documents> lesDocs;
	protected static List<Abonne> lesAbonnes;
	
	// obtenir les docs et les abonn�es
	public static void setlesDocsEtAbonne(List<Documents> lesDocus, List<Abonne> lesAbonnes) {
		Service.lesDocs = lesDocus;
		Service.lesAbonnes = lesAbonnes;
	}
	
	// reccup�re le doc et verifie l'existance du doc � partir du numero entr�
	protected static Documents getDoc(int noDoc) {
		for(Documents d : lesDocs)
			if(d.numero() == noDoc)
				return d;
		return null;
	}
	
	// reccup�re le doc et verifie l'existance de l'abonne � partir du numero entr�
	protected static Abonne getAbonne(int noAbonne) {
		for(Abonne a : lesAbonnes)
			if(a.getNumero() == noAbonne)
				return a;
		return null;
	}
}
