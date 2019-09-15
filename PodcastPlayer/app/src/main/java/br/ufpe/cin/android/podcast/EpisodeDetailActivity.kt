package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_episode_detail.*
import kotlinx.android.synthetic.main.itemlista.*
import org.jetbrains.anko.doAsync

class EpisodeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)


        val feedItem = (intent.getSerializableExtra("extra_itemFeed") as ItemFeed?)

        feed_title.text = feedItem?.title

        // Usando Picasso para lidar com as imagens
        Picasso.get().load(feedItem?.image).into(feed_image)

        // Eu sei que ta deprecated, se eu me lembrar de corrigir eu corrijo
        feed_description.text = Html.fromHtml(feedItem?.description)
        feed_link.text = feedItem?.link

    }
}
