package com.example.iem.test.ServerRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by iem on 26/01/2018.
 */

public interface RequestInterface {

    @POST("login-register/index.php")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
