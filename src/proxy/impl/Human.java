package proxy.impl;

import proxy.interfaces.IRun;
import proxy.interfaces.ISleep;
 
public class Human implements IRun, ISleep{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("--------------奔跑-----------------");
	}

	@Override
	public void sleep() {
		// TODO Auto-generated method stub
		System.out.println("--------------睡觉-----------------");
	}

}
