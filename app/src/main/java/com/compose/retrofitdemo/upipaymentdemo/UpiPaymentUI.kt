package com.compose.retrofitdemo.upipaymentdemo

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.retrofitdemo.MainActivity
import com.compose.retrofitdemo.ui.theme.greenColor
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.PaymentApp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpiPaymentUI(context: Context, mainActivity: MainActivity) {
    val activity = context as? Activity
    val amount = remember {
        mutableStateOf("")
    }

    val upiId = remember {
        mutableStateOf("")
    }

    val name = remember {
        mutableStateOf("")
    }

    val description = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "UPI Payment in Android",
            color = greenColor,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextField(value = amount.value, onValueChange = { amount.value = it },
            placeholder = {
                Text(text = "Enter Amount to paid")
            },
            textStyle = TextStyle(fontSize = 20.sp, color = Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextField(value = upiId.value, onValueChange = { upiId.value = it },
            placeholder = {
                Text(text = "Enter UPI ID")
            },
            textStyle = TextStyle(fontSize = 20.sp, color = Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextField(value = name.value, onValueChange = { name.value = it },
            placeholder = {
                Text(text = "Enter name")
            },
            textStyle = TextStyle(fontSize = 20.sp, color = Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextField(value = description.value, onValueChange = { description.value = it },
            placeholder = {
                Text(text = "Enter description")
            },
            textStyle = TextStyle(fontSize = 20.sp, color = Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val date: Date = Calendar.getInstance().time
            val dateFormate = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault())
            val transactionId: String = dateFormate.format(date)
            makePayment(
                amount.value,
                upiId.value,
                name.value,
                description.value,
                transactionId,
                context,
                activity!!,
                mainActivity
            )
        }) {
            Text(text = "MAKE PAYMENT", modifier = Modifier.padding(8.dp))
        }
    }
}

fun makePayment(
    amount: String,
    upiId: String,
    name: String,
    description: String,
    transactionId: String,
    context: Context,
    activity: Activity, mainActivity: PaymentStatusListener
) {
   try {
       val easiMyPayment = EasyUpiPayment(activity){
           this.paymentApp = PaymentApp.ALL
           this.payeeVpa = upiId
           this.payeeName = name
           this.transactionId = transactionId
           this.transactionRefId = transactionId
           this.payeeMerchantCode = transactionId
           this.description = description
           this.amount = amount
       }

       easiMyPayment.setPaymentStatusListener(mainActivity)
       easiMyPayment.startPayment()
   }catch (e : Exception){
       e.printStackTrace()
       Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
   }
}

