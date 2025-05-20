package com.example.mockotisample.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mockotisample.R
import com.example.mockotisample.databinding.ActivityMainBinding
import com.example.mockotisample.model.Pokemon
import com.example.mockotisample.model.toPokemon
import com.example.mockotisample.ui.adapter.PokedexAdapter
import com.example.mockotisample.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PokedexAdapter.OnClickListener {

    var mContext : Context? = null
    lateinit var _binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mContext = this

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        val adapter = PokedexAdapter(this)
        val layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(this, 2)
            } else {
                GridLayoutManager(this, 4)
            }
        _binding.recyclerView.layoutManager = layoutManager
        _binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.getAllPokemon()

            viewModel.pokemonLiveData.observe( this@MainActivity , Observer { it->
                when(it){
                    is NetworkResult.Error -> {
                        Toast.makeText(mContext,"Error", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        //Toast.makeText(this," ",Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Success -> {
                        Log.d("MainActivity","${it.data.toString()}")
                        if (it.data?.pokemonEntries?.isEmpty()==true){
                            Toast.makeText(mContext,"There is no item",Toast.LENGTH_LONG).show()
                        }else {
                            var templist = it.data?.pokemonEntries
                            var pokemonList: MutableList<Pokemon> = ArrayList<Pokemon>()
                            for (temp in templist!!)
                                pokemonList.add(temp.toPokemon())
                            adapter.submitList(pokemonList)
                        }
                    }
                }
            })
        }

    }

    override fun onPokemonClicked(pokemon: Pokemon) {

    }
}