package com.pandey.popcorn4.deeplinks;

import com.airbnb.deeplinkdispatch.DeepLinkSpec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@DeepLinkSpec(prefix = { "popcorn://backstack/"})
@Retention(RetentionPolicy.CLASS)
public @interface AppDeepLinkWithBackStack {
    String[] value();
}
