package protagoniste;

import Bataille.Bataille;

public class Homme extends EtreVivant{
	public Homme(String nom) {
		super(nom, 70);
	}

	public void rejointBataille(Bataille bataille) {
		bataille.ajouter(this);
	}
	
	@Override
	public void mourir() {
		this.bataille.eliminer(this);
	}

	@Override
	public String toString() {
		return "Homme [nom=" + nom + ", forceDeVie=" + forceDeVie + ", bataille=" + bataille + "]";
	}

	@Override
	public int compareTo(EtreVivant o) {
		// TODO Auto-generated method stub
		return 0;
	}


}
