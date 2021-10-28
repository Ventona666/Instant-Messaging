package attaque;

public abstract class ForceDeCombat {
	private int pointDeDegat;
	private String nom;
	protected boolean operationel = true;
	
	public ForceDeCombat(int pointDeDegat, String nom) {
		this.pointDeDegat = pointDeDegat;
		this.nom = nom;
	}
	
	public int getPointDeDegat() {
		return pointDeDegat;
	}
	
	public String getNom() {
		return nom;
	}
	
	public boolean isOperationel(){
		return operationel;
	}

	@Override
	public String toString() {
		return "ForceDeCombat [pointDeDegat=" + pointDeDegat + ", nom=" + nom + ", operationel=" + operationel + "]";
	}
	
	public int utiliser(){
		if (isOperationel()) {
			return pointDeDegat;
		}
		return 0;
		
	}

}