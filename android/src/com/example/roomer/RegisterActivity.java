package com.example.roomer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

@SuppressLint("NewApi")
public class RegisterActivity extends Activity
{
  private static final String[] DUMMY_CREDENTIALS = { "foo@example.com:hello", "bar@example.com:world" };
  public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
  private static RegisterActivity ra;
  public Context context;
  private UserLoginTask mAuthTask = null;
  private String mHandle;
  private EditText mHandleView;
  private View mLoginFormView;
  private TextView mLoginStatusMessageView;
  private View mLoginStatusView;
  private String mPassword;
  private EditText mPasswordView;
  private String mUserName;
  private EditText mUserNameView;
  public Toast toast;

  
  public void attemptLogin()
    throws NoSuchAlgorithmException
  {


      this.mUserNameView.setError(null);
      this.mPasswordView.setError(null);
      this.mUserName = this.mUserNameView.getText().toString();
      this.mPassword = this.mPasswordView.getText().toString();
      this.mHandle = this.mHandleView.getText().toString();
      EditText localEditText = null;
      int j;
      if (TextUtils.isEmpty(this.mPassword))
      {
        this.mPasswordView.setError(getString(2131165198));
        localEditText = this.mPasswordView;
        j = 1;
        if (!TextUtils.isEmpty(this.mUserName))

        this.mUserNameView.setError(getString(2131165198));
        localEditText = this.mUserNameView;
        j = 1;
      }
      while (true)
      {

        localEditText.requestFocus();
        int i = this.mPassword.length();
        j = 0;
        localEditText = null;
        if (i >= 4)
          break;
        this.mPasswordView.setError(getString(2131165196));
        localEditText = this.mPasswordView;
        j = 1;
        if (!this.mUserName.contains("@"))
        {
          this.mUserNameView.setError(getString(2131165195));
          localEditText = this.mUserNameView;
          j = 1;
        }
      }
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      HttpPost localHttpPost = new HttpPost(RoomerHomeLogin.homeSite + "php/authentication.php");
      try
      {
        Log.d("inregister", "");
        ArrayList localArrayList = new ArrayList(5);
        localArrayList.add(new BasicNameValuePair("email", this.mUserName));
        localArrayList.add(new BasicNameValuePair("sha1", this.mPassword));
        localArrayList.add(new BasicNameValuePair("handle", this.mHandle));
        localArrayList.add(new BasicNameValuePair("action", "1"));
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
          localSharedPreferencesEditor.putSha1(this.mPassword);
          localSharedPreferencesEditor.commitChanges();
          ra.finish();

        }
      }
      catch (ClientProtocolException localClientProtocolException)
      {
      }
      catch (IOException localIOException)
      {
      }
    }
  

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ra = this;
    this.context = getApplicationContext();
    setContentView(2130903041);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    this.mUserName = getIntent().getStringExtra("com.example.android.authenticatordemo.extra.EMAIL");
    this.mUserNameView = ((EditText)findViewById(2131427331));
    this.mUserNameView.setText(this.mUserName);
    this.mPasswordView = ((EditText)findViewById(2131427332));
    this.mHandleView = ((EditText)findViewById(2131427334));
    this.mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousInt == 2131427333) || (paramAnonymousInt == 0))
          try
          {
            RegisterActivity.this.attemptLogin();
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
    this.mLoginFormView = findViewById(2131427330);
    findViewById(2131427328).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          RegisterActivity.this.attemptLogin();
          return;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
        {
          localNoSuchAlgorithmException.printStackTrace();
        }
      }
    });
    this.mUserNameView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Log.d("wow", "text");
      }
    });
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    getMenuInflater().inflate(2131361793, paramMenu);
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
          arrayOfString1 = RegisterActivity.DUMMY_CREDENTIALS;
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
        if (arrayOfString2[0].equals(RegisterActivity.this.mUserName))
          return Boolean.valueOf(arrayOfString2[1].equals(RegisterActivity.this.mPassword));
        j++;
      }
    }

    protected void onCancelled()
    {
      RegisterActivity.this.mAuthTask = null;
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      RegisterActivity.this.mAuthTask = null;
      if (paramBoolean.booleanValue())
      {
        RegisterActivity.this.finish();
        return;
      }
      RegisterActivity.this.mPasswordView.setError(RegisterActivity.this.getString(2131165197));
      RegisterActivity.this.mPasswordView.requestFocus();
    }
  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.RegisterActivity
 * JD-Core Version:    0.6.2
 */