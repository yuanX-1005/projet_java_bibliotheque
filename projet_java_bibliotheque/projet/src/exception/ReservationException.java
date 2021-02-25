package exception;


public class ReservationException extends Exception {
	private static final long serialVersionUID = 1L;
//	private static final int AGE = 16;
//	private Document doc;
//	private Abonne ab;
	private int typeErreur;
	private String typeDoc;
	
	public ReservationException(String typeDoc, int i) {
		this.typeDoc = typeDoc;
		this.typeErreur = i;
	}
	
	@Override
	public String toString() {
		switch(typeErreur) {
		case 1 : // erreur de l'age
			return "vous n’avez pas l’âge pour emprunter ce " + typeDoc;
		case 2 : // le doc est non disponible
			return "ce " + typeDoc + " n'est pas disponible";
		default :
			return "erreur inconnu !";
		}
	
	}


}
