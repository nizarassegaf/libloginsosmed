package id.co.gits.testlibrarylogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import id.gits.loginmedsos.DaoSosmed;
import id.gits.loginmedsos.FaceBookLogin;
import id.gits.loginmedsos.FacebookCustomCallBack;
import id.gits.loginmedsos.GoogleLogin;
import id.gits.loginmedsos.GoogleOnclickListener;

import static id.gits.loginmedsos.FaceBookLogin.callbackManager;
import static id.gits.loginmedsos.GoogleOnclickListener.RQ_GOOGLE;

public class MainActivity extends AppCompatActivity {
    private GoogleLogin googleLogin;
    private FaceBookLogin faceBookLogin;
    private DaoSosmed daoSo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faceBookLogin = (FaceBookLogin) findViewById(R.id.btnFacebook);
        faceBookLogin.initLoginFaceBook(new FacebookCustomCallBack() {
            @Override
            public void onSuccessLogin(DaoSosmed daoSosmed) {
                daoSo = daoSosmed;
                try {
                    if(!daoSo.getEmail().isEmpty()){
                        Toast.makeText(getApplicationContext(), daoSo.getEmail()+" berhasil login",Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException npe) {
                    Toast.makeText(getApplicationContext(),"login facebook gagal, silahkan coba lagi",Toast.LENGTH_LONG).show();
                }

                Log.wtf("emailfb1", daoSo.getName());
                Log.wtf("emailfb", daoSo.getEmail());
            }

            @Override
            public void onErrorLogin(String error) {
                Toast.makeText(getApplicationContext(),"login facebook gagal, silahkan coba lagi",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelLogin() {
                Toast.makeText(getApplicationContext(),"login facebook canceled",Toast.LENGTH_LONG).show();
            }
        });

        googleLogin = (GoogleLogin) findViewById(R.id.btnGoogle);
        googleLogin.setOnClickListener(new GoogleOnclickListener() {

            @Override
            public void onClick(View view) {
                super.onClick(view);
                if(daoSo.getEmail() != null) {
                    Log.wtf("daoSosmed", daoSo.getEmail());
                }
            }

            @Override
            public void onConnected(@Nullable Bundle bundle) {
                super.onConnected(bundle);
            }

            @Override
            public void onConnectionSuspended(int i) {
                super.onConnectionSuspended(i);
            }

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                super.onConnectionFailed(connectionResult);
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_GOOGLE) {
            daoSo = googleLogin.resultLogoin(data);
            Toast.makeText(getApplicationContext(), daoSo.getEmail()+" berhasil login",Toast.LENGTH_LONG).show();
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
