public class Mot 
{
	private String nom_;
	private int nbTimeUsed_;
	private boolean recentlyUsed_;
	
	public Mot(String nom)
	{
		nom_ = nom;
		nbTimeUsed_ = 0;
		recentlyUsed_ = false;
	}
	
	public String getNom() 
	{
		return nom_;
	}
	
	public int getNbTimeUsed() 
	{
		return nbTimeUsed_;
	}
	
	public void incrementNbTimeUsed() 
	{
		nbTimeUsed_++;
	}
	
	public boolean getRecentlyUsed() 
	{
		return recentlyUsed_;
	}
	
	public void setRecentlyUsed(boolean recentlyUsed) 
	{
		recentlyUsed_ = recentlyUsed;
	}
}
