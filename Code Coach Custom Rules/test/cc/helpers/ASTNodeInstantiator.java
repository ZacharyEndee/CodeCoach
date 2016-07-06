package cc.helpers;

import org.eclipse.jdt.core.dom.ASTNode;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

public class ASTNodeInstantiator {

	public static <T> ASTNode instantiate(Class<T> c) {
		Objenesis objenesis = new ObjenesisStd();
		ObjectInstantiator<T> inst = objenesis.getInstantiatorOf(c);
		return (ASTNode) inst.newInstance();
	}

}
