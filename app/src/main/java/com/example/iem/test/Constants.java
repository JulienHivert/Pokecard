package com.example.iem.test;

import android.app.AlarmManager;

/**
 * Created by iem on 26/01/2018.
 */

public class Constants {

    public static final String BASE_URL = "http://10.0.2.2:8080/";
    public static final String REGISTER_OPERATION = "register";
    public static final String LOGIN_OPERATION = "login";
    public static final String CHANGE_PASSWORD_OPERATION = "chgPass";

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String IS_LOGGED_IN = "isLoggedIn";

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String UNIQUE_ID = "unique_id";

    public static final String BRAINTREE_TAG = "Braintree";
    public static final String get_token = "http://10.0.2.2:8080/BraintreePaymentsServer/main.php";
    public static final String send_payment_details = "http://10.0.2.2:8080/BraintreePaymentsServer/mycheckout.php";
    public static final int REQUEST_CODE = 1;

    public static final int ALARM_REQUEST_CODE =  123;
    public static final long INTERVAL_RANDOM_POKEMON = 7 * AlarmManager.INTERVAL_DAY;
    public static String NOTIFICATION_CHANNEL_ID = "channel_ID";

}
