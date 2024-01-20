package com.vinmahob.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import com.vinmahob.presentation.architecture.ui.widget.TextWidget
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText

class TextWidgetTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_text_when_textWidget_called_then_text_should_be_displayed() {
        //init
        val testMessage = "Test message"

        //act
        composeTestRule.setContent {
            TextWidget(
                text = testMessage,
                modifier = Modifier
            )
        }

        //assert
        composeTestRule.onNodeWithText(testMessage).assertIsDisplayed()
    }

}