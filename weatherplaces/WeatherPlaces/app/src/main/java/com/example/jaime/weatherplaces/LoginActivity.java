package com.example.jaime.weatherplaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaime.weatherplaces.APIs.DamApi;
import com.example.jaime.weatherplaces.APIs.DamApiServiceGenerator;
import com.example.jaime.weatherplaces.model.damModelsAPI.ResponseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView nombre, email, password;
    ImageView fotoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nombre = findViewById(R.id.nombreUsuario);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        DamApi apiDam = DamApiServiceGenerator.createService(DamApi.class);

        Call<ResponseUser> resp=apiDam.doLogin(email.getText().toString(), password.getText().toString());
        resp.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {

            }
        });
    }


}