package com.example.climaappsimple.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climaappsimple.R
import com.example.climaappsimple.database.WeatherDatabase
import com.example.climaappsimple.databinding.FragmentInfoBinding
import com.example.climaappsimple.network.RetrofitClient
import com.example.climaappsimple.repository.WeatherRepository
import com.example.climaappsimple.viewmodel.WeatherViewModel
import com.example.climaappsimple.viewmodel.WeatherViewModelFactory

class InfoFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuramos el ViewModel
        setupViewModel()

        // Cargar los datos del clima (usamos Buenos Aires como ciudad)
        viewModel.loadWeatherData("Buenos Aires")

        // Observar los datos del clima
        observeViewModel()
    }

    private fun setupViewModel() {
        val weatherDao = WeatherDatabase.getDatabase(requireContext()).weatherDao()
        val repository = WeatherRepository(weatherDao, RetrofitClient.instance)
        val factory = WeatherViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
    }

    private fun observeViewModel() {
        // Observar los datos de clima actual
        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherList ->
            // Verificamos si la lista no está vacía
            if (weatherList != null && weatherList.isNotEmpty()) {
                val currentWeather = weatherList[0]  // Usamos el primer item como el clima actual
                binding.tvTemperature.text = "${currentWeather.temp}°C"  // Temperatura actual
                binding.tvDescription.text = currentWeather.description  // Descripción del clima
                binding.tvMaxTemp.text = "Máxima: ${currentWeather.maxTemp}°C"  // Temperatura máxima
                binding.tvMinTemp.text = "Mínima: ${currentWeather.minTemp}°C"  // Temperatura mínima
            }
        })

        // Observar mensajes de error
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}


