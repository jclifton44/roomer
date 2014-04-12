package com.example.roomer;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;

public class HomeFeatureLayout
  implements View.OnTouchListener, GestureDetector.OnGestureListener
{
  private static final int SWIPE_MIN_DISTANCE = 25;
  private static final int SWIPE_PAGE_ON_FACTOR = 10;
  private static final int SWIPE_THRESHOLD_VELOCITY = 1000;
  private static boolean flingDisable = false;
  private int activeItem = 0;
  private float currentScrollX;
  private GestureDetector gestureDetector;
  private HorizontalScrollView hzsv;
  private int itemWidth = 0;
  private int mActiveFeature = 0;
  private int maxItem = 0;
  private float prevScrollX = 0.0F;
  private int scrollTo = 0;
  private boolean start = true;

  public HomeFeatureLayout()
  {
  }

  public HomeFeatureLayout(Context paramContext)
  {
  }

  public HomeFeatureLayout(Context paramContext, int paramInt1, int paramInt2, HorizontalScrollView paramHorizontalScrollView)
  {
    this.maxItem = paramInt1;
    this.itemWidth = paramInt2;
    this.hzsv = paramHorizontalScrollView;
    this.gestureDetector = new GestureDetector(this);
    paramHorizontalScrollView.setOnTouchListener(this);
  }

  public boolean onDown(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    if (flingDisable)
      return false;
    Log.d("fling", "fling");
    if ((paramMotionEvent1 == null) || (paramMotionEvent2 == null))
      return false;
    float f1 = paramMotionEvent1.getX();
    float f2 = paramMotionEvent2.getX();
    Log.d(""+f1, "PTX1");
    Log.d(""+f2, "PTX2");
    Log.d(""+Math.abs(paramFloat1), "VELOCITY");
    boolean bool2;
    if ((f1 - f2 > 25.0F) && (Math.abs(paramFloat1) > 1000.0F))
    {
      if (this.activeItem < -1 + this.maxItem)
        this.activeItem = (1 + this.activeItem);
      Log.d(""+f2, "Increasing VIew");
      bool2 = true;
    }
    while (true)
    {
      Log.d(""+this.itemWidth, "SDFSDFSDF");
      this.scrollTo = (this.activeItem * this.itemWidth);
      Log.d(""+this.activeItem, "SDFSDFSDF");
      this.hzsv.smoothScrollTo(this.scrollTo, 0);
      
      boolean bool1 = f2 - f1 < 25.0F;
      bool2 = false;
      if (bool1)
      {
        boolean bool3 = Math.abs(paramFloat1) < 1000.0F;
        bool2 = false;
        if (bool3)
        {
          Log.d(""+f2, "Decreasing VIew");
          if (this.activeItem > 0)
            this.activeItem = (-1 + this.activeItem);
          bool2 = true;
        }
      }
    }
  }

  public void onLongPress(MotionEvent paramMotionEvent)
  {
  }

  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public void onShowPress(MotionEvent paramMotionEvent)
  {
  }

  public boolean onSingleTapUp(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
	int k = 0;
    Boolean.valueOf(false);
    Log.d("fling2", "fling2");
    Boolean localBoolean;
    if (this.gestureDetector.onTouchEvent(paramMotionEvent))
    {
      Log.d("fling2.1", "fling2.1");
      localBoolean = Boolean.valueOf(true);
    }
    while (true)
    {
     // return localBoolean.booleanValue();
      if ((paramMotionEvent.getAction() == 1) || (paramMotionEvent.getAction() == 3))
      {
        int i = this.hzsv.getScrollX();
        int j = paramView.getMeasuredWidth();
        this.mActiveFeature = ((i + j / 2) / j);
        k = j * this.mActiveFeature;
        this.hzsv.smoothScrollTo(k, 0);
        Log.d("fling2.2", "fling2.2 " + j);
        localBoolean = Boolean.valueOf(true);
      }
      else
      {
        Log.d("fling2.3", "fling2.3");
        localBoolean = Boolean.valueOf(false);
      }
    }
  }

  class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
  {
    MyGestureDetector()
    {
    }

    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      int i = 2;
      while (true)
      {
        try
        {
          if ((paramMotionEvent1.getX() - paramMotionEvent2.getX() > 25.0F) && (Math.abs(paramFloat1) > 1000.0F))
          {
            int m = HomeFeatureLayout.this.hzsv.getMeasuredWidth();
            HomeFeatureLayout localHomeFeatureLayout2 = HomeFeatureLayout.this;
            if (HomeFeatureLayout.this.mActiveFeature < i)
              i = 1 + HomeFeatureLayout.this.mActiveFeature;
            localHomeFeatureLayout2.mActiveFeature = i;
            HomeFeatureLayout.this.hzsv.smoothScrollTo(m * HomeFeatureLayout.this.mActiveFeature, 0);
            return true;
          }
          if ((paramMotionEvent2.getX() - paramMotionEvent1.getX() > 25.0F) && (Math.abs(paramFloat1) > 1000.0F))
          {
            int j = HomeFeatureLayout.this.hzsv.getMeasuredWidth();
            HomeFeatureLayout localHomeFeatureLayout1 = HomeFeatureLayout.this;
          
            localHomeFeatureLayout1.mActiveFeature = HomeFeatureLayout.this.mActiveFeature;
            HomeFeatureLayout.this.hzsv.smoothScrollTo(j * HomeFeatureLayout.this.mActiveFeature, 0);
            return true;
          }
        }
        catch (Exception localException)
        {
          Log.e("Fling", "There was an error processing the Fling event:" + localException.getMessage());
        }
        return false;

      }
    }
  }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.HomeFeatureLayout
 * JD-Core Version:    0.6.2
 */