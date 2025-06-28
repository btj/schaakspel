package schaakspel;

import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * @invar | getBord() == null || getBord().getStuk(getRijIndex(), getKolomIndex()) == this
 */
public abstract class Stuk {
	
	/**
	 * @invar | 0 <= rijIndex && rijIndex < 8
	 * @invar | 0 <= kolomIndex && kolomIndex < 8
	 * @invar | true
	 * @invar | bord == null || bord.rijen[rijIndex][kolomIndex] == this // Fase 4
	 */
	final boolean isWit;
	/**
	 * @peerObject
	 */
	Bord bord;
	int rijIndex;
	int kolomIndex;
	
	public boolean isWit() { return isWit; }

	/**
	 * @peerObject
	 */
	public Bord getBord() { return bord; }
	
	/**
	 * @pre | getBord() != null
	 * @post | 0 <= result && result <= 7
	 */
	public int getRijIndex() { return rijIndex; }

	/**
	 * @pre | getBord() != null
	 * @post | 0 <= result && result <= 7
	 */
	public int getKolomIndex() { return kolomIndex; }
	
	Stuk(boolean isWit) {
		this.isWit = isWit;
	}
	
	/**
	 * @pre | getBord() != null
	 * @pre | 0 <= rijIndex && rijIndex < 8
	 * @pre | 0 <= kolomIndex && kolomIndex < 8
	 * 
	 * @inspects | this
	 */
	public abstract boolean kanBewegenNaar(int rijIndex, int kolomIndex);
	
	/**
	 * @throws IllegalStateException | getBord() == null
	 * @throws IllegalArgumentException | rijIndex < 0 || 8 <= rijIndex
	 * @throws IllegalArgumentException | kolomIndex < 0 || 8 <= kolomIndex
	 * @throws IllegalArgumentException | !kanBewegenNaar(rijIndex, kolomIndex)
	 * 
	 * @mutates_properties | getBord().getRijen(), this.getRijIndex(), this.getKolomIndex(),
	 *                     | getBord().getStuk(rijIndex, kolomIndex).getBord()
	 * 
	 * @post | getRijIndex() == rijIndex
	 * @post | getKolomIndex() == kolomIndex
	 * @post | old(getBord().getRijen())[rijIndex][kolomIndex] == null ||
	 *       | old(getBord().getRijen())[rijIndex][kolomIndex].getBord() == null
	 * @post | IntStream.range(0, 8).allMatch(rijIndex1 ->
	 *       |     IntStream.range(0, 8).allMatch(kolomIndex1 ->
	 *       |         getBord().getStuk(rijIndex1, kolomIndex1) ==
	 *       |         (rijIndex1 == rijIndex && kolomIndex1 == kolomIndex ?
	 *       |             this
	 *       |          : rijIndex1 == old(this.getRijIndex()) && kolomIndex1 == old(this.getKolomIndex()) ?
	 *       |             null
	 *       |          :
	 *       |             old(getBord().getRijen())[rijIndex1][kolomIndex1]
	 *       |         )
	 *       |     )
	 *       | )
	 */
	public void verplaatsNaar(int rijIndex, int kolomIndex) {
		if (bord == null)
			throw new IllegalStateException("Dit stuk staat niet op een bord");
		if (rijIndex < 0 || 8 <= rijIndex)
			throw new IllegalArgumentException("`rijIndex` ligt niet tussen 0 en 7");
		if (kolomIndex < 0 || 8 <= kolomIndex)
			throw new IllegalArgumentException("`kolomIndex` ligt niet tussen 0 en 7");
		if (!kanBewegenNaar(rijIndex, kolomIndex))
			throw new IllegalArgumentException("Dit stuk kan niet bewegen naar het gegeven vak");
			
		Stuk stuk = bord.rijen[rijIndex][kolomIndex];
		if (stuk != null)
			stuk.bord = null;
		bord.rijen[this.rijIndex][this.kolomIndex] = null; 
		bord.rijen[rijIndex][kolomIndex] = this;
		this.rijIndex = rijIndex;
		this.kolomIndex = kolomIndex;
	}

}
