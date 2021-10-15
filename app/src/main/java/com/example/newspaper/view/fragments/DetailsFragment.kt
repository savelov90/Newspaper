package com.example.newspaper.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.newspaper.R
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.entity.Article
import com.example.newspaper.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var article: Article
    private lateinit var articleFavorite: ArticleFavorite


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmsDetails()

        binding.detailsFabFavorites.setOnClickListener {
            if (!article.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_sharp_favorite_24)
                article.isInFavorites = true
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_sharp_favorite_border_24)
                article.isInFavorites = false
            }
        }
    }

    private fun setFilmsDetails() {
        //Получаем наш фильм из переданного бандла
        article = arguments?.get("article") as Article

        //Устанавливаем заголовок
        binding.detailsTitle.text = article.title
        //Устанавливаем картинку
        Glide.with(this)
            .load(article.urlToImage)
            .centerCrop()
            .into(binding.detailsPoster)
        //Устанавливаем описание
        binding.detailsDescription.text = article.description

  /*      binding.detailsFabFavorites.setImageResource(
            if (article.isInFavorites) R.drawable.ic_sharp_favorite
            else R.drawable.ic_sharp_favorite_border_24
        )*/
    }


}