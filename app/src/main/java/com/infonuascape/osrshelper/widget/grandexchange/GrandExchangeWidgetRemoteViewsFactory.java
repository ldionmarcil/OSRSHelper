package com.infonuascape.osrshelper.widget.grandexchange;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.infonuascape.osrshelper.R;
import com.infonuascape.osrshelper.fetchers.grandexchange.RSBuddyPriceFetcher;
import com.infonuascape.osrshelper.models.grandexchange.RSBuddyPrice;

import java.text.NumberFormat;

/**
 * Created by marc_ on 2018-01-14.
 */

public class GrandExchangeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final String TAG = "GrandExchangeWidget";

    public final static String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";
    private Context mContext;
    private int mAppWidgetId;
    private String itemId;
    private RSBuddyPrice rsBuddyPrice;
    private boolean isError;

    public GrandExchangeWidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        itemId = intent.getStringExtra(EXTRA_ITEM_ID);
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        Log.i(TAG, "onCreate");
        isError = false;
    }

    public void onDestroy() {
    }

    public int getCount() {
        return 1;
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_grand_exchange_price);


        // set value into textview
        if(rsBuddyPrice != null) {
            rv.setTextViewText(R.id.item_price, NumberFormat.getInstance().format(rsBuddyPrice.buying) + "gp");
        } else if(isError) {
            rv.setTextViewText(R.id.item_price, mContext.getResources().getString(R.string.error));
        }

        // Return the remote views object.
        return rv;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        Log.i(TAG, "onDataSetChanged");
        try {
            rsBuddyPrice = new RSBuddyPriceFetcher(mContext.getApplicationContext()).fetch(itemId);
            isError = rsBuddyPrice == null;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}