package br.ufpe.cin.android.podcast

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.itemlista.view.*


class CustomAdapter(private val ctx: Context, private val itemFeedList: List<ItemFeed>) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val view = LayoutInflater.from(ctx).inflate(R.layout.itemlista, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount() = itemFeedList.count()

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val feedItem = itemFeedList[position]

        holder.title.text = feedItem.title
        holder.date.text = feedItem.pubDate

        holder.title.setOnClickListener {

            val intent = Intent(ctx.applicationContext, EpisodeDetailActivity::class.java)
            intent.putExtra("extra_itemFeed", feedItem)

            ctx.startActivity(intent)
        }
    }


    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.item_title
        val date: TextView = itemView.item_date
    }

}