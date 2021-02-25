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
	protected Etats etat; // l'etait du document ( reservé , emprunté , disponible)
	protected int numDocAbo; // numero de l'abonné qui est interessé à ce document
	protected int hour[]= {0,0};
	
	private int duree = 2*60*60*1000; // durée de reservation: 2h
	
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
		if(this.etat != Etats.disponible) // verifie la disponibilité du document
			throw new ReservationException(titre, 2);
		else {
			this.etat = Etats.reserve; // l'etat du document change : il est réservé
			numDocAbo = ab.getNumero(); // récupère le numero de l'abonnée
			verifierEtat(this); //TimerTask qui fait que l'etat du document revient en mode disponible après 2H  
			
			//calcule l'heure de reservation
			Calendar now = Calendar.getInstance();
			now.add(Calendar.HOUR,2);
			hour[0] = now.get(Calendar.HOUR);
			hour[1] = now.get(Calendar.MINUTE);
		}
	}

	@Override
	public void empruntPar(Abonne ab) throws EmpruntException {
		// verifie la disponibilité du document
		if(this.etat == Etats.reserve && this.numDocAbo!= ab.getNumero()) // le document peut emprunt par client qu'il lui reserver
			throw new EmpruntException(titre, 2, hour);
		if(this.etat == Etats.emprunte)
			throw new EmpruntException(titre, 3);
		else {
			this.etat = Etats.emprunte; // l'etat du document change : il est emprunté
		}
	}

	@Override
	public void retour() {
		synchronized (this) {
			this.etat = Etats.disponible; // l'etat du document revient en disponible
		}
	}
	// Grace au Timer on peut verifier si la personne est venu recupéré le document sous les 2h,
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
