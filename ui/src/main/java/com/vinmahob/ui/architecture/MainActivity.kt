package com.vinmahob.ui.architecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vinmahob.ui.architecture.navigation.MainNavigation
import com.vinmahob.ui.architecture.theme.CleanArchitectureSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CleanArchitectureSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                }
            }
        }
    }
}

fun main(){
    val l1 = listOf<Int>(1,2,3)
    val l2 = listOf<Int>(3,4,5)

    println(l1)
    val result1 = l1.map { it+1 }
    println(l1)
    val list = listOf("123", "45")
    println(list.flatMap { it.toList() }) // [1, 2, 3, 4, 5]
}