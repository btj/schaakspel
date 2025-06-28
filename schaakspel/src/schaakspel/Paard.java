package schaakspel;

public class Paard extends Stuk {
	
	/**
	 * @post | isWit() == isWit
	 * @post | getBord() == null
	 */
	public Paard(boolean isWit) {
		super(isWit);
	}
	
	/**
	 * @throws IllegalStateException | getBord() == null
	 * @throws IllegalArgumentException | rijIndex < 0 || 8 <= rijIndex
	 * @throws IllegalArgumentException | kolomIndex < 0 || 8 <= kolomIndex
	 * 
	 * @inspects | this
	 * 
	 * @post | result == (
	 *       |     Math.abs(rijIndex - getRijIndex()) == 2 && Math.abs(kolomIndex - getKolomIndex()) == 1 ||
	 *       |     Math.abs(rijIndex - getRijIndex()) == 1 && Math.abs(kolomIndex - getKolomIndex()) == 2
	 *       | )
	 */
	@Override
	public boolean kanBewegenNaar(int rijIndex, int kolomIndex) {
		if (bord == null)
			throw new IllegalStateException("Dit stuk staat niet op een bord");
		if (rijIndex < 0 || 8 <= rijIndex)
			throw new IllegalArgumentException("`rijIndex` ligt niet tussen 0 en 7");
		if (kolomIndex < 0 || 8 <= kolomIndex)
			throw new IllegalArgumentException("`kolomIndex` ligt niet tussen 0 en 7");
		
		int dx = Math.abs(rijIndex - this.rijIndex);
		int dy = Math.abs(kolomIndex - this.kolomIndex);
		return dx == 2 && dy == 1 || dx == 1 && dy == 2;
	}

}
