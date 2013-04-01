package evolutionCheckerGUI;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import evolutionChecker.impl.ComponentFactory;
import evolutionChecker.spec.prov.ICheckEvolutionRules;
import evolutionChecker.spec.prov.IManager;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;



public class ECheckerGUI extends JFrame {

	private final String LOG = "./evolutionCheckerLog.txt";
	
	private javax.swing.JFileChooser jFileChooser = null;
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel jLabelOldAsset = null;

	private JLabel jLabel1 = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JLabel jLabel2 = null;

	private JTextArea jTextArea = null;

	private JButton jButton3 = null;

	private JButton jOldAssetButton = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	

	/**
	 * This method initializes jButton1 - the new asset metadata button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(387, 191, 80, 25));
			jButton1.setText("File...");
		}
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				loadFile( jTextField1 );
			}
		});
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(374, 265, 81, 27));
			jButton2.setText("File...");
			
		}
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				loadFile( jTextField2 );
			}
		});
		return jButton2;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			
		}
		//jTextArea.setText("hello 2007");
		return jTextArea;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(222, 328, 197, 34));
			jButton3.setText("Run EvolutionChecker");
		}
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String result = runEvolutionChecker();
				if( result != null){
					jTextArea.setText( result );
				}
				else
					jTextArea.setText("Please, check your parameters and try to run again");
			}
		});
		return jButton3;
	}
	
	private String runEvolutionChecker(){
		// get old asset metadata path
		String oldAssetMetadata = jTextField.getText();
		//String oldAssetMetadata = "/home/lsd/ra001973/workspace2/EvolutionChecker/src/java/evolutionChecker/impl/captcha.xml";
		// get new asset metadata path
		String newAssetMetadata = jTextField1.getText();
		//String newAssetMetadata = "/home/lsd/ra001973/workspace2/EvolutionChecker/src/java/evolutionChecker/impl/captchaCosmos.xml";
		// get rules files path
		String evolutionRulesPath = jTextField2.getText();
		//String evolutionRulesPath = "/home/lsd/ra001973/workspace2/EvolutionChecker/src/rules/fenixRule.drl";
		
		IManager imanager = ComponentFactory.createInstance();
		ICheckEvolutionRules ievolution = (ICheckEvolutionRules) imanager.getProvidedInterface("ICheckEvolutionRules");
		
		
		File file = new File( LOG );
		if( file.exists() ){
			file.delete();
			boolean createFile;
			try {
				createFile = file.createNewFile();
				if( createFile )
					System.out.println("File "+file.getAbsolutePath() + " created");
				else
					System.out.println("File "+file.getAbsolutePath() + " wasn't created");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		String assetNumber = ievolution.fireEvolutionRules(evolutionRulesPath, oldAssetMetadata, newAssetMetadata);
		
		String result = "";
		try {
			
			BufferedReader br = new BufferedReader( new FileReader( file ));
			String aux2 = br.readLine();
			while( aux2 != null ){
				result += aux2 + "\n";
				//System.out.println("aux2 = " + aux2);
				aux2 = br.readLine();
			
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String finalResult = result + "\n\nNew component version is " + assetNumber; 
		return finalResult;
	}

	private void loadFile(JTextField jTF) {
		int state = getJFileChooser().showOpenDialog(this);
		System.out.println("entrou no loadfile ");
		if (state == JFileChooser.APPROVE_OPTION) {
			File f = getJFileChooser().getSelectedFile();
			
			jTF.setText( f.getAbsolutePath() );
			
			//setTitle(title);
		}
	}
	
	/**
	 * This method initializes jOldAssetButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJOldAssetButton() {
		if (jOldAssetButton == null) {
			jOldAssetButton = new JButton();
			jOldAssetButton.setBounds(new Rectangle(385, 114, 77, 24));
			jOldAssetButton.setText("File...");			
		}
		jOldAssetButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				loadFile( jTextField );
			}
		});
		return jOldAssetButton;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(9, 367, 631, 250));
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(19, 148, 617, 26));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(15, 228, 618, 24));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(14, 296, 619, 25));
		}
		return jTextField2;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ECheckerGUI thisClass = new ECheckerGUI();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public ECheckerGUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(653, 648);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(211, 27, 223, 39));
			jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
			jLabel.setText("EvolutionChecker");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(243, 264, 127, 28));
			jLabel2.setText("Select the rules file");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(190, 192, 195, 24));
			jLabel1.setText("Select the new asset metadata");
			jLabelOldAsset = new JLabel();
			jLabelOldAsset.setBounds(new Rectangle(189, 112, 194, 24));
			jLabelOldAsset.setText("Select the old asset metadata ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabelOldAsset, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getJOldAssetButton(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJTextField2(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jFileChooser
	 * 
	 * @return javax.swing.JFileChooser
	 */
	private javax.swing.JFileChooser getJFileChooser() {
		if (jFileChooser == null) {
			jFileChooser = new javax.swing.JFileChooser();
			jFileChooser.setMultiSelectionEnabled(false);
			//jFileChooser.setFileFilter(new XSLTFilter());
			System.out.println("Files and Directories");
		}
		
				
		return jFileChooser;
	}
	
}  //  @jve:decl-index=0:visual-constraint="2,4"
