package com.example.komarnik

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.komarnik.databinding.FragmentSecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var _view : View
    private lateinit var linearLayout: LinearLayout
    private var listOfDim : MutableList<Triple<String, Double, Double>> = mutableListOf()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var destroyed = false
    private var paused = false

    private val binding get() = _binding!!
    private var shouldFill: Boolean = false
    private var numberOfKomarnik : Int = 0;
    private var everyThingIsOk: Boolean = true



    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        _view = inflater.inflate(R.layout.fragment_second, container, false)

      //  var number = requireArguments().getString("number");

        val container = _view.findViewById<ScrollView>(R.id.input_fields_container)
        linearLayout = LinearLayout(activity)
        linearLayout.orientation = LinearLayout.VERTICAL

        drawDinamiclyOrStaticly(container,inflater)


            return _view

    }

    private fun drawDinamiclyOrStaticly(container: ScrollView, inflater: LayoutInflater) {
        println(numberOfKomarnik)
        if(numberOfKomarnik == 0) {
            //drawFirstTimeDinamicly
            addKomarnik(container, inflater)
            val childView1 = inflater.inflate(R.layout.button_izracunaj_i_dodaj, container, false)
            linearLayout.addView(childView1)
            container.addView(linearLayout)
        }else{
            //Draw when going back from fragment, we alredy have number of fields, we dont need dynamicly
            for (i in 0 until numberOfKomarnik){
                val childView = inflater.inflate(R.layout.list_item, container, false)
                val nameField = childView.findViewById<EditText>(R.id.editTextKomarnikFirst)
                var name :String = nameField.text.toString()
                if (name == "Komarnik") {
                    name = "$name $i"
                }
                nameField.setText(name)

                linearLayout.addView(childView)
            }
            val childView1 = inflater.inflate(R.layout.button_izracunaj_i_dodaj, container, false)
            linearLayout.addView(childView1)
            container.addView(linearLayout)
        }
        val addKomarnikButton = _view.findViewById<Button>(R.id.buttonDodajKomarnik)
        val removeKomarnik = _view.findViewById<Button>(R.id.buttonIzbrisi)
        val calculateButton = _view.findViewById<Button>(R.id.buttonIzracunaj)
        val calculatePriceButton = _view.findViewById<Button>(R.id.buttonCena)

        addKomarnikButton.setOnClickListener {
            container.removeViewAt(0)
            addKomarnik(container, inflater)
            container.addView(linearLayout)
            removeKomarnik.visibility = View.VISIBLE
            calculateButton.visibility = View.VISIBLE
            calculatePriceButton.visibility = View.VISIBLE
        }
        removeKomarnik.setOnClickListener{
            if (linearLayout.childCount > 1) {
                linearLayout.removeViewAt(linearLayout.childCount - 2)
                if(numberOfKomarnik == listOfDim.size )
                    listOfDim.removeAt(listOfDim.size - 1)
                numberOfKomarnik -= 1

            }
            if (linearLayout.childCount == 1){
                removeKomarnik.visibility = View.GONE
                calculateButton.visibility = View.GONE
                calculatePriceButton.visibility = View.GONE
            }
        }

    }


    override fun onResume() {
        super.onResume()
        println("MMMMMMMMMM")
        if(destroyed && listOfDim.isNotEmpty()) {
            val numOfChild = linearLayout.childCount - 1
            var sizeOfAllocated : Int = listOfDim.size
            for (i in 0 until numOfChild) {
                if(i < sizeOfAllocated) {
                    val childView = linearLayout.getChildAt(i)
                    val nameField = childView.findViewById<EditText>(R.id.editTextKomarnikFirst)
                    val widthField = childView.findViewById<EditText>(R.id.editTextSirina)
                    val heightField = childView.findViewById<EditText>(R.id.editTextVisina)


                    val name: String = listOfDim[i].first
                    nameField.setText(name)

                    val width: String = listOfDim[i].second.toString()
                    widthField.setText(width)

                    val height: String = listOfDim[i].third.toString()
                    heightField.setText(height)

                }
            }
            destroyed = false
        }

    }

    override fun onPause() {
        super.onPause()
        paused = true

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calculateButton = view.findViewById<Button>(R.id.buttonIzracunaj)

        calculateButton.setOnClickListener{
            if (linearLayout.childCount > 1) {
                everyThingIsOk= true
                var mutableMap = checkFieldsAndFillMap()
                if (everyThingIsOk) {
                    findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment,
                        Bundle().apply {
                            putSerializable("mapOfDimensions", mutableMap as java.io.Serializable)
                        })
                }
            }
        }
        val calculatePriceButton = view.findViewById<Button>(R.id.buttonCena)
        calculatePriceButton.setOnClickListener{
            if (linearLayout.childCount > 1) {
                everyThingIsOk= true
                var mutableMap = checkFieldsAndFillMap()
                if (everyThingIsOk) {
                    findNavController().navigate(R.id.action_SecondFragment_to_fourthFragment,
                        Bundle().apply {
                            putSerializable("mapOfDimensions", mutableMap as java.io.Serializable)
                        })
                }
            }

        }
    }

    fun addKomarnik(container : ScrollView, inflater: LayoutInflater){

            val childView = inflater.inflate(R.layout.list_item, container, false)
            val nameField = childView.findViewById<EditText>(R.id.editTextKomarnikFirst)
            var name :String = nameField.text.toString()
            if (name == "Komarnik") {
                name = "$name $numberOfKomarnik"
            }
            numberOfKomarnik += 1
            nameField.setText(name)
            if(linearLayout.childCount == 0) {
                linearLayout.addView(childView)
            }else{
                linearLayout.addView(childView,linearLayout.childCount - 1)
            }

    }

    fun checkFieldsAndFillMap() : MutableMap<String, Pair<Double, Double>> {
        listOfDim.clear()

        val mutableMap: MutableMap<String, Pair<Double, Double>> = mutableMapOf()
        // We iterate to numOfChild -1 because of button that is part of linearLayout
        val numOfChild = linearLayout.childCount - 1

        for (i in 0 until numOfChild) {
            val childView = linearLayout.getChildAt(i)
            val nameField = childView.findViewById<EditText>(R.id.editTextKomarnikFirst)
            val widthField = childView.findViewById<EditText>(R.id.editTextSirina)
            val heightField = childView.findViewById<EditText>(R.id.editTextVisina)

            val name: String = nameField.text.toString()
            val width: String = widthField.text.toString()
            val height: String = heightField.text.toString()


            if(width == "" || height == "" || width.toDouble() < 0 || width.toDouble() < 0){
                Toast.makeText(this.context,"Unesi sve dimenzije", Toast.LENGTH_LONG).show()
                everyThingIsOk = false
                break
            }else {
                val widthD : Double = width.toDouble()
                val heightD : Double = height.toDouble()
                listOfDim.add(Triple(name, widthD, heightD))

                val pair = Pair(widthD, heightD)

                mutableMap[name] = pair

            }
        }
        return mutableMap

    }
    override fun onDestroyView() {
        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        super.onDestroyView()
        _binding = null
        destroyed = true
    }
}