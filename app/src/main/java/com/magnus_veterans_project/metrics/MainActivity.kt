package com.magnus_veterans_project.metrics

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Native imports
import com.magnus_veterans_project.io.clear
import com.magnus_veterans_project.io.strIn
import com.magnus_veterans_project.io.strOut
import com.magnus_veterans_project.metrics.ui.theme.MetricsTheme

/* Project: Metrics
 * Package: com.magnus_veterans_project.metrics
 * Coders:  Shawn Gallagher
 * Date:    2/20/2026
 * Version: 0.1.1
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //get the main activity's (application's main) context
        val activityContext: Context = this.applicationContext
        setContent {
            MetricsTheme {
                Scaffold { innerPadding ->
                    //Instantiating StateData object for organization (and cautious scope manipulation)
                    val sd = StateData(
                        rememberTextFieldState(),
                        rememberTextFieldState(),
                        remember {mutableStateOf("")},
                        remember {mutableStateOf("")}
                    )
                    Column {
                        DemoComposable(Modifier.padding(innerPadding), sd)
                        RWButtonComposable(activityContext, sd)
                    }
                }
            }
        }
    }
}

/* Read & Write button composable: writes text fields to sample file 'text.txt', then reads from
 * file and updates text bellow
 */
@Composable
fun RWButtonComposable(context: Context, sd: StateData) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                sd.txt1State.value = strIn(
                    context,
                    "data.txt"
                )
            }
        ) {
            Text("Read")
        }
        Button(
            onClick = {
                strOut(
                    context,
                    "data.txt",
                    (sd.tf1State.text.toString() + sd.tf2State.text.toString())
                )

            }
        ) {
            Text("Write")
        }
        Button(
            onClick = {
                clear(
                    context,
                    "data.txt"
                )
            }
        ) {
            Text("Clear")
        }
    }
}

//Data class for state data (text fields and labels)
//this is probably improper; fix later...
data class StateData(val tf1State: TextFieldState, val tf2State: TextFieldState, val txt1State: MutableState<String>, val txt2State: MutableState<String>)

//Main composable object for the application (very slapdash)
@Composable
fun DemoComposable(modifier: Modifier = Modifier, sd: StateData) {

    //TextField box
    Surface(modifier = modifier) {
        Column {
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                TextField (
                    state = sd.tf1State,
                    label = { Text("tf1") },
                    modifier = Modifier.width(IntrinsicSize.Min).weight(0.5F),
                    lineLimits = TextFieldLineLimits.SingleLine
                )
                TextField(
                    state = sd.tf2State,
                    label = { Text("tf2") },
                    modifier = Modifier.padding(0.dp).weight(0.5F),
                    lineLimits = TextFieldLineLimits.SingleLine
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {

                Text(text = sd.txt1State.value, fontSize = 24.sp)
                Text(text = sd.txt2State.value, fontSize = 24.sp)
            }
        }
    }
}

//Preview of DemoComposable
@Preview(showBackground = true)
@Composable
fun DemoPreview() {
    val sd = StateData(rememberTextFieldState(), rememberTextFieldState(), remember {mutableStateOf("")}, remember {mutableStateOf("")})
    DemoComposable(Modifier.fillMaxSize(), sd)
}