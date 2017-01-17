package id.gits.loginmedsos;

import android.os.Bundle;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by teddy on 1/5/17.
 */

public abstract class FacebookCustomCallBack implements FacebookCallback<LoginResult> {
    public static final int RQ_FACEBOOK = 64206;
    private DaoSosmed daoSosmed;
    private String[] email = {""};

    public abstract void onSuccessLogin(DaoSosmed daoSosmed);

    public abstract void onErrorLogin(String error);

    public abstract void onCancelLogin();

    public FacebookCustomCallBack() {
        daoSosmed = new DaoSosmed();
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            String FEmail = object.getString("email");
                            String FName = object.getString("name");
                            String FId = object.getString("id");
                            daoSosmed.setEmail(FEmail);
                            daoSosmed.setName(FName);
                            daoSosmed.setId(FId);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();

        onSuccessLogin(daoSosmed);
    }

    @Override
    public void onCancel() {
        onCancelLogin();
    }

    @Override
    public void onError(FacebookException error) {
        onErrorLogin(error.toString());
    }
}
