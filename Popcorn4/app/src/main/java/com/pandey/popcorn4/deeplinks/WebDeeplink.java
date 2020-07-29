package com.pandey.popcorn4.deeplinks;

import com.airbnb.deeplinkdispatch.DeepLinkSpec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@DeepLinkSpec(prefix = { "http://www.popcorn.com/"})
@Retention(RetentionPolicy.CLASS)
public @interface WebDeeplink {
    String[] value();
}
