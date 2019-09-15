package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Usando doAsync da Anko
        doAsync {

            // Usando HttpURLConnection como Client HTTP para requisitar RSS
            // Uma permissão no AndroidManifest para uso de Internet tb foi necessaria
            val httpCon =
                (URL("https://feed.megafono.host/primocast").openConnection() as HttpURLConnection)

            val feedStr: String?
            var itemFeedListDownloaded: List<ItemFeed> = ArrayList()

            // Pegando referencia ao banco de dados
            val itemFeedDB = ItemFeedDB.getDatabase(this@MainActivity)

            // Logica para baixar RSS e armazenar no DB
            try {

                val inStream = BufferedInputStream(httpCon.inputStream)
                feedStr = inStream.bufferedReader().readText()

                // Fazendo o Parsing do XML
                itemFeedListDownloaded = Parser.parse(feedStr)

                // Ao tentar usar o operador de spread (*), um erro de tipagem ocorria
                // Achei mais simples fazer um for each.
                itemFeedListDownloaded.forEach {
                    itemFeedDB.itemFeedDAO().addFeedItem(it)
                }

            } catch (e: IOException) {
                Log.d("RSSFEED", "Erro ao baixar RSS")
            } finally {
                httpCon.disconnect()
            }

            // Logica para recuperar os itens do DB
            var itemFeedListFromDB: List<ItemFeed> = ArrayList()
            try {
                itemFeedListFromDB = itemFeedDB.itemFeedDAO().selectAllFeedItem()
            } catch (e: IOException) {
                Log.d("RSSFEED", "Erro ao acessar DB")
            }


            uiThread {

                val feedAdapter = CustomAdapter(this@MainActivity, itemFeedListFromDB)
                val linearManager = LinearLayoutManager(this@MainActivity)

                recycler_feed.adapter = feedAdapter
                recycler_feed.layoutManager = linearManager

            }

        }

    }
}
