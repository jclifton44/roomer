package com.example.roomer;

import android.content.Context;
import android.util.Log;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

public class UserNode
{
  public static locationService ls;
  private String email;
  private String handle;

  public UserNode(Context paramContext, locationService paramlocationService)
  {
    ls = paramlocationService;
    SharedPreferencesEditor localSharedPreferencesEditor = new SharedPreferencesEditor(paramContext, "MyPrefsFile");
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpPost localHttpPost = new HttpPost(RoomerHomeLogin.homeSite + "php/authentication.php");
    try
    {
      ArrayList localArrayList = new ArrayList(4);
      localArrayList.add(new BasicNameValuePair("email", localSharedPreferencesEditor.getUserName()));
      localArrayList.add(new BasicNameValuePair("sha1", localSharedPreferencesEditor.getSha1()));
      localArrayList.add(new BasicNameValuePair("lat", ""+locationService.latitude));
      localArrayList.add(new BasicNameValuePair("long", ""+locationService.longitude));
      localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
      Log.d("Starting", "YA");
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      Log.d("Starting", "END");
      String str = new String(EntityUtils.toString(localHttpResponse.getEntity(), "UTF-8"));
      Log.d("Starting second Ac", str);
      Log.d("IN cREATE ACTIVE USER", str);
      if (str.contains("200"))
      {
        JsonObject localJsonObject = (JsonObject)new JsonParser().parse(str);
        this.handle = localJsonObject.get("handle").toString();
        this.email = localJsonObject.get("email").toString();
        Log.d("Starting second Ac", "Yep2");
        Log.d("Starting second Ac", "Yep3");
      }
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public UserNode(String paramString1, String paramString2)
  {
    setEmail(paramString2);
    setHandle(paramString1);
  }

  public static UserNode getUserNode(String paramString1, String paramString2)
  {
    return null;
  }

  public String getEmail()
  {
    return this.email;
  }

  public String getHandle()
  {
    return this.handle;
  }

  public void setEmail(String paramString)
  {
    this.email = paramString;
  }

  public void setHandle(String paramString)
  {
    this.handle = paramString;
  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.UserNode
 * JD-Core Version:    0.6.2
 */