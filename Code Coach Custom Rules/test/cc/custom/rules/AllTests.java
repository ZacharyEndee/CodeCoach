package cc.custom.rules;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConstantsAreAllUppercaseTest.class, LowerCaseLetterAtStartOfMethodNameTest.class,
		MethodLengthTest.class })
public class AllTests {

}
