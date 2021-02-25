package serveurs;

import java.io.*;
import java.net.Socket;

import documents.Documents;

public class ServiceRetour extends Service implements Runnable {

	private final Socket client;
	
	public ServiceRetour(Socket sock) {
		this.client = sock;
	}
		
	@Override
	public void run() {

		String reponse = null;
		try {
			System.err.println("retour d'un document");
			
	        // Creation de streams pour lire et ecrire du texte dans cette socket
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("Tapez le numéro du document que vous voulez rendre ");
			System.out.println("<-- Tapez le numéro du document que vous voulez rendre");
			
			// lire le num de doc souhaite
			int noDoc = Integer.parseInt(in.readLine());
			
			System.out.println("-> Le numéro du document rendu : " + noDoc);
			
			// recupere et le document			
			Documents documentRetourne = getDoc(noDoc);
			
			if(getDoc(noDoc) != null) {
				synchronized (documentRetourne) {
					try {
						documentRetourne.retour(); // retourne le document
						reponse = "Le document est bien retourné!";
					} catch (Exception e) {
					}
				}
			}
			else {
				reponse = "Le N° de Document n'existe pas.";
			}
			out.println(reponse);
			System.out.println("<-- " + reponse);
		} catch (IOException e) {}

			try {
				client.close();
			} catch (IOException e2) {
		}
		
	}
	
	//fermer la socket
	protected void finalize() throws Throwable {
		client.close();
	}

}
