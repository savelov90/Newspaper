package com.example.newspaper.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspaper.data.db_fav.ArticleAbstract
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.databinding.FragmentFavoritesBinding
import com.example.newspaper.disposable.AutoDisposable
import com.example.newspaper.disposable.addTo
import com.example.newspaper.view.MainActivity
import com.example.newspaper.view.rv_adapters.NewsListRecyclerAdapter
import com.example.newspaper.view.rv_adapters.TopSpacingItemDecoration
import com.example.newspaper.viewmodel.FavoritesFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class FavoritesFragment : Fragment() {


    private lateinit var newsAdapter: NewsListRecyclerAdapter
    private lateinit var binding: FragmentFavoritesBinding
    private val autoDisposable = AutoDisposable()

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(FavoritesFragmentViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        autoDisposable.bindTo(lifecycle)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFav()

        viewModel.newsListData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    newsAdapter.addItems(list)
                }
                .addTo(autoDisposable)
    }


    private fun initFav() {
        //находим наш RV
        binding.favRecycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            newsAdapter =
                NewsListRecyclerAdapter(object : NewsListRecyclerAdapter.OnItemClickListener {
                    override fun click(article: ArticleAbstract) {
                        (requireActivity() as MainActivity).launchDetailsFragment(article as ArticleFavorite)
                    }
                })
            //Присваиваем адаптер
            adapter = newsAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(10)
            addItemDecoration(decorator)
        }

    }


}