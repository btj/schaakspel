package schaakspel.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import schaakspel.Bord;
import schaakspel.Paard;
import schaakspel.Pion;
import schaakspel.Stuk;

class SchaakspelTest {

	Paard paard0 = new Paard(true);
	Paard paard1 = new Paard(true);
	Paard paard2 = new Paard(false);
	Pion pion0 = new Pion(false);
	Pion pion1 = new Pion(true);
	Pion pion2 = new Pion(false);
	
	Stuk[][] rijen = new Stuk[8][8];
	{
		rijen[0][1] = paard1;
		rijen[1][1] = pion1;
		rijen[2][1] = pion2;
		rijen[2][2] = paard2;
	}
	Bord bord = new Bord(rijen);

	@Test
	void testPaardConstructor() {
		assertTrue(paard0.isWit());
		assertNull(paard0.getBord());
	}
	
	@Test
	void testPionConstructor() {
		assertFalse(pion0.isWit());
		assertNull(pion0.getBord());
	}
	
	@Test
	void testBordConstructor() {
		assertArrayEquals(rijen, bord.getRijen());
		assertArrayEquals(rijen[1], bord.getRij(1));
		assertSame(bord, paard1.getBord());
		assertEquals(0, paard1.getRijIndex());
		assertEquals(1, paard1.getKolomIndex());
	}
	
	@Test
	void testPionKanBewegenNaar() {
		assertTrue(pion1.kanBewegenNaar(2, 2));
		assertFalse(pion1.kanBewegenNaar(2, 1));
		assertFalse(pion1.kanBewegenNaar(0, 1));
		assertFalse(pion1.kanBewegenNaar(7, 1));
		assertFalse(pion1.kanBewegenNaar(1, 7));
		assertFalse(pion1.kanBewegenNaar(2, 7));
	}
	
	@Test
	void testPaardKanBewegenNaar() {
		assertTrue(paard1.kanBewegenNaar(2, 2));
		assertFalse(paard1.kanBewegenNaar(2, 1));
	}
	
	@Test
	void testVerplaatsNaar() {
		pion1.verplaatsNaar(2, 2);
		assertNull(paard2.getBord());
		assertSame(bord, pion1.getBord());
		assertEquals(2, pion1.getRijIndex());
		assertEquals(2, pion1.getKolomIndex());
		assertNull(bord.getStuk(1, 1));
	}
	
	@Test
	void testIterator() {
		ArrayList<Object> vakken = new ArrayList<>();
		for (Iterator<Object> i = bord.iterator(); i.hasNext(); )
			vakken.add(i.next());
		assertEquals(64, vakken.size());
		for (int rijIndex = 0; rijIndex < 8; rijIndex++)
			for (int kolomIndex = 0; kolomIndex < 8; kolomIndex++)
				assertEquals(rijen[rijIndex][kolomIndex], vakken.get(rijIndex * 8 + kolomIndex));
	}
	
	@Test
	void testForEachPaard() {
		ArrayList<Paard> paarden = new ArrayList<>();
		bord.forEachPaard(paard -> paarden.add(paard));
		assertEquals(List.of(paard1, paard2), paarden);
	}
	
	@Test
	void testPaardenOpWitteRijenStream() {
		List<Paard> paardenOpWitteRijen = bord.paardenOpWitteRijenStream().toList();
		assertEquals(List.of(paard1), paardenOpWitteRijen);
	}

}
