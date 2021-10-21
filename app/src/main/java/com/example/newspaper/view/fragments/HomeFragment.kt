package com.example.newspaper.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspaper.data.db_fav.ArticleAbstract
import com.example.newspaper.data.db_first.entity.Article
import com.example.newspaper.databinding.FragmentHomeBinding
import com.example.newspaper.view.MainActivity
import com.example.newspaper.view.rv_adapters.NewsListRecyclerAdapter
import com.example.newspaper.view.rv_adapters.TopSpacingItemDecoration
import com.example.newspaper.viewmodel.HomeFragmentViewModel

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var newsAdapter: NewsListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    private var newsDataBase = listOf<Article>()

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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
        initRecyckler()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyckler()

        viewModel.newsListLiveData.observe(viewLifecycleOwner, Observer<List<Article>> {
            newsDataBase = it
            newsAdapter.addItems(it)
        })



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

        viewModel.newsListLiveData.observe(viewLifecycleOwner, Observer<List<Article>> {
            newsDataBase = it
            newsAdapter.addItems(it)
        })

    }


}