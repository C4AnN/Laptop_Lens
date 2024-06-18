import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    private const val PREFS_NAME = "user_prefs"
    private const val KEY_TOKEN = "token"

    // Fungsi untuk menyimpan token ke SharedPreferences
    fun saveToken(context: Context, token: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    // Fungsi untuk mengambil token dari SharedPreferences
    fun getToken(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    // Fungsi untuk menghapus token dari SharedPreferences (misalnya saat logout)
    fun clearToken(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(KEY_TOKEN)
        editor.apply()
    }
}
