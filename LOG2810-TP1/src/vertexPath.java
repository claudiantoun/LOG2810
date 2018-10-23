
public class vertexPath {
	private boolean visited_;
	private int id_;
	private int totalTime_;
	private String actualPath_;    //"1,4,3,10"
	
	
	
	public vertexPath(int id, int totalTime, String actualPath, boolean visited)
	{
		id_ = id;
		totalTime_ = totalTime;
		actualPath_ = actualPath;
		visited_ = visited;
	}
	
	public void setTotalTime(int totalTime)
	{
		totalTime_= totalTime;
	}
}
