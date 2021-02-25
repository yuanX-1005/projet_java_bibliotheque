package serveurs;

import java.io.*;
import java.net.*;

import appli.Abonne;
import documents.Documents;
import exception.EmpruntException;

public class ServiceEmprunt extends Service implements Runnable{

	private final Socket client;
	
	public ServiceEmprunt(Socket sock) {
		this.client = sock;
	}
	
	@Override
	public void run() {
		String reponse = null;
		try {
			System.err.println("emprunt de document");
	        // Creation de streams pour lire et ecrire du texte dans cette socket
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			// envoie le catalogue
			out.println("Le catalogue comprend les documents suivants: "); 
			System.out.println("<-- Le catalogue : ");
			out.println(lesDocs.size());
			
			for(Documents d : lesDocs) {
				out.println("num�ro du document : " + d.numero() + " | " + d.getTitre()); 
				System.out.println("<-- num�ro de document : " + d.numero() + " | " + d.getTitre());
			}
			
			out.println("Tapez le num�ro d'abonn� ");
			System.out.println("<-- Tapez le num�ro d'abonn�");
			
			// lire le num d'abonne
			int noAbonne = Integer.parseInt(in.readLine());
				
			System.out.println("--> Le num�ro d'abonn� : " + noAbonne);
			
			out.println("Tapez le num�ro du document souhait� ");
			System.out.println("<-- Tapez le num�ro du document souhait� ");
			// lire le num de doc souhaite
			int noDoc = Integer.parseInt(in.readLine());
			
			System.out.println("--> Le num�ro du document souhait� : " + noDoc);
			
			// recupere l'abonn�e (grace � son sum�ro) et le document			
			Abonne abonneEmprunte = getAbonne(noAbonne);
			Documents documentEmprunte = getDoc(noDoc);
			
			// verifier l'existance de abonne et document
			if (getAbonne(noAbonne) != null && getDoc(noDoc) != null ) {
					try {
						documentEmprunte.empruntPar(abonneEmprunte); // emprunte le document
						reponse = "Emprunt r�ussi pour M./Mme" + abonneEmprunte.getNom() + "!";
					} catch (EmpruntException e) {
						reponse = e.toString();
					}
			}
			else {
				reponse = "N� Abonne OU N� de Document n'existe pas";
			}
			out.println(reponse);
			System.out.println("<-- " + reponse);
		} catch (IOException e) {}

		try {
			client.close();
		} catch (IOException e2) {
		}
		
	}

	// ferme la socket
	protected void finalize() throws Throwable {
		client.close();
	}

}
