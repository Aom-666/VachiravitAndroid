package com.example.vachiravitandroid

import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class showInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val editTextCarID = findViewById<EditText>(R.id.editTextCarID)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)

        val textViewCarInfo = findViewById<TextView>(R.id.textViewCarInfo)

        buttonSearch.setOnClickListener {
            if (editTextCarID.text.isEmpty()) {
                textViewCarInfo.text = "กรุณากรอกหมายเลขรถ"
                return@setOnClickListener
            }

            val url = "http://10.13.4.112:3000/get/car/${editTextCarID.text}"
            val okHttpClient = OkHttpClient()
            val request: Request = Request.Builder()
                .url(url)
                .get()
                .build()

            val response = okHttpClient.newCall(request).execute()

            if(response.isSuccessful){
                val obj = JSONObject(response.body!!.string())
                val status = obj["status"].toString()

                if (status == "true") {
                    val CarID = obj["CarID"].toString()
                    val Brand = obj["Brand"].toString()
                    val Model = obj["Model"].toString()
                    val Years = obj["Years"].toString()
                    val Color = obj["Color"].toString()
                    val Price = obj["Price"].toString()
                    val GearType = obj["GearType"].toString()
                    val FuelType = obj["FuelType"].toString()
                    val NumberOfDoors = obj["NumberOfDoors"].toString()
                    val NumberOfSeats = obj["NumberOfSeats"].toString()

                    textViewCarInfo.text = buildString {
                        append("CarID: $CarID")
                        append("| Brand: $Brand")
                        append("| Model: $Model")
                        append("| Years: $Years")
                        append("| Color: $Color")
                        append("| Price: $Price")
                        append("| GearType: $GearType")
                        append("| FuelType: $FuelType")
                        append("| NumberOfDoors: $NumberOfDoors")
                        append("| NumberOfSeats: $NumberOfSeats")
                    }
                    val message = obj["message"].toString()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                } else {
                    val message = obj["message"].toString()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(applicationContext, "ไม่สามารถเชื่อต่อกับเซิร์ฟเวอร์ได้", Toast.LENGTH_LONG).show()
            }


        }
    }
}