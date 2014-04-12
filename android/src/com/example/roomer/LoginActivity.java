package com.example.roomer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressLint("NewApi")
public class LoginActivity extends Activity
{
  private static final String[] DUMMY_CREDENTIALS = { "foo@example.com:hello", "bar@example.com:world" };
  public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
  public static final String PREFS_LOGIN = "MyPrefsFile";
  static EditText email;
  private static LoginActivity rhl;
  static EditText sha1;
  String homeSite = "http://73.55.136.67/";
  private UserLoginTask mAuthTask = null;
  private View mLoginFormView;
  private TextView mLoginStatusMessageView;
  private View mLoginStatusView;
  private String mPassword;
  private EditText mPasswordView;
  private String mUserName;
  private EditText mUserNameView;

  @TargetApi(13)

  public void attemptLogin()
    throws NoSuchAlgorithmException
  {
    if (this.mAuthTask != null);
    
      this.mUserNameView.setError(null);
      this.mPasswordView.setError(null);
      this.mUserName = this.mUserNameView.getText().toString();
      this.mPassword = this.mPasswordView.getText().toString();
      EditText localEditText = null;
      int j;
      if (TextUtils.isEmpty(this.mPassword))
      {
        this.mPasswordView.setError(getString(2131165198));
        localEditText = this.mPasswordView;
        j = 1;
      }

        if (TextUtils.isEmpty(this.mUserName))
        {
          this.mUserNameView.setError(getString(2131165198));
          localEditText = this.mUserNameView;
          j = 1;
        }

        localEditText.requestFocus();
        int i = this.mPassword.length();
        j = 0;
        localEditText = null;
        if (i < 4)
        {
          this.mPasswordView.setError(getString(2131165196));
          localEditText = this.mPasswordView;
          j = 1;
        }
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      HttpPost localHttpPost = new HttpPost(this.homeSite + "php/authentication.php");
      try
      {
        ArrayList localArrayList = new ArrayList(4);
        localArrayList.add(new BasicNameValuePair("email", this.mUserName));
        localArrayList.add(new BasicNameValuePair("sha1", this.mPassword));
        localArrayList.add(new BasicNameValuePair("action", "2"));
        localArrayList.add(new BasicNameValuePair("webdesk", "0"));
        localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
        String str = new String(EntityUtils.toString(localDefaultHttpClient.execute(localHttpPost).getEntity(), "UTF-8"));
        Log.d("Starting second Ac", str);
        if (str.contains("200"))
        {
          Log.d("Starting second Ac", "Yep1");
          Intent localIntent = new Intent(this, RoomerHome.class);
          Log.d("Starting second Ac", "Yep2");
          startActivity(localIntent);
          Log.d("Starting second Ac", "Yep3");
          SharedPreferencesEditor localSharedPreferencesEditor = new SharedPreferencesEditor(getApplicationContext(), "MyPrefsFile");
          localSharedPreferencesEditor.editClear();
          localSharedPreferencesEditor.putUserName(this.mUserName);
        //  localSharedPreferencesEditor.putSha1(HashSha1.SHA1(this.mPassword));
          localSharedPreferencesEditor.putHandle(((JsonObject)new JsonParser().parse(str)).get("handle").toString());
          localSharedPreferencesEditor.commitChanges();
          rhl.finish();
          return;
        }
      }
      catch (IOException localIOException)
      {
      }
    }
  

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    rhl = this;
    setContentView(2130903040);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    this.mUserName = getIntent().getStringExtra("com.example.android.authenticatordemo.extra.EMAIL");
    this.mUserNameView = ((EditText)findViewById(2131427331));
    Log.d("Logging", "log");
    this.mUserNameView.setText(this.mUserName);
    Log.d("Logging", "logqw");
    this.mPasswordView = ((EditText)findViewById(2131427332));
    Log.d("Logging", "lw");
    this.mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousInt == 2131427333) || (paramAnonymousInt == 0))
          try
          {
            LoginActivity.this.attemptLogin();
            return true;
          }
          catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
          {
            while (true)
              localNoSuchAlgorithmException.printStackTrace();
          }
        return false;
      }
    });
    Log.d("Loggi222ng", "logqw");
    this.mLoginFormView = findViewById(2131427330);
    findViewById(2131427328).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          LoginActivity.this.attemptLogin();
          return;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
        {
          localNoSuchAlgorithmException.printStackTrace();
        }
      }
    });
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    getMenuInflater().inflate(2131361792, paramMenu);
    return true;
  }

  public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
  {
    public UserLoginTask()
    {
    }

    protected Boolean doInBackground(Void[] paramArrayOfVoid)
    {
      while (true)
      {
        String[] arrayOfString1;
        int j;
        try
        {
          Thread.sleep(2000L);
          arrayOfString1 = LoginActivity.DUMMY_CREDENTIALS;
          int i = arrayOfString1.length;
          j = 0;
          if (j >= i)
            return Boolean.valueOf(true);
        }
        catch (InterruptedException localInterruptedException)
        {
          return Boolean.valueOf(false);
        }
        String[] arrayOfString2 = arrayOfString1[j].split(":");
        if (arrayOfString2[0].equals(LoginActivity.this.mUserName))
          return Boolean.valueOf(arrayOfString2[1].equals(LoginActivity.this.mPassword));
        j++;
      }
    }

    protected void onCancelled()
    {
      LoginActivity.this.mAuthTask = null;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
      LoginActivity.this.startActivity(new Intent(LoginActivity.this, LoginActivity.class));
      return true;
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      LoginActivity.this.mAuthTask = null;
      if (paramBoolean.booleanValue())
      {
        LoginActivity.this.finish();
        return;
      }
      LoginActivity.this.mPasswordView.setError(LoginActivity.this.getString(2131165197));
      LoginActivity.this.mPasswordView.requestFocus();
    }
  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.LoginActivity
 * JD-Core Version:    0.6.2
 */