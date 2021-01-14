package id.train.sportaria.util.pref

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private val sp: SharedPreferences by lazy {
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    private val spe: SharedPreferences.Editor by lazy {
        sp.edit()
    }

    fun clear() {
        spe.clear().apply()
    }

    var isBookmarkView: Boolean
        get() = sp.getBoolean(IS_BOOKMARK_VIEW_PREF, false)
        set(value) = spe.putBoolean(IS_BOOKMARK_VIEW_PREF, value).apply()

    companion object {
        const val PREF_NAME = "fooball_pref"
        const val IS_BOOKMARK_VIEW_PREF = "is_bookmark_view"
    }
}