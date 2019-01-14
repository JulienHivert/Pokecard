package com.example.iem.test.Login_Register_Auth;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iem.test.Constants;
import com.example.iem.test.Login_Register_Auth.Fragments.LoginFragment;
import com.example.iem.test.R;

import com.example.iem.test.notification.AlarmReceiver;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONObject;


/**
 * Created by julien on 24/01/18.
 */

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    TwitterLoginButton twitterLoginButton;
    private Button testHome;
    private LoginButton facebookLoginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.main_activity);

        facebookLoginButton = findViewById(R.id.BtnFacebook);
        twitterLoginButton = findViewById(R.id.twitter_auth);
        testHome = findViewById(R.id.btntest);
        callbackManager = CallbackManager.Factory.create();


        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
           @Override
           public void success(Result<TwitterSession> result) {
               TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
               TwitterAuthToken authToken = session.getAuthToken();
               String token = authToken.token;
               String secret = authToken.secret;
               login(session);
           }
           @Override
           public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(), "erreur d'authenfication", Toast.LENGTH_SHORT).show();
                Log.e(Constants.FAILURE, "erreur : " +exception);
           }
       });

        pref = getPreferences(0);
        testHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);

                } else {
                    Snackbar.make(v, R.string.string_internet_connection_not_available, Snackbar.LENGTH_SHORT).show();

                }
                return true;
            }
        });
        initFragment();
        scheduleAlarm();

        facebookLoginButton.setReadPermissions("email");
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Annulé", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Erreur", Toast.LENGTH_SHORT).show();
                Log.e(Constants.FAILURE, "Erreur " + error);
            }
        });
    }

  private void login(TwitterSession session){
        String username = session.getUserName();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
  }

    private void initFragment(){
        Fragment fragment;
        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
            fragment = new LoginFragment();
        }else {
            fragment = new LoginFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.RLFragment,fragment);
        ft.commit();
    }

    private void scheduleAlarm(){
        Intent intent = new Intent(MainActivity.this, Notification.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager !=null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, Constants.INTERVAL_RANDOM_POKEMON, pIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void getUserDetails(LoginResult loginResult){
        GraphRequest data_Request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Intent facebookIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        facebookIntent.putExtra("userProfile", object.toString());
                        startActivity(facebookIntent);
                    }
                }
        );
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_Request.setParameters(permission_param);
        data_Request.executeAsync();

    }
}
