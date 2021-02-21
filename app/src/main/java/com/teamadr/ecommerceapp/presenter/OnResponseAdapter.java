package com.teamadr.ecommerceapp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.model.response.ResponseBody;

import java.lang.ref.WeakReference;

public abstract class OnResponseAdapter<T extends ResponseBody, U extends ResponseBody> implements OnResponseListener<T, U> {
    private WeakReference<Context> contextWeakReference;

    public OnResponseAdapter(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
    }

    public abstract void complete(boolean success);

    public abstract void success(T body);

    public void error(U body) {
        AlertDialog.Builder builder = new AlertDialog.Builder(contextWeakReference.get());
        String message;
        if (TextUtils.isEmpty(body.getMsg())) {
            Context context = contextWeakReference.get();
            if (context == null) {
                message = "Unknown error";
            } else {
                message = context.getString(R.string.error_happened);
            }
        } else {
            message = body.getMsg();
        }
        builder.setTitle(R.string.error)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss())
                .show();
    }

    public void unexpectedError() {
        Toast.makeText(contextWeakReference.get(), R.string.error_happened, Toast.LENGTH_LONG);
    }

    @Override
    public void onSuccess(T body) {
        complete(true);
        success(body);
    }

    @Override
    public void onError(U body) {
        complete(false);
        error(body);
    }

    @Override
    public void onError() {
        complete(false);
        unexpectedError();
    }

    @Override
    public void onNetworkConnectionError() {
        complete(false);
        Toast.makeText(contextWeakReference.get(), R.string.no_internet_connection, Toast.LENGTH_LONG);

    }

    @Override
    public void onTimeOut() {
        complete(false);
        Toast.makeText(contextWeakReference.get(), R.string.time_out, Toast.LENGTH_LONG);
    }
}

