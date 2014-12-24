package com.unlogicon.headhunter.authorization;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.unlogicon.headhunter.Constants;
import com.unlogicon.headhunter.R;
import com.unlogicon.headhunter.Settings;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import static com.unlogicon.headhunter.UtilsApi.getCallBackLogin;

/**
 * Created by Nik on 05.10.2014.
 */
public class ActivityAuthorization extends ActionBarActivity {

    private Activity activity;
    private AQuery aq;

    private LinearLayout linearLayout;
    private WebView loginWeb;

    private Settings settings;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);

        activity = this;
        aq = new AQuery(activity);
        settings = new Settings(activity);

        getSupportActionBar().setTitle(getString(R.string.authorization_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        linearLayout = (LinearLayout) findViewById(R.id.main);
        loginWeb = new WebView(this);
        linearLayout.addView(loginWeb);

        loginWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                URI uri = null;
                try {
                    uri = new URI(url);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (uri.getHost().equals(Constants.REDIRECT_URL)) {
                    aq.progress(activity).ajax(getCallBackLogin(activity, Uri.parse(url).getQueryParameter("code")));
                }
            }
        });
        loginWeb.loadUrl(Constants.AUTHORIZATION_URL);

    }

    public void onResponse(String url, String s, AjaxStatus status) {

        AQUtility.debug(s);

        if (status.getCode() == 200 && s != null) {
            try {
                JSONObject result = new JSONObject(s);
                settings.setAccessToken(result.optString("access_token"));
                settings.setTokenType(result.optString("token_type"));
                settings.setExpiresIn(result.optString("expires_in"));
                settings.setRefreshToken(result.optString("refresh_token"));
            } catch (Exception e) {
                AQUtility.debug("error result parser", e.toString());
                e.printStackTrace();
            }

            setResult(RESULT_OK);
            finish();
        }
        else {
            setResult(RESULT_CANCELED);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.channel_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
