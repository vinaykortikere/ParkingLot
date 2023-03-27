package com.plm.data;

import com.plm.app.ParkingLot;
import com.plm.constants.VehicleTypes;

// Path: Bike.java
public class MotorCycle extends Vehicle{
	public MotorCycle(ParkingLot parkingLot){
		super(VehicleTypes.TWV, parkingLot);
	}

    public boolean park() {
        if(getParkingLot().getAvailableSlotsForTWV() > 0) {
            getParkingLot().setAvailableSlotsForTWV(getParkingLot().getAvailableSlotsForTWV()-1);
           return true;
        } else {
            return false;
        }
    }

    public void unpark() {
        getParkingLot().setAvailableSlotsForTWV(getParkingLot().getAvailableSlotsForTWV()+1);
    }
}