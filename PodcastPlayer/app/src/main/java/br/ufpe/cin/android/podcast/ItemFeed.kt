package br.ufpe.cin.android.podcast

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName= "item_feed")
// Acredito que de todos os atributos, o link faz mais sentido como Primary Key,
// Talvez acresentar um simples id para cada fosse uma solução melhor.
// Usando a interface Serializable para ter a capacidade e passar os objetos entre activities.
// Um approach melhor seria utilizar da interface parcelable.
data class ItemFeed(val title: String, @PrimaryKey val link: String, val pubDate: String, val description: String, val downloadLink: String, val image: String? = null) : Serializable {

    override fun toString(): String {
        return title
    }
}
