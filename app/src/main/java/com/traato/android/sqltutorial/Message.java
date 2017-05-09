package com.traato.android.sqltutorial;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Naresh on 06-05-2017.
 */

public class Message {

    public static void message(Context context, String string)
    {
        Toast.makeText(context, string, Toast.LENGTH_LONG);
    }
}
