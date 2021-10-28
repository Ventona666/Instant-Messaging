package attaque;

public abstract class Pouvoir extends ForceDeCombat{
	private int nbUtilisationPouvoir;
	private int nbUtilisationPouvoirInitial;
	
	public Pouvoir(int pointDeDegat, String nom, int nbUtilisationPouvoir) {
		super(pointDeDegat, nom);
		this.nbUtilisationPouvoir = nbUtilisationPouvoir;
		this.nbUtilisationPouvoirInitial = nbUtilisationPouvoir;
	}
	
	public void regeneratePouvoir() {
		nbUtilisationPouvoir = nbUtilisationPouvoirInitial;
	}
	
	@Override
	public int utiliser() {
		if (nbUtilisationPouvoir > 1) {
			nbUtilisationPouvoir--;
		}
		else {
			super.operationel = false;
		}
		return super.getPointDeDegat();
	}

}
