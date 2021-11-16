package com.example.newspaper.view.rv_viewholders

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.newspaper.R
import com.example.newspaper.data.db_fav.ArticleAbstract
import com.example.newspaper.databinding.NewsItemBinding
import com.squareup.picasso.Picasso


//В конструктор класс передается layout, который мы создали(film_item.xml)
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val newsItemBinding = NewsItemBinding.bind(itemView)
    //Привязываем view из layout к переменным
    private val title = newsItemBinding.title
    private val source = newsItemBinding.source
    private val time =  newsItemBinding.time
    private val picture =  newsItemBinding.picture

    //В этом методе кладем данные из Article в наши view
    fun bind(article: ArticleAbstract) {
        //Устанавливаем заголовок
        title.text = article.title
        //Устанавливаем постер
        //Указываем контейнер, в которм будет "жить" наша картинка

        Picasso.get()
                .load(article.urlToImage)
                .error(android.R.drawable.stat_notify_error)
                .into(picture)

        //Устанавливаем описание
        source.text = article.author
        time.text = editData(article.publishedAt)
    }

    private fun editData(data:String) : String {
        val edit = data.toCharArray()
        val string = "${edit[8]}" + "${edit[8]}" + "." + "${edit[5]}" + "${edit[6]}" + "." + "${edit[0]}" + "${edit[1]}" + "${edit[2]}" + "${edit[3]}"
        return string
    }
}