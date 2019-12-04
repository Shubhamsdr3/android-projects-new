package com.pandey.popcorn4.deeplinks;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.deeplinkdispatch.DeepLinkHandler;
import com.airbnb.deeplinkdispatch.DeepLinkResult;
import com.pandey.popcorn4.HomeActivity;
import com.pandey.popcorn4.base.BaseActivity;

@DeepLinkHandler(AppDeeplinkModule.class)
public class DeeplinkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeepLinkDelegate deepLinkDelegate =
                new DeepLinkDelegate(new AppDeeplinkModuleLoader());
        DeepLinkResult deepLinkResult = deepLinkDelegate.dispatchFrom(this);
        if (!deepLinkResult.isSuccessful()) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        finish(); // Finish this Activity since the correct one has been just started
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
