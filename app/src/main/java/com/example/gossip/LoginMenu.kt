package com.example.gossip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginMenu : AppCompatActivity() {

    /**
     * Called when the activity is started
     *
     * @param savedInstanceState Detailed info on android docs. Contains saved data on activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // TODO (Implement this makes code look cleaner and breaks it down)
        //button_login.setOnClickListener(clickListener)

        //Login button listener
        // TODO (Can be deleted once other methods are done and tested)
        button_login.setOnClickListener {
            val email = editText_login_email.text.toString()
            val password = editText_login_password.text.toString()

            //Checks to see if the fields are not empty. They crash if they are
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this, "Email or password is empty",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //Attempts to login into firebase
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("Login", "Login Success")

                        //Starts new activity if successful
                        val intent = Intent(this, MessagesMenu::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        //Tells the user failed
                        Log.w("Login", "Sign in with email failed")
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                        TODO("Better failed messages")

                    }
                }
        }
    }

    /**
     * Sets the buttons behaviours
     * TODO (Yet to be tested and implemented)
     */
    private val clickListener: View.OnClickListener = View.OnClickListener {
        when (it?.id) {
            //Login button behaviour
            R.id.button_login -> {
                val email = editText_login_email.text.toString()
                val password = editText_login_password.text.toString()

                //Checks to see if the fields are not empty. They crash if they are
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this, "Email or password is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnClickListener
                }

                emailLogin(email, password)

            }
        }
    }

    /**
     * Attempts to login into firebase with email and password
     *
     * @param email the email used for the login. String format
     * @param password the password used for the login. String format
     */
    private fun emailLogin(email: String, password: String) {
        //Attempts to login into firebase
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("Login", "Login Success")

                    //Starts new activity if successful
                    val intent = Intent(this, MessagesMenu::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    //Tells the user failed
                    Log.w("Login", "Sign in with email failed")
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    TODO("Better failed messages for user")
                }
            }
    }
}