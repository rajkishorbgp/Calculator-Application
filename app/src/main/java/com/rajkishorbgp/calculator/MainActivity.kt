package com.rajkishorbgp.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.view.isGone
import com.rajkishorbgp.calculator.databinding.ActivityMainBinding
import  net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastNumberic = false
    private var stateError = false
    private var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onAllClearClick(view: View) {
        binding.dataTv.text=""
        binding.resultTv.text=""
        stateError = false
        lastDot = false
        lastNumberic = false
        binding.resultTv.visibility = View.GONE
    }


    fun onDigitClick(view: View) {
        if (stateError){

            binding.dataTv.text=(view as Button).text
            stateError=false
        }else{
            binding.dataTv.append((view as Button).text)
        }
        lastNumberic = true
        onEquel()
    }


    fun onClearClick(view: View) {
        binding.dataTv.text=""
        lastNumberic = false
    }


    fun onBackClick(view: View) {
        binding.dataTv.text=binding.dataTv.text.toString().dropLast(1)
        try {
            val lastChar = binding.dataTv.text.toString().last()
            if (lastChar.isDigit()){
                onEquel()
            }
        }catch (e: java.lang.Exception){
            binding.resultTv.text=""
            binding.resultTv.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }


    fun onOperatorClick(view: View) {
        if (!stateError && lastNumberic){
            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNumberic = false
            onEquel()
        }
    }


    fun onEquelClick(view: View) {
        onEquel()
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)
    }

    fun onEquel(){
        if (lastNumberic && !stateError){
            val txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                binding.resultTv.visibility=View.VISIBLE

                binding.resultTv.text = "=" + result.toString()
            }catch (ex: java.lang.ArithmeticException){
                Log.e("evaluate error", ex.toString())
                binding.resultTv.text="Error"
                stateError=true
                lastNumberic = false
            }

        }
    }


}