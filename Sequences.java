import java.io.*;
import java.util.ArrayList;

/* Contains the Main */

public class Sequences {
	
	//---------------------------------------
	// attributs
	//---------------------------------------
	private ArrayList<Sequence> data;
	
	//---------------------------------------
	// methods
	//---------------------------------------
	
	public Sequences(String file) {
		BufferedReader br = null;
		String nom = null;
		String sequence = null;
		this.data = new ArrayList<Sequence>();
		try {
			br = new BufferedReader (new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.matches(">.*")) {
					nom = line;
				}
				else {
					sequence = line;
					Sequence s = new Sequence(nom, sequence);
					this.data.add(s);
				}
			}
			br.close();
		} catch(FileNotFoundException e) {
			//ce qu'il se passe quand le fichier n'est pas trouve
			System.out.println("Fail!!!!!!");
		} catch(IOException e) {
			System.out.println("Erreur de lecture");
		} catch (Exception e) {
			System.out.println("Toujours pas. N'oublie pas de préciser le fichier d'entree");
		}
	}
	
	/*recuperer la sequence numero i de la liste
	 *entier i entre 0 et n-1 où n est la longueur de la liste
	 */
	public Sequence getSequence(int i)
	{
		return data.get(i);
	}
	
	public ArrayList<Sequence> getListeSeq()
	{
		return data;
	}
	
	//---------------------------------------
	// main
	//---------------------------------------
	
	public static void main (String[] args) 
	{
		String input = null;
		Sequences donnees;
		UPGMA upgma;
		Alignement A;
		Affichage f;
		
		/*acquisition des donnees*/
		for (String s: args)
		{
			input = s;
		}
		
		donnees = new Sequences(input);
		upgma = new UPGMA(donnees);
		A = new Alignement(upgma);
		f = new Affichage(A);
		
	}
}
