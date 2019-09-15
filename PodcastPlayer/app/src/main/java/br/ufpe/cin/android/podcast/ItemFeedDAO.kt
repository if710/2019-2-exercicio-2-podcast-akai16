package br.ufpe.cin.android.podcast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemFeedDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFeedItem(vararg feedItemFeed: ItemFeed)

    @Query("SELECT * FROM item_feed")
    fun selectAllFeedItem() : List<ItemFeed>

}