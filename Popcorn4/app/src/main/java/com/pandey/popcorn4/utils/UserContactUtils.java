package com.pandey.popcorn4.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserContactUtils {

    public static Pair<String, String> getContactValue(@NonNull Context mCtx, @Nullable Intent data) {
        Cursor cursor = null;
        String name = null;
        String phoneNo = null;
        try {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    cursor = mCtx.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                        name = cursor.getString(nameIndex);

                        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNo = cursor.getString(phoneIndex);
                        if (!TextUtils.isEmpty(phoneNo)) {
                            phoneNo = phoneNo.replace("+91", "");
                            phoneNo = phoneNo.replaceAll("[^\\d]", "");
                            if (phoneNo.length() > 10) {
                                phoneNo = phoneNo.substring(phoneNo.length() - 10);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return new Pair<>(name, phoneNo);
    }
}
