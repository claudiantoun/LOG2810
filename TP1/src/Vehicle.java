
public class Vehicle 
{
	private static String vehicleType_;
	private static String transportationRisk_;
	private Double batteryPercentage_;
	private Double durability_;
	private String longestPath_;
	private int longestTime_;
	
	public Vehicle(String vehicleType, String transportationRisk, Double batteryPercentage) 
	{
		vehicleType_ = vehicleType;
		transportationRisk_ = transportationRisk;
		batteryPercentage_ = batteryPercentage;
		durability_ = 0.0;
		longestPath_ = "";
		longestTime_ = 0;
	}
	
	public String getVehicleType() 
	{
		return vehicleType_;
	}
	
	public String getTransportationRisk() 
	{
		return transportationRisk_;
	}
	
	public double getBatteryPercentage() 
	{
		return batteryPercentage_;
	}
	
	public void setBatteryPercentage(Double batteryPercentage) 
	{
		batteryPercentage_ = batteryPercentage;
	}
	
	public Double getDurability() 
	{
		return durability_;
	}
	
	public void setDurability(Double percentage) 
	{
		durability_ = 60/percentage*80;
	}
	
	public String getLongestPath() 
	{
		return longestPath_;
	}

	public void setLongestPath(String longestPath) 
	{
		longestPath_ = longestPath;
	}

	public int getLongestTime() 
	{
		return longestTime_;
	}

	public void setLongestTime(int longestTime) 
	{
		longestTime_ = longestTime;
	}
}
