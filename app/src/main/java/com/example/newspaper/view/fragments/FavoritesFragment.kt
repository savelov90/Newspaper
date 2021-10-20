package com.example.newspaper.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspaper.R
import com.example.newspaper.data.db_fav.ArticleAbstract
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.entity.Article
import com.example.newspaper.databinding.FragmentFavoritesBinding
import com.example.newspaper.databinding.FragmentHomeBinding
import com.example.newspaper.view.MainActivity
import com.example.newspaper.view.rv_adapters.NewsListRecyclerAdapter
import com.example.newspaper.view.rv_adapters.TopSpacingItemDecoration
import com.example.newspaper.viewmodel.FavoritesFragmentViewModel
import com.example.newspaper.viewmodel.HomeFragmentViewModel


class FavoritesFragment : Fragment() {


    private lateinit var newsAdapter: NewsListRecyclerAdapter
    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(FavoritesFragmentViewModel::class.java)
    }

    private var newsDataBase = listOf<ArticleFavorite>()

        //Используем backing field
        set(value) {
            //Если придет такое же значение то мы выходим из метода
            if (field == value) return
            //Если прило другое значение, то кладем его в переменную
            field = value
            //Обновляем RV адаптер
            newsAdapter.addItems(field)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
        initFav()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFav()

        viewModel.newsListLiveData.observe(viewLifecycleOwner, Observer<List<ArticleFavorite>> {
            newsDataBase = it
        })
        newsAdapter.addItems(newsDataBase)

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