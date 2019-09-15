package br.ufpe.cin.android.podcast

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(ItemFeed::class), version = 1)
abstract class ItemFeedDB : RoomDatabase() {

    abstract fun itemFeedDAO(): ItemFeedDAO

    // Aplicando o padrao de Projeto Singleton,
    // para diminuir a quantidade de recursos e possiveis memory leaks
    // que poderiam ser causados em multiplos acessos ao BD.
    companion object {
        private var INSTANCE: ItemFeedDB? = null

        fun getDatabase(ctx: Context): ItemFeedDB {

            if (INSTANCE == null) {
                synchronized(ItemFeedDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        ItemFeedDB::class.java,
                        "itemFeed.db"
                    ).build()
                }
            }

            return INSTANCE!!
        }
    }


}