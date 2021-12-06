package com.example.newspaper.view.rv_viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.newspaper.databinding.NewsCategoryBinding


class NewsCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    private val newsCategoryBinding = NewsCategoryBinding.bind(itemView)

    //Привязываем view из layout к переменным
    private val category = newsCategoryBinding.textViewSmall


    //В этом методе кладем данные из String в наши view
    fun bind(string: String) {
        //Устанавливаем заголовок
        category.text = string
    }

}