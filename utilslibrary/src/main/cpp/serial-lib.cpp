#include <termios.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string>
#include <jni.h>

#include "android/log.h"

static const char *TAG = "serial_port";
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, TAG, fmt, ##args)
int fd;
extern "C" JNIEXPORT jstring JNICALL
Java_com_wangdh_utilslibrary_serialportlibrary_SerialPort_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "我是c++数据返回测试";
    return env->NewStringUTF(hello.c_str());
}

static speed_t getBaudrate(jint baudrate) {
    switch (baudrate) {
        case 0:
            return B0;
        case 50:
            return B50;
        case 75:
            return B75;
        case 110:
            return B110;
        case 134:
            return B134;
        case 150:
            return B150;
        case 200:
            return B200;
        case 300:
            return B300;
        case 600:
            return B600;
        case 1200:
            return B1200;
        case 1800:
            return B1800;
        case 2400:
            return B2400;
        case 4800:
            return B4800;
        case 9600:
            return B9600;
        case 19200:
            return B19200;
        case 38400:
            return B38400;
        case 57600:
            return B57600;
        case 115200:
            return B115200;
        case 230400:
            return B230400;
        case 460800:
            return B460800;
        case 500000:
            return B500000;
        case 576000:
            return B576000;
        case 921600:
            return B921600;
        case 1000000:
            return B1000000;
        case 1152000:
            return B1152000;
        case 1500000:
            return B1500000;
        case 2000000:
            return B2000000;
        case 2500000:
            return B2500000;
        case 3000000:
            return B3000000;
        case 3500000:
            return B3500000;
        case 4000000:
            return B4000000;
        default:
            return -1;
    }
}

/**

* 设置串口数据，校验位,速率，停止位

* @param nBits 类型 int数据位 取值 位7或8

* @param nEvent 类型 char 校验类型 取值N ,E, O,,S

* @param mStop 类型 int 停止位 取值1 或者 2

*/

int set_opt(jint nBits, jchar nEvent, jint nStop) {
    //LOGE("set_opt:nBits=%d,nEvent=%c,nSpeed=%d,nStop=%d", nBits, nEvent, nStop);
    struct termios newtio;
    if (tcgetattr(fd, &newtio) != 0) {
        LOGE("setup serial failure");
        return -1;
    }
    bzero(&newtio, sizeof(newtio));
    //c_cflag标志可以定义CLOCAL和CREAD，这将确保该程序不被其他端口控制和信号干扰，同时串口驱动将读取进入的数据。CLOCAL和CREAD通常总是被是能的
    newtio.c_cflag |= CLOCAL | CREAD;
    switch (nBits) //设置数据位数
    {
        case 7:
            newtio.c_cflag &= ~CSIZE;
            newtio.c_cflag |= CS7;
            break;
        case 8:
            newtio.c_cflag &= ~CSIZE;
            newtio.c_cflag |= CS8;
            break;
        default:
            break;
    }
    switch (nEvent) //设置校验位
    {
        case 'O':
            newtio.c_cflag |= PARENB; //enable parity checking
            newtio.c_cflag |= PARODD; //奇校验位
            newtio.c_iflag |= (INPCK | ISTRIP);
            break;
        case 'E':
            newtio.c_cflag |= PARENB; //
            newtio.c_cflag &= ~PARODD; //偶校验位
            newtio.c_iflag |= (INPCK | ISTRIP);
            break;
        case 'N':
            newtio.c_cflag &= ~PARENB; //清除校验位
            break;
        default:
            break;
    }
    switch (nStop) //设置停止位
    {
        case 1:
            newtio.c_cflag &= ~CSTOPB;
            break;
        case 2:
            newtio.c_cflag |= CSTOPB;
            break;
        default:
            // LOGW("nStop:%d,invalid param", nStop);
            break;
    }
    newtio.c_cc[VTIME] = 0;//设置等待时间
    newtio.c_cc[VMIN] = 0;//设置最小接收字符
    tcflush(fd, TCIFLUSH);
    if (tcsetattr(fd, TCSANOW, &newtio) != 0) {
        LOGE("参数设置失败");
        return -1;
    }
    LOGE("参数设置成功");
    return 1;
}


static void throwException(JNIEnv *env, const char *name, const char *msg) {
    jclass cls = env->FindClass(name);
    /* if cls is NULL, an exception has already been thrown */
    if (cls != NULL) {
        env->ThrowNew(cls, msg);
    }

    /* free the local ref */
    env->DeleteLocalRef(cls);
}

