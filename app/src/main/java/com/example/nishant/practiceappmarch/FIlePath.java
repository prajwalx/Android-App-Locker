package com.example.nishant.practiceappmarch;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * Created by Nishant on 15-Mar-17.
 */

public class FIlePath {

    @TargetApi(19)
    public static String getPath(Context context, Uri uri)
    {
        boolean isKitkat= Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT;

        if(isKitkat && DocumentsContract.isDocumentUri(context, uri))
        {
            if(isExternalStorageDocument(uri))
            {
                final String docId=DocumentsContract.getDocumentId(uri);
                final String[] split=docId.split(":");
                final String type=split[0];

                if("primary".equalsIgnoreCase(type))
                {
                    return Environment.getExternalStorageDirectory()+"/"+split[1];
                }
            }

            else if(isDownloadsDocument(uri))
            {
                final String id=DocumentsContract.getDocumentId(uri);
                final Uri contentUri= ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));
                return getDataColumn(context,contentUri,null,null);

            }

            else if(isMediaDocument(uri))
            {
                final String docId=DocumentsContract.getDocumentId(uri);
                final String[] split=docId.split(":");
                final String type=split[0];
                Uri contentUri=null;
                if("image".equals(type))
                {
                    contentUri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                else if("video".equals(type))
                {
                    contentUri=MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
                else if("audio".equals(type))
                {
                    contentUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection="_id=?";
                final String[] selectionArgs= new String[]{split[1]};
                return getDataColumn(context,contentUri,selection,selectionArgs);
            }
        }

        else if("content".equalsIgnoreCase(uri.getScheme()))
        {
            if(isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context,uri,null,null);
        }
        else if("file".equalsIgnoreCase(uri.getScheme()))
        {
            return uri.getPath();
        }
        return null;
    }




    public static String getDataColumn(Context context,Uri uri,String selection,String[] selectionArgs)
    {
        Cursor cursor=null;
        final String column="_data";
        final String[] projection={column};

        try
        {
        cursor=context.getContentResolver().query(uri,projection,selection,selectionArgs,null);
        if(cursor!=null && cursor.moveToFirst())
        {
            final int index=cursor.getColumnIndexOrThrow(column);
            return cursor.getString(index);
        }
        }finally {
            if(cursor!=null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri)
    {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri)
    {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri)
    {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri)
    {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
