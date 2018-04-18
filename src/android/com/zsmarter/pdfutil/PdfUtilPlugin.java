package com.zsmarter.pdfutil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by hechengbin on 2017/9/3.
 */

public class PdfUtilPlugin extends CordovaPlugin{

    private static final String TAG = "PdfUtilPlugin";
    private static final String DOWNLOAD = "Download";
    private static final String OPENPDF = "OpenPdf";
    private static final String DELETEFILE = "DeleteFile";
    private static final String DOWNLOAD_URL = "downloadUrl";
    private static final String DOWNLOAD_PATH = "downloadPath";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String PDF_PATH = "pdfPath";

    private String abPath = "";
    private Context context;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        abPath = Environment.getExternalStorageDirectory() + "/";
        context = cordova.getActivity();
    }

    @Override
    public boolean execute(String action, JSONArray args,final CallbackContext callbackContext) throws JSONException {

        JSONObject obj = args.getJSONObject(0);

        if (action.equals(DOWNLOAD)) {
            //下载
            String downloadUrl = obj.optString(DOWNLOAD_URL);
            String downloadPath = obj.optString(DOWNLOAD_PATH);
            String accessToken = obj.optString(ACCESS_TOKEN);
            DownloadUtil.get().download(downloadUrl, downloadPath,accessToken, new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess(String path) {
                    Log.i(TAG,"onDownloadSuccess");
                    PluginResult pluginResult=   new PluginResult(PluginResult.Status.OK, "下载成功");
                    pluginResult.setKeepCallback(true);

                    callbackContext.sendPluginResult(pluginResult);
                    openPDF(path,callbackContext);
                }
                @Override
                public void onDownloading(int progress) {
                    Log.i(TAG,"progress " +progress);
                }
                @Override
                public void onDownloadFailed() {
                    Log.i(TAG,"onDownloadFailed");
                    PluginResult pluginResult=   new PluginResult(PluginResult.Status.ERROR, "下载失败！");
                    pluginResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(pluginResult);
                }
            });

        }else if (action.equals(OPENPDF)){
            //打开本地文件
            Log.e("hcb",OPENPDF);
            String pdfPath = abPath+obj.optString(PDF_PATH);
            openPDF(pdfPath,callbackContext);

        }else if (action.equals(DELETEFILE)){
            //删除文件
        }
        return true;
    }

    private void openPDF(String path,CallbackContext callbackContext){
        Log.e("lifu","open pdf "+path);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = null;
        File file = new File(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
          uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.zsmarter.jt.fileprovider", file);
          Log.e("hcb",uri.toString());
        }else {
          Log.e("hcb",Build.VERSION.SDK_INT+"");
          uri = Uri.fromFile(file);
        }
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setDataAndType(uri, "application/pdf");

        Log.e(TAG,"openPDF path " +path+" context "+context);
        try{
            context.startActivity(i);

        }catch(Exception e){
            Log.e("lifu","open pdf failed");
            Log.e("lifu","begin send plugin result");
            PluginResult pluginResult=   new PluginResult(PluginResult.Status.ERROR, "打开协议失败，未找到相应的PDF阅读器！");
            pluginResult.setKeepCallback(true);

            callbackContext.sendPluginResult(pluginResult);
        }

    }

}
