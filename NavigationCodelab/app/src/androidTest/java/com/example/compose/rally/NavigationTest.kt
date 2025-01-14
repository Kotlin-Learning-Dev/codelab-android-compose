/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.rally

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// 測試類，用於驗證 Jetpack Compose 應用的導航邏輯
class NavigationTest {

    // 規則：啟動 Jetpack Compose 測試環境
    @get:Rule
    val composeTestRule = createComposeRule()

    // 用於導航測試的 NavHostController
    lateinit var navController: TestNavHostController

    // 初始化測試導航主機
    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            // 創建一個 TestNavHostController 來模擬導航控制
            navController = TestNavHostController(LocalContext.current)
            // 添加 ComposeNavigator，允許導航到 Composable 組件
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            // 初始化應用的導航主機，並將 navController 傳入
            RallyNavHost(navController = navController)
        }
    }

    // 測試：驗證起始目的地是否為 "Overview Screen"
    @Test
    fun rallyNavHost_verifyOverviewStartDestination() {
        // 查找具有 "Overview Screen" ContentDescription 的節點，並檢查是否顯示
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed() // 驗證此節點是否在屏幕上顯示
    }

    // 測試：點擊 "All Accounts" 按鈕後，是否正確導航到 "Accounts Screen"
    @Test
    fun rallyNavHost_clickAllAccount_navigatesToAccounts() {
        // 查找 "All Accounts" 的節點並模擬點擊
        composeTestRule
            .onNodeWithContentDescription("All Accounts")
            .performClick()

        // 驗證是否導航到了 "Accounts Screen"
        composeTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed() // 確認 "Accounts Screen" 是否顯示
    }

    // 測試：點擊 "All Bills" 按鈕後，是否正確導航到 "bills" 路徑
    @Test
    fun rallyNavHost_clickAllBills_navigateToBills() {
        // 滾動到 "All Bills" 的節點並模擬點擊
        composeTestRule.onNodeWithContentDescription("All Bills")
            .performScrollTo() // 確保節點在屏幕上可見
            .performClick()    // 模擬點擊操作

        // 檢查當前導航控制器的路徑是否為 "bills"
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "bills") // 驗證當前路徑是否正確
    }
}
