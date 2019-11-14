package com.example.myapplication

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = spinnerAge.getItemAtPosition(position)
        Toast.makeText(this,"Selected Item = $selectedItem" , Toast.LENGTH_LONG ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Handling item selected listener for spinner
        spinnerAge.onItemSelectedListener = this

        buttonCalculate.setOnClickListener {
            calculatePremium()
        }

        /*val buttonThird: Button
        buttonThird.setOnContextClickListener(this)*/
    }

    private fun calculatePremium() {
        //Get the age group
        val age: Int = spinnerAge.selectedItemPosition

        var premium = when(age){
            0 -> 60 //Less than 17
            1 -> 70 //17 to 25
            2 -> 90 //26 to 30
            3 -> 120 //31 to 40
            4 -> 150 //41 to 55
            else -> 150
        }

        //Get the gender selection
        val gender = radioGroupGender.checkedRadioButtonId
        if(gender == R.id.radioButtonMale) {
            //Calculate premium for male
            premium += when (age) {
                0 -> 0 //Less than 17
                1 -> 50 //17 to 25
                2 -> 100 //26 to 30
                3 -> 150 //31 to 40
                4 -> 200 //41 to 55
                else -> 200
            }
        }
        else{
            //Calculate premium for female
            premium += 0
        }

        //Determine smoker or non-smoker
        val smoker: Boolean = checkBoxSmoker.isChecked
        if(smoker) {
            //Calculate premium for smoker
            premium += when (age) {
                0 -> 0 //Less than 17
                1 -> 100 //17 to 25
                2 -> 150 //26 to 30
                3 -> 200 //31 to 40
                4 -> 250 //41 to 55
                else -> 300
            }
        }

        val symbol = Currency.getInstance(Locale.getDefault()).symbol
        textViewPremium.text = String.format("%s %s %d", getString(R.string.insurance_premium),symbol, premium)
    }

    fun reset(view: View) {
        textViewPremium.text = getString(R.string.insurance_premium)
        spinnerAge.setSelection(0)
        radioGroupGender.clearCheck()
        checkBoxSmoker.isChecked = false
    }

}
