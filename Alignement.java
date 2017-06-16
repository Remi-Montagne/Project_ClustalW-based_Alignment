public class Alignement {
	
	//-----------------------------------------------------------------
	// Attributs
	//-----------------------------------------------------------------	

	private char[][] alignement;


	//-----------------------------------------------------------------
	// Methodes
	//-----------------------------------------------------------------

	// Constructeur
	public Alignement(UPGMA M)
	{
		/*on prend la racine de l'arbre guide*/
		Noeud noeud = M.getArbreGuide();
		
		/*la fonction auxiliaire est appelée dessus*/
		alignementAux(noeud);
		
		this.alignement = noeud.getAli();
	}
		
	public void	alignementAux(Noeud noeud)
	{
		/*si le noeud n'est pas nul, on construit l'alignement des fils gch et dt puis on les aligne au niveau du noeud courant*/
		if(noeud != null) //if1
		{
			Noeud fg = noeud.getfg();
			Noeud fd = noeud.getfd();
			
			alignementAux(fg);
			alignementAux(fd);
			
			if(fg != null && fd != null) //if2
			{
				
				/*si fg et fd ne son pas des consensus, on appelle alignerSeqSeq() pour trouver l'alignement au niveau du noeu
				 * et on calcule le consensus pour donner a la cle (qui est une sequence)*/
				if(fg.getCle().getConsensus() == false && fd.getCle().getConsensus() == false)
				{
					char[][] result = alignerSeqSeq(fg.getCle(), fd.getCle());
					noeud.setAli(result);
					noeud.setCle(calculeConsensus(noeud.getAli()));
				}
				
				else if(fg.getCle().getConsensus() == true && fd.getCle().getConsensus() == false)
				{
					noeud.setAli(alignerConsSeq(fg.getCle(), fd.getCle(), fg.getAli()));	
					noeud.setCle(calculeConsensus(noeud.getAli()));
				}
				
				else if(fg.getCle().getConsensus() == true && fd.getCle().getConsensus() == false)
				{
					noeud.setAli(alignerConsSeq(fg.getCle(), fd.getCle(), fg.getAli()));
					noeud.setCle(calculeConsensus(noeud.getAli()));
				}
				
				else if(fg.getCle().getConsensus() == false && fd.getCle().getConsensus() == true)
				{
					noeud.setAli(alignerSeqCons(fg.getCle(), fd.getCle(), fd.getAli()));
					noeud.setCle(calculeConsensus(noeud.getAli()));
				}
				
				else // les deux fils sont en fait des consensus
				{
					noeud.setAli(alignerConsCons(fg.getCle(), fd.getCle(), fg.getAli(), fd.getAli()));
					noeud.setCle(calculeConsensus(noeud.getAli()));
				}
			}//fin if2
		
		}//fin if1

	
	}// constructeur
		 
		public char[][] alignerSeqSeq(Sequence seq1, Sequence seq2)
		{
			String sequence1 = seq1.getSeq();
			String sequence2 = seq2.getSeq();
			
			MatriceScoreUnitaire u = new MatriceScoreUnitaire();
			MatriceAlignSimple M = new MatriceAlignSimple(seq1, seq2, u);
			char[][] matParcours = M.getParcours();
			
			int col = sequence1.length();
			int lin = sequence2.length();
			int l = taille(matParcours);
			char[][] ali = new char[2][l-1];
					
			l--;
			while(l > 0)
			{
				if(matParcours[lin][col] == 'd')
				{
					ali[0][l-1] = sequence1.charAt(col-1);
					ali[1][l-1] = sequence2.charAt(lin-1);
					lin--;
					col--;
					l--;
				}
			
				else if(matParcours[lin][col] == 'g')
				{
					ali[0][l-1] = sequence1.charAt(col-1);
					ali[1][l-1] = '-';
					col--;
					l--;
				}
				
				else //matParcours[i][j] == "h"
				{
					ali[0][l-1] = '-';
					ali[1][l-1] = sequence2.charAt(lin-1);
					lin--;
					l--;
				}
			}
			
			return ali;
		}
		
		public char[][] alignerConsSeq(Sequence consensus, Sequence sequence, char[][] alignement_inter)
		{
			String cons = consensus.getSeq();
			String seq = sequence.getSeq();
			int col = cons.length(); /*taille du consensus*/
			int lin = seq.length();  /*taille de la sequence*/
			int prof = alignement_inter.length; /*nombre de lignes de l'alignement du consensus*/
			int l; /*taille du nouvel alignement a construire*/
			int curseur; /*curseur pour parcourir une colonne de l'alignement intermediaire associe au consensus*/
			
			MatriceScoreUnitaire u = new MatriceScoreUnitaire();
			MatriceAlignMultiple M = new MatriceAlignMultiple(consensus, sequence, u, alignement_inter);
			char[][] matParcours = M.getParcours();
			
			l = taille(matParcours);
			char[][] ali = new char[prof+1][l-1];
			
			l--;
			
			while(l > 0)
			{
				if(matParcours[lin][col] == 'd')
				{

					for (curseur = 0; curseur < prof; curseur++)
					{
						ali[curseur][l-1] = alignement_inter[curseur][col-1];
					}
					ali[prof][l-1] = seq.charAt(lin-1);
					lin--;
					col--;
					l--;
				}
			
				else if(matParcours[lin][col] == 'g')
				{

					for (curseur = 0; curseur < prof; curseur ++)
					{
						ali[curseur][l-1] = alignement_inter[curseur][col-1];
					}
					ali[prof][l-1] = '-';
					col--;
					l--;
				}
				
				else //matParcours[lin][col] == "h"
				{
					for (curseur = 0; curseur < prof; curseur ++)
					{
						ali[curseur][l-1] = '-';
					}
					ali[prof][l-1] = seq.charAt(lin);
					
					lin--;
					l--;
				}
			}
			
			return ali;	
		}
		
		public char[][] alignerSeqCons(Sequence sequence, Sequence consensus, char[][] alignement_inter)
		{
			String cons = consensus.getSeq();
			String seq = sequence.getSeq();
			int col = cons.length(); /*taille du consensus*/
			int lin = seq.length();  /*taille de la sequence*/
			int prof = alignement_inter.length; /*nombre de lignes de l'alignement du consensus*/
			int l; /*taille du nouvel alignement a construire*/
			int curseur; /*curseur pour parcourir une colonne de l'alignement intermediaire associe au consensus*/
			
			MatriceScoreUnitaire u = new MatriceScoreUnitaire();
			MatriceAlignMultiple M = new MatriceAlignMultiple(consensus, sequence, u, alignement_inter);
			char[][] matParcours = M.getParcours();
			
			l = taille(matParcours);
			char[][] ali = new char[prof+1][l-1];
			
			l--;
			
			while(l > 0)
			{
				if(matParcours[lin][col] == 'd')
				{

					for (curseur = 1; curseur <= prof; curseur++)
					{
						ali[curseur][l-1] = alignement_inter[curseur-1][col-1];
					}
					ali[0][l-1] = seq.charAt(lin-1);
					lin--;
					col--;
					l--;
				}
			
				else if(matParcours[lin][col] == 'g')
				{

					for (curseur = 1; curseur <= prof; curseur ++)
					{
						ali[curseur][l-1] = alignement_inter[curseur-1][col-1];
					}
					ali[0][l-1] = '-';
					col--;
					l--;
				}
				
				else //matParcours[lin][col] == "h"
				{
					for (curseur = 1; curseur <= prof; curseur ++)
					{
						ali[curseur][l-1] = '-';
					}
					ali[0][l-1] = seq.charAt(lin);
					
					lin--;
					l--;
				}
			}
			
			return ali;	
		}
		
		
		
		public char[][] alignerConsCons(Sequence consensus1, Sequence consensus2, char[][] alignement_inter1, char[][] alignement_inter2)
		{
			String cons1 = consensus1.getSeq();
			String cons2 = consensus2.getSeq();
			int col = cons1.length(); /*taille du consensus1*/
			int lin = cons2.length();  /*taille du consensus2*/
			int k1 = alignement_inter1.length; /*nombre de lignes du consensus1*/
			int k2 = alignement_inter2.length; /*nombre de lignes du consensus2*/
			int l; /*taille du nouvel alignement a construire*/
			int curseur1; /*curseur pour parcourir une colonne du consensus*/
			int curseur2; /*curseur pour parcourir une colonne du consensus*/
			
			MatriceScoreUnitaire u = new MatriceScoreUnitaire();
			MatriceAlignMultiple M = new MatriceAlignMultiple(consensus1, consensus2, u, alignement_inter1, alignement_inter2);
			char[][] matParcours = M.getParcours();
			
			l = taille(matParcours);
			char[][] ali = new char[k1+k2][l-1];
			
			l--;
			
			while(l >0)
			{
				if(matParcours[lin][col] == 'd')
				{
					for(curseur1 = 0; curseur1 < k1; curseur1++)
					{
						ali[curseur1][l-1] = alignement_inter1[curseur1][col-1];
					}
					for(curseur2 = k1; curseur2 < k1+k2; curseur2++)
					{
						ali[curseur2][l-1] = alignement_inter2[curseur2-k1][lin-1];
					}
					lin--;
					col--;
					l--;
				}
			
				else if(matParcours[lin][col] == 'g')
				{
					for (curseur1 = 0; curseur1 < k1; curseur1 ++)
					{
						ali[curseur1][l-1] = alignement_inter1[curseur1][col-1];
					}
					for (curseur2 = k1; curseur2 < k1+k2; curseur2 ++)
					{
						ali[curseur2][l-1] = '-';
					}
					col--;
					l--;
				}
				
				else //matParcours[i][j] == "h"
				{
					for (curseur1 = 0; curseur1 < k1; curseur1 ++)
					{
						ali[curseur1][l-1] = '-';
					}
					for (curseur2 = k1; curseur2 < k1+k2; curseur2 ++)
					{
						ali[curseur2][l-1] = alignement_inter2[curseur2-k1][lin-1];
					}
					lin--;
					l--;
				}
			}
			
			return ali;	
		}
		
	
	/*calcule la taille du tableau nécessaire pour l'alignement*/
	public int taille(char[][] matParcours)
	{
		int nb_cases = 0;
		int col = matParcours[0].length -1;
		int lin = matParcours.length -1;
		while(col >= 0 && lin >= 0)
		{
			if(matParcours[lin][col] == 'd')
			{
				nb_cases++;
				col--;
				lin--;
			}
			
			else if(matParcours[lin][col] == 'g')
			{
				nb_cases++;
				col--;
			}
			
			else //matParcours[i][j] == "h"
			{
				nb_cases++;
				lin--;
			}
		}

		return nb_cases;
	}
	
	/*établit le consensus d'un alignement*/
	public Sequence calculeConsensus(char[][] ali)
	{
		int i, j;
		int nb_col = ali[0].length;
		int nb_lign = ali.length;
		boolean bol = true;
		int a, t, g, c;
		
		String consensus = "";
		
		/*pour chaque colonne*/ 
		for(i = 0; i < nb_col; i++)
		{
			a = 0;
			t = 0;
			g = 0;
			c = 0;
			
			for(j = 0; j < nb_lign; j++)
			{
				if(ali[j][i] == 'A')
				{
					a++;
				}
					
				else if(ali[j][i] == 'T')
				{
					t++;
				}
				
				else if(ali[j][i] == 'C')
				{
					c++;
				}

				else if(ali[j][i] == 'G')
				{
					g++;
				}
			}
			
			/*une fois le nombre de caracteres par colonnes compte, on ajoute un char au string courrant*/	
			if(a > t && a > c && a > g)
			{
				consensus = consensus.concat("A");
			}
				
			else if(t > a && t > c && t > g)
			{
				consensus = consensus.concat("T");
			}
					
			else if(c > a && c > t && c > g)
			{
				consensus = consensus.concat("C");
			}
					
			else if(g > a && g > t && g > c)
			{
				consensus = consensus.concat("G");
			}
					
			else
			{
				consensus = consensus.concat("X");
			}
		}
			
		Sequence seq = new Sequence("cons", consensus);
		seq.setConsensus(bol);
			
		return seq;
	}

	public char[][] getAlignementFinal()
	{
		return alignement;
	}
	
	public void afficheAlign()
	{
		int i, j, nb_lign, nb_col;
		
		nb_lign = alignement.length;
		nb_col = alignement[0].length;
		
		for(i = 0; i < nb_lign; i++)
		{
			for(j = 0; j< nb_col; j++)
			{
				System.out.print(alignement[i][j]);
			}
			System.out.print("\n");
		}
		
		System.out.println("OK");
	}

}// Alignemment
