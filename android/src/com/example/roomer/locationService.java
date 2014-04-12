package com.example.roomer;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class locationService
  implements LocationListener
{
  public static double latitude = 0.0D;
  public static double longitude = 0.0D;
  public boolean LocationUpdated = false;
  public boolean constant;
  protected LocationListener locationListener;
  protected LocationManager locationManager;

  public locationService(Context paramContext, boolean paramBoolean)
  {
    this.locationManager = ((LocationManager)paramContext.getSystemService("location"));
    if (paramBoolean)
      this.locationManager.requestLocationUpdates("gps", 0L, 0.0F, this);
    this.constant = paramBoolean;
  }

  public static void updateLocation()
  {
  }

  public static void updateLocation(double paramDouble1, double paramDouble2)
  {
  }

  public void onLocationChanged(Location paramLocation)
  {
    if (this.constant)
    {
      if (!this.LocationUpdated)
      {
        latitude = paramLocation.getLatitude();
        longitude = paramLocation.getLongitude();
        Log.d("Latitude:" + latitude + ", Longitude:" + longitude, "YO");
        updateLocation(latitude, longitude);
        this.LocationUpdated = true;
        new Handler().postDelayed(new Runnable()
        {
          public void run()
          {
            locationService.this.LocationUpdated = false;
          }
        }
        , 4000L);
      }
      return;
    }
    latitude = paramLocation.getLatitude();
    longitude = paramLocation.getLongitude();
    Log.d("Latitude:" + latitude + ", Longitude:" + longitude, "YO");
    updateLocation(latitude, longitude);
  }

  public void onProviderDisabled(String paramString)
  {
  }

  public void onProviderEnabled(String paramString)
  {
  }

  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
  }

  public void requestSingleUpdate()
  {
    this.locationManager.requestSingleUpdate("gps", this, null);
  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.locationService
 * JD-Core Version:    0.6.2
 */