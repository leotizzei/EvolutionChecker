package evolutionChecker.impl;

import evolutionChecker.spec.prov.IManager;


public class ComponentFactory {
	public static IManager createInstance(){
		
		IManager im = (IManager) new Manager();
		return im;
	}
	
	
	
}
