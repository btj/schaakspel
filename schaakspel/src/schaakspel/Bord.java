package schaakspel;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @invar | IntStream.range(0, 8).allMatch(rijIndex ->
 *        |     IntStream.range(0, 8).allMatch(kolomIndex ->
 *        |         getStuk(rijIndex, kolomIndex) == null ||
 *        |         getStuk(rijIndex, kolomIndex).getBord() == this &&
 *        |         getStuk(rijIndex, kolomIndex).getRijIndex() == rijIndex &&
 *        |         getStuk(rijIndex, kolomIndex).getKolomIndex() == kolomIndex
 *        |     )
 *        | )
 */
public class Bord {
	
	/**
	 * @invar | rijen != null
	 * @invar | rijen.length == 8
	 * @invar | IntStream.range(0, 8).allMatch(rijIndex ->
	 *        |     rijen[rijIndex] != null &&
	 *        |     rijen[rijIndex].length == 8 &&
	 *        |     IntStream.range(0, 8).allMatch(kolomIndex ->
	 *        |         rijen[rijIndex][kolomIndex] == null ||
	 *        |         rijen[rijIndex][kolomIndex].bord == this &&
	 *        |         rijen[rijIndex][kolomIndex].rijIndex == rijIndex &&
	 *        |         rijen[rijIndex][kolomIndex].kolomIndex == kolomIndex
	 *        |     )
	 *        | )
	 * 
	 * @representationObject
	 * @representationObjects
	 * @peerObjects | ...rijen
	 */
	Stuk[][] rijen = new Stuk[8][8];
	
	/**
	 * @creates | result, ...result
	 * @peerObjects | ...result
	 * @post | result != null
	 * @post | result.length == 8
	 * @post | Arrays.stream(result).allMatch(rij -> rij != null && rij.length == 8)
	 */
	public Stuk[][] getRijen() {
		Stuk[][] result = new Stuk[8][];
		for (int rijIndex = 0; rijIndex < 8; rijIndex++)
			result[rijIndex] = rijen[rijIndex].clone();
		return result;
	}
	
	/**
	 * @pre | 0 <= rijIndex && rijIndex < 8
	 * 
	 * @creates | result
	 * @post | Arrays.equals(result, getRijen()[rijIndex])
	 */
	public Stuk[] getRij(int rijIndex) {
		return rijen[rijIndex].clone();
	}
	
	/**
	 * @pre | 0 <= rijIndex && rijIndex < 8
	 * @pre | 0 <= kolomIndex && kolomIndex < 8
	 * 
	 * @post | result == getRijen()[rijIndex][kolomIndex]
	 */
	public Stuk getStuk(int rijIndex, int kolomIndex) {
		return rijen[rijIndex][kolomIndex];
	}
	
	/**
	 * @pre | rijen != null
	 * @pre | rijen.length == 8
	 * @pre | Arrays.stream(rijen).allMatch(rij -> rij != null && rij.length == 8 &&
	 *      |     Arrays.stream(rij).allMatch(stuk -> stuk == null || stuk.getBord() == null))
	 * @pre | Arrays.stream(rijen).flatMap(rij -> Arrays.stream(rij)).filter(stuk -> stuk != null).distinct().count() ==
	 *      | Arrays.stream(rijen).flatMap(rij -> Arrays.stream(rij)).filter(stuk -> stuk != null).count()
	 *      
	 * @mutates | ...Arrays.stream(rijen).flatMap(rij -> Arrays.stream(rij)).toList()
	 * 
	 * @post | Arrays.deepEquals(getRijen(), rijen)
	 */
	public Bord(Stuk[][] rijen) {
		for (int rijIndex = 0; rijIndex < 8; rijIndex++)
			for (int kolomIndex = 0; kolomIndex < 8; kolomIndex++) {
				Stuk stuk = rijen[rijIndex][kolomIndex];
				if (stuk != null) {
					stuk.bord = this;
					stuk.rijIndex = rijIndex;
					stuk.kolomIndex = kolomIndex;
					this.rijen[rijIndex][kolomIndex] = stuk;
				}
			}
	}
	
	public Iterator<Object> iterator() {
		return new Iterator<>() {
			int rijIndex;
			int kolomIndex;
			@Override
			public boolean hasNext() {
				return rijIndex < 8;
			}
			@Override
			public Object next() {
				Object result = rijen[rijIndex][kolomIndex++];
				if (kolomIndex == 8) {
					kolomIndex = 0;
					rijIndex++;
				}
				return result;
			}
		};
	}
	
	public void forEachPaard(Consumer<? super Paard> consumer) {
		for (Iterator<Object> i = iterator(); i.hasNext(); )
			if (i.next() instanceof Paard p)
				consumer.accept(p);
	}
	
	public Stream<Paard> paardenOpWitteRijenStream() {
		return Arrays.stream(rijen)
				     .filter(rij -> Arrays.stream(rij).allMatch(stuk -> stuk == null || stuk.isWit))
				     .flatMap(rij -> Arrays.stream(rij).flatMap(stuk -> stuk instanceof Paard p ? Stream.of(p) : null));
	}
	
}
