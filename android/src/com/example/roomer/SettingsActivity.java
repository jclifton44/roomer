package com.example.roomer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceActivity.Header;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import java.util.List;

public class SettingsActivity extends PreferenceActivity
{
  private static final boolean ALWAYS_SIMPLE_PREFS = true;
  private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener()
  {
    public boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
    {
      String str = paramAnonymousObject.toString();
      if ((paramAnonymousPreference instanceof ListPreference))
      {
        ListPreference localListPreference = (ListPreference)paramAnonymousPreference;
        int i = localListPreference.findIndexOfValue(str);
        CharSequence localCharSequence = null;
        if (i >= 0)
          localCharSequence = localListPreference.getEntries()[i];
        paramAnonymousPreference.setSummary(localCharSequence);
      }

        if ((paramAnonymousPreference instanceof RingtonePreference))
        {
          if (TextUtils.isEmpty(str))
          {
            paramAnonymousPreference.setSummary(2131165213);
          }
          else
          {
            Ringtone localRingtone = RingtoneManager.getRingtone(paramAnonymousPreference.getContext(), Uri.parse(str));
            if (localRingtone == null)
              paramAnonymousPreference.setSummary(null);
            else
              paramAnonymousPreference.setSummary(localRingtone.getTitle(paramAnonymousPreference.getContext()));
          }
        }
        else
          paramAnonymousPreference.setSummary(str);
        return true;
      }

  };

  private static void bindPreferenceSummaryToValue(Preference paramPreference)
  {
    paramPreference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
    sBindPreferenceSummaryToValueListener.onPreferenceChange(paramPreference, PreferenceManager.getDefaultSharedPreferences(paramPreference.getContext()).getString(paramPreference.getKey(), ""));
  }

  private static boolean isSimplePreferences(Context paramContext)
  {
    return true;
  }

  private static boolean isXLargeTablet(Context paramContext)
  {
    return (0xF & paramContext.getResources().getConfiguration().screenLayout) >= 4;
  }

  private void setupSimplePreferencesScreen()
  {
    if (!isSimplePreferences(this))
      return;
   // addPreferencesFromResource(2130968577);
    findPreference("button").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SettingsActivity.this.logout();
        return false;
      }
    });
   // ((EditTextPreference)findPreference("handlePreferenceId"));
    new SharedPreferencesEditor(getApplicationContext(), "MyPrefsFile").getHandle();
    PreferenceCategory localPreferenceCategory = new PreferenceCategory(this);
    localPreferenceCategory.setTitle("More Settings");
    getPreferenceScreen().addPreference(localPreferenceCategory);
  }

  public void logout()
  {
    SharedPreferences.Editor localEditor = getApplicationContext().getSharedPreferences("MyPrefsFile", 0).edit();
    localEditor.clear();
    localEditor.commit();
    startActivity(new Intent(this, RoomerHomeLogin.class));
    finish();
  }

  @TargetApi(11)
  public void onBuildHeaders(List<PreferenceActivity.Header> paramList)
  {
    if (!isSimplePreferences(this))
      loadHeadersFromResource(2130968578, paramList);
  }

  public boolean onIsMultiPane()
  {
    return (isXLargeTablet(this)) && (!isSimplePreferences(this));
  }

  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    setupSimplePreferencesScreen();
  }

  @TargetApi(11)
  public static class DataSyncPreferenceFragment extends PreferenceFragment
  {
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      addPreferencesFromResource(2130968576);
      SettingsActivity.bindPreferenceSummaryToValue(findPreference("sync_frequency"));
    }
  }

  @TargetApi(11)
  public static class GeneralPreferenceFragment extends PreferenceFragment
  {
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      addPreferencesFromResource(2130968577);
      SettingsActivity.bindPreferenceSummaryToValue(findPreference("example_text"));
      SettingsActivity.bindPreferenceSummaryToValue(findPreference("example_list"));
    }
  }

  @TargetApi(11)
  public static class NotificationPreferenceFragment extends PreferenceFragment
  {
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      addPreferencesFromResource(2130968579);
      SettingsActivity.bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
    }
  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.SettingsActivity
 * JD-Core Version:    0.6.2
 */