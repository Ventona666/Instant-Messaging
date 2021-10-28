package attaque;

public class Arc extends Arme{
	private int nbFlechesRestantes;
	
	public Arc(int nbFlechesRestantes) {
		super(50, "Arc");
	}
	
	@Override 
	public int utiliser() {
		if (nbFlechesRestantes < 1){
			super.operationel = false;
		}
		else {
			nbFlechesRestantes--;
		}
		return 50;
	}
}
