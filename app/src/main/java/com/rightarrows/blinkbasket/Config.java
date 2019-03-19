package com.rightarrows.blinkbasket;

public class Config {
    public static final String BASE_URL="http://hoax.rightarrows.com/blink/request.php?";
    public static final String SLIDER = BASE_URL + "action=get-home-slider";
    public static final String CATEGORY = BASE_URL + "action=get-home-item";
    public static final String SEND_NUMBER = BASE_URL + "action=login-user";
    public static final String SUBSCRIPTION_SCHEDULE = BASE_URL + "action=subscription-schedule";
    public static final String VERIFY_OTP = BASE_URL + "action=verify-otp";
    public static final String USER_REGISTER = BASE_URL + "action=user-registration";

    public static final String RESPONSE_SUCCESS = "true";
    public static final String RESPONSE_FAIL = "false";
    public static final int SOCKET_TIMEOUT =30000 ;
    public static final String SHARED_PREF_NAME ="blinksharedpreference" ;
    public static final String GET_MAX_MIN =BASE_URL + "action=get-date"; ;
}
