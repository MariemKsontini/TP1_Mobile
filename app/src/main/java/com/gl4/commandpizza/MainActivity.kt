package com.gl4.commandpizza

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    lateinit var nom: TextInputLayout
    lateinit var prenom: TextInputLayout
    lateinit var adresse: TextInputLayout
    lateinit var radioGroup: RadioGroup
    lateinit var fromage: CheckBox
    lateinit var champignon: CheckBox
    lateinit var peperoni: CheckBox
    var size: String = ""
    lateinit var Commnader_button: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nom = findViewById(R.id.nom)
        prenom = findViewById(R.id.prenom)
        adresse = findViewById(R.id.adresse)
        radioGroup = findViewById(R.id.radioGroup)
        fromage = findViewById(R.id.fromage)
        champignon = findViewById(R.id.champignon)
        peperoni = findViewById(R.id.peperoni)
        Commnader_button = findViewById(R.id.commander_button)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton: RadioButton = findViewById(checkedId)
            size = selectedRadioButton.text.toString()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                1
            )
        }

        Commnader_button.setOnClickListener {
            var ingredients = "Les ingrédients sont : "
            if (fromage.isChecked) {
                ingredients += "fromage / "
            }
            if (champignon.isChecked) {
                ingredients += "champignon / "
            }
            if (peperoni.isChecked) {
                ingredients += "peperoni / "
            }

            val nomText = nom.editText?.text.toString()
            val prenomText = prenom.editText?.text.toString()
            val adresseText = adresse.editText?.text.toString()

            val message = "Le client $nomText $prenomText qui habite à $adresseText a commandé Pizza $size $ingredients"
            val phone = "93982136"
            SendSMS(phone, message)
            showToast("Commande envoyé : $message")
        }
    }

    fun SendSMS(number: String, message: String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(number, null, message, null, null)
    }
    private fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}