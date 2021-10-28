package protagoniste;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import Bataille.Bataille;
import attaque.Pouvoir;

public class Monstre <P extends Pouvoir> extends EtreVivant{
	private P[]attaques; 
	private ZoneDeCombat zoneDeCombat;
	private Domaine domaine;
	private int i = 0;
	GestionAttaque gestionAttaque = null;
	
	@SafeVarargs
	public Monstre(String nom, int forceDeVie, ZoneDeCombat zoneDeCombat, Domaine domaine, P...attaques){
		super(nom, forceDeVie);
		this.zoneDeCombat = zoneDeCombat;
		this.domaine = domaine;
		this.attaques = attaques;
	}
	
	public ZoneDeCombat getZoneDeCombat() {
		return zoneDeCombat;
	}
	
	public Domaine getDomaine() {
		return domaine;
	}

	
	@Override
	public String toString() {
		return "Monstre [attaques=" + Arrays.toString(attaques) + ", zoneDeCombat=" + zoneDeCombat + ", getForceDeVie()=" + getForceDeVie() + ", getNom()="
				+ getNom() + "]";
	}


	class GestionAttaque implements Iterator<P>{
		private P[]attaquesPossibles = attaques;
		private int nbAttaquesPossibles = attaques.length;
		private int indiceIterator = 0;
		
		@Override
		public boolean hasNext() {
			if(!attaquesPossibles[indiceIterator].isOperationel()) {
				attaquesPossibles[indiceIterator] = attaquesPossibles[nbAttaquesPossibles-1];
				nbAttaquesPossibles--;
			}
			return nbAttaquesPossibles > 0;
		}

		@Override
		public P next() {
			if(hasNext()) {
				Random random = new Random();
				indiceIterator = random.nextInt(nbAttaquesPossibles);
				P attaque = attaquesPossibles[indiceIterator];
				attaque.utiliser();	
				return attaque;
			}
			throw new NoSuchElementException();
		}
		
		public Iterator<P> iterator(){
			return new GestionAttaque();
		}
	}
	
	public void entreEnCombat() {
		for(P attaque : attaques) {
			attaque.regeneratePouvoir();
		}
		gestionAttaque = new GestionAttaque();
		
	}
	
	public P attaque() {
		return gestionAttaque.next();
	}
	
	@Override
	public void rejointBataille(Bataille bataille) {
		bataille.ajouter(this);
		
	}
	
	@Override
	public void mourir() {
		this.bataille.eliminer(this);
	}

	@Override
	public int compareTo(EtreVivant etreVivantToCompare) {
		int comparaison = super.nom.compareTo(etreVivantToCompare.nom);
		if(comparaison != 0) {
			return comparaison;
		}
		return 0;
	}
}


