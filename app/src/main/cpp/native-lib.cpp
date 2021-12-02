#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_milo_bsdiffdemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" {
extern int main(int argc, char *argv[]);
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_milo_bsdiffdemo_MainActivity_diffapk(JNIEnv *env, jobject instance, jstring old_file,
                                              jstring patch_file,
                                              jstring new_file) {
    const char *placeHolder = "";
    const char *newFile = env->GetStringUTFChars(new_file, nullptr);
    const char *oldFile = env->GetStringUTFChars(old_file, nullptr);
    const char *patchFile = env->GetStringUTFChars(patch_file, nullptr);

    char *argv[] = {const_cast<char *>(placeHolder), const_cast<char *>(oldFile),
                    const_cast<char *>(newFile),
                    const_cast<char *>(patchFile)};
    int ret = main(4, argv);

    env->ReleaseStringUTFChars(old_file, oldFile);
    env->ReleaseStringUTFChars(new_file, newFile);
    env->ReleaseStringUTFChars(patch_file, patchFile);

    return ret == 0;
}

