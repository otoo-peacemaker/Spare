package com.peacemaker.android.spare.ui.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.android.material.snackbar.Snackbar
import com.peacemaker.android.spare.MainActivity
import com.peacemaker.android.spare.R

open class BaseFragment: Fragment() {
    override fun onStart() {
        super.onStart()
        val actionBar = (activity as AppCompatActivity).supportActionBar
         activity?.changeBackNavigationIcon(actionBar)
    }

    fun setTextViewPartialColor(textView: TextView, fullText: String, partialText: String, color: Int) {
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf(partialText)
        if (startIndex >= 0) {
            val endIndex = startIndex + partialText.length
            spannableString.setSpan(ForegroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textView.text = spannableString
    }

    fun showLoadingScreen(visibility:Boolean){
        (activity as MainActivity).setProgressBar(visibility)
    }

    /**
     * This function takes in an email and password as strings and uses regular expressions to check if they are valid.
     * @param email it checks if the string matches the format of a typical email address
     * @param password  it checks if the string contains at least one lowercase letter,
     * one uppercase letter, one number, and is at least 8 characters long
     * */
    fun validateEmailAndPassword(email: String, password: String, message:(String)->Unit): Boolean {
        // Check if email is valid
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        if (!email.matches(emailRegex)) {
            message.invoke("Email is not valid")
            return false
        }
        // Check if password meets criteria
        val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
        if (!password.matches(passwordRegex)) {
            message.invoke("Please enter a strong password with at least one lowercase letter,\n" +
                    "one uppercase letter, one number, and is at least 8 characters long")
            return false
        }
        // If both email and password are valid, return true
        return true
    }
    fun validateString(string: String):Boolean{
        return with(string){
            this.isNotEmpty()
            this.length >3
        }
    }

    /**
     * This function takes in a view, a message, and an action to be performed when the "Retry" button is clicked.
     * @param view view that the SnackBar should be displayed on (for example, a CoordinatorLayout or a ConstraintLayout).
     * @param message to be displayed in the SnackBar.
     * @param action A lambda expression that defines the action to be performed when the "Retry" button is clicked.
     * */
    fun showRetrySnackBar(view: View, message: String, action: () -> Unit) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("Retry") { action.invoke() }
        snackBar.setActionTextColor(ContextCompat.getColor(view.context, R.color.md_theme_light_primary))
        snackBar.show()
    }

    fun showSnackBar(view: View, message: String) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
        snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.md_theme_light_primary))
        snackBar.show()
    }

    fun showActionBarOnFragment(fragment: Fragment, show:Boolean) {
        val actionBar = (fragment.requireActivity() as AppCompatActivity).supportActionBar
        if (show) actionBar?.show() else actionBar?.hide()
    }

    fun getDrawable(resId:Int ):Drawable?{
        return ContextCompat.getDrawable(requireContext(), resId)
    }
    fun setupAutoCompleteTextView(
        autoCompleteTextView: AutoCompleteTextView,
        suggestions: List<String>,
        onItemClick: ((String) -> Unit)? = null) {
        val adapter = ArrayAdapter(
            autoCompleteTextView.context,
            android.R.layout.simple_dropdown_item_1line,
            suggestions
        )
        autoCompleteTextView.setAdapter(adapter)
       // autoCompleteTextView.threshold = 1
        autoCompleteTextView.setDropDownBackgroundDrawable(getDrawable(R.drawable.chevron_down))
        onItemClick?.let { listener ->
            autoCompleteTextView.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val selectedItem = adapter.getItem(position) as String
                    listener.invoke(selectedItem)
                }
        }
    }

    fun barDataSet(
        colorResId: Int,
        entries: ArrayList<BarEntry>,
        label: String): BarDataSet {
        return BarDataSet(entries, label).apply {
            color = resources.getColor(colorResId, null)
            valueTextColor = resources.getColor(R.color.black, null)
            valueTextSize = 10f
        }
    }

    fun setBarChart(
        barChart: BarChart,
        dataSets: ArrayList<IBarDataSet?>, valueFormatter: ArrayList<String>,
        isLegendEnable: Boolean=false,
        setPercentFormatter: Boolean=true,
        barSpace:Float = 0.45f,
        grpSpace:Float = 0.2f) {

        //connect our data to the vBarDataSet UI Screen
        barChart.apply {
            data = BarData(dataSets).apply { barWidth = 0.5f }
            animateXY(2000, 2000, Easing.EaseInSine)
            description.isEnabled = false
            if (setPercentFormatter){
                data.setValueFormatter(PercentFormatter())
            }
            groupBars(0.3f, grpSpace, barSpace)
            legend.isEnabled = isLegendEnable
            setBackgroundColor(Color.TRANSPARENT)
            //format axis
            xAxis.textColor = resources.getColor(R.color.black, null)
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.setDrawLabels(true)
            axisLeft.axisMaximum = 100f
            axisLeft.axisMinimum = 0f
            axisLeft.isEnabled = false
            axisLeft.setDrawAxisLine(true)
            axisLeft.textColor = resources.getColor(R.color.black, null)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisRight.isEnabled = false
            // Display labels for bars
            if (valueFormatter.isNotEmpty()) xAxis.valueFormatter = MyXValueFormatter(valueFormatter)

            setFitBars(true)
            invalidate()
        }
    }

    class MyXValueFormatter(private val str: ArrayList<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val position = value.toInt()
            return if (position < str.size && position > -1) {
                str[position]
            } else {
                ""
            }
        }
    }

    inline fun <reified T : ViewBinding> Fragment.inflateViewBindingDialog(
        crossinline bindingInflater: (LayoutInflater) -> T,
        crossinline block: AlertDialog.Builder.(T) -> Unit = {}): AlertDialog {
        val binding = bindingInflater(LayoutInflater.from(requireContext()))
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .apply { block(binding) }
            .create()
            .apply { setView(binding.root) }
    }
}