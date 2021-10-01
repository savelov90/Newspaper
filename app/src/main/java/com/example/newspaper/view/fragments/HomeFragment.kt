package com.example.newspaper.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspaper.R
import com.example.newspaper.data.Article
import com.example.newspaper.data.Source
import com.example.newspaper.databinding.FragmentHomeBinding
import com.example.newspaper.view.MainActivity
import com.example.newspaper.view.rv_adapters.NewsListRecyclerAdapter
import com.example.newspaper.view.rv_adapters.TopSpacingItemDecoration

class HomeFragment : Fragment() {

    private lateinit var newsAdapter: NewsListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding

    val source = Source("2", "New York Times")
    val newsDataBase = listOf(
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
        Article("19:22", source,"This should be a description, fucking fuck, fucking fuck, fucking fuck",
            "https://saintscalpelburg.com/wp-content/uploads/2021/04/4vqCQ5XlOcg-1-1024x579.jpg"),
    )

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyckler()
    }

    private fun initRecyckler() {
        //находим наш RV
        binding.mainRecycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            newsAdapter = NewsListRecyclerAdapter(object : NewsListRecyclerAdapter.OnItemClickListener{

                override fun click(article: Article) {

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
//Кладем нашу БД в RV
        newsAdapter.addItems(newsDataBase)
    }


}