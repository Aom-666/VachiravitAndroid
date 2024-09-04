package com.example.vachiravitandroid

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
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

class addInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonSee = findViewById<Button>(R.id.buttonSee)

        val editTextCarBrand = findViewById<EditText>(R.id.editTextCarBrand)
        val editTextCarModel = findViewById<EditText>(R.id.editTextCarModel)
        val editTextYear = findViewById<EditText>(R.id.editTextYear)
        val editTextColor = findViewById<EditText>(R.id.editTextColor)
        val editTextPrice = findViewById<EditText>(R.id.editTextPrice)
        val editTextGearType = findViewById<EditText>(R.id.editTextGearType)
        val editTextFuelType = findViewById<EditText>(R.id.editTextFuelType)
        val editTextDoors = findViewById<EditText>(R.id.editTextDoors)
        val editTextSeats = findViewById<EditText>(R.id.editTextSeats)

        buttonSave.setOnClickListener {
            if (editTextCarBrand.text.isEmpty() || editTextCarModel.text.isEmpty() ||
                editTextYear.text.isEmpty() || editTextColor.text.isEmpty() ||
                editTextPrice.text.isEmpty() || editTextGearType.text.isEmpty() ||
                editTextFuelType.text.isEmpty() || editTextDoors.text.isEmpty() ||
                editTextSeats.text.isEmpty()){
                Toast.makeText(applicationContext, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val url = "http://10.13.4.112:3000/add/car"

            val okHttpClient = OkHttpClient()
            val formBody: RequestBody = FormBody.Builder()
                .add("Brand",editTextCarBrand.text.toString())
                .add("Model",editTextCarModel.text.toString())
                .add("Years",editTextYear.text.toString())
                .add("Color",editTextColor.text.toString())
                .add("Price",editTextPrice.text.toString())
                .add("GearType",editTextGearType.text.toString())
                .add("FuelType",editTextFuelType.text.toString())
                .add("NumberOfDoors",editTextDoors.text.toString())
                .add("NumberOfSeats",editTextSeats.text.toString())
                .build()
            val request: Request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

            val response = okHttpClient.newCall(request).execute()

            if(response.isSuccessful){
                val obj = JSONObject(response.body!!.string())
                val status = obj["status"].toString()

                if (status == "true") {
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

        buttonSee.setOnClickListener {
            intent = Intent(this, showInfoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}