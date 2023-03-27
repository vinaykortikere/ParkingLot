package com.plm.data;

import com.plm.app.ParkingLot;
import com.plm.constants.VehicleTypes;

public class Car extends Vehicle{
	public Car(ParkingLot parkingLot){
		super(VehicleTypes.LMV, parkingLot);
	}

    public boolean park() {
        if(getParkingLot().getAvailableSlotsForLMV() > 0) {
            getParkingLot().setAvailableSlotsForLMV(getParkingLot().getAvailableSlotsForLMV()-1);
            return true;
        } else {
            return false;
        }
    }

    public void unpark() {
        getParkingLot().setAvailableSlotsForLMV(getParkingLot().getAvailableSlotsForLMV()+1);
    }
}