package com.pandey.popcorn4.deeplinks;


import com.airbnb.deeplinkdispatch.DeepLinkSpec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@DeepLinkSpec(prefix = { "popcorn://"})
@Retention(RetentionPolicy.CLASS)
public @interface AppDeeplink {
    String[] value();
}
