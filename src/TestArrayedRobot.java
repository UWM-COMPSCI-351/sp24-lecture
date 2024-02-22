import java.util.Comparator;
import java.util.function.Supplier;

import edu.uwm.cs351.ArrayedRobot;
import edu.uwm.cs351.ArrayedRobot.FunctionalPart;
import junit.framework.TestCase;

public class TestArrayedRobot extends TestCase {
	protected void assertException(Class<? extends Throwable> c, Runnable r) {
		try {
			r.run();
			assertFalse("Exception should have been thrown",true);
		} catch (Throwable ex) {
			assertTrue("should throw exception of " + c + ", not of " + ex.getClass(), c.isInstance(ex));
		}
	}
	
	public static Comparator<FunctionalPart> nonDiscrimination = (p1,p2) -> 0;
	public static Comparator<FunctionalPart> byFunction = (p1,p2) -> p1.getFunction().compareTo(p2.getFunction());
	public static Comparator<FunctionalPart> reverseByFunction = (p2,p1) -> p1.getFunction().compareTo(p2.getFunction());
	public static Comparator<FunctionalPart> olderFirst = (p1,p2) -> p1.getId() - p2.getId();
	public static Comparator<FunctionalPart> byFunctionThenId = (p1,p2) -> {
		int c = p1.getFunction().compareTo(p2.getFunction());
		if (c != 0) return c;
		return p1.getId() - p2.getId();
	};
	public static Comparator<FunctionalPart> reverseByFunctionThenId = (p2,p1) -> byFunctionThenId.compare(p1,p2);
	
	ArrayedRobot self;
	
	@Override
	protected void setUp() {
		try {
			assert self.getFirst() == null;
			assertFalse("Assertions must be enabled to run these tests", true);
		} catch (NullPointerException ex) {
			assertTrue("All good", true);
		}
		self = new ArrayedRobot(nonDiscrimination);
	}

	protected String asString(Supplier<?> supp) {
		try {
			return "" + supp.get();
		} catch(RuntimeException ex) {
			return ex.getClass().getSimpleName();
		}
	}
	
	protected String asString(Runnable r) {
		return asString(() -> { r.run(); return "void"; });
	}

	
	/// test0x: tests of getFirst(), addPart without comparators
	
	public void test00() {
		assertNull(self.getFirst());
	}
	
	public void test01() {
		FunctionalPart p1 = new FunctionalPart();
		self.addPart("ARM", p1);
		assertEquals("ARM", p1.getFunction());
	}
	
	public void test03() {
		FunctionalPart p1 = new FunctionalPart();
		self.addPart("ARM", p1);
		assertSame(p1, self.getFirst());
	}
	
