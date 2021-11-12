package com.example.newspaper.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newspaper.App
import com.example.newspaper.interactor.Interactor
import io.reactivex.rxjava3.core.Completable
import java.util.concurrent.Executors
import javax.inject.Inject

class SettingsFragmentViewModel : ViewModel() {
    //Инжектим интерактор
    @Inject
    lateinit var interactor: Interactor
    val categoryPropertyLifeData: MutableLiveData<String> = MutableLiveData()

    init {
        App.instance.dagger.inject(this)
        //Получаем категорию при инициализации, чтобы у нас сразу подтягивалась категория
        getCategoryProperty()
    }

    private fun getCategoryProperty() {
        //Кладем категорию в LiveData
        categoryPropertyLifeData.value = interactor.getDefaultLangFromPreferences()
    }

    fun deleteNews(): Completable {
            return  Completable.create {  subscriber ->
                interactor.deleteAll()
                subscriber.onComplete()
            }
    }


    fun putCategoryProperty(category: String) {
        //Сохраняем в настройки
        interactor.saveDefaultLangToPreferences(category)
        //И сразу забираем, чтобы сохранить состояние в модели
        getCategoryProperty()
    }
}