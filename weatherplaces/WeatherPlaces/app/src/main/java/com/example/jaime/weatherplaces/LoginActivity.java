package com.example.jaime.weatherplaces;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaime.weatherplaces.APIs.DamApi;
import com.example.jaime.weatherplaces.APIs.DamApiServiceGenerator;
import com.example.jaime.weatherplaces.Utilities.FileUtils;
import com.example.jaime.weatherplaces.model.damModelsAPI.ResponseUser;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    TextView nombre, email, password;
    ImageView fotoUsuario, iconapp;
    DamApi apiDam = DamApiServiceGenerator.createService(DamApi.class);
    Uri selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nombre = findViewById(R.id.nombreUsuario);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fotoUsuario = findViewById(R.id.userImg);
        iconapp = findViewById(R.id.iconoapp);
        Picasso.with(LoginActivity.this)
                .load("http://dynamax.com/images/uploads/icons/Revised-Icons_Weather-Stations.png")
                //.resize(600,600)
                .into(iconapp);

    }





    public void doRegister(View view) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Esto puede tardar unos segundos");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    if(selected != null) {
        String strfile = FileUtils.getFilePath(LoginActivity.this, selected);
        File file = new File(strfile);
        RequestBody parteFoto = RequestBody.create(
                MediaType.parse(getContentResolver().getType(selected)),
                file
        );
        MultipartBody.Part foto =
                MultipartBody.Part.createFormData("imagen", file.getName(), parteFoto);


        RequestBody parte1 = RequestBody.create(MultipartBody.FORM, email.getText().toString());
        RequestBody parte2 = RequestBody.create(MultipartBody.FORM, password.getText().toString());
        RequestBody parte3 = RequestBody.create(MultipartBody.FORM, nombre.getText().toString());

        Call<ResponseUser> resp = apiDam.doRegister(parte1, parte2, parte3, foto);

        resp.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    Log.v("Upload", "success");
                    ResponseUser usuario = response.body();
                    progressDialog.dismiss();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("userNAme",usuario.getDisplayName());
                    i.putExtra("userEmail", usuario.getEmail());
                    i.putExtra("userPassword", usuario.getPassword());
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {

            }
        });
        }
    }

    public void doLogin(View view) {
        Call<ResponseUser> resp= apiDam.doLogin(email.getText().toString(), password.getText().toString());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Esto puede tardar unos segundos");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        resp.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if(response.isSuccessful()){
                    ResponseUser usuario = response.body();
                    progressDialog.dismiss();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("userEmail", usuario.getEmail());
                    i.putExtra("userPassword", usuario.getPassword());
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {

            }
        });
    }

    public void pickImg(View view) {
        mayRequestStoragePermission(view);
        Intent getIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        getIntent.setType("image/*");
        startActivityForResult(getIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==PICK_IMAGE){
                onSelectFromGalleryResult(data);
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            try {
                Uri chosenImageUri = data.getData();
                selected = chosenImageUri;
                Picasso
                        .with(LoginActivity.this)
                        .load(chosenImageUri)
                        .into(fotoUsuario);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean mayRequestStoragePermission(View view) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(view, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {

                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                //mOptionButton.setEnabled(true);
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permisos denegados");
            builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });

            builder.show();
        }
    }
}
