package cc.rules.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RuleResultTest {

	private static final int endpos = 10;
	private static final int startpos = 1;
	private static final boolean passed = true;
	private static final String source = "source code";

	private RuleResult rr;

	@Before
	public void setup() {
		rr = new RuleResult();
		rr.setEndPosition(endpos);
		rr.setStartPosition(startpos);
		rr.setPassed(passed);
		rr.setRule(new TestRule());
		rr.setSource(source);
	}

	@Test
	public void testGetStartPosition() {
		assertEquals(rr.getStartPosition(), startpos);
	}

	@Test
	public void testGetEndPosition() {
		assertEquals(rr.getEndPosition(), endpos);
	}

	@Test
	public void testGetPassed() {
		assertEquals(rr.passed(), passed);
	}

	@Test
	public void testGetRule() {
		assertTrue(rr.getRule() instanceof TestRule);
	}

	@Test
	public void testGetSource() {
		assertEquals(rr.getSource(), source);
	}
}
