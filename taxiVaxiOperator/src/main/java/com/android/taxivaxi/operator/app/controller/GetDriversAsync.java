package com.android.taxivaxi.operator.app.controller;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.android.taxivaxi.operator.app.custom.CustomProgressDialog;
import com.android.taxivaxi.operator.app.webservice.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MB on 9/4/2015.
 */
public class GetDriversAsync extends AsyncTask<String, String, JSONObject> {
    Context context;
    Handler mHandler;
    String URL;
    private JSONParser jsonParser = new JSONParser();
    private Dialog spinningDialog;
    public GetDriversAsync(Context context, Handler handler, String url) {

        this.context = context;
        this.mHandler = handler;
        this.URL = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        try {
            spinningDialog = CustomProgressDialog.showProgressDialog(context);
            spinningDialog.setCancelable(false);
            spinningDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject doInBackground(String... params) {
       JSONObject json = null;
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("access_token", params[0]));
        postParameters.add(new BasicNameValuePair("city_id", params[1]));

        try {
            json = jsonParser.makeHttpRequest(URL, "POST", postParameters);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return json;
    }
    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        try {
            spinningDialog.dismiss();

            if (result != null) {
                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
