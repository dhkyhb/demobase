package com.wangdh.demolist.utils;

import android.content.Context;
import android.content.Intent;

public class UIHelper {
    public static void start(Context context, Class t) {
        Intent intent = new Intent(context, t);
        context.startActivity(intent);
    }
}
