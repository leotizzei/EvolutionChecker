package evolutionChecker.impl;

import droolsmgr.spec.prov.ImpactLevel;
import evolutionChecker.spec.prov.ICheckEvolutionRules;

class Facade_ICheckEvolutionsRules implements ICheckEvolutionRules{

	/**
	 * @param args
	 */
	
	private String rulesFile;
	
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		Facade_ICheckEvolutionsRules cc = new Facade_ICheckEvolutionsRules();
/*		if(args.length<3){
			System.out.println("Usage: java <arg1> <arg2> <rule file>");
			System.exit(-1);
		}
*/			
		System.out.println("starting evolution checker...");
		
		
		String rulesFile = "/home/lsd/ra001973/workspace2/EvolutionChecker/src/rules/fenixRule.drl";
		//String rulesFile = "/home/lsd/ra001973/workspace2/EvolutionChecker/src/rules/teste.drl";
		
		// preAsset,posAsset
		
		String preAsset = "/home/lsd/ra001973/workspace2/estudoDeCaso_oldPetstore/oldPetstore/oldPetstore.xml";
		//String preAsset = "/home/lsd/ra001973/workspace2/EvolutionChecker/src/java/evolutionChecker/impl/captcha.xml";
		
		String posAsset = "/home/lsd/ra001973/workspace2/estudoDeCaso_oldPetstore/oldPetstore/newPetstore.xml";
		//String posAsset = "/home/lsd/ra001973/workspace2/EvolutionChecker/src/java/evolutionChecker/impl/captchaCosmos.xml";
		
		
		String msg = cc.fireEvolutionRules( rulesFile, preAsset , posAsset );
		//System.out.println(msg);
		//componentpath, package
		System.out.println("execution time:"+((double)(System.currentTimeMillis()-time)/1000)+" seconds");
		
	}

	private String computeNewVersion(int impact, int[] intVersion){
		String newVersion;
		//		impact high
		if( impact == ImpactLevel.HIGH.eval() ){
			int aux = intVersion[0] + 1;
			newVersion = aux + ".0.1";
		}
		else{
			//impact medium
			if( impact == ImpactLevel.MEDIUM.eval() ){
				int aux = intVersion[1] + 1;
				newVersion = intVersion[0]+"."+aux+".1";
			}
			//impact low
			else{
				if( impact == ImpactLevel.LOW.eval() ){
					int aux = intVersion[2] + 1;
					newVersion = intVersion[0]+"."+intVersion[1]+"."+aux;
				}
				else{
					if( impact == ImpactLevel.NA.eval() ){
						System.out.println("Some Evolution Rules has been broken. You must start a new versioning tree");
						newVersion = "1.0.1";
					}
					else
						newVersion = null;
				}
				
			}
		}
		return newVersion;
	}

	
	/**
	 * this method define the new version number of the asset after analyse
	 * the changes in the asset.
	 * @param preAsset is the absolute path to the metadata of the old asset
	 * @param posAsset is the absolute path to the metadata of the new asset
	 */
	public String fireEvolutionRules(String evolutionRulesPath, String preAsset, String posAsset) {
		if( evolutionRulesPath != null ){
			//setRules(evolutionRulesPath);
			
			if( ( preAsset != null ) && ( posAsset != null) && ( evolutionRulesPath != null ) ){

				Control c = new Control();
				//get the change impact 
				int impact = c.manager( preAsset , posAsset , evolutionRulesPath );
				//System.out.println("impact is " + impact );
				ImpactLevel impactLevel  = ImpactLevel.qualitative( impact );
				//System.out.println( impactLevel.toString());

				if( impact == -1){
					System.err.println("The rules could not be fired");
					return null;
				}
				else{
					MetadataReader metareader = new MetadataReader();
					//get the version of the old asset
					String oldVersion = metareader.getMetadataVersion( preAsset );
					if( ( oldVersion != null ) && ( !oldVersion.equals("")) ){
						int[] intVersion = this.changeVersionToInt( oldVersion );
						String newVersion = this.computeNewVersion(impact, intVersion);
						if( newVersion != null){
							//this method creates a new metadata with the new version number 
							//create a new xml
							//this.putNewVersionNumber( posAsset , newVersion );
							System.out.println("The old version was " + oldVersion );
							System.out.println("The new version is " + newVersion);
							return newVersion;
						}
						else{
							return oldVersion;
						}
					}
					else{
						System.err.println("Error: The old version is either null or empty.");
						return null;
					}
				}
			}
			else{
				System.err.println(" Some parameters are null");
				return null;
			}

		}
		else{
			System.err.println("Evolution rules is null");
			return null;
		}
	}

	
	/**
	 * convert the version number from string to an array of int 
	 * @param version the version that will be converted
	 * @return an array of int that corresponds the version number
	 */
	private int[] changeVersionToInt(String version) throws NumberFormatException{
		int[] intVersion;

		if( version != null){
			//System.out.println("version "+version);
			String[] onlyNumbers = version.split( "\\." );
			intVersion = new int[onlyNumbers.length];
			for(int i =0;i<onlyNumbers.length;i++){
				intVersion[i] = Integer.parseInt(onlyNumbers[i]);
			}
		}
		else{
			intVersion = new int[1];
			intVersion[0] = -1;

		}
		return intVersion;

	}

	


}
