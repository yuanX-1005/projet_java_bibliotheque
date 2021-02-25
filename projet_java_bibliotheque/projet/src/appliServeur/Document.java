package appli;

import exception.EmpruntException;
import exception.ReservationException;

public interface Document {
	int numero();
	void reservationPour(Abonne ab) throws ReservationException;
	void empruntPar(Abonne ab) throws EmpruntException;
	// retour d'un document ou annulation r�servation
	void retour();
}
