package com.example.gossip

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.chat_log_settings.*

/**
 * A group of Simon Zhao, Pakjira Jitsawatpaiboon and Minh Tran
 *
 * This class control the anonimity of the chat by changing the text view of the messages sent and received
 */
class Anonimity_control : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState) 
        //Links the button with the corresponding buttons in the content
    setContentView(R.layout.chat_log_settings)
        val buttonFull = findViewById<Button>(R.id.btn_Full_Anonimity)
        val buttonHalf = findViewById<Button>(R.id.btn_Full_Anonimity)
        val buttonNo = findViewById<Button>(R.id.btn_Full_Anonimity)

    setContentView(R.layout.chat_log_from_row)
        val textReceived = findViewById<TextView>(R.id.textView_chat_log)

    setContentView(R.layout.chat_log_from_row)
        val textSent = findViewById<TextView>(R.id.textView_chat_log)

    //Set message received and sent in same colour
    buttonFull.setOnClickListener(){
            textReceived.setBackgroundColor(Color.argb(255, 255, 255, 255))
            textSent.setBackgroundColor(Color.argb(255, 255, 255, 255))
    }

        //Set message received and and sent in different colours
    buttonHalf.setOnClickListener(){
            textReceived.setBackgroundColor(Color.argb(255, 0, 255, 255))
            textSent.setBackgroundColor(Color.argb(255, 255, 0, 255))
    }

        //Dont change anything if there is a no button clicked
    buttonNo.setOnClickListener(){
            //This will show the full user id and etc
    }

        btn_Full_Anonimity.setOnClickListener(){

        }

        btn_Half_Anonimity.setOnClickListener(){

        }

        btn_No_Anonimity.setOnClickListener(){

        }
    }
}