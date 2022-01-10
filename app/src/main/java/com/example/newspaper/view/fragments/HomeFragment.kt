package com.example.newspaper.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspaper.R
import com.example.newspaper.data.db_fav.ArticleAbstract
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.entity.Article
import com.example.newspaper.databinding.FragmentHomeBinding
import com.example.newspaper.disposable.AutoDisposable
import com.example.newspaper.disposable.addTo
import com.example.newspaper.view.AnimationHelper
import com.example.newspaper.view.MainActivity
import com.example.newspaper.view.rv_adapters.NewsCategoryAdapter
import com.example.newspaper.view.rv_adapters.NewsListRecyclerAdapter
import com.example.newspaper.view.rv_adapters.TopSpacingItemDecoration
import com.example.newspaper.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var newsAdapter: NewsListRecyclerAdapter
    private lateinit var newsCategoryAdapter: NewsCategoryAdapter
    private lateinit var binding: FragmentHomeBinding
    private val autoDisposable = AutoDisposable()
    private lateinit var allNews: Observable<List<Article>>

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        autoDisposable.bindTo(lifecycle)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)

        initPullToRefresh()
        initRecyckler()
        initRecyclerCat()
        newsCategoryAdapter.addItems(getCatList())
    }

    override fun onStart() {
        super.onStart()
        viewModel.setCategoryProperty("General")
        viewModel.getNews()

        allNews = viewModel.newsListData

        allNews.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    newsAdapter.addItems(list)
                }
                .addTo(autoDisposable)
    }

    private fun initRecyckler() {
        //находим наш RV
        binding.mainRecycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            newsAdapter =
                NewsListRecyclerAdapter(object : NewsListRecyclerAdapter.OnItemClickListener {
                    override fun click(article: ArticleAbstract) {
                        (requireActivity() as MainActivity).launchDetailsFragment(article as Article)
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

    private fun initRecyclerCat() {
        //находим наш RV
        binding.categoryRecycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            newsCategoryAdapter =
               NewsCategoryAdapter(object : NewsCategoryAdapter.OnItemClickListener {
                   @SuppressLint("NotifyDataSetChanged")
                   override fun click(string: String) {
                       viewModel.setCategoryProperty(string)
                       newsCategoryAdapter.setCategory(string)
                       newsCategoryAdapter.addItems(getCatList())
                       viewModel.getNews()
                   }
               })
            //Присваиваем адаптер
            adapter = newsCategoryAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(10)
            addItemDecoration(decorator)
        }
    }


    private fun getCatList(): List<String> {
        return this.resources.getStringArray(R.array.api_category).toList()
    }

    private fun initPullToRefresh() {
        //Вешаем слушатель, чтобы вызвался pull to refresh
        binding.pullToRefresh.setOnRefreshListener {
            //Чистим адаптер(items нужно будет сделать паблик или создать для этого публичный метод)
            newsAdapter.items.clear()
            //Делаем новый запрос фильмов на сервер
            viewModel.getNews()

            //Убираем крутящееся колечко
            binding.pullToRefresh.isRefreshing = false
        }
    }


}