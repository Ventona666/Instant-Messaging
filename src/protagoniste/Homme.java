package protagoniste;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import Bataille.Bataille;
import attaque.Arme;

public class Homme extends EtreVivant{
	Map<ZoneDeCombat, List<Arme>> armes = new EnumMap<>(ZoneDeCombat.class);
	Arme armeChoisie;
	
	public Arme getArmeChoisie() {
		return armeChoisie;
	}

	public Homme(String nom) {
		super(nom, 70);
	}
	
	public void ajouterArmes(Arme...arme) {
		for(Arme arm : arme) {
			for(ZoneDeCombat zoneDeCombat : arm.getZoneDeCombat()) {
				List<Arme> listeArmes = armes.get(zoneDeCombat);
				listeArmes.add(arm);
			}
		}
	}
	
	public void supprimerArme(Arme arme) {
		for(ZoneDeCombat zoneDeCombat : armes.keySet()) {
			List<Arme> listeArmes = armes.get(zoneDeCombat);
			if(listeArmes.contains(arme)) {
				listeArmes.remove(arme);
			}
		}
	}
	
	public class ArmeComparator implements Comparator<Arme>{
		@Override
		public int compare(Arme arme1, Arme arme2) {
			boolean arme1Op = arme1.isOperationel();
			boolean arme2Op = arme2.isOperationel();
			if (arme1Op == arme2Op){
				int arme1Dmg = arme1.getPointDeDegat();
				int arme2Dmg = arme2.getPointDeDegat();
				
				if(arme1Dmg == arme2Dmg) {		
					
				}	
				
			}
			return(arme1.getNom().compareTo(arme2.getNom()));
		}
		
	}
	

	public Arme choisirArme(Monstre<?> monstre) {
		ZoneDeCombat zoneDeCombat = monstre.getZoneDeCombat();
		List<Arme> listeArmeAdaptees = armes.get(zoneDeCombat);
		NavigableSet<Arme> armesTriees = new TreeSet<>(new ArmeComparator());
		if(!armesTriees.isEmpty()) {
			int forceDeVieMonstre = monstre.getForceDeVie();
			List<Arme> armesAdaptees = 
		}
		
		
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
