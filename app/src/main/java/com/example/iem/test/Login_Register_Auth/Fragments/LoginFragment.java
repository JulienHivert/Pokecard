package com.example.iem.test.Login_Register_Auth.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iem.test.Constants;
import com.example.iem.test.Login_Register_Auth.HomeActivity;
import com.example.iem.test.Login_Register_Auth.InternetConnection;
import com.example.iem.test.ServerRequest.RequestInterface;
import com.example.iem.test.ServerRequest.ServerRequest;
import com.example.iem.test.ServerRequest.ServerResponse;
import com.example.iem.test.Model.User;
import com.example.iem.test.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by iem on 26/01/2018.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton appCompatButton;
    private EditText email1 ,  password1;
    private ProgressBar progressBar;
    private TextView textView;
    private SharedPreferences pref;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_login, container, false);
            initViews(view);
            return view;
    }

    private void initViews(View view) {

        pref = getActivity().getPreferences(0);

        appCompatButton = view.findViewById(R.id.btn_login);
        email1 = view.findViewById(R.id.et_email);
        password1 = view.findViewById(R.id.et_password);
        progressBar = view.findViewById(R.id.progress);
        textView = view.findViewById(R.id.tv_register);

        appCompatButton.setOnClickListener(this);
        textView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

        case R.id.tv_register:
            goToRegister();
            break;

        case R.id.btn_login:
            if (InternetConnection.checkConnection(getApplicationContext())) {
                String email = email1.getText().toString();
                String password = password1.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {

                    progressBar.setVisibility(View.VISIBLE);
                    loginProcess(email,password);

                } else {

                    Snackbar.make(getView(), "Veuillez remplir les champs !", Snackbar.LENGTH_LONG).show();
                }
                break;
            } else {
                Snackbar.make(getView(), R.string.string_internet_connection_not_available, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void loginProcess(String email,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN,true);
                    editor.putString(Constants.EMAIL,resp.getUser().getEmail());
                    editor.putString(Constants.NAME,resp.getUser().getName());
                    editor.putString(Constants.UNIQUE_ID,resp.getUser().getUnique_id());
                    editor.apply();
                    goToProfile();

                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                Log.d(Constants.FAILURE,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }private void goToRegister(){

        Fragment register = new RegisterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.RLFragment,register);
        ft.commit();
    }

    private void goToProfile(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

}
