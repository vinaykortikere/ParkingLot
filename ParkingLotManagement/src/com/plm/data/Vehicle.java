package com.plm.data;

import java.util.Date;

import com.plm.app.FeeRange;
import com.plm.app.ParkingLot;
import com.plm.constants.FeesType;
import com.plm.constants.VehicleTypes;

public abstract class Vehicle {

	private VehicleTypes vehicleType;
	private ParkingLot parkingLot;
	private String vehicleNumber;
	private Date entryTime;
	private Date exitTime;

	public void setVehicleType(VehicleTypes vehicleType) {
		this.vehicleType = vehicleType;

	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	public Vehicle(VehicleTypes vehicleType, ParkingLot parkingLot) {
		this.vehicleType = vehicleType;
		this.parkingLot = parkingLot;
	}

	public void setVehicleInfo(String vehicleNumber, Date entryTime) {
		this.vehicleNumber = vehicleNumber;
		this.entryTime = entryTime;
	}

	public VehicleTypes getVehicleType() {
		return vehicleType;
	}

	public abstract boolean park();

	public abstract void unpark();

	public String generateTicket(FeeRange[] feeRange) {
		return "Vehicle Number: " + getVehicleNumber() + "\nEntry Time: " + getEntryTime() + "\nExit Time: "
				+ getExitTime()
				// + "\nRate: " + rate
				+ "\nTotal Amount: " + calculateAmount(feeRange);
	}

	public int calculateAmount(FeeRange[] feeRange) {
		double diffMinutes = (getExitTime().getTime() - getEntryTime().getTime()) / (60 * 1000); 
		double diffHours = diffMinutes / 60;
		int time =0;
		if (this.getParkingLot().getFeesType().equals(FeesType.FLAT_HOURLY)) {
			time = (int) Math.ceil(diffHours);
			return feeRange[0].getRate() * time;
		} else if (this.getParkingLot().getFeesType().equals(FeesType.RANGE_CUMMULATIVE)) {
			if(diffMinutes < 720) {
				time = (int)diffHours;
			} else {
				time = (int) Math.ceil(diffHours);
			}
			int amount = 0;
			for (FeeRange feeRange2 : feeRange) {
				if (time >= feeRange2.getStartHour()) {
					if(feeRange2.isHourly()) {
						amount+= feeRange2.getRate() * (time-feeRange2.getStartHour());
					} else if(feeRange2.isDaily() && time >24) {
						amount+= feeRange2.getRate() * ((time%24) - feeRange2.getStartHour());
					}
					else {
						amount += feeRange2.getRate();
					}
				}
			}
			return amount;
		} else if (this.getParkingLot().getFeesType().equals(FeesType.RANGE_NON_CUMMULATIVE)) {
			int amount = 0;
			time = (int) Math.ceil(diffHours);
			if (time > 24) {
				double days = Math.ceil(time / 24.0);
				for (FeeRange feeRange2 : feeRange) {
					if (feeRange2.isDaily()) {
						amount = (int)(days * feeRange2.getRate());
						break;
					}
				}
			} else {
				time = time -1;
				for (FeeRange feeRange2 : feeRange) {
					if (!feeRange2.isDaily()) {
						if (time >= feeRange2.getStartHour() && time < feeRange2.getEndHour()) {
							amount = feeRange2.getRate();
							break;
						}
					}
				}
			}
			return amount;
		} else {
			return 0;
		}
	}
}