/*
 * Class:     android_serialport_SerialPort 静态函数就是jClass 否者是jobject
 * Method:    open
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */
extern "C" JNIEXPORT jobject JNICALL
Java_com_wangdh_utilslibrary_serialportlibrary_SerialPort_open
        (JNIEnv *env, jobject instance, jstring path, jint baudrate, jint parity, jint dataBits,
         jint stopBit, jint flags) {
    int fd;
    speed_t speed;
    jobject mFileDescriptor;

    /* Check arguments */
    {
        speed = getBaudrate(baudrate);
        if (speed == -1) {
            throwException(env, "java/lang/IllegalArgumentException", "Invalid baudrate");
            return NULL;
        }
        if (parity < 0 || parity > 2) {
            throwException(env, "java/lang/IllegalArgumentException", "Invalid parity");
            return NULL;
        }
        if (dataBits < 5 || dataBits > 8) {
            throwException(env, "java/lang/IllegalArgumentException", "Invalid dataBits");
            return NULL;
        }
        if (stopBit < 1 || stopBit > 2) {
            throwException(env, "java/lang/IllegalArgumentException", "Invalid stopBit");
            return NULL;
        }
    }

    /* Opening device */
    {
        jboolean iscopy;
        const char *path_utf = env->GetStringUTFChars(path, &iscopy);
        LOGD("c++ 打开串口 %s  flags 0x%x", path_utf, O_RDWR | flags);
        fd = open(path_utf, O_RDWR | flags);
        LOGD("c++ open() fd = %d", fd);
        env->ReleaseStringUTFChars(path, path_utf);
        if (fd == -1) {
            throwException(env, "java/io/IOException", "Cannot open port");
            return NULL;
        }
    }

    /* Configure device */
    {
        struct termios cfg;
        LOGD("Configuring serial port");
        if (tcgetattr(fd, &cfg)) {
            LOGE("tcgetattr() failed");
            close(fd);
            throwException(env, "java/io/IOException", "tcgetattr() failed");
            return NULL;
        }

        cfmakeraw(&cfg);
        cfsetispeed(&cfg, speed);
        cfsetospeed(&cfg, speed);

        /* More attribute set */
//        switch (parity) {
//            case 0: break;
//            case 1: cfg.c_cflag |= PARENB; break;
//            case 2: cfg.c_cflag &= ~PARODD; break;
//        }

        switch (parity) {
            case 0:
                cfg.c_cflag &= ~PARENB;
                cfg.c_cflag &= ~INPCK;
                break;
            case 1:
                cfg.c_cflag |= PARENB;
                cfg.c_cflag |= PARODD;
                cfg.c_cflag |= INPCK;
                break;
            case 2:
                cfg.c_cflag |= PARENB;
                cfg.c_cflag &= ~PARODD;
                cfg.c_cflag |= INPCK;
                break;
        }

        cfg.c_cflag &= ~CSIZE;
//https://blog.csdn.net/linqiang_csdn/article/details/79537093
        switch (dataBits) {
            case 5:
                cfg.c_cflag |= CS5;
                break;
            case 6:
                cfg.c_cflag |= CS6;
                break;
            case 7:
                cfg.c_cflag |= CS7;
                break;
            case 8:
                cfg.c_cflag |= CS8;
                break;
        }
        switch (stopBit) {
            case 1:
                cfg.c_cflag &= ~CSTOPB;
                break;
            case 2:
                cfg.c_cflag |= CSTOPB;
                break;
        }

        if (tcsetattr(fd, TCSANOW, &cfg)) {
            LOGE("tcsetattr() failed");
            close(fd);
            /* TODO: throw an exception */
            return NULL;
        }
    }

    /* Create a corresponding file descriptor */
    {
        jclass cFileDescriptor = env->FindClass("java/io/FileDescriptor");
        jmethodID iFileDescriptor = env->GetMethodID(cFileDescriptor, "<init>", "()V");
        jfieldID descriptorID = env->GetFieldID(cFileDescriptor, "descriptor", "I");
        mFileDescriptor = env->NewObject(cFileDescriptor, iFileDescriptor);
        env->SetIntField(mFileDescriptor, descriptorID, (jint) fd);
    }

    return mFileDescriptor;
}

extern "C" JNIEXPORT void JNICALL
Java_com_wangdh_utilslibrary_serialportlibrary_SerialPort_close
        (JNIEnv *env, jobject thiz) {
    jclass SerialPortClass = env->GetObjectClass(thiz);
    jclass FileDescriptorClass = env->FindClass("java/io/FileDescriptor");

    jfieldID mFdID = env->GetFieldID(SerialPortClass, "mFd", "Ljava/io/FileDescriptor;");
    if (mFdID == NULL) {
        LOGD("close()", "串口未打开，无法关闭");
        return;
    }
    jfieldID descriptorID = env->GetFieldID(FileDescriptorClass, "descriptor", "I");

    jobject mFd = env->GetObjectField(thiz, mFdID);
    jint descriptor = env->GetIntField(mFd, descriptorID);
    LOGD("close(fd = %d)", descriptor);
    close(descriptor);
}