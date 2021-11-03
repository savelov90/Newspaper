package com.example.newspaper.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newspaper.R
import com.example.newspaper.databinding.FragmentSettingsBinding
import com.example.newspaper.viewmodel.SettingsFragmentViewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(SettingsFragmentViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Слушаем, какой у нас сейчас выбран вариант в настройках
        viewModel.categoryPropertyLifeData.observe(viewLifecycleOwner, Observer<String> {
            when(it) {
                LANG_US -> binding.radioGroup.check(R.id.radio_us)
                LANG_RU -> binding.radioGroup.check(R.id.radio_ru)
                LANG_UA -> binding.radioGroup.check(R.id.radio_ua)

            }
        })
        //Слушатель для отправки нового состояния в настройки
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.radio_us -> viewModel.putCategoryProperty(LANG_US)
                R.id.radio_ru -> viewModel.putCategoryProperty(LANG_RU)
                R.id.radio_ua -> viewModel.putCategoryProperty(LANG_UA)
            }
        }
    }

    companion object {
        private const val LANG_US = "us"
        private const val LANG_RU = "ru"
        private const val LANG_UA = "ua"
    }
}