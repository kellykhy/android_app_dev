package com.kellykim.quizforkids;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class message {
    public static void showMessage(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
}