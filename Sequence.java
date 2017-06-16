public class Sequence {
	//-----------------------------------------------------------------
	// attributs
	//-----------------------------------------------------------------
	
	/*
	 * chaîne de nucleotides
	 */
	private String seq;
	
	/*
	 * nom de la sequence
	 */
	private String nom;
	
	/*
	 * indique si la sequence est un consensus de sequences (true) ou une sequence normale (false)
	 */
	private boolean consensus;
	
	//---------------------------------------
	// methodes
	//---------------------------------------
	
	//constructeurs
	
	public Sequence (String nom, String sequence) {
		this.nom = nom;
		this.seq = sequence;
		
		/*
		 * par defaut, on considere que la sequence n est pas un consensus
		 */
		this.consensus = false;
	}
	
	//accesseurs
	public String getName() {
		return this.nom;
	}

	public String getSeq() {
		return this.seq;
	}
	
	public boolean getConsensus(){
		return this.consensus;
	}
	
	//modifieurs
	
	/*
	 * seule la caracteristique consensus est modifiable
	 */
	public void setConsensus(boolean a){
		this.consensus = a;
	}

}
