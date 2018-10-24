
public class Vehicle {

	private static String vehicleType_;
	private static String transportationRisk_;
	private Double batteryPercentage_;
	private Double durability_;
	
	public Vehicle(String vehicleType, String transportationRisk, Double batteryPercentage) {
		
		vehicleType_ = vehicleType;
		transportationRisk_ = transportationRisk;
		batteryPercentage_ = batteryPercentage;
		durability_ = 0.0;
	}
	
	public String getVehicleType() {
		return vehicleType_;
	}
	
	public String getTransportationRisk() {
		return transportationRisk_;
	}
	
	public double getBatteryPercentage() {
		return batteryPercentage_;
	}
	
	public void setBatteryPercentage(Double batteryPercentage) {
		batteryPercentage_ = batteryPercentage;
	}
	
	public Double getDurability() {
		return durability_;
	}
	
	public void setDurability(Double percentage) {
		durability_ = 60/percentage*100;
	}
}
