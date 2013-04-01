package evolutionChecker.impl;


class Control {
	/*private static String ARG1;
	private static String ARG2;
	private String rulesClassPath = "evolutionChecker/impl/rules/";*/
	private int MAXARGS = 5;
	private String args[];
	
	
	Control(){
		this.args = new String[MAXARGS];
	}
	
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		if(args.length!=3){
			System.err.println("Usage: java Control arg1 arg2");
			System.exit(-1);
		}
		Control c = new Control();
		
	
		
		c.manager(args[0],args[1],args[2]);
		System.out.println("tempo:"+((double)(System.currentTimeMillis()-time)/1000)+" segundos");
	}
	
	
	protected int manager(String oldMetadataPath, String newMetadataPath , String rulesFile){
	    	
		//get drools provided interface
		droolsmgr.spec.prov.IManager manager = droolsmgr.impl.ComponentFactory.createInstance();
		droolsmgr.spec.prov.IDroolsMgt inferenceEngine = (droolsmgr.spec.prov.IDroolsMgt) manager.getProvidedInterface( "IDroolsMgt" );
		
		String[] params = new String[2];
		params[ 0 ] = oldMetadataPath;
		params[ 1 ] = newMetadataPath;
		//System.out.println("assert old metadata: " + res1 + "\nassert new metadata:"+ res2 );
		//fire the evolution rules
		Integer newVersion = (Integer) inferenceEngine.fireRules( rulesFile, params );
		if( newVersion != null )
			return newVersion.intValue();
		else{
			System.err.println("'newVersion' is null");
			return -1;
		}
		/*}*/
		
	}	
	
	

}
