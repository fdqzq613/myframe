package com.some.web.db;

public enum  DataSourceEnum {
	 DEFAULT("default");

	    private String value;

	    DataSourceEnum(String value){this.value=value;}

	    public String getValue() {
	        return value;
	    }
}
