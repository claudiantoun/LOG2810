
public class vehicleList extends vertexPath 
{
	private double batteryLife_;
	private boolean canStillGo_;
	
	public vehicleList(int id, int totalTime, String actualPath, boolean visited, double batteryLife, boolean canStillGo) 
	{
		super(id, totalTime, actualPath, visited);
		// TODO Auto-generated constructor stub
		batteryLife_ = batteryLife;
		canStillGo_ = canStillGo;
	}
	
	public double getBatteryLife() {
		return batteryLife_;
	}
	
	public void setBatteryLife(Double battery) 
	{
		batteryLife_ = battery;
	}

	public boolean getCanStillGo() 
	{
		return canStillGo_;
	}

	public void setCanStillGo(boolean canStillGo) 
	{
		canStillGo_ = canStillGo;
	}
}

