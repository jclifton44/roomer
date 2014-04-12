package com.example.roomer;

import android.content.Context;
import android.util.Log;
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

public class Message
{
  private Context context;
  private double latitude;
  private double longitude;
  private String message;
  private ArrayList<String> photoUrls;
  private UserNode uNode;
  private String userProfileUrl;

  public Message(String paramString, Context paramContext, UserNode paramUserNode)
  {
    this.context = paramContext;
    this.uNode = paramUserNode;
    setMessage(paramString);
  }

  public static ArrayList<String> convert(ArrayList<Message> paramArrayList)
  {
    return null;
  }

  public static ArrayList<Message> getMessagesServer(int paramInt1, int paramInt2, int paramInt3)
  {
    Log.d("136:RoomerHomeLogin", "Attempting Login");
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    new ArrayList();
    HttpPost localHttpPost = new HttpPost(RoomerHomeLogin.homeSite + "action.php");
    try
    {
      ArrayList localArrayList = new ArrayList(5);
      localArrayList.add(new BasicNameValuePair("limit", "20"));
      localArrayList.add(new BasicNameValuePair("offset", "0"));
      localArrayList.add(new BasicNameValuePair("action", "paginateMessage"));
      localArrayList.add(new BasicNameValuePair("lat", ""+locationService.latitude));
      localArrayList.add(new BasicNameValuePair("long", ""+locationService.longitude));
      localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
      Log.d("Starting", "YA");
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      Log.d("Starting", "END");
      String str = new String(EntityUtils.toString(localHttpResponse.getEntity(), "UTF-8"));
      Log.d("Starting second Ac", str);
      if (str.contains("200"))
        Log.d(str, "");
      return null;
    }
    catch (IOException localIOException)
    {

    }
	return null;

  }

  public String getMessage()
  {
    return this.message;
  }

  public boolean send()
  {
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpPost localHttpPost = new HttpPost(RoomerHomeLogin.homeSite + "php/action.php");
    try
    {
      Log.d("inregister", "");
      SharedPreferencesEditor localSharedPreferencesEditor = new SharedPreferencesEditor(this.context, "MyPrefsFile");
      ArrayList localArrayList = new ArrayList(4);
      UserNode.ls.requestSingleUpdate();
      localArrayList.add(new BasicNameValuePair("body", getMessage()));
      localArrayList.add(new BasicNameValuePair("title", localSharedPreferencesEditor.getHandle()));
      Log.d(""+locationService.latitude, "LAT");
      localArrayList.add(new BasicNameValuePair("lat", ""+locationService.latitude));
      localArrayList.add(new BasicNameValuePair("long", ""+locationService.longitude));
      localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
      String str = new String(EntityUtils.toString(localDefaultHttpClient.execute(localHttpPost).getEntity(), "UTF-8"));
      Log.d("Starting second Ac", str);
      boolean bool1 = str.contains("200");
      boolean bool2 = false;
      if (bool1)
        bool2 = true;
      return bool2;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      return false;
    }
    catch (IOException localIOException)
    {
    }
    return false;
  }

  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.Message
 * JD-Core Version:    0.6.2
 */