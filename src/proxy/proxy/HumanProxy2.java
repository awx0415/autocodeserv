package proxy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
 
public class HumanProxy2 implements InvocationHandler{
	
	private Object target = null;;
	
	public HumanProxy2(Object target)
	{
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		System.out.println("目标22222222222-----------" + target);
		method.invoke(target, args);
		
		return null;
	}

}
