package com.plm.constants;

public enum VehicleTypes {
	LMV("LMV"), HTV("HTV"), TWV("TWV");

	private String name;

	VehicleTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
