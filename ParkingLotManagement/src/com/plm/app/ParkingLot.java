package com.plm.app;
import java.util.HashMap;

import com.plm.constants.FeesType;
import com.plm.constants.VehicleTypes;
import com.plm.data.Vehicle;

public class ParkingLot{
	private int availableSlotsForLMV;
	private int availableSlotsForTWV;
	private int availableSlotsForHTV;

	private FeesType feesType;

    public void setFeesType(FeesType feesType) {
		this.feesType = feesType;
	}
    
    public void setFeesType(String feesType) {
    	this.feesType = FeesType.valueOf(feesType);
	}

	public FeesType getFeesType() {
        return feesType;
    }
    private HashMap<VehicleTypes,FeeRange[]> feeRanges;

	public HashMap<VehicleTypes, FeeRange[]> getFeeRanges() {
		return feeRanges;
	}

	public void setFeeRanges(HashMap<VehicleTypes, FeeRange[]> feeRanges) {
		this.feeRanges = feeRanges;
	}

	private HashMap<String, Vehicle> vehicles = new HashMap<String, Vehicle>();

	public HashMap<String, Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(HashMap<String, Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public int getAvailableSlotsForLMV() {
		return availableSlotsForLMV;
	}

	public void setAvailableSlotsForLMV(int availableSlotsForLMV) {
		this.availableSlotsForLMV = availableSlotsForLMV;
	}

	public int getAvailableSlotsForTWV() {
		return availableSlotsForTWV;
	}

	public void setAvailableSlotsForTWV(int availableSlotsForTWV) {
		this.availableSlotsForTWV = availableSlotsForTWV;
	}

	public int getAvailableSlotsForHTV() {
		return availableSlotsForHTV;
	}

	public void setAvailableSlotsForHTV(int availableSlotsForHTV) {
		this.availableSlotsForHTV = availableSlotsForHTV;
	}

	public ParkingLot(int availableSlotsForLMV, int availableSlotsForTWV, int availableSlotsForHTV){
		this.availableSlotsForLMV = availableSlotsForLMV;
		this.availableSlotsForTWV = availableSlotsForTWV;
		this.availableSlotsForHTV = availableSlotsForHTV;
	}

	public String park(Vehicle vehicle){
		if(vehicle.park()) {
			this.vehicles.put(vehicle.getVehicleNumber(), vehicle);
			return "Vehicle Parked :" + vehicle.getVehicleNumber();
		}
		return "No slots available";
	}

	public String unPark(String vehicleNumber){
		Vehicle vehicle = this.vehicles.get(vehicleNumber);
		if(vehicle == null) {
			return "Vehicle not found";
		}
		this.vehicles.remove(vehicleNumber);
		vehicle.unpark();
		return "Vehicle Unparked: Detials as below \n\n" + vehicle.generateTicket(this.feeRanges.get(vehicle.getVehicleType()));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n\nParkingLot [availableSlotsForCar=")
		.append(availableSlotsForLMV)
		.append(", availableSlotsForBike=")
		.append(availableSlotsForTWV )
		.append(", availableSlotsForBus=")
		.append(availableSlotsForHTV)
		.append("]\n\n Vehicles currently parked: ")
		.append(this.vehicles.keySet())
		.append("\n\n");
		return builder.toString();
		
	}
}

