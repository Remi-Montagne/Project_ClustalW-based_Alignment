public class MatriceAlignSimple {

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
	
	public MatriceAlignSimple(Sequence sequence1, Sequence sequence2, MatriceScoreUnitaire matScoreUnit)
	{
		int i, j;
		int res;
		int d = matScoreUnit.getGap();
		
		int col = sequence1.getSeq().length(); /*longueur seq 1*/
		int lin = sequence2.getSeq().length(); /*longueur seq 2*/
		
		/* declaration des matrice de score et d'alignement */
		matScoreAlign = new int[lin+1][col+1];
		matParcours = new char[lin+1][col+1];
		
		/*initialisation 1e ligne et 1e colonne */
		for(i = 1; i <= lin; i++)
		{
			matScoreAlign[i][0] = -i * d;
			matParcours[i][0] = 'h';
		}
		
		for(j = 1; j<=col; j++)
		{
			matScoreAlign[0][j] = -j * d;
			matParcours[0][j] = 'g';
		}
		
		matParcours[0][0] = 'd';
		
		/*remplissage de la matrice */
		for(i = 1; i <= lin; i++)
		{
			for(j = 1; j <= col; j++)
			{
				
				res = 0;
				matParcours[i][j] = 'd';
				
				/*si les nucleotides matchent, on ajoute la valeur d'un match au score de l'alignement precedent (i-1, j-1)
				 *et res prend cette valeur. On ajoute 'd' pour diagonale a la case (i,j) de la matrice du parcours */
				if(sequence1.getSeq().charAt(j-1) == sequence2.getSeq().charAt(i-1))
				{
					res = matScoreAlign[i-1][j-1] + matScoreUnit.getMatch();
				}
				
				/*si les nucleotides ne matchent pas, on ajoute la valeur d'un mismatch au score de l'alignement precedent (i-1, j-1)
				 *et res prend cette valeur. On ajoute 'd' pour diagonale a la case (i,j) de la matrice du parcours */
				else
				{
					res = matScoreAlign[i-1][j-1] + matScoreUnit.getMismatch();
				}
				
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
		
		scoreMax = matScoreAlign[lin][col];
	}
	
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
