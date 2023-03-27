package com.plm.app;

public class FeeRange {
	private int startHour;
	private boolean isDaily;
	private boolean isHourly;
	private int endHour;
	private int rate;

	public boolean isHourly() {
		return isHourly;
	}

	public void setHourly(boolean isHourly) {
		this.isHourly = isHourly;
	}

	public boolean isDaily() {
		return isDaily;
	}

	public void setDaily(boolean isDaily) {
		this.isDaily = isDaily;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getRate() {
		return rate;
	}

	public FeeRange(int startHour, int endHour, int rate, boolean isDaily, boolean isHourly) {
		this.startHour = startHour;
		this.endHour = endHour;
		this.rate = rate;
		this.isDaily = isDaily;
		this.isHourly = isHourly;
	}

}
