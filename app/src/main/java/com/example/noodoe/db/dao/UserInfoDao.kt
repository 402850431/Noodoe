package com.example.noodoe.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.noodoe.db.entity.UserInfo

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info_table WHERE id = 0")
    fun getUserInfo(): Flow<UserInfo?>

    @Query("DELETE FROM user_info_table")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg userInfo: UserInfo)

}