package evolutionChecker.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

class Persist {

	private static int TAM_BUFFER = 64768;
	
	public void materialize(Node n,String newversion){
		try {
			/*Date d = new Date();
			String data = d.toString();
			String[] datas = data.split(" ");
			System.out.println("datas[3]="+datas[3]);
			datas[3].replace(':','_');*/
			FileOutputStream fos = new FileOutputStream("newmetadata.xml");
			String text = n.getNodeName();
			text = "<"+text+">";
		
			byte[] nos = new byte[TAM_BUFFER];
			traverse(n,nos,fos,newversion);
			 
			//fos.write(nos);
			fos.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		

	private void traverse(Node n,byte[] nos,FileOutputStream fos,String newversion) throws IOException{
		if(n==null)
			return;
		else{
			String text;
			if(!n.getNodeName().equals("#text")&&(!n.getNodeName().equals("#comment"))){
				
				text = "<" + n.getNodeName() + " ";
				NamedNodeMap nnm = n.getAttributes();
				if(n.getNodeName().equalsIgnoreCase("synchronizationcontract")||
						n.getNodeName().equalsIgnoreCase("qualityofservicecontract")){
						text = text+">"+n.getTextContent(); 
				}
				else{
					if(nnm!=null)
						for(int i=0;i<nnm.getLength();i++){
							Node aux = nnm.item(i);
							if(aux.getNodeName().equalsIgnoreCase("version")&&n.getNodeName().equalsIgnoreCase("asset"))
								text = text + aux.getNodeName() +"=\""+ newversion+"\" ";
							else
								text = text + aux.getNodeName() +"=\""+aux.getNodeValue()+"\" ";
						}
					text = text + ">";
				}
				nos = text.getBytes();
				fos.write(nos);
				
			}
			traverse(n.getFirstChild(),nos,fos,newversion);
			if(!n.getNodeName().equals("#text")&&(!n.getNodeName().equals("#comment"))){
				text = "</"+n.getNodeName()+">";
				nos = text.getBytes();
				fos.write(nos);
			}
			traverse(n.getNextSibling(),nos,fos,newversion);
		}
			
		
	}
		
}
