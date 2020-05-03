package eu.learn.firstcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lastIsNumeric = false
    var lastIsDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view : View){
        tvInput.append((view as Button).text)
        lastIsNumeric = true
    }

    fun onClear(view: View){
        tvInput.text = ""
        lastIsNumeric = false
        lastIsDot = false
    }

    fun onDot(view: View){
        if(lastIsNumeric && !lastIsDot){
            tvInput.append(".")
            lastIsDot = true
            lastIsNumeric = false
        }
    }

    fun onOperator(view: View){
        if(lastIsNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastIsNumeric = false
            lastIsDot = false
        }
    }

    fun onEqual( view: View){
        if(lastIsNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")){
                    tvValue= tvValue.substring(1)
                    prefix ="-"
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvInput.text = removeLastZero((first.toDouble() - second.toDouble()).toString())
                }else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        first = prefix + first
                    }
                    tvInput.text = removeLastZero((first.toDouble() + second.toDouble()).toString())
                }else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        first = prefix + first
                    }
                    tvInput.text = removeLastZero((first.toDouble() * second.toDouble()).toString())
                }else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (!prefix.isEmpty()) {
                        first = prefix + first
                    }
                    tvInput.text = removeLastZero((first.toDouble() / second.toDouble()).toString())
                }


            }catch (e: ArithmeticException){
                e.printStackTrace()
            }

        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("+") || value.contains("-") || value.contains("*") ||
                    value.contains("/")
        }
    }

    private fun removeLastZero(value: String) : String{
        var result = value
        if(result.contains(".0")){
            result = value.substring(0, value.length-2)
        }
        return result
    }
}
