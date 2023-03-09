package com.aab.bot.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aab.bot.R
import com.aab.bot.databinding.ActivityChatBinding
import com.aab.bot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var  binding: ActivityMainBinding
    companion object{
        const val USERNAME_KEY = "username"
        const val SHARED_PREFERENCE_KEY = "my_prefs"
        const val DEFAULT_USER = "User X"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainActivityStartChatButton.setOnClickListener {
            if(binding.mainActivityUsernameInput.text.isEmpty()){
                makeToast("Please enter a username")
            }else{
                val sharedPrefs = getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE) ?: return@setOnClickListener
                with (sharedPrefs.edit()){
                    putString(USERNAME_KEY, binding.mainActivityUsernameInput.text.toString())
                    apply()
                }
                goToMainActivity()
            }
        }

    }

    private fun makeToast(msg: String){
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, msg, duration)
        toast.show()
    }

    private fun goToMainActivity(){
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
    }
}