import java.util.ArrayList;

public class UPGMA{
	
	//---------------------------------------
	// attributs
	//---------------------------------------
	
	private Noeud arbreGuide;
	private Sequences seqs;

	//---------------------------------------
	// constructeurs
	//---------------------------------------

	public UPGMA(Sequences seqs)
	{
		this.seqs = seqs;
		int l_max = seqs.getListeSeq().size(); //nombre de sequences
		double[][] matDist = new double[l_max][l_max]; //matrice des distances permettant de construire l'arbre guide
		int i, j, l; //variables d'itération
		MatriceAlignSimple m; //matrice d'alignement qui sera utilisee pour aligner les paires de sequences entre elles
		MatriceScoreUnitaire scoreUnit = new MatriceScoreUnitaire(); //matrice de score unitaire
		double max; int i_max, j_max; //le score max de la matrice de distance et ses coordonnees
		boolean bol = true;
		
		max = 0;
		i_max = 0;
		j_max = 0;
		l = l_max;
		
		/* On cree toutes les feuilles (noeud) de l'arbre-guide. Chacune correspond a une sequence.
		 * On les met dans une liste puis les feuilles seront liées les unes aux autres au fur et à mesure.*/
		ArrayList<Noeud> listeNoeuds = new ArrayList<Noeud>();
		
		for(i = 0; i < l; i++)
		{
			Noeud n = new Noeud(seqs.getSequence(i));
			listeNoeuds.add(n);
		}
		
		/* la moitié supérieur de la matrice est remplie avec des scores d'alignement*/
		for(i = 0; i < l; i++)
		{
			for(j = i+1; j < l; j++)
			{
				m = new MatriceAlignSimple(seqs.getSequence(i), seqs.getSequence(j), scoreUnit);
				matDist[i][j] = m.getScore();
			}
		}
		
		/*on construit l'arbre et on met a jour la matrice.*/
		while(l > 1)
		{
			/*On affiche la matrice de distance pour verification*/
			/*System.out.println("\n" + l + " sequences dans la liste --> " + (l-1) +" :\n");*/
			
			/*On cherche d'abord le max de la matrice des distances*/
			max = 0;
			for(i = 0; i < l; i++)
			{
				for(j = i+1; j < l; j++)
				{
					if(matDist[i][j] > max)
					{
						max = matDist[i][j];
						i_max = i;
						j_max = j;
					}
				}
			}
			
			/* On cree un noeud parent qui a les sequences avec le meilleur score comme fg et fd.
			 * On retire de la liste les noeuds i_max et j_max, on replace le parent à la place i*/
			Noeud p = new Noeud(listeNoeuds.get(i_max), listeNoeuds.get(j_max));
			listeNoeuds.set(i_max, p);
			listeNoeuds.remove(j_max);
			
			
			/*On met a jour la matrice de scores*/
			/*On recalcule les scores a changer de la matrice*/
			for(j = i_max+1; j < l; j++)
			{
				matDist[i_max][j] = (matDist[i_max][j] + matDist[j_max][j]) / 2;
			}
			
			/*l diminue de 1*/
			l = l-1;
			
			/*tout ce qui est à droite de j_max est decale d'une case vers la gauche : permet de supprimer la colonne j_max*/
			for(i = 0; i < l_max; i++)
			{
				for(j = j_max; j < l_max-1; j++)
				{
					matDist[i][j] = matDist[i][j+1];
				}
			}
			
			/*tout ce qui en dessous de j_max est decale d'une case vers le haut : permet de supprimer ligne j_max*/
			for(i = j_max; i < l_max-1; i++)
			{
				for(j = i_max; j < l_max; j++)
				{
					matDist[i][j] = matDist[i+1][j];
				}
			}
			
			
			/*on remet 0 dans les diagonales*/
			for(i=0; i < l; i++)
			{
				matDist[i][i] = 0;
			}
			
			this.arbreGuide = p;			
		}
	}
	
	public void afficheMatDist(double[][]matDist, int l)
	{
		int i, j;
		
		for(i = 0; i < l; i++)
		{
			for(j = 0; j< i; j++)
			{
				System.out.print(" 0.0 ");
			}
			
			for(j = i; j < l; j++)
			{
				System.out.print(" " + matDist[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
		
	public Noeud getArbreGuide()
	{
		return arbreGuide;
	}
		
	public void afficheArbreGuide(Noeud n, int count) 
	{
		int i;
		String spacer = "-----";
			
		if(n != null)
		{
			count++;
			
			if(n.getCle() != null)
			{
				for(i = 0; i < count; i++)
				{
					System.out.print(spacer);
				}
				System.out.println(n.getCle().getName());
			}
			
			else
			{
				for(i = 0; i < count; i++)
				{
					System.out.print(spacer);
				}
				
				System.out.println("O");
			}
					
			if(n.getfg() != null)
			{
				afficheArbreGuide(n.getfg(), count);
			}
				
			if(n.getfd() != null)
			{
				afficheArbreGuide(n.getfd(), count);
			}
			
			count--;
		}
	} 
}
