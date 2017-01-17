package id.gits.loginmedsos;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import java.util.Arrays;

/**
 * Created by teddy on 1/5/17.
 */

public class FaceBookLogin extends RelativeLayout {

    private LinearLayout btnLogin;
    private TextView textLogin;
    private ImageView icLogin;
    public static CallbackManager callbackManager;
    public static final int DEF_FINISHED_TEXT_SIZE = 18;
    private String mButtonText;
    private Drawable mButtonBackground;
    private Drawable mIconLogin;
    private ColorStateList mTextColor;
    private float mTextSize;
    private ColorStateList mIconColor;
    private View mView;

    public FaceBookLogin(Context context) {
        super(context);
        bindView();
    }

    public FaceBookLogin(Context context, AttributeSet attrs) {
        super(context, attrs);
        bindView();

        setAtribut(context, attrs);

        setButtonText(mButtonText);
        setButtonBackground(mButtonBackground);
        setIconButton(mIconLogin);
        setTextColor(mTextColor);
        setTextSize(mTextSize);
        setIconColor(mIconColor);
    }

    public FaceBookLogin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bindView();
    }


    public void bindView() {
        FacebookSdk.sdkInitialize(getContext());
        AppEventsLogger.activateApp(getContext());
        callbackManager = CallbackManager.Factory.create();

        inflate(getContext(), R.layout.facebook_login, this);
        btnLogin = (LinearLayout) findViewById(R.id.login_button);
        textLogin = (TextView) findViewById(R.id.textgoogle);
        icLogin = (ImageView) findViewById(R.id.icgoogle);
        mView = (View) findViewById(R.id.viewgoogle);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions((Activity) getContext(), Arrays.asList(
                        "public_profile", "email", "user_birthday", "user_friends"));
            }
        });

    }

    public void initLoginFaceBook(FacebookCustomCallBack callBack) {
        LoginManager.getInstance().registerCallback(callbackManager, callBack);
    }

    /**
     * Set text size button facebook
     * @param textSize
     */
    public void setTextSize(float textSize){
        mTextSize = textSize;
        textLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
    }

    /**
     * Set text color button facebook
     * @param color
     */
    public void setTextColor(ColorStateList color){
        mTextColor = color;
        textLogin.setTextColor(mTextColor);
    }

    /**
     * Set text size button facebook
     * @param buttonText
     */
    public void setButtonText(String buttonText) {
        mButtonText = buttonText;
        textLogin.setText(mButtonText);
        textLogin.setVisibility(TextUtils.isEmpty(mButtonText) ? View.GONE : View.VISIBLE);
    }

    /**
     * Set icon button facebook
     * @param drawable
     */
    public void setIconButton(Drawable  drawable){
        mIconLogin = drawable;
        if(!TextUtils.isEmpty(mButtonText) && drawable != null){
            mView.setVisibility(VISIBLE);
        }

        if (drawable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                icLogin.setBackground(mIconLogin);
            } else {
                icLogin.setBackgroundDrawable(mIconLogin);
            }
            icLogin.setVisibility(VISIBLE);
        }
        else {
            icLogin.setVisibility(GONE);
        }
    }

    /**
     * Set icon color at button facebook
     * @param color
     */
    public void setIconColor(ColorStateList color){
        mIconColor = color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            icLogin.setBackgroundTintList(mIconColor);
        }
    }

    /**
     * Set button background facebook
     * @param drawable
     */
    public void setButtonBackground(Drawable drawable) {
        mButtonBackground = drawable;
        if (drawable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                btnLogin.setBackground(mButtonBackground);
            } else {
                btnLogin.setBackgroundDrawable(mButtonBackground);
            }
        }
    }

    private void setAtribut(Context context,AttributeSet attrs){
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FacebookLogin,
                0, 0);
        try {
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEF_FINISHED_TEXT_SIZE, context.getResources().getDisplayMetrics());
            mButtonText = a.getString(R.styleable.FacebookLogin_loginfb_Text);
            mButtonBackground = a.getDrawable(R.styleable.FacebookLogin_loginfb_ButtonBackground);
            mIconLogin = a.getDrawable(R.styleable.FacebookLogin_loginfb_Icon);
            mTextColor = ColorStateList.valueOf(a.getColor(R.styleable.FacebookLogin_loginfb_TextColor,1));
            mTextSize = a.getDimension(R.styleable.FacebookLogin_loginfb_TextSize,px);
            mIconColor = ColorStateList.valueOf(a.getColor(R.styleable.FacebookLogin_loginfb_IcColor,1));
        } finally {
            a.recycle();
        }
    }

}
