package com.plm.data;

import com.plm.app.ParkingLot;
import com.plm.constants.VehicleTypes;

// Path: Bus.java
public class Bus extends Vehicle{
	//Bus Class which is the child class of Vehicle
	//Has method to get the vehicle type
	public Bus(ParkingLot parkingLot){
		super(VehicleTypes.HTV, parkingLot);
	}

    public boolean park() {
        if(getParkingLot().getAvailableSlotsForHTV() > 0) {
            getParkingLot().setAvailableSlotsForHTV(getParkingLot().getAvailableSlotsForHTV()-1);
            return true;
        } else {
           return false;
        }
    }

    public void unpark() {
        getParkingLot().setAvailableSlotsForHTV(getParkingLot().getAvailableSlotsForHTV()+1);
    }
}