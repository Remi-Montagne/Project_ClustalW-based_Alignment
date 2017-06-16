public class Noeud{
	
	//---------------------------------------
	// attributs
	//---------------------------------------
	
	private Sequence cle;
	private char[][] ali;
	private Noeud fg;
	private Noeud fd;

	//---------------------------------------
	// constructeurs
	//---------------------------------------

	public Noeud(Noeud fg, Noeud fd)
	{
		this.cle = null;
		this.fg = fg;
		this.fd = fd;
	}
	
	public Noeud(Sequence cle)
	{
		this.cle = cle;
		this.fg = null;
		this.fd = null;
	}
	
	//---------------------------------------
	// méthodes
	//---------------------------------------
	
	//Accesseurs
	
	public Sequence getCle()
	{
		return cle;
	}
	
	public Noeud getfg()
	{
		return fg;
	}

	public Noeud getfd()
	{
		return fd;
	}
	
	public char[][] getAli()
	{
		return ali;
	}
		
	//setteurs
	public void setAli(char[][] alignement_voulu)
	{
		this.ali = alignement_voulu;
	}
	
	public void setCle(Sequence seq_ou_cons)
	{
		this.cle = seq_ou_cons;
	}
	
	
	//affichage
	
	public void afficheNoeud()
	{
		System.out.println("noeud\ncle : " + cle + "\nfg : " + fg + "\nfd : " + fd);
	}
	
	public void afficheAliNoeud()
	{
		int i, j, lin, col;
		System.out.print("affiche Alignement du Noeud\n");
		lin = ali.length;
		col = ali[0].length;
		
		for(i = 0; i < lin; i++)
		{
			for(j = 0; j< col; j++)
			{
				System.out.print(this.ali[i][j]);
			}
			System.out.print("\n");
		}
	}
	
	public void afficheCle()
	{
		System.out.print(cle.getSeq() + "\n");

	}
}
