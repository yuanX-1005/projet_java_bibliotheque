package appli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ApplicationClient {
	private static final int PORTRES = 3000; // port pour une reservation
	private static final int PORTEMP = 4000; // port pour un emprunt
	private static final int PORTRET = 5000; // port pour un retour   
	private final static String HOST = "localhost"; 
 
 public static void main(String[] args){
	 String rep;
	do {
		 System.out.println("Entrez le numéro du service que vous voulez effectuer : \n" + 
				 "1 : reserver un document \t 2 : emprunter un document \t 3 : retourner un document ");
		 System.out.println("----------------------------------");
		 @SuppressWarnings("resource")
		 Scanner sc = new Scanner(System.in);
		 int fonc = sc.nextInt();
		 switch(fonc){
			 case 1 : 
				ApplicationClient client1 = new ApplicationClient(); // reservation
				client1.reserver();
				break;
			 case 2 : 
				ApplicationClient client2 = new ApplicationClient(); // emprunt 
				client2.emprunt();
				break;
			 case 3 :
				ApplicationClient client3 = new ApplicationClient(); // retour
				client3.retourne();
				break;
		 }
		 System.err.println("\nc : continue\t autre : sortir");
		@SuppressWarnings("resource")
		Scanner sd = new Scanner(System.in); // recommancer 
		rep = sd.nextLine();
	 }while(rep.contentEquals("c"));
	System.out.println("fin");
 }
 
 public void reserver(){ 
	 try {
		 Socket socket = new Socket(HOST, PORTRES);
         // Creation de streams pour lire et ecrire du texte dans cette socket
         BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
         PrintWriter out = new PrintWriter (socket.getOutputStream ( ), true);
         
         System.out.println("Connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());
         
         // lire et afficher le catalogue
         String catalogue = in.readLine(); 
         System.out.println(catalogue);
         
         // lire la longueur du catalogue
         int volumeDocs = Integer.parseInt(in.readLine()); 
         
         // affiche le catalogue
         @SuppressWarnings("unused")
         String str;
         for(int i = 0; i < volumeDocs; i++) { 
        	 System.out.println((str = in.readLine()));
         }
         
         String msg = in.readLine();
         System.out.println(msg);
         
         // enregistre le numero d'abonne entre par client
         BufferedReader noAbonne = new BufferedReader(new InputStreamReader(System.in));            
         String input = noAbonne.readLine();
         out.println(input); // envoie au serveur
         
         msg = in.readLine();
         System.out.println(msg);
         
         // enregistre le numero du document souhaité entré par client
         BufferedReader noDoc = new BufferedReader(new InputStreamReader(System.in));            
         input = noDoc.readLine();
         out.println(input); // envoie au serveur
         
         String reponse = in.readLine();
         System.out.println(reponse);
         
         socket.close();
     
	 }
	 catch (Exception e) {}
 }
 
 
 public void emprunt(){ 
	 try {
		 Socket socket = new Socket(HOST, PORTEMP);
         // Creation de streams pour lire et ecrire du texte dans cette socket
         BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
         PrintWriter out = new PrintWriter (socket.getOutputStream ( ), true);
         
         System.out.println("Connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());
        
         // lire et afficher le catalogue
         String catalogue = in.readLine();
         System.out.println(catalogue);
         
         // lire la longueur du catalogue
         int volumeDocs = Integer.parseInt(in.readLine());;
         
         // affiche le catalogue
         @SuppressWarnings("unused")
         String str;
         for(int i = 0; i < volumeDocs; i++) {
        	 System.out.println((str = in.readLine()));
         }
         
         String msg = in.readLine();
         System.out.println(msg);
         
         // enregistre le numero d'abonne entré par client
         BufferedReader noAbonne = new BufferedReader(new InputStreamReader(System.in));            
         String input = noAbonne.readLine();
         out.println(input);
         
         msg = in.readLine();
         System.out.println(msg);
         
         // enregistre le numero du document souhaité entré par client
         BufferedReader noDoc = new BufferedReader(new InputStreamReader(System.in));            
         input = noDoc.readLine();
         out.println(input);
         
         String reponse = in.readLine();
         System.out.println(reponse);
         
        
         
        socket.close();
     
	 }
	 catch (Exception e) {}
 }
 
 public void retourne() {
	 try {
		 Socket socket = new Socket(HOST, PORTRET);
         // Creation de streams pour lire et ecrire du texte dans cette socket
         BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
         PrintWriter out = new PrintWriter (socket.getOutputStream ( ), true);
         
         System.out.println("Connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());
        
         String msg = in.readLine();
         System.out.println(msg);
         
         // enregistre le numero du document souhaité entré par client
         BufferedReader noDoc = new BufferedReader(new InputStreamReader(System.in));            
         String input = noDoc.readLine();
         out.println(input);
         
         String reponse = in.readLine();
         System.out.println(reponse);
        
         
         socket.close();
     
	 }
	 catch (Exception e) {}
 }
 
}
