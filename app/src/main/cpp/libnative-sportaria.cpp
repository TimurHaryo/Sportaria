//
// Created by Timur on 08/01/2021.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_id_train_sportaria_util_SecretDoor_getBaseUrl(JNIEnv *env, jobject /*this*/) {
    std::string baseUrl = "https://www.thesportsdb.com/api/v1/json/1/";
    return env->NewStringUTF(baseUrl.c_str());
}
