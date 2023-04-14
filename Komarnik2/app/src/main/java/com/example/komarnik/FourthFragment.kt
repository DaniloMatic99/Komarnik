package com.example.komarnik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FourthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FourthFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var _view : View
    private lateinit var linearLayout: LinearLayout
    val mutableMap: MutableMap<String, List<Double>> = mutableMapOf()


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
        _view = inflater.inflate(R.layout.fragment_fourth, container, false)
        var mapOfDimensions = requireArguments()
            .getSerializable("mapOfDimensions") as MutableMap<String, Pair<Double, Double>>


        val calculatePriceButton = _view.findViewById<Button>(R.id.buttonCalcPrice)
        calculatePriceButton.setOnClickListener{
            var cost : EditText = _view.findViewById(R.id.editTextPrice)
            var price = cost.text.toString()
            if(price == "" || price.toDouble() < 0){
                Toast.makeText(this.context,"Unesi cenu", Toast.LENGTH_LONG).show()

            }else {
                findNavController().navigate(R.id.action_fourthFragment_to_fifthFragment2,
                    Bundle().apply {
                        putSerializable("mapOfDimensions", mapOfDimensions as java.io.Serializable)
                        putString("price", price)
                    })
            }
        }
        return _view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FourthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FourthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}