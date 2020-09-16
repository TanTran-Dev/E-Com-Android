package com.teamadr.ecommerceapp.utils;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Utils {
    public static String getMimeType(String url) {
        String type = "";
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    public static MultipartBody.Part createMultiPart(String name, File file) {
        String mimeType = Utils.getMimeType(file.getPath());
        if (TextUtils.isEmpty(mimeType)) {
            mimeType = "image/png";
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);
    }
}
