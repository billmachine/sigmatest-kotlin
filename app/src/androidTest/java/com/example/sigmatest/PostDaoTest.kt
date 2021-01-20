package com.example.sigmatest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.sigmatest.data.entity.PostEntity
import com.example.sigmatest.data.protocol.PostDao
import com.example.sigmatest.di.AppDatabase
import com.example.sigmatest.helper.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: PostDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.postDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertPosts() = runBlockingTest {
        val allPostEntity =
            listOf<PostEntity>(
            PostEntity(1, "body1", "title1", 1), PostEntity(2, "body2", "title2", 1)
            )
        dao.deleteAll()
        dao.insertAll(allPostEntity)

        dao.getAll().observeForever {
//            assertThat(it).contains(allPostEntity)
            assertThat(it).isEqualTo(allPostEntity)
        }
    }

    @Test
    fun deletePosts() = runBlockingTest {
        val allPostEntity =
            listOf<PostEntity>(
                PostEntity(1, "body1", "title1", 1), PostEntity(2, "body2", "title2", 1)
            )
        dao.insertAll(allPostEntity)

        dao.deleteAll()
        dao.getAll().observeForever {
            assertThat(it).isEqualTo(listOf<PostEntity>())
        }
    }

//    @Test
//    fun deleteShoppingItem() = runBlockingTest {
//        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)
//        dao.insertShoppingItem(shoppingItem)
//        dao.deleteShoppingItem(shoppingItem)
//
//        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()
//
//        assertThat(allShoppingItems).doesNotContain(shoppingItem)
//    }

//    @Test
//    fun observeTotalPriceSum() = runBlockingTest {
//        val shoppingItem1 = ShoppingItem("name", 2, 10f, "url", id = 1)
//        val shoppingItem2 = ShoppingItem("name", 4, 5.5f, "url", id = 2)
//        val shoppingItem3 = ShoppingItem("name", 0, 100f, "url", id = 3)
//        dao.insertShoppingItem(shoppingItem1)
//        dao.insertShoppingItem(shoppingItem2)
//        dao.insertShoppingItem(shoppingItem3)
//
//        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()
//
//        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)
//    }
}