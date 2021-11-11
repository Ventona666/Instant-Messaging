package Bataille;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import protagoniste.Monstre;
import protagoniste.ZoneDeCombat;

public class Grotte {
	private Map<Salle, List<Salle>> planGrotte = new LinkedHashMap<>();
	private Map<Salle, Bataille> batailles = new HashMap<>();
	private Set<Salle> sallesExplorees = new HashSet<>();
	private int numeroSalleDecisive;

	public void ajouterSalle(ZoneDeCombat zoneDeCombat, Monstre<?>... monstres) {
		Salle salle = new Salle(planGrotte.size() + 1, zoneDeCombat);
		Bataille bataille = new Bataille();
		for (Monstre<?> monstre : monstres) {
			monstre.rejointBataille(bataille);
		}
		planGrotte.put(salle, new ArrayList<>());
		batailles.put(salle, bataille);
	}

	private Salle trouverSalle(int numSalle) {
		Set<Salle> listeSalle =  planGrotte.keySet();
		for(Salle salle : listeSalle) {
			if (salle.getNumSalle() == numSalle) {
				return salle;
			}
		}
		throw new NoSuchElementException();
	}
	
	public void configurerAcces(int numSalle, int...numAutresSalles) {
		Salle salle = trouverSalle(numSalle);
		List<Salle> listeSalle = planGrotte.get(salle);
		for(int numAutreSalle : numAutresSalles) {
			Salle autreSalle = trouverSalle(numAutreSalle);
			listeSalle.add(autreSalle);
		}
	}

	public void setNumeroSalleDecisive(int numeroSalleDecisive) {
		this.numeroSalleDecisive = numeroSalleDecisive;
	}
	
	public boolean salleDecisive(Salle salle) {
		return salle.getNumSalle() == numeroSalleDecisive;
	}
	
	public Salle premiereSalle() {
		Salle salle = trouverSalle(1);
		sallesExplorees.add(salle);
		return salle;
	}
	
	public Salle salleSuivante(Salle salleAQuitter) {
		List<Salle> listeSalle = new ArrayList<>(planGrotte.get(salleAQuitter));
		listeSalle.removeAll(sallesExplorees);
		if (listeSalle.isEmpty()) {
			listeSalle.addAll(planGrotte.get(salleAQuitter));
		}
		Random randomGenerateur = new Random();
		int indiceSalleAExplorer = randomGenerateur.nextInt(listeSalle.size());
		Salle salleAExplorer = listeSalle.get(indiceSalleAExplorer);
		sallesExplorees.add(salleAExplorer);
		return salleAExplorer;
	}

	public String afficherPlanGrotte() {
		StringBuilder affichage = new StringBuilder();
		for (Map.Entry<Salle, List<Salle>> entry : planGrotte.entrySet()) {
			Salle salle = entry.getKey();
			List<Salle> acces = planGrotte.get(salle);
			affichage.append("La " + salle + ".\nelle possede " + acces.size() + " acces : ");
			for (Salle access : acces) {
				affichage.append(" vers la salle " + access);
			}
			Bataille bataille = batailles.get(salle);
			Camp<Monstre<?>> camp = bataille.getCampMonstre();
			Monstre<?> monstre = camp.selectionner();
			if (camp.nbCombattants() > 1) {
				affichage.append("\n" + camp.nbCombattants() + " monstres de type ");
			} else {
				affichage.append("\nUn monstre de type ");
			}
			affichage.append(monstre.getNom() + " la protege.\n");
			if (salle.getNumSalle() == numeroSalleDecisive) {
				affichage.append("C'est dans cette salle que se trouve la pierre de sang.\n");
			}
			affichage.append("\n");
		}
		return affichage.toString();
	}
}
