package com.talentgrid.app.constants;

public class ApplicationConstants {

    private ApplicationConstants(){
        throw new AssertionError("Utitity class cannot be instantiated");
    }

    public static final String JWT_SECRET_KEY = "JWT_SECRET";
    public static final String JWT_SECRET_DEFAULT_VALUE = "J2UxHRUEBTVzET5E089szpshwCIKHALK";
    public static final String JWT_HEADER = "Authorization";

    public static final String ROLE_JOB_SEEKER = "ROLE_JOB_SEEKER";

    public static final String ACTIVE_STATUS = "ACTIVE";

}
