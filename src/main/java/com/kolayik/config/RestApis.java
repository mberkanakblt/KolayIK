package com.kolayik.config;

public class RestApis {

    private static final String VERSION = "/v1";
    private static final String DEV = "/dev";
    private static final String BASE_URL = DEV + VERSION;

    public static final String USER = BASE_URL+"/user";
    public static final String COMMENT = BASE_URL+"/comment";



    public static final String LOGIN = "/login";
    public static final String DO_REGISTER ="/doregister";
    public static final String ADD_COMMENT = "/add-comment";
    public static final String GET_COMMENT = "/get-comment";

    public static final String CREATE_PERSONNEL = "/create-personnel";
    public static final String UPDATE_PERSONNEL = "/update-personnel";
    public static final String DELETE_PERSONNEL = "/delete-personnel";
    public static final String GET_ALL_PERSONNEL = "/get-all-personnel";
    public static final String UPDATE_PERSONNEL_STATUS = "/update-personnel-status";


}
