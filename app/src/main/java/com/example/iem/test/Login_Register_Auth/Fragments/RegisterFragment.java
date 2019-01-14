package com.example.iem.test.Login_Register_Auth.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

public class RegisterFragment extends Fragment  implements View.OnClickListener{

        private AppCompatButton btn_register;
        private EditText et_email, et_password, et_name;
        private TextView tv_login;
        private ProgressBar progressbar;

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
        savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        btn_register = view.findViewById(R.id.btn_register);
        tv_login = view.findViewById(R.id.tv_login);
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        progressbar = view.findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    String name = et_name.getText().toString();
                    String email = et_email.getText().toString();
                    String password = et_password.getText().toString();

                    if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                        progressbar.setVisibility(View.VISIBLE);
                        registerProcess(name, email, password);

                    } else {

                        Snackbar.make(getView(), "Veuillez remplir les champs !", Snackbar.LENGTH_LONG).show();
                    }
                    break;
                } else {
                    Snackbar.make(getView(), R.string.string_internet_connection_not_available, Snackbar.LENGTH_SHORT).show();
                }
        }

    }

    private void registerProcess(String name, String email, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progressbar.setVisibility(View.INVISIBLE);
                Log.d(Constants.FAILURE, "failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void goToLogin() {

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.RLFragment, login);
        ft.commit();
    }
}

