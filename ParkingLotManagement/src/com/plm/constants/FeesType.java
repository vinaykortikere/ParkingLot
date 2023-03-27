package com.plm.constants;
public enum FeesType {
    FLAT_HOURLY("FLAT_HOURLY") ,
    RANGE_CUMMULATIVE("RANGE_CUMMULATIVE"), 
    RANGE_NON_CUMMULATIVE("RANGE_CUMMULATIVE");
    
    private String name;
    
	FeesType(String name) {
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
}
