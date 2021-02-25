package serveurs;

import java.io.IOException;
import java.net.ServerSocket;

public class Serveur implements Runnable {

	private ServerSocket listen_socket;
	
	public Serveur(int port) throws IOException {
		listen_socket = new ServerSocket(port); // initialiser le socket
	}

	@Override
	public void run() {
		try {
			System.err.println("Lancement du serveur au port " + this.listen_socket.getLocalPort());
			while(true) {
				switch (listen_socket.getLocalPort()) { // entrer dans services 
					case 3000 :
						new Thread(new ServiceReservation(listen_socket.accept())).start();
						break;
					case 4000 :
						new Thread(new ServiceEmprunt(listen_socket.accept())).start();
						break;
					case 5000 :
						new Thread(new ServiceRetour(listen_socket.accept())).start();
						break;
					default :
						System.out.println("port inconnu");
				}
			}
			
		} catch (IOException e) {
			System.err.println("Arrêt du serveur au port " + this.listen_socket.getLocalPort());
		}
			try {
				this.listen_socket.close(); // fermer le socket
			} catch (IOException e1) {
		}
	}
	
	// fermer le socket
	protected void finalize() throws Throwable { 
		try {
			this.listen_socket.close();
		} 
		catch (IOException e1) {
		}
	}

}
