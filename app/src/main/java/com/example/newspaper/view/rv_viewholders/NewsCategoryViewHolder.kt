package com.example.newspaper.view.rv_viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.newspaper.App
import com.example.newspaper.data.Preference.PreferenceProvider
import com.example.newspaper.databinding.NewsCategoryBinding
import com.example.newspaper.interactor.Interactor
import javax.inject.Inject


class NewsCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var newsCategoryBinding = NewsCategoryBinding.bind(itemView)
    //Привязываем view из layout к переменным
    var category = newsCategoryBinding.textViewSmall


    //В этом методе кладем данные из String в наши view
    fun bind(string: String) {
        //Устанавливаем заголовок
        category.text = string
    }

}