package protagoniste;

import Bataille.Bataille;

public abstract class EtreVivant implements Comparable <EtreVivant>{
	protected String nom;
	protected int forceDeVie;
	protected Bataille bataille;
	
	public EtreVivant(String nom, int forceDeVie) {
		this.nom = nom;
		this.forceDeVie = forceDeVie;
		
	}
	
	public int getForceDeVie() {
		return forceDeVie;
	}
	
	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return "EtreVivant [nom=" + nom + ", forceDeVie=" + forceDeVie + "]";
	}
	
	public void rejointBataille(Bataille bataille) {
		this.bataille = bataille;
	}
	
	public abstract void mourir();
	
	public boolean equals(Object obj) {
		if(obj instanceof EtreVivant) {
			EtreVivant etreVivant = (EtreVivant) obj;
			return nom.equals(etreVivant.nom);
		}
		return false;
	}
}
