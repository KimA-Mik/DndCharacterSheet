package ru.kima.dndcharactersheet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import ru.kima.dndcharactersheet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var app: App
    lateinit var fragmentContainerView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = applicationContext as App
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentContainerView = binding.fragmentContainer
        app.setActivity(this)

        //TODO: explore elevated colors dependency with xml colors
//        val color = SurfaceColors.SURFACE_2.getColor(this)
//        window.statusBarColor = color
//        window.navigationBarColor = color
    }

    override fun onDestroy() {
        super.onDestroy()
        app.freeActivity()
    }
}