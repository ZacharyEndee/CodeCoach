package code_coach.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class RuleModelTest {

	private RuleModel model;

	@Before
	public void setup() {
		model = new RuleModel();
	}

	@Test
	public void testGetSetRuleName() {
		model.setRuleName("name");
		assertEquals("name", model.getRuleName());
	}

	@Test
	public void testGetSetRuleType() {
		model.setRuleType("type");
		assertEquals("type", model.getRuleType());
	}

	@Test
	public void testGetSetPackage() {
		model.setRulePackage("name");
		assertEquals("name", model.getRulePackage());
	}

}
