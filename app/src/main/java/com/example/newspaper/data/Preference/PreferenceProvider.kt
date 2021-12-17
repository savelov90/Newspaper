package com.example.newspaper.data.Preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceProvider(context: Context) {
    //Нам нужен контекст приложения
    private val appContext = context.applicationContext

    private var category : String
    //Создаем экземпляр SharedPreferences
    private val preference: SharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        //Логика для первого запуска приложения, чтобы положить наши настройки,
        //Сюда потом можно добавить и другие настройки
        if (preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putString(KEY_DEFAULT_LANG, DEFAULT_LANG) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
        category = "General"
    }

    //Category prefs
    //Сохраняем категорию
    fun saveDefaultLang(language: String) {
        preference.edit { putString(KEY_DEFAULT_LANG, language) }
    }
    //Забираем категорию
    fun getDefaultLang(): String {
        return preference.getString(KEY_DEFAULT_LANG, DEFAULT_LANG) ?: DEFAULT_LANG
    }

    fun setCategory(categoryFromRecycler: String) {
        category = categoryFromRecycler
    }

    fun getCategory() : String {
        return category
    }

    //Ключи для наших настроек, по ним мы их будем получать
    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_DEFAULT_LANG = "default_language"
        private const val DEFAULT_LANG = "us"
    }
}