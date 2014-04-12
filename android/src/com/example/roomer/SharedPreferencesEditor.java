package com.example.roomer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesEditor
{
  public SharedPreferences.Editor ed;
  public SharedPreferences pref;
  public String preferenceName;

  public SharedPreferencesEditor(Context paramContext, String paramString)
  {
    this.pref = paramContext.getSharedPreferences(paramString, 0);
    this.preferenceName = paramString;
    getEditor();
  }

  public void commitChanges()
  {
    this.ed.commit();
  }

  public void editClear()
  {
    this.ed.clear();
  }

  public void getEditor()
  {
    this.ed = this.pref.edit();
  }

  public String getHandle()
  {
    return this.pref.getString("key_handle", this.preferenceName);
  }

  public String getSha1()
  {
    return this.pref.getString("key_password", this.preferenceName);
  }

  public String getUserName()
  {
    return this.pref.getString("key_email", this.preferenceName);
  }

  public void putHandle(String paramString)
  {
    this.ed.putString("key_handle", paramString);
  }

  public void putSha1(String paramString)
  {
    this.ed.putString("key_password", paramString);
  }

  public void putUserName(String paramString)
  {
    this.ed.putString("key_email", paramString);
  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.SharedPreferencesEditor
 * JD-Core Version:    0.6.2
 */