package schaakspel;

public class Pion extends Stuk {

	/**
	 * @post | isWit() == isWit
	 * @post | getBord() == null
	 */
	public Pion(boolean isWit) {
		super(isWit);
	}
	
	/**
	 * @pre | getBord() != null
	 * @pre | 0 <= rijIndex && rijIndex < 8
	 * @pre | 0 <= kolomIndex && kolomIndex < 8
	 * 
	 * @inspects | this
	 * 
	 * @post | result == (
	 *       |     Math.abs(rijIndex - getRijIndex()) == 1 &&
	 *       |     (rijIndex - getRijIndex() == 1) == isWit() &&
	 *       |     Math.abs(kolomIndex - getKolomIndex()) <= 1 &&
	 *       |     (kolomIndex == getKolomIndex()) == (getBord().getStuk(rijIndex, kolomIndex) == null)
	 *       | )
	 */
	@Override
	public boolean kanBewegenNaar(int rijIndex, int kolomIndex) {
		int dr = rijIndex - this.rijIndex;
		int dk = kolomIndex - this.kolomIndex;
		if (Math.abs(dr) != 1)
			return false;
		if ((dr == 1) != isWit)
			return false;
		if (kolomIndex == this.kolomIndex)
			return bord.getStuk(rijIndex, kolomIndex) == null;
		else if (Math.abs(kolomIndex - this.kolomIndex) == 1)
			return bord.getStuk(rijIndex, kolomIndex) != null;
		return false;
	}
	
}
