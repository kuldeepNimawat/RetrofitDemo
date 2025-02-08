package com.compose.retrofitdemo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.retrofitdemo.retrofit.MyViewModel
import com.compose.retrofitdemo.ui.theme.RetrofitDemoTheme
import com.compose.retrofitdemo.ui.theme.greenColor
import com.compose.retrofitdemo.upipaymentdemo.UpiPaymentUI
import dagger.hilt.android.AndroidEntryPoint
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.TransactionDetails

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentStatusListener {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(greenColor),
                                title = {
                                    Text(
                                       // text = "Retrofit Post Request",
                                        text = "UPI Payment Demo",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        }
                    ) { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues) // ✅ Use the paddingValues here
                        ) {
                            //----Retrofit/Volley post Requests-------
                            //postDataUI()
                            //----UPI Payment Demo----
                            UpiPaymentUI(LocalContext.current,this@MainActivity)
                        }
                    }
                }
            }
        }
    }

    override fun onTransactionCancelled() {
        Toast.makeText(this, "Transaction canceled by user..", Toast.LENGTH_SHORT).show()
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        Toast.makeText(this, "Transaction completed by user..", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun postDataUI(viewModel : MyViewModel = hiltViewModel()/*androidx.lifecycle.viewmodel.compose.viewModel()*/) {
    val context = LocalContext.current
    val responseTxt by viewModel.response.collectAsState()
    val username = remember {
        mutableStateOf(TextFieldValue())
    }

    val job = remember {
        mutableStateOf(TextFieldValue())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Retrofit POST Request in Android", color = greenColor, style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = username.value, onValueChange = { username.value = it },
            placeholder = { Text(text = "Enter your name") },
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = job.value, onValueChange = { job.value = it },
            placeholder = { Text(text = "Enter your job") },
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                      //---Retrofit Post Request----------
               // viewModel.sendUserData(username.value.text, job.value.text)
                      //-----volley post request
                viewModel.sendUserData1(username.value.text, job.value.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = "POST DATA", modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = responseTxt, color = Color.Black,
            fontSize = 20.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

    }
}


