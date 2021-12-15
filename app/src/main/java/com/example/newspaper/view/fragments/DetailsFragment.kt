package com.example.newspaper.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newspaper.R
import com.example.newspaper.data.db_fav.ArticleAbstract
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.databinding.FragmentDetailsBinding
import com.example.newspaper.disposable.AutoDisposable
import com.example.newspaper.disposable.addTo
import com.example.newspaper.viewmodel.DetailsFragmentViewModel
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


@Suppress("DEPRECATION")
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var articleAbstract: ArticleAbstract
    private lateinit var articleFavorite: ArticleFavorite
    private lateinit var checkArticle: Observable<ArticleFavorite>
    private lateinit var result: ArticleFavorite
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(DetailsFragmentViewModel::class.java)
    }
    private val autoDisposable = AutoDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        autoDisposable.bindTo(lifecycle)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmsDetails()

        articleFavorite = getArticleFavorite(articleAbstract)

        val checkParam = articleAbstract.title
        checkArticle = viewModel.checkFav(checkParam)
        checkArticle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { art ->
                    result = art
                    binding.detailsFabFavorites.setImageResource(
                            if (result.title == articleAbstract.title) R.drawable.ic_sharp_bookmark_24
                            else R.drawable.ic_sharp_bookmark_border_24
                    )
                    articleAbstract.isInFavorites = result.title == articleAbstract.title
                }
                .addTo(autoDisposable)

        binding.detailsFabFavorites.setOnClickListener {
            if (!articleAbstract.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_sharp_bookmark_24)
                articleFavorite.isInFavorites = true
                articleAbstract.isInFavorites = true
                viewModel.putFav(articleFavorite)
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_sharp_bookmark_border_24)
                articleFavorite.isInFavorites = false
                articleAbstract.isInFavorites = false
                viewModel.delFav(articleFavorite)
            }
        }

        val link = articleAbstract.url
        binding.detailsFabOrigin.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(browserIntent)
        }

        binding.detailsFabShare.setOnClickListener {
            //Создаем интент
            val intent = Intent()
            //Указываем action с которым он запускается
            intent.action = Intent.ACTION_SEND
            //Кладем данные о нашем фильме
            intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Смотри какая новость: ${articleAbstract.title}"
            )
            //Указываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            //Запускаем наше активити
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }

    private fun getArticleFavorite(articleAbstract: ArticleAbstract): ArticleFavorite {
        val articleFavorite = ArticleFavorite(
                id = articleAbstract.id,
                publishedAt = articleAbstract.publishedAt,
                description = articleAbstract.description,
                title = articleAbstract.title,
                urlToImage = articleAbstract.urlToImage,
                isInFavorites = articleAbstract.isInFavorites,
                author = articleAbstract.author,
                url = articleAbstract.url,
                source = articleAbstract.source)

        return articleFavorite
    }

    private fun setFilmsDetails() {
        //Получаем наш фильм из переданного бандла
        articleAbstract = arguments?.getParcelable<ArticleAbstract>("article") as ArticleAbstract

        //Устанавливаем заголовок
        binding.detailsTitle.text = articleAbstract.title
        //Устанавливаем картинку
        Picasso.get()
                .load(articleAbstract.urlToImage)
                .resize(800,500)
                .into(binding.detailsPoster)
        //Устанавливаем описание
        binding.detailsDescription.text = articleAbstract.description

    }
}