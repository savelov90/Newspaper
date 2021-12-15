package com.example.newspaper.view.rv_adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newspaper.R
import com.example.newspaper.data.db_fav.ArticleAbstract
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.entity.Article
import com.example.newspaper.view.rv_viewholders.NewsCategoryViewHolder
import com.example.newspaper.view.rv_viewholders.NewsViewHolder

//в параметр передаем слушатель, чтобы мы потом могли обрабатывать нажатия из класса Activity
class NewsCategoryAdapter(private val clickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //Здесь у нас хранится список элементов для RV
    val items = mutableListOf<String>()
    private var category : String
    init {
        category = "business"
    }

    //Этот метод нужно переопределить на возврат количества элементов в списке RV
    override fun getItemCount() = items.size

    //В этом методе мы привязываем наш ViewHolder и передаем туда "надутую" верстку нашей категории
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_category, parent, false))
    }

    //В этом методе будет привязка полей из объекта String к View из news_category.xml
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsCategoryViewHolder -> {
                //Вызываем метод bind(), который мы создали, и передаем туда объект
                //из нашей базы данных с указанием позиции
                if (items[position] == category) {
                    holder.category.setTextColor(Color.BLACK)
                    holder.bind(items[position])
                    holder.itemView.setOnClickListener {
                        clickListener.click(items[position])
                    }
                } else {
                    holder.category.textScaleX = 1F
                    holder.category.setTextColor(Color.GRAY)
                    holder.bind(items[position])
                    holder.itemView.setOnClickListener {
                        clickListener.click(items[position])
                    }

                }
            }
        }
    }

    //Метод для добавления объектов в наш список
    @SuppressLint("NotifyDataSetChanged")
    fun addItems(list: List<String>) {
        //Сначала очищаем(если не реализовать DiffUtils)
        items.clear()
        //Добавляем
        items.addAll(list)
        //Уведомляем RV, что пришел новый список, и ему нужно заново все "привязывать"
        notifyDataSetChanged()
    }


    fun setCategory(categoryFromRecycler: String) {
        category = categoryFromRecycler
    }

    fun getCategory() : String {
        return category
    }

    //Интерфейс для обработки кликов
    interface OnItemClickListener {
        fun click(string: String)
    }
}