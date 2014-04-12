package com.example.roomer;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("NewApi")
public class RoomerHome extends Activity
{
  private static final int BUTTON_WIDTH = 150;
  private static final int HEADER_HEIGHT = 150;
  private static final int TEXTVIEW_HEIGHT = 100;
  public static ArrayList<String> list;
  public StableArrayAdapter adapter;
  public EditText edt;
  private HomeFeatureLayout horizontalScrollView;
  private LinearLayout linearLayout;
  public ListView listv;
  public locationService ls;
  public Toast toast;
  private UserNode un;
  String[] values = { "Android", "iPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android", "iPhone", "WindowsMobile" };

  public void addMessage(Message paramMessage)
  {
    list.add(paramMessage.getMessage());
    //this.adapter = new StableArrayAdapter(this, 17367043, list);
    this.listv.setAdapter(this.adapter);
  }

  public void addMessage(ArrayList<Message> paramArrayList)
  {
  }

  public void logout()
  {
    SharedPreferences.Editor localEditor = getApplicationContext().getSharedPreferences("MyPrefsFile", 0).edit();
    localEditor.clear();
    localEditor.commit();
    startActivity(new Intent(this, RoomerHomeLogin.class));
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getActionBar().hide();
    this.ls = RoomerHomeLogin.ls;
    this.ls.requestSingleUpdate();
    this.un = new UserNode(getApplicationContext(), this.ls);
    getWindow().addFlags(2048);
    getWindow().clearFlags(1024);
    getWindow().addFlags(1024);
    getWindow().clearFlags(2048);
    int i = getWindowManager().getDefaultDisplay().getWidth();
    int j = getWindowManager().getDefaultDisplay().getHeight();
    setContentView(2130903042);
    HorizontalScrollView localHorizontalScrollView = (HorizontalScrollView)findViewById(2131427336);
    new HomeFeatureLayout(getApplicationContext(), 3, i, localHorizontalScrollView);
    LinearLayout localLinearLayout1 = (LinearLayout)findViewById(2131427337);
    ScrollView localScrollView = new ScrollView(getApplicationContext());
    localScrollView.setBackgroundColor(-1);
    ViewGroup.LayoutParams localLayoutParams1 = new ViewGroup.LayoutParams(i, -2);
    localScrollView.setLayoutParams(localLayoutParams1);
    list = new ArrayList();
    for (int k = 0; ; k++)
    {
      int m = this.values.length;
      if (k >= m)
      {
        LinearLayout localLinearLayout2 = new LinearLayout(this);
        ListView localListView = new ListView(this);
        this.listv = localListView;
        ImageView localImageView1 = new ImageView(this);
        localImageView1.setImageResource(2130837521);
        ImageView localImageView2 = new ImageView(this);
        localImageView2.setImageResource(2130837520);
        ImageView localImageView3 = new ImageView(this);
        localImageView3.setImageResource(2130837505);
        ImageView localImageView4 = new ImageView(this);
        EditText localEditText = new EditText(this);
        this.edt = localEditText;
        localImageView4.setImageResource(2130837513);
        View.OnClickListener local1 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            RoomerHome.this.logout();
          }
        };
        localImageView4.setOnClickListener(local1);
        View.OnClickListener local2 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            Intent localIntent = new Intent(RoomerHome.this, SettingsActivity.class);
            RoomerHome.this.startActivity(localIntent);
          }
        };
        localImageView1.setOnClickListener(local2);
        View.OnClickListener local3 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            String str = RoomerHome.this.edt.getText().toString();
            RoomerHome.this.edt.setText("");
            if (str != null)
            {
              if (RoomerHome.this.un == null)
                Log.d("HEY ITS NULL", "NULLLL");
              RoomerHome localRoomerHome = RoomerHome.this;
              Message localMessage = new Message(str, RoomerHome.this.getApplicationContext(), RoomerHome.this.un);
              localRoomerHome.addMessage(localMessage);
              RoomerHome.this.refreshMessages();
              localMessage.send();
            }
          }
        };
        localImageView2.setOnClickListener(local3);
        View.OnClickListener local4 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            Intent localIntent = new Intent(RoomerHome.this, SettingsActivity.class);
            RoomerHome.this.startActivity(localIntent);
          }
        };
        localImageView3.setOnClickListener(local4);
        Button localButton = new Button(this);
        localImageView1.getLayoutParams();
        localLinearLayout2.setBackgroundColor(-39322);
        localLinearLayout2.setOrientation(0);
        localLinearLayout2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        localLinearLayout2.getLayoutParams().height = 150;
        localLinearLayout2.getLayoutParams().width = i;
        localLinearLayout2.addView(localImageView1);
        localLinearLayout2.addView(localImageView4);
     //   StableArrayAdapter localStableArrayAdapter = new StableArrayAdapter(this, 17367043, list);
       // this.adapter = adapter;
        this.listv.setAdapter(this.adapter);
        this.listv.setTranscriptMode(2);
        this.listv.setStackFromBottom(true);
        this.listv.setLayoutParams(new ViewGroup.LayoutParams(-1, -60 + (-100 + (j - 150))));
        LinearLayout localLinearLayout3 = new LinearLayout(this);
        LinearLayout localLinearLayout4 = new LinearLayout(this);
        LinearLayout localLinearLayout5 = new LinearLayout(this);
        LinearLayout localLinearLayout6 = new LinearLayout(this);
        localLinearLayout3.setOrientation(1);
        localLinearLayout4.setOrientation(0);
        localEditText.setLayoutParams(new ViewGroup.LayoutParams(-150 + (i - 150), -2));
        localEditText.setGravity(80);
        localEditText.setMinimumHeight(100);
        localEditText.setMaxHeight(100);
        localEditText.clearFocus();
        localButton.setLayoutParams(new ViewGroup.LayoutParams(150, -2));
        localImageView2.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
        localImageView3.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
        ViewGroup.LayoutParams localLayoutParams2 = new ViewGroup.LayoutParams(i, j);
        localLinearLayout3.setLayoutParams(localLayoutParams2);
        ViewGroup.LayoutParams localLayoutParams3 = new ViewGroup.LayoutParams(i, j);
        localLinearLayout5.setLayoutParams(localLayoutParams3);
        ViewGroup.LayoutParams localLayoutParams4 = new ViewGroup.LayoutParams(i, j);
        localLinearLayout6.setLayoutParams(localLayoutParams4);
        localLinearLayout3.setBackgroundColor(-1);
        localLinearLayout5.setBackgroundColor(-10027162);
        localLinearLayout6.setBackgroundColor(-39322);
        localLinearLayout4.addView(localImageView2);
        localLinearLayout4.addView(localEditText);
        localLinearLayout4.addView(localImageView3);
        localLinearLayout3.addView(localLinearLayout2);
        localLinearLayout3.addView(this.listv);
        localLinearLayout3.addView(localLinearLayout4);
        localLinearLayout1.addView(localLinearLayout3);
        localLinearLayout1.addView(localLinearLayout5);
        localLinearLayout1.addView(localLinearLayout6);
        return;
      }
      list.add(this.values[k]);
    }
  }

  public void refreshMessages()
  {
   // this.adapter = new StableArrayAdapter(this, 17367043, Message.convert(Message.getMessagesServer(0, 40, 1)));
    this.listv.setAdapter(this.adapter);
  }

  private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.RoomerHome
 * JD-Core Version:    0.6.2
 */