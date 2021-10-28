package livre;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import Bataille.Bataille;
import Bataille.Camp;
import protagoniste.Domaine;
import protagoniste.EtreVivant;
import protagoniste.Homme;
import protagoniste.Monstre;
import protagoniste.ZoneDeCombat;
import protagoniste.Heros;

public class AideEcrivain {
	Bataille bataille;
	List<Homme> listeTriee = new LinkedList<>();
	private NavigableSet<Monstre<?>> monstreDomaineSet = new TreeSet<>(new MonstreComparator());
	private NavigableSet<Monstre<?>> monstresZoneSet = new TreeSet<>(new MonstreComparator2());
	private NavigableSet<Monstre<?>> monstreDeFeu;
	private NavigableSet<Monstre<?>> monstreDeGlace;
	private NavigableSet<Monstre<?>> monstreTranchants;
	
	public NavigableSet<Monstre<?>> getMonstreDeFeu() {
		return monstreDeFeu;
	}

	public NavigableSet<Monstre<?>> getMonstreDeGlace() {
		return monstreDeGlace;
	}

	public NavigableSet<Monstre<?>> getMonstreTranchants() {
		return monstreTranchants;
	}
	
	public Monstre firstMonstreDomaine(Domaine domaine) {
		return monstreDomaineSet.ceiling(new Monstre<>("", 0, null, domaine, null));
	}
	
	public void initMonstreDeFeu() {
		monstreDeFeu = new TreeSet<>();
		monstreDeFeu = monstreDomaineSet.headSet(new Monstre<>("", 0, null, Domaine.GLACE, null), true);
	}

	public void initMonstreDeGlace() {
		monstreDeGlace = new TreeSet<>();
		monstreDeGlace = monstreDomaineSet.subSet(firstMonstreDomaine(Domaine.GLACE), true, firstMonstreDomaine(Domaine.TRANCHANT), false);
	}
	
	public void initMonstreTranchant() {
		monstreTranchants = new TreeSet<>(monstreDomaineSet);
		monstreTranchants = monstreDomaineSet.tailSet(new Monstre<>("", 0, null, Domaine.TRANCHANT, null), true);
	}
	
	public AideEcrivain(Bataille bataille) {
		this.bataille = bataille;
		}
	
	public List<Homme> visualiserForcesHumaines() {
		int indiceHeros = 0;
		Camp <Homme> listeCamp = bataille.getCampHumain();
		for(Iterator<Homme> iter = listeCamp.iterator(); iter.hasNext();) {
			Homme personnage = iter.next();
			if(personnage instanceof Heros) {
				listeTriee.add(indiceHeros, personnage); //TODO refaire !
				indiceHeros++;
			}
			else {
				listeTriee.add(personnage);
			}
		}
		return listeTriee;
	}
	
	public class MonstreComparator implements Comparator<Monstre>{
		@Override
		public int compare(Monstre monstre1, Monstre monstre2) {
			Domaine domaine1 = monstre1.getDomaine();
			Domaine domaine2 = monstre2.getDomaine();
			if (domaine1.equals(domaine2)) {
				return monstre1.compareTo(monstre2);
			}
			return (domaine1.name()).compareTo(domaine2.name());
		}
	}
	
	public class MonstreComparator2 implements Comparator<Monstre>{
		@Override
		public int compare(Monstre monstre1, Monstre monstre2) {
			ZoneDeCombat zoneDeCombat1 = monstre1.getZoneDeCombat();
			ZoneDeCombat zoneDeCombat2 = monstre2.getZoneDeCombat();
			if (zoneDeCombat1.equals(zoneDeCombat2)) {
				int comparaison = monstre1.getForceDeVie() - monstre2.getForceDeVie();
				if (comparaison < 0) {
					return 1;
				}
				else if(comparaison > 0) {
					return -1;
				}
 				return monstre1.compareTo(monstre2);
			}
			else {
				return (zoneDeCombat1.name()).compareTo(zoneDeCombat2.name());
			}
		}
	}
	
	public void updateMonstresZone() {
		Camp<Monstre<?>> campMonstre = bataille.getCampMonstre();
		for(Iterator<Monstre<?>> iter = campMonstre.iterator(); iter.hasNext();) {
			monstresZoneSet.add(iter.next());
		}
	}
	public void updateMonstresDomaine() {
		Camp<Monstre<?>> campMonstre = bataille.getCampMonstre();
		for(Iterator<Monstre<?>> iter = campMonstre.iterator(); iter.hasNext();) {
			monstreDomaineSet.add(iter.next());
		}
	}
	
	public StringBuilder ordreMonstreZone() {
		updateMonstresZone();
		StringBuilder chaine = new StringBuilder("");
		ZoneDeCombat currentZone = null;
		for(Monstre monstre : monstresZoneSet) {
			ZoneDeCombat oldZone = currentZone;
			currentZone = monstre.getZoneDeCombat();
			if(oldZone != currentZone) {
				chaine.append("\n" + currentZone.name() + ":\n");
			}
			chaine.append(monstre.getNom()+ " : " + monstre.getForceDeVie() + ", ");
		}
		return chaine;
	}
	
	
	public StringBuilder ordreMonstreDomaine() {
		updateMonstresDomaine();
		StringBuilder chaine = new StringBuilder("");
		Domaine currentDomaine = null;
		for(Monstre monstre : monstreDomaineSet) {
			Domaine oldDomaine = currentDomaine;
			currentDomaine = monstre.getDomaine();
			if(oldDomaine != currentDomaine) {
				chaine.append("\n" + currentDomaine.name() + ":\n");
			}
			chaine.append(monstre.getNom()+ ", ");
		}
		return chaine;
	}
	
	public StringBuilder ordreNaturelMonstre() {
		NavigableSet<String> ensemble = new TreeSet<>();
		
		Camp<Monstre<?>> campMonstre = bataille.getCampMonstre();
		StringBuilder chaine = new StringBuilder("");
		for(Iterator<Monstre<?>> iter = campMonstre.iterator(); iter.hasNext();) {
			ensemble.add(iter.next().getNom());
		}
		
		for (String word : ensemble) {
			if (!chaine.isEmpty()) {
				chaine.append(", ");
			}
			chaine.append(word);
		}
		return chaine;
	}
	
}
