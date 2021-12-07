package com.example.newspaper.view.rv_viewholders

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.newspaper.R
import com.example.newspaper.data.db_fav.ArticleAbstract
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.databinding.NewsItemBinding
import com.example.newspaper.disposable.AutoDisposable
import com.example.newspaper.disposable.addTo
import com.example.newspaper.view.fragments.HomeFragment
import com.example.newspaper.viewmodel.DetailsFragmentViewModel
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


//В конструктор класс передается layout, который мы создали(film_item.xml)
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    private val newsItemBinding = NewsItemBinding.bind(itemView)
    //Привязываем view из layout к переменным
    private val title = newsItemBinding.title
    private val source = newsItemBinding.source
    private val time =  newsItemBinding.time
    private val picture =  newsItemBinding.picture
    private val like =  newsItemBinding.like

    //В этом методе кладем данные из Article в наши view
    fun bind(article: ArticleAbstract) {
        //Устанавливаем заголовок
        title.text = article.title
        //Устанавливаем постер
        //Указываем контейнер, в которм будет "жить" наша картинка

        if (article.urlToImage.isEmpty()) {
            Picasso.get()
                .load(R.string.advert.toString())
                .error(android.R.drawable.stat_notify_error)
                .into(picture)
        } else{
            Picasso.get()
                .load(article.urlToImage)
                .error(android.R.drawable.stat_notify_error)
                .into(picture)
        }

        //Устанавливаем описание
        source.text = article.author
        time.text = editData(article.publishedAt)

        like.setImageResource(
                if (article.isInFavorites) R.drawable.ic_sharp_favorite_24
                else R.drawable.ic_sharp_favorite_border_24
        )

    }

    private fun editData(data:String) : String {
        val edit = data.toCharArray()
        val string = "${edit[8]}" + "${edit[9]}" + "." + "${edit[5]}" + "${edit[6]}" + "." + "${edit[0]}" + "${edit[1]}" + "${edit[2]}" + "${edit[3]}"
        return string
    }
}