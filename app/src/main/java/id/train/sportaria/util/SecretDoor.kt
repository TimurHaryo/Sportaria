package id.train.sportaria.util

object SecretDoor {
    init {
        System.loadLibrary("native-lib")
    }

    external fun getBaseUrl(): String
}