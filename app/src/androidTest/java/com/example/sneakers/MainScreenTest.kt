package com.example.sneakers

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.sneakers.room.AppDatabase
import com.example.sneakers.ui.main.MainRepository
import com.example.sneakers.ui.main.MainScreen
import com.example.sneakers.ui.main.MainViewModel
import org.junit.After
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val database = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).allowMainThreadQueries().build()

    @Test
    fun addToCartFlow() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            MainScreen(MainViewModel(MainRepository(database)), navController)
        }

        composeTestRule
            .onNodeWithContentDescription("Cart")
            .performClick()
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithContentDescription("Home")
            .performClick()
        composeTestRule.waitForIdle()

        composeTestRule
            .onAllNodesWithText(text = "Nike Air", substring = true, ignoreCase = true)
            .onFirst()
            .performClick()
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(text = "add to cart", ignoreCase = true)
            .performClick()
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithContentDescription("Cart")
            .performClick()
        composeTestRule.waitForIdle()
    }

    @After
    fun after() {
        database.close()
    }
}