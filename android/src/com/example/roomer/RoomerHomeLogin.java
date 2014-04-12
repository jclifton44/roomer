package com.example.roomer;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class RoomerHomeLogin extends Activity
{
  public static final String PREFS_LOGIN = "MyPrefsFile";
  public static String homeSite = "http://73.55.136.67/";
  static locationService ls;
  static RoomerHomeLogin rhl;
  static EditText sha1;
  protected static RoomerHomeLogin thisAc;
  static UserNode un;
  static EditText username;
  static String usha1;
  static String uusername;

  public void attemptLogin()
  {
    Log.d("136:RoomerHomeLogin", "Attempting Login");
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpPost localHttpPost = new HttpPost(homeSite + "php/authentication.php");
    try
    {
      ArrayList localArrayList = new ArrayList(4);
      localArrayList.add(new BasicNameValuePair("email", uusername));
      localArrayList.add(new BasicNameValuePair("sha1", usha1));
      localArrayList.add(new BasicNameValuePair("action", "4"));
      localArrayList.add(new BasicNameValuePair("webdesk", "0"));
      localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
      Log.d("Starting", "YA");
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      Log.d("Starting", "END");
      String str = new String(EntityUtils.toString(localHttpResponse.getEntity(), "UTF-8"));
      Log.d("Starting second Ac", str);
      if (str.contains("200"))
      {
        Log.d("Starting second Ac", "Yep1");
        Intent localIntent = new Intent(this, RoomerHome.class);
        Log.d("Starting second Ac", "Yep2");
        startActivity(localIntent);
        Log.d("Starting second Ac", "Yep3");
        finish();
      }
      return;
    }
    catch (IOException localIOException)
    {
    }

  }

  @SuppressLint("NewApi")
protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    thisAc = this;
    SharedPreferencesEditor localSharedPreferencesEditor = new SharedPreferencesEditor(getApplicationContext(), "MyPrefsFile");
//    if (Build.VERSION.SDK_INT > 9)
  //    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
    uusername = localSharedPreferencesEditor.getUserName();
    usha1 = localSharedPreferencesEditor.getSha1();
    Log.d("logging", "Credendials");
    ls = new locationService(getApplicationContext(), false);
    if ((uusername != null) && (usha1 != null))
    {
      Log.d(uusername, usha1);
      attemptLogin();
    }
    getActionBar().hide();
    rhl = this;
    setContentView(2130903043);
    TextView localTextView1 = (TextView)findViewById(2131427329);
    TextView localTextView2 = (TextView)findViewById(2131427328);
    Log.d("asdf", "asdf");
    localTextView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(RoomerHomeLogin.this, LoginActivity.class);
        RoomerHomeLogin.this.startActivity(localIntent);
        Log.d("Starting second Ac", "Yep");
        RoomerHomeLogin.thisAc.finish();
      }
    });
    localTextView2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(RoomerHomeLogin.this, RegisterActivity.class);
        RoomerHomeLogin.this.startActivity(localIntent);
        Log.d("Starting second Ac", "Yep");
        RoomerHomeLogin.thisAc.finish();
      }
    });
  }

  protected void onDestroy()
  {
    super.onDestroy();
  }

  public void onPause()
  {
    super.onPause();
    Log.d("In Pause", "PAUSE");
  }

  protected void onRestart()
  {
    super.onRestart();
  }

  public void onResume()
  {
    super.onResume();
    Log.d("In Resume", "RESUME");
  }

  public void onStart()
  {
    super.onStart();
    Log.d("In Start", "START");
  }

  protected void onStop()
  {
    super.onStop();
    Log.d("In Stop", "STOP");
  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.RoomerHomeLogin
 * JD-Core Version:    0.6.2
 */