package com.example.gossip

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_list.view.*

/**
 * Makes the select user screen
 *
 * Calls on a recylcerviewer to do so
 */
class NewMessagesActivity : AppCompatActivity() {


    /**
     * Loads the activity and changes the title of activity.
     * Also calls the recyclerview
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"

        //val adapter = GroupAdapter<GroupieViewHolder>()

        rv_select_user.layoutManager = LinearLayoutManager(this)
        fetchUser()

    }

    /**
     * Fetches users from the database, and calls on the recycler view.
     */
    private fun fetchUser() {
        val database = FirebaseDatabase.getInstance().getReference("/user")

        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val userList = mutableListOf<Users>()
                p0.children.forEach{
                    val userItem = it.getValue(Users::class.java)
                    if (userItem != null) {
                        userList.add(userItem)
                    }
                }

                rv_select_user.adapter = MyAdapter(userList)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }
}

/**
 * Recyclerview. Used to display a group of items
 *
 * This class is mainly taken from the API in kotlin. It is used to display information is the
 * specified textboxes.
 *
 * @property userList a list of usernames we are displaying
 */
class MyAdapter(private val userList: MutableList<Users>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    /**
     * A viewholder
     *
     * A custom view holder. Allows adding interactions with each item. This one implements a
     * button.
     *
     * @property itemView the item we are adding stuff to
     */
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        init {
            itemView.setOnClickListener(){
                val intent = Intent(itemView.context, ChatLog::class.java)
                val pos = it.textView_userlist_selectPos.text.toString().toInt()
                val uname = userList[pos].username
                val id = userList[pos].uid
                intent.putExtra("USERNAME", uname)
                intent.putExtra("USERID", id)
                itemView.context.startActivity(intent)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent?.context)
        val cellRow = textView.inflate(R.layout.user_list, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(cellRow)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val i = userList[position]
        holder.itemView.textView_username_new_messages?.text = i.username
        holder.itemView.textView_userlist_selectPos.text = position.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return userList.size
    }

}
