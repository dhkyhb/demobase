package com.wangdh.utilslibrary.utils.logger;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/20 18:08<br>
 * 描述:  <br>
 */
public class TLog {
    private static Context context;
    public static boolean isTest = true;
    public static boolean isDebug = true;

    private static Settings settings = new Settings();

    public static void init(Context con) {
        context = con;
    }

    public static void e(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }
        Log.e(tag, msg);
    }

    public static void e(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }
        List<String> sub = sub(msg, 1000);
        String module = getModule();
        for (String s : sub) {
            settings.getLogAdapter().e(module, s);
        }
    }

    public static void d(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }
        Log.d(tag, msg);
    }

    public static void d(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }
        settings.getLogAdapter().d(getModule(), msg);
    }

    private static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    public static void p(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = "";
        }
        settings.getLogAdapter().e(getModule(), msg);
    }

    private static String getModule() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = -1;
        int methodCount = 2;
        for (int i = 0; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            boolean isTLog = name.equals(TLog.class.getName());
            if (isTLog) {
                stackOffset = i;
            } else if (stackOffset >= 0 && !isTLog) {
                break;
            } else if (i == trace.length && stackOffset < 0) {
                return "TAG";
            }
        }

        int count = 1;
        stackOffset++;
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < count; i++) {
            StackTraceElement stackTraceElement = trace[stackOffset + i];
            if (stackTraceElement == null) {
                continue;
            }
            if (i > 0) {
                Log.e(getTAG(stackTraceElement), "-");
            } else {
                builder.append(getTAG(stackTraceElement));
            }
        }
        return builder.toString();
    }

    private static String getTAG(StackTraceElement stack) {
        StringBuilder builder = new StringBuilder("");
        builder.append(" (")
                .append(stack.getFileName())
                .append(":")
                .append(stack.getLineNumber())
                .append(") [")
//                    .append(stackTraceElement.getClassName())
//                    .append(".")
                .append(stack.getMethodName()).append("]");
        return builder.toString();
    }

    public static Context context() {
        return context;
    }

    public static void showToast(final int message) {
        if ("main".equals(Thread.currentThread().getName())) {
            showToast(message, Toast.LENGTH_LONG, 0);
        } else {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    showToast(message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
                }
            }.execute();
        }
    }

    public static void showToast(final String message) {
        if ("main".equals(Thread.currentThread().getName())) {
            showToast(message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
        } else {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    showToast(message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
                }
            }.execute();
        }
    }

    public static void showToast(final Context context, final String message) {
        showToast(message);
//        Activity context1 = (Activity) context;
//        context1.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                showToast(context, message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
//            }
//        });
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, Gravity.BOTTOM);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.BOTTOM);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity) {
        showToast(context().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity, Object... args) {
        showToast(context().getString(message, args), duration, icon, gravity);
    }

    private static long lastToastTime;
    private static String lastToast = "";

    public static void showToast(Context context, String message, int duration, int icon,
                                 int gravity) {
        TLog.e("showToast(String message, int duration, int icon, int gravity) message=" + message);
        if (message != null && !"".equalsIgnoreCase(message)) {
            long time = System.currentTimeMillis();
            //|| Math.abs(time - lastToastTime) > 2000
            if (!message.equalsIgnoreCase(lastToast) || Math.abs(time - lastToastTime) > 2000
            ) {
//                View view = LayoutInflater.from(context).inflate(
//                        R.layout.view_toast, null);
//                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
//                if (icon != 0) {
//                    ((ImageView) view.findViewById(R.id.icon_iv))
//                            .setImageResource(icon);
//                    ((ImageView) view.findViewById(R.id.icon_iv))
//                            .setVisibility(View.VISIBLE);
            }
            Toast toast = new Toast(context);
//                toast.setView(view);
            if (gravity == Gravity.CENTER) {
                toast.setGravity(gravity, 0, 0);
            } else {
                toast.setGravity(gravity, 0, 35);
            }

            toast.setDuration(duration);
            toast.show();
            lastToast = message;
            lastToastTime = System.currentTimeMillis();
        }
    }

    public static void showToast(String message, int duration, int icon,
                                 int gravity) {
        showToast(context(), message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
    }

    public static String getString(int id) {
        try {
            if (id != 0) {
                return context().getString(id);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static void l(String msg) {
        p(msg);
    }

    public static List<String> sub(String msg, int i) {
        ArrayList<String> strings = new ArrayList<>();
        if (msg.length() <= i) {
            strings.add(msg);
            return strings;
        }
        String ss = msg.substring(0, i);
        strings.add(ss);
        String substring = msg.substring(i);
        List<String> sub = sub(substring, i);
        strings.addAll(sub);
        return strings;
    }
}
