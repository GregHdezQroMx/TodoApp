package com.jght.todoapp.addtask.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class AddTaskDialogKtTest {

    @get:Rule
    val contentTestRule = createComposeRule()

    @Test
    fun whenDialogGetATrue_thenShowDialog() {
        contentTestRule.setContent {
            AddTasksDialog(
                show = true,
                onDismiss = {},
                onTaskAdded = {}
            )
        }

        contentTestRule.onNodeWithTag("dialog").apply{
            assertIsDisplayed()
        }
    }

    @Test
    fun whenDialogGetAFalse_thenHideDialog() {
        contentTestRule.setContent {
            AddTasksDialog(
                show = false,
                onDismiss = {},
                onTaskAdded = {}
            )
        }

        contentTestRule.onNodeWithTag("dialog").apply{
            assertDoesNotExist()
        }
    }
}