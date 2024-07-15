package com.amper.personapp.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.amper.personapp.R
import com.amper.personapp.data.model.PersonDto
import com.amper.personapp.databinding.ActivityProfileBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonActivity : AppCompatActivity(),
    ProfileListFragment.ProfileListener {

    private val viewBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        supportFragmentManager.beginTransaction().add(
            viewBinding.fragmentViewProfile.id,
            ProfileListFragment.newInstance(this)
        ).commit()

    }

    override fun onProfileClick(profile: PersonDto) {
        supportFragmentManager.beginTransaction().add(
            viewBinding.fragmentViewProfile.id,
            PersonFragment.newInstance(profile)
        ).addToBackStack(PersonFragment.TAG).commit()
    }
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_profile_placeholder)
        .into(this)
}
