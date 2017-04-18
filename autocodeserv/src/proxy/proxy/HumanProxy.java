package proxy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
 
public class HumanProxy implements InvocationHandler{
	
	private Object target = null;;
	
	public HumanProxy(Object target)
	{
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		System.out.println("目标-----------" + target);
		method.invoke(target, args);
		
		return null;
	}

}
