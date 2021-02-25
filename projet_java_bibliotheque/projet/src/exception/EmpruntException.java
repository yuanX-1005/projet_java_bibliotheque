package exception;

public class EmpruntException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private int typeErreur;
	private String typeDoc;
	private int[] heureMax;
	
	public EmpruntException(String typeDoc, int i) {
		this.typeDoc = typeDoc;
		this.typeErreur = i;
	}

	public EmpruntException(String typeDoc, int i, int[] hour) {
		this.typeDoc = typeDoc;
		this.typeErreur = i;
		this.heureMax = hour;
		
	}


	@Override
	public String toString() {
		switch(typeErreur) {
		case 1 : // l'age limite n'est pas atteint
			return "vous n�avez pas l��ge pour emprunter ce " + typeDoc;
		case 2 : // le doc est en mode reservation
			return "ce " + typeDoc + " est r�serv� jusqu'� " + heureMax[0] + ":" + heureMax[1];
		case 3 : // le doc est en mode emprunt
			return "ce " + typeDoc + " est d�j� emprunt� ";
		default :
			return " erreur inconnu !";
		}
	}

	
}
