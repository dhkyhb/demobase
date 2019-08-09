package com.wangdh.utilslibrary.utils.file;

import android.content.Context;
import android.content.res.AssetManager;

import com.wangdh.utilslibrary.rx.ObservableOnSubscribeAbs;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangdh
 * @time 2019/8/5 17:00
 * @describe
 */
public class FileAssetsTool {
    /**
     * 通过 assets 文件获取输入流
     *
     * @param context
     * @param name
     * @return
     * @throws IOException
     */
    public InputStream getInputByName(Context context, String name) throws IOException {
        AssetManager manager = context.getAssets();
        if (manager == null) return null;
        InputStream open = manager.open(name);
        return open;
    }

    /**
     * 将 assets的文件拷贝到sd卡中
     *
     * @param context
     * @param assetsName assets文件名
     * @param sdPath     要拷贝的路径
     * @param sdFileName 要拷贝到sd卡的文件名
     * @param callback   回调
     */
    public void copyAssetsToSD(Context context, String assetsName, String sdPath, String sdFileName, Observer callback) {
        Map<String, Object> data = new HashMap<>();
        data.put("context", context);
        data.put("assetsName", assetsName);
        data.put("sdPath", sdPath);
        data.put("sdFileName", sdFileName);

        ObservableOnSubscribeAbs<Map> observableOnSubscribe = new ObservableOnSubscribeAbs<Map>() {
            @Override
            public void subscribe(ObservableEmitter<Map> emitter) throws Exception {
                Map<String, Object> data = getData();
                Context context = (Context) data.get("context");
                String assetsName = (String) data.get("assetsName");
                String sdPath = (String) data.get("sdPath");
                String sdFileName = (String) data.get("sdFileName");

                boolean isS = false;
                FileOutputStream fos = null;
                InputStream is = null;

                try {
                    System.out.println("==============从asset复制文件到内存==============copyAssets============================.");
                    File files = new File(sdPath);
                    if (!files.exists()) {
                        files.mkdirs();
                    }
                    File file = new File(files, sdFileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    AssetManager manager = context.getAssets();
                    if (manager == null) return;
                    is = manager.open(assetsName);

                    fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int byteCount = 0;

                    int available = is.available();
                    int pro = 0;
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                        // buffer字节
                        fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                        pro += byteCount;
                        String div = div(String.valueOf(pro), String.valueOf(available));
                        int progress = Float.valueOf(mul(div, "100")).intValue();
                        data.put("progress", String.valueOf(progress));
                        emitter.onNext(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.flush();// 刷新缓冲区
                        fos.close();
                    } catch (Exception e) {
                    }
                    try {
                        is.close();
                    } catch (Exception e) {
                    }
                }
            }
        };

        observableOnSubscribe.setData(data);
        if (callback == null) {
            callback = new Observer<Map>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Map map) {
                    String progress = (String) map.get("progress");
                    TLog.e("复制进度" + progress);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
        }

        Observable.create(observableOnSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    public static String div(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }

    /**
     * 读取文本文件到 StringBuilde 中， 目前用于读取json文件夹 进行解析
     *
     * @param context
     * @return
     */
    public StringBuilder readeTextByAssets(Context context, String name) {
        StringBuilder jsonSB = new StringBuilder();
        try {
            BufferedReader addressJsonStream = null;
            addressJsonStream = new BufferedReader(new InputStreamReader(context.getAssets().open(name)));
            String line;
            while ((line = addressJsonStream.readLine()) != null) {
                jsonSB.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonSB;
    }
}
