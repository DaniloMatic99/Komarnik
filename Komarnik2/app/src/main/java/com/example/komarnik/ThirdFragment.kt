package com.example.komarnik

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.io.File
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.math.RoundingMode
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var _view : View
    private lateinit var linearLayout: LinearLayout
    val mutableMap: MutableMap<String, List<Double>> = mutableMapOf()
    private var param1: String? = null
    private var param2: String? = null

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
        _view = inflater.inflate(R.layout.fragment_third, container, false)
        var mapOfDimensions = requireArguments()
            .getSerializable("mapOfDimensions") as MutableMap<String, Pair<Double, Double>>


        val list : MutableList<Pair<Double, Double>> = mutableListOf()
        for((key,pair) in mapOfDimensions){
            list.add(pair)
        }

        var number : Int = mapOfDimensions.size

        val container = _view.findViewById<ScrollView>(R.id.inputFieldsThirdFragment)
        linearLayout = LinearLayout(activity)
        linearLayout.orientation = LinearLayout.VERTICAL

        for ((key,value) in mapOfDimensions) {
            val childView = inflater.inflate(R.layout.resault_item, container, false)
            val width = value.first
            val height = value.second
            val name :String= key

            val nameEd = childView.findViewById<EditText>(R.id.editTextKomarnik)
            val mreza = childView.findViewById<EditText>(R.id.editTextMreza)
            val rucica = childView.findViewById<EditText>(R.id.editTextRucica)
            val lajsna = childView.findViewById<EditText>(R.id.editTextZavrsna)
            val vodjica = childView.findViewById<EditText>(R.id.editTextVodjice)
            val dimension = childView.findViewById<TextView>(R.id.textViewDimensions)

            val tmp : String = "" + width + "cm X " + height + "cm"
            val printText : String = "$name\n$tmp"
            nameEd.setText(printText)
            nameEd.isEnabled = false

          //  dimension.text = tmp
           /// dimension.isEnabled = false

            val formatter = DecimalFormat("#.##")
            formatter.roundingMode = RoundingMode.FLOOR // to round down to 2 decimal places

            val mrezaCalcD :Double = width - 2.6
            val mrezaCalc :String = String.format("%.1f", mrezaCalcD)
            mreza.setText(mrezaCalc)
            mreza.isEnabled = false

            val rucicaCalcD : Double = width - 2.6 - 5.9
            val rucicaCalc :String = String.format("%.1f", rucicaCalcD)
            rucica.setText(rucicaCalc)
            rucica.isEnabled = false

            val lajsnaCalcD : Double = width - 7.4
            val lajsnaCalc :String = String.format("%.1f",lajsnaCalcD)
            lajsna.setText(lajsnaCalc)
            lajsna.isEnabled = false

            val vodjicaCalcD : Double = height - 6.2
            val vodjicaCalc :String = String.format("%.1f", vodjicaCalcD)
            vodjica.setText(vodjicaCalc)
            vodjica.isEnabled = false

            var lista :List<Double> = listOf(width, height, mrezaCalcD, rucicaCalcD, lajsnaCalcD, vodjicaCalcD)
            mutableMap[name] = lista
            linearLayout.addView(childView)
        }
        val childView = inflater.inflate(R.layout.button_sacuvaj, container, false)
        linearLayout.addView(childView)


        container.addView(linearLayout)

        return _view
    }

    private fun showSaveDialog() {
        var name = requireArguments().getString("name")
        name = "$name- mere"
        val filenameEditText = EditText(requireContext())
        filenameEditText.setText(name)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Sačuvaj fajl")
            .setMessage("Unesi ime fajla:")
            .setView(filenameEditText)
            .setPositiveButton("Sačuvaj") { _, _ ->
                val filename = filenameEditText.text.toString().trim()
                if (filename.isNotEmpty()) {
                    saveToFile("$filename.txt")
                } else {
                    Toast.makeText(requireContext(), "Unesi ime fajla", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Otkaži", null)
            .create()

        dialog.show()
    }
    private fun saveToFile(fileName: String) {
        //val fileName = "my_file.txt" // name of the file you want to save
        var name = requireArguments().getString("name")
        var adress = requireArguments().getString("adress")
        var phoneNumber = requireArguments().getString("number")
        var fileContents: String = ""
        for ((key,value) in mutableMap) {
            var sirina = value[0].toFloat()
            var visina = value[1].toFloat()
            var mreza = value[2].toFloat()
            var rucica = value[3].toFloat()
            var lajsna = value[4].toFloat()
            var vodjica = value[5].toFloat()
            fileContents = fileContents +
                "${key}\n" +
                "Širina X Visina:   ${sirina}cm X ${visina}cm\n\n" +
                "Mreža:             ${mreza}cm\n" +
                "Ručica:            ${rucica}cm\n" +
                "Lajsna             ${lajsna}cm\n" +
                "Vođica             ${vodjica}cm" +
                "\n------------------------------------------------------\n\n\n"// contents of the file
        }
        fileContents = fileContents + "\n\n\n" +
                "Ime i prezime:     ${name}\n" +
                "Adresa:            ${adress}\n" +
                "Broj telefona:     ${phoneNumber}\n"
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
                }
                val uri: Uri? = context?.contentResolver?.insert(MediaStore.Files.getContentUri("external"), values)
                val outputStream: OutputStream? = uri?.let { context?.contentResolver?.openOutputStream(it) }
                outputStream?.use { it.write(fileContents.toByteArray()) }
            } else {
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "fileName")
                file.writeText(fileContents)
            }

            Toast.makeText(context, "File saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("TAG", "Error writing file", e)
            Toast.makeText(context, "Error writing file", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton = view.findViewById<Button>(R.id.buttonSacuvaj)
        saveButton.setOnClickListener{
            showSaveDialog()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}