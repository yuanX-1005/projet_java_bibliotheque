package serveurs;

import java.io.*;
import java.net.Socket;

import appli.Abonne;
import documents.Documents;
import exception.ReservationException;

public class ServiceReservation extends Service implements Runnable {

	private final Socket client;
	
	public ServiceReservation(Socket sock) {
		this.client = sock;
	}


	@Override
	public void run() {
		String reponse = null;
		try {
			System.err.println("reservation de document");
			// Creation de streams pour lire et ecrire du texte dans cette socket
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			// envoie le catalogue
			out.println("Le catalogue comprend les documents suivants: "); 
			System.out.println("<-- Le catalogue : ");
			out.println(lesDocs.size());
			
			for(Documents d : lesDocs) {
				out.println("num�ro du document : " + d.numero() + " | " + d.getTitre()); 
				System.out.println("<-- num�ro du document : " + d.numero() + " | " + d.getTitre());
			}
			
			out.println("Tapez le num�ro d'abonn� ");
			System.out.println("<-- Tapez le num�ro d'abonn�");
			// lire le num d'abonn�e
			int noAbonne = Integer.parseInt(in.readLine());
				
			System.out.println("--> Le num�ro d'abonn� : " + noAbonne);
			
			out.println("Tapez le num�ro de document souhait� ");
			System.out.println("<-- Tapez le num�ro de document souhait� ");
			
			// lire le num du doc souhait�
			int noDoc = Integer.parseInt(in.readLine());
			System.out.println("--> Le num�ro de document souhait� : " + noDoc);
			
			// recupere l'abonn�e (grace � son sum�ro) et le document			
			Abonne abonneEmprunte = getAbonne(noAbonne);
			Documents documentEmprunte = getDoc(noDoc);
			
			// verifie l'existance de abonne et document
			if (abonneEmprunte != null && documentEmprunte != null ) {
					try {
						documentEmprunte.reservationPour(abonneEmprunte); // reserve le document
						reponse = "Reservation r�ussie pour " + abonneEmprunte.getNom() + " !";
					} catch (ReservationException e) {
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
	
	//ferme la socket
	protected void finalize() throws Throwable {
		client.close();
	}



}
