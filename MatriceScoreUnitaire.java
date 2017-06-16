
public class MatriceScoreUnitaire {
	
	//---------------------------------------
	// attributs
	//---------------------------------------
	private int match;
	private int mismatch;
	private int gap;
	private int x;
	
	//---------------------------------------
	// méthodes
	//---------------------------------------
	
	//constructeurs
	public MatriceScoreUnitaire () {
		this.match = 1;
		this.mismatch = 0;
		this.gap = 2;
		this.x = 0;
	}
	
	public MatriceScoreUnitaire(int match, int mismatch, int gap, int x) {
		this.match = match;
		this.mismatch = mismatch;
		this.gap = gap;
		this.x = x;
	}
	
	//accesseurs
	public int getMatch() {
		return this.match;
	}
	
	public int getMismatch() {
		return this.mismatch;
	}
	
	public int getGap() {
		return this.gap;
	}
	
		public int getX() {
		return this.x;
	}

	
	//modifieurs
	public void setMatch(int match) {
		this.match = match;
	}
	
	public void setMismatch(int mismatch) {
		this.mismatch = mismatch;
	}
	
	public void setGap(int gap) {
		this.gap = gap;
	}
}
