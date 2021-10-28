package Bataille;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import protagoniste.EtreVivant;

public class Camp <E extends EtreVivant> implements Iterable<E>{
	private List<E> liste = new LinkedList<>();
	private int nombreEtreVivant = 0;
	
	
	public void ajouter(E etreVivant) {
		boolean dejaPresent = false;
		
		for(E e : liste) {
			dejaPresent = dejaPresent || e.equals(etreVivant);
		}
		
		if(!dejaPresent) {
			liste.add(etreVivant);
			nombreEtreVivant++;
		}
	}
	
	public void eliminer(E etreVivant) {
		liste.remove(etreVivant);
		nombreEtreVivant--;
	}
	
	public String toString() {
		return liste.toString();
	}

	private class Iterateur implements Iterator<E>{
		private int indiceIterateur = 0;
		
		public boolean hasNext() {
			return (nombreEtreVivant != 0 && indiceIterateur < nombreEtreVivant);
		}

		public E next() {
			if (hasNext()) {
				E etreVivant = liste.get(indiceIterateur);
				indiceIterateur++;
				return etreVivant;
			}
			throw new NoSuchElementException();
		
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterateur();
	}
	
	public int nbCombattants() {
		  return liste.size();
		}

	public E selectionner() {
	  Random randomGenerateur = new Random();
	  int numeroCombattant = randomGenerateur.nextInt(liste.size());
	  return liste.get(numeroCombattant);
	}
}