	public void test05() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		assertTrue(self.addPart("ARM", p2));
		assertSame(p2, self.getFirst());
	}

	public void test06() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertSame(p1, self.getPart(null, 0));
		assertSame(p2, self.getPart(null, 1));
	}

	public void test07() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertEquals("ARM", p1.getFunction());
		assertEquals("LEG", p2.getFunction());
	}
	
	public void test08() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		self.addPart("", p1);
		self.addPart("", p2);
		self.addPart("", p3);
		assertSame(p3, self.getFirst());
	}
	
	public void test09() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);
		
		assertSame(p1, self.getFirst());
		assertSame(p2, self.getPart(null, 1));
		assertSame(p3, self.getPart(null, 2));
		assertSame(p4, self.getPart(null, 3));
		assertNull(self.getPart(null, 1));
	}
	
	
	/// test2x/3x: getPart tests without comparators
	
	public void test20() {
		assertNull(self.getPart(null, 0));
	}
	
	public void test21() {
		FunctionalPart p1 = new FunctionalPart();
		self.addPart("ARM", p1);
		assertSame(p1, self.getPart(null, 0));
	}
	
	public void test22() {
		FunctionalPart p1 = new FunctionalPart();
		self.addPart("ARM", p1);
		assertNull(self.getPart(null, 1));
	}
	
	public void test23() {
		FunctionalPart p1 = new FunctionalPart();
		self.addPart("ARM", p1);
		assertNull(self.getPart("arm", 0));
	}
	
	public void test24() {
		FunctionalPart p1 = new FunctionalPart();
		self.addPart("LEG", p1);
		assertSame(p1, self.getPart(new String("LEG"), 0));
	}
	
	public void test25() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertSame(p1, self.getPart(null, 0));
	}
	
	public void test26() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertSame(p2, self.getPart(null, 1));
	}
	
	public void test27() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertSame(p1, self.getPart(new String("ARM"), 0));
	}
	
	public void test28() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertSame(p2, self.getPart("LEG", 0));
	}
	
	public void test29() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertNull(self.getPart("HEAD", 0));
	}
	
	public void test30() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertNull(self.getPart("ARM", 1));		
	}

	public void test31() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertNull(self.getPart("LEG", 1));
	}
	
	public void test32() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertNull(self.getPart(null, 2));
	}
	
	public void test33() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);
		assertSame(p1, self.getPart("ARM", 0));
	}
	
	public void test34() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);
		assertSame(p3, self.getPart(new String("ARM"), 1));
	}
	
	public void test35() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);
		assertSame(p2, self.getPart("LEG", 0));
	}
	
	public void test36() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);
		assertSame(p4, self.getPart(new String("HEAD"), 0));
	}
	
	public void test37() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);
		assertNull(self.getPart("ARM", 2));
		assertNull(self.getPart("LEG", 1));
		assertNull(self.getPart("HEAD", 1));
	}
	
	public void test38() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);
		assertSame(p1, self.getPart(null, 0));
		assertSame(p2, self.getPart(null, 1));
		assertSame(p3, self.getPart(null, 2));
		assertSame(p4, self.getPart(null, 3));
		assertNull(self.getPart(null, 4));
	}
	
	public void test39() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);
		assertException(IllegalArgumentException.class, () -> self.getPart(null, -1));
		assertException(IllegalArgumentException.class, () -> self.getPart("ARM", -2));
	}
	
	
	/// test6x: tests of addPart with comparators
	
	public void test60() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self = new ArrayedRobot(nonDiscrimination);
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertSame(p1, self.getFirst());
	}
	
	public void test61() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self = new ArrayedRobot(byFunction);
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertSame(p1, self.getFirst());
		assertSame(p2, self.getPart(null, 1));
	}
	
	public void test62() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self = new ArrayedRobot(reverseByFunction);
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		assertSame(p2, self.getFirst());
		assertSame(p1, self.getPart(null, 1));
	}
	
	public void test63() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		self.addPart("arm", p1);
		self.setComparator(olderFirst);
		self.addPart("leg", p2);
		self.addPart("head", p3);
		assertSame(p1, self.getFirst());
		assertSame(p2, self.getPart(null, 1));
		assertSame(p3, self.getPart(null, 2));
	}
	
	
	public void test66() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self = new ArrayedRobot(byFunction);
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);

		assertSame(p1, self.getPart(null, 0));
		assertSame(p3, self.getPart(null, 1));
		assertSame(p4, self.getPart(null, 2));
		assertSame(p2, self.getPart(null, 3));
		assertNull(self.getPart(null, 4));
	}
	
	public void test67() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.setComparator(byFunctionThenId);
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("HEAD", p4);

		assertSame(p1, self.getPart(null, 0));
		assertSame(p3, self.getPart(null, 1));
		assertSame(p4, self.getPart(null, 2));
		assertSame(p2, self.getPart(null, 3));
		assertNull(self.getPart(null, 4));
	}
	
	public void test68() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.setComparator(byFunction);
		self.addPart("ARM", p1);
		self.addPart("ARM", p2);
		self.addPart("ARM", p3);
		self.addPart("ARM", p4);

		assertSame(p1, self.getPart(null, 0));
		assertSame(p2, self.getPart(null, 1));
		assertSame(p3, self.getPart(null, 2));
		assertSame(p4, self.getPart(null, 3));
		assertNull(self.getPart(null, 4));
	}
	
	public void test69() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.setComparator(byFunctionThenId);
		self.addPart("ARM", p4);
		self.addPart("ARM", p3);
		self.addPart("ARM", p2);
		self.addPart("ARM", p1);

		assertSame(p1, self.getPart(null, 0));
		assertSame(p2, self.getPart(null, 1));
		assertSame(p3, self.getPart(null, 2));
		assertSame(p4, self.getPart(null, 3));
		assertNull(self.getPart(null, 4));
	}
	
	
	/// test8x/9x: tests of setComparator
	
	public void test80() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		self.setComparator(nonDiscrimination);
		self.addPart("LEG", p1);
		self.addPart("ARM", p2);
		assertSame(p1, self.getFirst());
		self.setComparator(byFunction);
		assertSame(p2, self.getPart(null, 0));
		assertSame(p1, self.getPart(null, 1));
		assertNull(self.getPart(null, 2));
	}
	
	public void test89() {
		FunctionalPart p1 = new FunctionalPart();
		FunctionalPart p2 = new FunctionalPart();
		FunctionalPart p3 = new FunctionalPart();
		FunctionalPart p4 = new FunctionalPart();
		self.setComparator(reverseByFunction);
		self.addPart("ARM", p1);
		self.addPart("LEG", p2);
		self.addPart("ARM", p3);
		self.addPart("LEG", p4);

		assertSame(p2, self.getPart(null, 0));
		assertSame(p4, self.getPart(null, 1));
		assertSame(p1, self.getPart(null, 2));
		assertSame(p3, self.getPart(null, 3));
		assertNull(self.getPart(null, 4));

		self.setComparator(byFunctionThenId);
		assertSame(p1, self.getPart(null, 0));
		assertSame(p3, self.getPart(null, 1));
		assertSame(p2, self.getPart(null, 2));
		assertSame(p4, self.getPart(null, 3));
		assertNull(self.getPart(null, 4));
	}
}

