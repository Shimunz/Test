package com.example.gossip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_menu.*

/**
 * Main menu
 * The first screen user will see
 * Displays all the chats
 */
class MainMenu : AppCompatActivity() {

    /**
     * Creates the main menu for new users
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        //button_Menu_Login.setOnClickListener(clickListener)
        //button_Menu_Register.setOnClickListener(clickListener)

        //To login Menu
        button_Menu_Login.setOnClickListener{
            val loginIntent = Intent(this, LoginMenu::class.java)
            startActivity(loginIntent)
        }

        //To Register Menu
        button_Menu_Register.setOnClickListener{
            val registerIntent = Intent(this, RegisterMenu::class.java)
            startActivity(registerIntent)
        }
    }

    //Sets buttons behaviour
    private val clickListener :View.OnClickListener = View.OnClickListener {
        when (it?.id){
            //Sets the register button behaviour
            // TODO (Needs testing)
            R.id.button_Menu_Register->{
                val registerIntent = Intent(this, RegisterMenu::class.java)
                startActivity(registerIntent)
            }
            //Sets the login button behaviour
            // TODO (Needs testing)
            R.id.button_Menu_Login->{
                val loginIntent = Intent(this, LoginMenu::class.java)
                startActivity(loginIntent)
            }
        }
    }

    /**
     * Checks to see if user is already logged in
     */
    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val intent = Intent(this, MessagesMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}