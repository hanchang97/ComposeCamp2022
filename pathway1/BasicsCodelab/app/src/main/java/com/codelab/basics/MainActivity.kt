package com.codelab.basics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp(Modifier.fillMaxSize())
            }
        }
        // xml의 setContentView 대신 setContent 사용
    }
}

@Composable
private fun MyApp(modifier: Modifier = Modifier) {

    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(modifier = modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier) {
        for (name in names) {
            Greeting(name = name)
        }
    }.also { Log.e("AppTest", "Outer Column Recomposition") }
    // 최초 한 번 호출
}

@Composable
fun Greeting(name: String) {
    val testNum = remember { mutableStateOf(1) }
    var expanded by remember { mutableStateOf(false) }
    val extraPadding = if (expanded) 48.dp else 0.dp

    Log.e("AppTest", "Greeting called")
    // state 객체가 컴포저블 함수 내부에 있으므로 리컴포지션 시 매번 호출

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(0.dp, 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello")
                Text(text = name)
            }.also { Log.e("AppTest", "Column Recomposition") }
            ElevatedButton(onClick = { // 클릭 시 동작
                testNum.value = testNum.value + 1
                Log.e("AppTest", "testNum : ${testNum}")
                expanded = !expanded
            }) {
                Text(
                    text = "Show Current Number : ${testNum.value}",
                    color = Color.Black
                ).also { Log.e("AppTest", "Text Recomposition") }
            }.also { Log.e("AppTest", "ElevatedButton Recomposition") }
        }.also { Log.e("AppTest", "Row Recomposition") }
    }.also { Log.e("AppTest", "Surface Recomposition") }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}