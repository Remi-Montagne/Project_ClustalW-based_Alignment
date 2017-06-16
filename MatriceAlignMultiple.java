public class MatriceAlignMultiple{

	//-----------------------------------------------------------------
	// Attributs
	//-----------------------------------------------------------------
	private int scoreMax;			//le score de l'alignement de 2 sequences par Needleman et Wunsh
	private int[][] matScoreAlign;	//la matrice de score
	private char[][] matParcours;	//la matrice mémorisant le parcours a effectuer pour aligner 2 sequences

	//---------------------------------------
	// Méthodes
	//---------------------------------------

	// Constructeur
	
	public MatriceAlignMultiple(Sequence consensus, Sequence sequence, MatriceScoreUnitaire matScoreUnit, char[][] ali)
	{
		int i, j, k;
		int res, somme;
		int lin; /*taille de la sequence*/
		int col; /*taille du consensus*/
		int d = matScoreUnit.getGap();/*penalite gap*/
		int prof = ali.length-1;
		
		col = consensus.getSeq().length();
		lin = sequence.getSeq().length();
		
		/* declaration des matrice de score et de parcours */
		matScoreAlign = new int[lin+1][col+1];
		matParcours = new char[lin+1][col+1];
		
		/*initialisation 1e ligne et 1e colonne */
		for(i = 0; i <= lin; i++)
		{
			matScoreAlign[i][0] = -i * d;
			matParcours[i][0] = 'h';
		}
		
		for(j = 0; j<=col; j++)
		{
			matScoreAlign[0][j] = -j * d;
			matParcours[0][j] = 'g';
		}
		
		/*remplissage de la matrice */
		for(i = 1; i <= lin; i++)
		{
			for(j = 1; j <= col; j++)
			{
				res = 0;
				somme = 0;
				matParcours[i][j] = 'd';
				
				/*calcul du score si on aligne le nucleotide j de la sequence avec les nucleotides a la position i de l'alignement
				 *dans ce cas on ajoute le score d'alignement (donne par la boucle puis la forule) au score de la case (i-1), (j-1)*/
				for(k = 0; k < prof; k++)
				{
					if(ali[k][j-1] == sequence.getSeq().charAt(i-1))
					{
						somme = somme + matScoreUnit.getMatch();
					}
				
					else
					{
						somme = somme + matScoreUnit.getMismatch();
					}
				}
				
				res = somme / ali.length + matScoreAlign[i-1][j-1];
				
				/*si le score de la case de gauche moins la penalite de gap est superieur a res, on remplace res par ce score
				 *on ajoute 'g' pour gauche a la case (i,j) de la matrice du parcours */
				if(matScoreAlign[i][j-1] - d > res)
				{
					res = matScoreAlign[i][j-1] - d;
					matParcours[i][j] = 'g';
				}
				
				/*si le score de la case du dessus moins la penalite de gap est superieur a res, on remplace res par ce score
				 *on ajoute 'h' pour gauche a la case (i,j) de la matrice du parcours */
				if(matScoreAlign[i-1][j] - d > res)
				{
					res = matScoreAlign[i-1][j] - d;
					matParcours[i][j] = 'h';
				}	
				
				matScoreAlign[i][j] = res;
				
			}
		}
	}
	
	public MatriceAlignMultiple(Sequence consensus1, Sequence consensus2, MatriceScoreUnitaire matScoreUnit, char[][] ali1, char[][] ali2)
	{
		int i, j, k, l;
		int res, somme;
		int n, m;
		int d = matScoreUnit.getGap();
		
		m = consensus1.getSeq().length(); /*taille consensus 1*/
		n = consensus2.getSeq().length(); /*taille consensus 2*/
		
		/* declaration des matrice de score et de parcours */
		matScoreAlign = new int[n+1][m+1];
		matParcours = new char[n+1][m+1];
		
		/*initialisation 1e ligne et 1e colonne */
		for(i = 0; i <= n; i++)
		{
			matScoreAlign[i][0] = -i * d;
			matParcours[i][0] = 'h';
		}
		
		for(j = 0; j<=m; j++)
		{
			matScoreAlign[0][j] = -j * d;
			matParcours[0][j] = 'g';
		}
		
		/*remplissage de la matrice */
		for(i = 1; i <= n; i++) /*pour i qui parcourt le consensus 1*/ 
		{
			for(j = 1; j <= m; j++) /*pour j qui parcourt le consensus 2*/
			{
				res = 0;
				somme = 0;
				matParcours[i][j] = 'd';
				
				/*calcul du score si on aligne le nucleotide j de la sequence avec les nucleotides a la position i de l'alignement
				 *dans ce cas on ajoute le score d'alignement (donne par la boucle puis la forule) au score de la case (i-1), (j-1)*/
				for(k = 0; k < ali1.length; k++) /*k parcourt les lignes de l'alignement 1*/
				{
					for(l = 0; l < ali2.length; l++) /*l parcourt les lignes de l'alignement 2*/
					{
						if(ali1[k][j-1] == ali2[l][i-1])
						{
							somme = somme + matScoreUnit.getMatch();
						}
					
						else
						{
							somme = somme + matScoreUnit.getMismatch();
						}
					}
				}//fin calcul du score
				
				res = somme / (ali1.length * ali2.length) + matScoreAlign[i-1][j-1];
				
				/*si le score de la case de gauche moins la penalite de gap est superieur a res, on remplace res par ce score
				 *on ajoute 'g' pour gauche a la case (i,j) de la matrice du parcours */
				if(matScoreAlign[i][j-1] - d > res)
				{
					res = matScoreAlign[i][j-1] - d;
					matParcours[i][j] = 'g';
				}
				
				/*si le score de la case du dessus moins la penalite de gap est superieur a res, on remplace res par ce score
				 *on ajoute 'h' pour gauche a la case (i,j) de la matrice du parcours */
				if(matScoreAlign[i-1][j] - d > res)
				{
					res = matScoreAlign[i-1][j] - d;
					matParcours[i][j] = 'h';
				}	
				
				matScoreAlign[i][j] = res;
				
			}
		}// fin remplissage matrice
		
		scoreMax = matScoreAlign[n][m];
	}// fin constructeur
	
	// Manipulations
	
	public int[][] getMatScoreAlign()
	{
		return matScoreAlign;
	}
	
	public char[][] getParcours()
	{
		return matParcours;
	}
	
	public int getScore()
	{
		return scoreMax;
	}
	
	public void afficheMatParcours()
	{
		int i, j;
		int n = matParcours.length;
		int m = matParcours[0].length;
		
		for(i = 0; i < n; i++)
		{
			for(j = 0; j< m; j++)
			{
				System.out.print(" " + matParcours[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
} //MatriceAlign
