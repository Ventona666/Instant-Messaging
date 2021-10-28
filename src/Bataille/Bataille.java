package Bataille;

import protagoniste.Homme;
import protagoniste.Monstre;

public class Bataille{
	Camp<Homme> campHumain = new Camp<>();
	Camp<Monstre<?>> campMonstre = new Camp<>();
	
	public void ajouter(Homme homme) {
		campHumain.ajouter(homme);
	}
	
	public void ajouter(Monstre<?> monstre) {
		campMonstre.ajouter(monstre);
	}
	
	public void eliminer(Homme homme) {
		campHumain.eliminer(homme);
	}
	
	public void eliminer(Monstre<?> monstre) {
		campMonstre.eliminer(monstre);
	}
	
	public Camp<Homme> getCampHumain() {
		return campHumain;
	}

	public Camp<Monstre<?>> getCampMonstre() {
		return campMonstre;
	}
	
}
