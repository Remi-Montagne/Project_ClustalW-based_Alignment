import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.*;
import javax.swing.text.LayeredHighlighter.LayerPainter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import java.util.*;

public class Affichage extends JFrame {

//Attributs	
	private JSplitPane split;
	private JTextPane textPaneSeq =  new JTextPane();
	private JScrollPane scroll;      
	private JPanel panSeq = new JPanel(new BorderLayout());
	private Alignement alignementFinal;


//Constructeurs
	public Affichage(Alignement alignementFinal) 
	{
		this("Alignement multiple de sequences",300000,300000,alignementFinal);
	}

	public Affichage(String titre, int largeur, int hauteur, Alignement alignementFinal)
	{      
		this.alignementFinal = alignementFinal;       
		this.setTitle(titre);
		this.setSize(largeur,hauteur);
		this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		StyledDocument doc = textPaneSeq.getStyledDocument();

		char[][] ali = alignementFinal.getAlignementFinal();

		int nbSequences = ali.length;
		System.out.println("nbSequences = " + nbSequences);
		int tailleAlignement = ali[0].length;

		int [] avanceeSequences = new int[nbSequences];

		for(int i = 0; i < nbSequences; i++)
		{
			avanceeSequences[i] = 0;
		}

		int nbLettresParLigne = 100;
		int debut, fin;
		for(debut = 0; debut < tailleAlignement; debut+= nbLettresParLigne)
		{
			if(debut < tailleAlignement)
			{
				fin = (debut+nbLettresParLigne < tailleAlignement)?debut+nbLettresParLigne : tailleAlignement;
				affichageBorne(ali,debut,fin,doc,avanceeSequences);
			}
		}

		scroll = new JScrollPane(textPaneSeq,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.getContentPane().add(scroll);

    this.setVisible(true);

	}
	
	public void affichageBorne(char[][] ali, int debut, int fin, StyledDocument doc,int [] avanceeSequences) 
	{
		int nbSeq = ali.length;
		char c;
		Color couleurA = new Color(64,128,255);
		StyleAffichage styleA = new StyleAffichage("Courier",15,couleurA);
		StyleAffichage styleT = new StyleAffichage("Courier",15,Color.GREEN);
		StyleAffichage styleG = new StyleAffichage("Courier",15,Color.CYAN);
		StyleAffichage styleC = new StyleAffichage("Courier",15,Color.MAGENTA);
		StyleAffichage styleDefaut = new StyleAffichage("Courier",15,Color.WHITE);
		try{
			for(int j = 0; j < nbSeq; j++) {
			// insertion nom sequence	doc.insertString(doc.getLength(),seq.get(Integer.valueOf(j)).getSurnom(),listeAttributs[26]);
				for(int i = debut; i < fin; i++) {
						c = ali[j][i];
						if(Character.isLetter(c)) 
						{
							avanceeSequences[j]++;
						}
						
						if(c == 'A') 
						{
								doc.insertString(doc.getLength(),"A",styleA);
						}
						else if(c == 'T') 
						{
							doc.insertString(doc.getLength(),"T",styleT);
						}
						else if(c == 'G') 
						{
							doc.insertString(doc.getLength(),"G",styleG);
						}

						else if(c == 'C') 
						{
							doc.insertString(doc.getLength(),"C",styleC);
						}
						else
						{
							doc.insertString(doc.getLength(),Character.toString(c),styleDefaut);
						}				
					}
					doc.insertString(doc.getLength(),"  ",styleDefaut);
					doc.insertString(doc.getLength(),String.valueOf(avanceeSequences[j]),styleDefaut);
					doc.insertString(doc.getLength(),"\n",styleDefaut);
				}
				doc.insertString(doc.getLength(),"\n\n\n",styleDefaut);
		} catch(Exception e) {};
	}

}

