package com.example.komarnik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FifthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FifthFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var _view : View
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_fifth, container, false)

        var mapOfDimensions = requireArguments()
            .getSerializable("mapOfDimensions") as MutableMap<String, Pair<Double, Double>>
        var price = requireArguments().getString("price")

        val container = _view.findViewById<ScrollView>(R.id.inputFieldsFifthFragment)
        linearLayout = LinearLayout(activity)
        linearLayout.orientation = LinearLayout.VERTICAL

        var totalPriceD :Double= 0.0
        for ((key, value) in mapOfDimensions){
            val childView = inflater.inflate(R.layout.show_price, container, false)
            val nameField = childView.findViewById<EditText>(R.id.editTextKomarnikPrice)
            val name = key.toString()
            nameField.setText(name)
            nameField.isEnabled = false

            val width :Double= value.first
            val height :Double= value.second
            val dimension :Double= (width/100) * (height/100)

            val dimensionsField = childView.findViewById<EditText>(R.id.editTextDimenzije)
            val stringDimension :String = String.format("%.2f",dimension)

            var tmp :String =stringDimension + "m\u00B2"
            dimensionsField.setText(tmp)
            dimensionsField.isEnabled = false

            val priceField = childView.findViewById<EditText>(R.id.editTextCena)
            val finalPriceD : Double = dimension * price!!.toDouble()
            var finalPrice = String.format("%.2f",finalPriceD)
            priceField.setText(finalPrice)
            priceField.isEnabled = false

            totalPriceD += finalPriceD

            linearLayout.addView(childView)
        }
        val childView = inflater.inflate(R.layout.field, container, false)
        val totalPriceField = childView.findViewById<EditText>(R.id.editTextCenaKonacna)
        val totalPrice = String.format("%.2f",totalPriceD)
        totalPriceField.setText(totalPrice)
        totalPriceField.isEnabled = false

        linearLayout.addView(childView)
        container.addView(linearLayout)
        return _view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FifthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FifthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}