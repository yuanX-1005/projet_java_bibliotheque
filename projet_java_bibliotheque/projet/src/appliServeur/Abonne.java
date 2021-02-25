package appli;

import java.util.Calendar;
import java.util.Date;

public class Abonne {
	
	private int numero;
	private String nom;
	private Date dateNaissance;
	
	
	public Abonne(int numero, String nom, Date dateNaissance) {
		this.numero = numero;
		this.nom = nom;
		this.dateNaissance = dateNaissance;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public String getNom() {
		return nom;
	}

	public int getAge() {
		
        // occupe la date d'aujourd'hui
		Calendar date = Calendar.getInstance(); 

        // si la date d'aujourd'hui est sup à date de naissance  
		if (date.before(dateNaissance)) {
		     throw new IllegalArgumentException(
                "La date de naissance est superieure à la date d'aujourd'hui");
        }
		
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH) + 1;
		int day = date.get(Calendar.DAY_OF_MONTH);
		
        // récupere la date de naissance
		date.setTime(dateNaissance); 
		int yearBirth = date.get(Calendar.YEAR);
        int monthBirth = date.get(Calendar.MONTH);
        int dayBirth = date.get(Calendar.DAY_OF_MONTH);

        //calcul l'age de l'abonnée
        int age = year - yearBirth;
        if (month <= monthBirth) {
            if (month == monthBirth) {
                //month == monthBirth
                if (day < dayBirth) {
                    age--;
                } else {
                    //fait rien
                }
            } else {
                //month > monthBirth
                age--;
            }
        } else {
            //month < monthBirth
            // fait rien 
        }

        return age;
	}

	
	
}

