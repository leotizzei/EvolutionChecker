package evolutionChecker.impl;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;

import javax.xml.parsers.SAXParser;


public class MetadataReader extends DefaultHandler{
	
	private static String version;
	
	public String getMetadataVersion(String filename)  {
	   //System.out.println("preAsset  = "+filename);
	    // Use an instance of ourselves as the SAX event handler
	   DefaultHandler handler = new MetadataReader();
	   // Use the default (non-validating) parser
	   SAXParserFactory factory = SAXParserFactory.newInstance();
	   try {
		  // Parse the input
		  SAXParser saxParser = factory.newSAXParser();
		 // System.out.println("filename="+filename);
		  saxParser.parse( new File(filename), handler);
		  
		  
	   } catch (Throwable t) {
		   t.printStackTrace();
	   }
	   return this.getVersion();
	}

	public void setVersion( String v) {
		version = v;
	} 
	

	public String getVersion() {
		return version;
	}
	
	    //===========================================================
	    // SAX DocumentHandler methods
	    //===========================================================

	    public void startDocument()
	    throws SAXException
	    {
	        
	    }

	    public void endDocument()
	    throws SAXException
	    {
	       ;
	    }

	    public void startElement(String namespaceURI,
	                             String lName, // local name
	                             String qName, // qualified name
	                             Attributes attrs)
	    throws SAXException
	    {
	      
	    	String eName = lName; // element name
	        if ("".equals(eName)) eName = qName; // namespaceAware = false
	      //  emit(eName); nl();
	        if(eName.equalsIgnoreCase("asset")){ //so imprime elementos topic
	        	
	        	if (attrs != null) {
	        		for (int i = 0; i < attrs.getLength(); i++) {
	        			String aName = attrs.getQName(i); // Attr name
	        			//System.out.println("atribute "+aName+" = "+attrs.getValue(i));
	        			if(aName.equalsIgnoreCase("version")){
	        				//System.out.println("achou version");
	        				this.setVersion(attrs.getValue(i));
	        				
	        			}
	        		}
	        	}
	        	
	        }
	       
	    }

	    public void endElement(String namespaceURI,
	                           String sName, // simple name
	                           String qName  // qualified name
	                          )
	    throws SAXException
	    {
	     //   emit("</"+sName+">");
	    }

	    public void characters(char buf[], int offset, int len)
	    throws SAXException
	    {
	       // String s = new String(buf, offset, len);
	        //emit(s);
	    }

	   
	   
	


}
