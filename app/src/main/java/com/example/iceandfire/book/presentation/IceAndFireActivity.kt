package com.example.iceandfire.book.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.iceandfire.R
import com.example.iceandfire.book.presentation.view.BookFragment
import com.example.iceandfire.character.CharacterFragment
import com.example.iceandfire.databinding.ActivityIceAndFireBinding
import com.example.iceandfire.house.HouseFragment

class IceAndFireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIceAndFireBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIceAndFireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationView()
        setDefaultFragment(savedInstanceState)
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavIceAndFire.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_book -> {
                    loadFragment(BookFragment())
                    true
                }

                R.id.navigation_character -> {
                    loadFragment(CharacterFragment())
                    true
                }

                R.id.navigation_house -> {
                    loadFragment(HouseFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerIceAndFire, fragment)
            .commit()
    }

    private fun setDefaultFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            binding.bottomNavIceAndFire.selectedItemId = R.id.navigation_book
        }
    }
}