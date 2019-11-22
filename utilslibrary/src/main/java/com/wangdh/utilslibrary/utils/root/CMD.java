package com.wangdh.utilslibrary.utils.root;

import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class CMD {
    private BufferedReader bufferedReader = null;
    private String str = null;
    private StringBuffer data = new StringBuffer();
    private Process process = null;
    private boolean isSU = false;

    public CMD() {
        startRead();
    }

    public CMD su() {
        isSU = true;
        return this;
    }

    private boolean isDestroy = false;

    public void destroy() {
        isDestroy = true;
        if (process != null) {
            process.destroy();
        }
    }

    private Thread readhread;

    private void startRead() {
        readhread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (isDestroy) {
                        return;
                    }
                    try {
                        int read = InputStream.read();
                        if (read > 1) {

                            BufferedReader stringBuffer = new BufferedReader(InputStream);
                            if (cmdInterface != null) {
                                cmdInterface.response(stringBuffer.readLine());
                            }
                        }

                    } catch (Exception e) {
                    }
                }
            }
        };
        readhread.start();
    }

    DataOutputStream outputStream;
    InputStreamReader InputStream;

    public void send(String... cmd) throws Exception {
        if (process == null) {
            String dd = isSU ? "su" : "";
            process = Runtime.getRuntime().exec(dd);
            outputStream = new DataOutputStream(process.getOutputStream());
            InputStreamReader ErrorStream = new InputStreamReader(process.getErrorStream());
            InputStream = new InputStreamReader(process.getInputStream());
        }

        for (String s : cmd) {
            outputStream.writeBytes(s + "\n");
            outputStream.flush();
        }
//        process.waitFor();
    }

    private CMDInterface cmdInterface;

    public interface CMDInterface {
        void response(String msg);
    }

    public CMDInterface getCmdInterface() {
        return cmdInterface;
    }

    public void setCmdInterface(CMDInterface cmdInterface) {
        this.cmdInterface = cmdInterface;
    }
}
