package documents;

import appli.Abonne;
import exception.EmpruntException;
import exception.ReservationException;

public class DVDs extends Documents{
	
	private static final int AGEMIN = 16; // age min pour acceder aux document adultes
	private boolean adulte; 
	private String typeDoc = "DVD";
	
	
	public DVDs(int numero, String titre, boolean adulte) {
		super(numero,titre);
		this.adulte = adulte;
	}

	@Override
	public void reservationPour(Abonne ab) throws ReservationException {
		synchronized (this) { // securise le document
			if (verifierAge(ab)) // verifie l'age de l'abonnée
				throw new ReservationException(typeDoc, 1);
			super.reservationPour(ab);
		}
		
	}

	@Override
	public void empruntPar(Abonne ab) throws EmpruntException {
		synchronized (this) {  // securise le document
			if (verifierAge(ab)) // verifie l'age de l'abonnée
				throw new EmpruntException(typeDoc, 1);
			super.empruntPar(ab);

		}
	}
	
	// verifie l'age
	private boolean verifierAge(Abonne ab){
		return adulte == true && ab.getAge() < AGEMIN;
	}
	
	
}
