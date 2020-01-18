package com.wangdh.utilslibrary.netlibrary.dowload;


import android.support.annotation.NonNull;
import android.util.Log;

import com.wangdh.utilslibrary.utils.Arith;

import java.io.IOException;
import java.math.BigDecimal;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;


/**
 * 处理返回数据
 * Created by zhongjh on 2018/5/18.
 */
public class DownloadResponseBody extends ResponseBody {

    private ResponseBody responseBody;

    private DownloadListener mDownloadListener;
    private DownloadHandler mDownloadHandler = new DownloadHandler();

    // BufferedSource 是okio库中的输入流，这里就当作inputStream来使用。
    private BufferedSource bufferedSource;


    DownloadResponseBody(ResponseBody responseBody, DownloadListener mDownloadListener) {
        this.responseBody = responseBody;
        this.mDownloadListener = mDownloadListener;
        this.mDownloadHandler.initHandler(mDownloadListener);
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 处理数据
     *
     * @param source 数据源
     * @return 返回处理后的数据源
     */
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            private int tag = -1;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
//                int jdu = (int) (totalBytesRead * 100 / responseBody.contentLength());
                String bfb = Arith.div2(String.valueOf(totalBytesRead), String.valueOf(responseBody.contentLength()));
                String mul = Arith.mul(bfb, "100");
                Double aDouble = Double.valueOf(mul);
                int jdu = aDouble.intValue();
//                TLog.e(String.valueOf(totalBytesRead)+"/ "+String.valueOf(responseBody.contentLength())+"="+mul);
                if (null != mDownloadListener) {
                    if (bytesRead != -1) {
                        // 回调进度ui
                        if (tag < 0 || jdu - tag >= 10 || (jdu == 100 && tag != 100)) {
                            tag = jdu;
                            Log.e("download2", "read: " + jdu);
                            mDownloadHandler.onProgress(jdu);
                        }

                    }

                }
                return bytesRead;
            }
        };

    }
    public static String div(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, 2, BigDecimal.ROUND_DOWN).toString();
    }

    public static String mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }

}
