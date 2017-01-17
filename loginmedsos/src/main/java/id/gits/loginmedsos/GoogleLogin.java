package id.gits.loginmedsos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import static id.gits.loginmedsos.GoogleOnclickListener.RQ_GOOGLE;
import static id.gits.loginmedsos.GoogleOnclickListener.mGoogleApiClient;

/**
 * Created by nizarassegaf on 1/9/2017.
 */

public class GoogleLogin extends RelativeLayout {

    public static final int DEF_FINISHED_TEXT_SIZE = 18;
    private String mButtonText;
    private Drawable mButtonBackground;
    private Drawable mIconLogin;
    private ColorStateList mTextColor;
    private ColorStateList mIconColor;
    private float mTextSize;
    private LinearLayout btnLogin;
    private TextView textLogin;
    private ImageView icLogin;
    private View mView;

    public GoogleLogin(Context context) {
        super(context);
    }

    public GoogleLogin(Context context, AttributeSet attrs) {
        super(context, attrs);
        bindView();

        setAtribut(context, attrs);
    }

    public GoogleLogin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bindView();
    }

    private void bindView() {
        inflate(getContext(), R.layout.google_login, this);
        btnLogin = (LinearLayout) findViewById(R.id.signin_button);
        textLogin = (TextView) findViewById(R.id.textgoogle);
        icLogin = (ImageView) findViewById(R.id.icgoogle);
        mView = (View) findViewById(R.id.viewgoogle);

        initLoginGoogle((Activity) getContext());
    }

    /**
     * Initiate login google
     *
     * @param activity
     */
    public void initLoginGoogle(final Activity activity) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                activity.startActivityForResult(signInIntent, RQ_GOOGLE);
            }
        });

    }

    /**
     * Set text size button facebook
     *
     * @param buttonText
     */
    public void setButtonText(String buttonText) {
        mButtonText = buttonText;
        //btnLogin.setVisibility(TextUtils.isEmpty(mButtonText) ? View.GONE : View.VISIBLE);
        textLogin.setText(mButtonText);
        textLogin.setVisibility(TextUtils.isEmpty(mButtonText) ? View.GONE : View.VISIBLE);
    }

    /**
     * Set text size button facebook
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        mTextSize = textSize;
        textLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
    }

    /**
     * Set text color button facebook
     *
     * @param color
     */
    public void setTextColor(ColorStateList color) {
        mTextColor = color;
        textLogin.setTextColor(mTextColor);
    }

    /**
     * Set icon button facebook
     *
     * @param drawable
     */
    public void setIconButton(Drawable drawable) {
        mIconLogin = drawable;
        if (!TextUtils.isEmpty(mButtonText) && drawable != null) {
            mView.setVisibility(VISIBLE);
        }

        if (drawable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                icLogin.setBackground(mIconLogin);
            } else {
                icLogin.setBackgroundDrawable(mIconLogin);
            }
            icLogin.setVisibility(VISIBLE);
        } else {
            icLogin.setVisibility(GONE);
        }
    }

    /**
     * Set icon color at button facebook
     *
     * @param color
     */
    public void setIconColor(ColorStateList color) {
        mIconColor = color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            icLogin.setBackgroundTintList(mIconColor);
        }
    }

    /**
     * Set button background facebook
     *
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

    private void setAtribut(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GoogleLogin,
                0, 0);
        try {
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEF_FINISHED_TEXT_SIZE, context.getResources().getDisplayMetrics());
            mButtonText = a.getString(R.styleable.GoogleLogin_login_Text);
            mButtonBackground = a.getDrawable(R.styleable.GoogleLogin_login_ButtonBackground);
            mIconLogin = a.getDrawable(R.styleable.GoogleLogin_login_Icon);
            mTextColor = ColorStateList.valueOf(a.getColor(R.styleable.GoogleLogin_login_TextColor, 1));
            mTextSize = a.getDimension(R.styleable.GoogleLogin_login_TextSize, px);
            mIconColor = ColorStateList.valueOf(a.getColor(R.styleable.GoogleLogin_login_IcColor, 1));
        } finally {
            a.recycle();
        }

        injectView();
    }

    /**
     * get result from GoogleSignInApi
     *
     * @param data
     * @return
     */
    public static DaoSosmed resultLogoin(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        DaoSosmed daoSosmed = new DaoSosmed();
        if (result.isSuccess()) {
            if (daoSosmed != null) {
                GoogleSignInAccount acct = result.getSignInAccount();
                daoSosmed.setEmail(acct.getEmail());
                daoSosmed.setId(acct.getId());
                daoSosmed.setName(acct.getDisplayName());
            }
        }
        return daoSosmed;
    }

    private void injectView() {
        setButtonText(mButtonText);
        setButtonBackground(mButtonBackground);
        setIconButton(mIconLogin);
        setTextColor(mTextColor);
        setTextSize(mTextSize);
        setIconColor(mIconColor);
    }

}
