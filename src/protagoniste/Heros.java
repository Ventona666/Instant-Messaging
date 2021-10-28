package protagoniste;

public class Heros extends Homme{
	public Heros (String nom){
		super(nom);
		this.forceDeVie = 100;
	}

	@Override
	public String toString() {
		return "Heros [bataille=" + bataille + ", getForceDeVie()=" + getForceDeVie() + ", getNom()=" + getNom() + "]";
	}

}
