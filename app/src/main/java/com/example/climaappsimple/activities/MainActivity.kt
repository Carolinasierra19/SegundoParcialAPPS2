package com.example.climaappsimple.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climaappsimple.R
import com.example.climaappsimple.database.WeatherDatabase
import com.example.climaappsimple.network.RetrofitClient
import com.example.climaappsimple.repository.WeatherRepository
import com.example.climaappsimple.viewmodel.WeatherViewModel
import com.example.climaappsimple.viewmodel.WeatherViewModelFactory
import com.example.climaappsimple.adapter.WeatherAdapter
import com.example.climaappsimple.view.InfoFragment
import com.example.climaappsimple.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Titulo
        binding.tvTitle.text = "App Clima - Buenos Aires"

        // Cargar el InfoFragment con la información del clima actual
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, InfoFragment())
                .commit()
        }

        // Inicializar el ViewModel
        setupViewModel()

        // Configurar RecyclerView
        setupRecyclerView()

        // Cargar los pronósticos del clima para los siguientes 7 días
        viewModel.loadWeatherData("Buenos Aires")

        // Observar el ViewModel
        observeViewModel()
    }

    private fun setupRecyclerView() {
        weatherAdapter = WeatherAdapter()
        binding.recyclerViewWeather.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = weatherAdapter
        }
    }

    private fun setupViewModel() {
        val weatherDao = WeatherDatabase.getDatabase(applicationContext).weatherDao()
        val repository = WeatherRepository(weatherDao, RetrofitClient.instance)
        val factory = WeatherViewModelFactory(repository)
        viewModel = factory.create(WeatherViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.weatherData.observe(this, Observer { weatherList ->
            weatherList?.let {
                weatherAdapter.submitList(it)
            }
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
