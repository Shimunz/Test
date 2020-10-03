package com.example.gossip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupMenu
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId

/**
 * Main messages page
 * Has new message in sign out buttons
 */
class MessagesMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages_menu)

        supportActionBar?.title = "Chats"
    }

    /**
     * Checks to see if user is already logged in
     */
    override fun onStart() {
        super.onStart()
        getUserData()
    }

    //Creates a menu inflater
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    //Shows the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_new_message -> {
                val intent = Intent(this, NewMessagesActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.button_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getUserData() {
        val userAuth = FirebaseAuth.getInstance().currentUser

        if (userAuth != null) {
            FirebaseInstanceId.getInstance().instanceId.
                    addOnCompleteListener(OnCompleteListener {
                        if(!it.isSuccessful) {
                            Log.w("getInstanceID failed", it.exception)
                            return@OnCompleteListener
                        }

                        val token = it.result?.token

                        Log.d("getInstanceID", token)
                    })
        } else {
            Log.d("Current_User", "Auth failed")
            val intent = Intent(this, MainMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
