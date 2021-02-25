package documents;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import appli.Abonne;
import appli.Document;
import etats.Etats;
import exception.EmpruntException;
import exception.ReservationException;

public abstract class Documents implements Document {

	private int numero; // numero du document
	private String titre; // titre du document
	protected Etats etat; // l'etait du document ( reserv� , emprunt� , disponible)
	protected int numDocAbo; // numero de l'abonn� qui est interess� � ce document
	protected int hour[]= {0,0};
	
	private int duree = 2*60*60*1000; // dur�e de reservation: 2h
	
	public Documents(int numero, String titre) {
		this.numero = numero;
		this.titre = titre;
		this.etat = Etats.disponible;
		
	}
	
	@Override
	public int numero() {
		return this.numero;
	}
	
	//retourne le titre de document
	public String getTitre() {
		return this.titre;
	}

	@Override
	public void reservationPour(Abonne ab) throws ReservationException {
		if(this.etat != Etats.disponible) // verifie la disponibilit� du document
			throw new ReservationException(titre, 2);
		else {
			this.etat = Etats.reserve; // l'etat du document change : il est r�serv�
			numDocAbo = ab.getNumero(); // r�cup�re le numero de l'abonn�e
			verifierEtat(this); //TimerTask qui fait que l'etat du document revient en mode disponible apr�s 2H  
			
			//calcule l'heure de reservation
			Calendar now = Calendar.getInstance();
			now.add(Calendar.HOUR,2);
			hour[0] = now.get(Calendar.HOUR);
			hour[1] = now.get(Calendar.MINUTE);
		}
	}

	@Override
	public void empruntPar(Abonne ab) throws EmpruntException {
		// verifie la disponibilit� du document
		if(this.etat == Etats.reserve && this.numDocAbo!= ab.getNumero()) // le document peut emprunt par client qu'il lui reserver
			throw new EmpruntException(titre, 2, hour);
		if(this.etat == Etats.emprunte)
			throw new EmpruntException(titre, 3);
		else {
			this.etat = Etats.emprunte; // l'etat du document change : il est emprunt�
		}
	}

	@Override
	public void retour() {
		synchronized (this) {
			this.etat = Etats.disponible; // l'etat du document revient en disponible
		}
	}
	// Grace au Timer on peut verifier si la personne est venu recup�r� le document sous les 2h,
	// dans le cas contraire il redeviendra disponible
	public void verifierEtat(Documents documents) {
		Timer temps = new Timer();
		TimerTask tache = new TimerTask() {
			@Override
			public void run() {
				if(documents.etat != Etats.emprunte)
					documents.etat = Etats.disponible;
			}
		};
		temps.schedule(tache, duree);
	}

}
