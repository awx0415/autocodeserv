package proxy;

import java.lang.reflect.Proxy;

import proxy.impl.Human;
import proxy.interfaces.IRun;
import proxy.interfaces.ISleep;
import proxy.proxy.HumanProxy;
import proxy.proxy.HumanProxy2;
 
public class ProxyTest {

	public static void main(String[] args) throws Exception
	{
		Human human = new Human();
		HumanProxy proxy = new HumanProxy(human);
		IRun run = (IRun)Proxy.newProxyInstance(Human.class.getClassLoader(), Human.class.getInterfaces(), proxy);
		run.run();
		
		HumanProxy2 proxy2 = new HumanProxy2(human);
		IRun run2 = (IRun)Proxy.newProxyInstance(Human.class.getClassLoader(), Human.class.getInterfaces(), proxy2);
		run2.run();
	}
}
