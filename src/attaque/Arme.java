package attaque;

import java.util.HashSet;
import java.util.Set;

import protagoniste.ZoneDeCombat;

public abstract class Arme extends ForceDeCombat{
	Set<ZoneDeCombat> zoneDeCombat = new HashSet<>();
	
	public Set<ZoneDeCombat> getZoneDeCombat() {
		return zoneDeCombat;
	}

	protected Arme(int pointDeDegat, String nom, ZoneDeCombat...zoneDeCombats) {
		super(pointDeDegat, nom);
		for(ZoneDeCombat zoneDC : zoneDeCombats) {
			this.zoneDeCombat.add(zoneDC);
		}
	}
}
