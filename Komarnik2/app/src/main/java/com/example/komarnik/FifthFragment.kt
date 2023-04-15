package com.example.komarnik

import android.app.AlertDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.io.File
import java.io.OutputStream

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
    private var totalPriceD :Double= 0.0

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

        val childView1 = inflater.inflate(R.layout.button_sacuvaj,container, false)
        linearLayout.addView(childView1)

        container.addView(linearLayout)
        return _view
    }
    private fun showSaveDialog() {
        var name = requireArguments().getString("name")
        name = "$name- ponuda"
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
        var mapOfDimensions = requireArguments()
            .getSerializable("mapOfDimensions") as MutableMap<String, Pair<Double, Double>>
        var price = requireArguments().getString("price")
        var namePerson = requireArguments().getString("name")
        var adress = requireArguments().getString("adress")
        var phoneNumber = requireArguments().getString("number")

        var content = String.format("%-19s %-30s\n","Ime i prezime:", "${namePerson}")
        content += String.format("%-19s %-30s\n","Adresa:", "${adress}")
        content += String.format("%-19s %-30s\n","Broj telefona:", "${phoneNumber}")
        content += "------------------------------------------------------\n\n\n\n\n"

        for ((key,value) in mapOfDimensions) {
            val width :Double= value.first
            val height :Double= value.second
            val dimension :Double= (width/100) * (height/100)
            val dimensionString :String = String.format("%.2f",dimension)

            val finalPriceD : Double = dimension * price!!.toDouble()
            val finalPriceString :String = String.format("%.2f",finalPriceD)


            content += "------------------------------------------------------\n"
            content += String.format("%-30s\n", key)
            content += String.format("%-19s %-30s\n","Širina X Visina:", "${width}cm X ${height}cm")
            content += String.format("%-19s %-30s\n","Dimenzije:", "$dimensionString m\u00B2")
            content += String.format("%-19s %-30s\n","Cena:", finalPriceString)
            content += "------------------------------------------------------\n\n"

        }
        val totalPriceString :String = String.format("%.2f",totalPriceD)
        content += "\n\n\n------------------------------------------------------\n"
        content += String.format("%-19s %-30s\n","Cena po m\u00B2:", price)
        content += "------------------------------------------------------\n"
        content += String.format("%-19s %-30s\n","Ukupna cena:", totalPriceString)
        content += "------------------------------------------------------\n"
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
                }
                val uri: Uri? = context?.contentResolver?.insert(MediaStore.Files.getContentUri("external"), values)
                val outputStream: OutputStream? = uri?.let { context?.contentResolver?.openOutputStream(it) }
                outputStream?.use { it.write(content.toByteArray()) }
            } else {
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "fileName")
                file.writeText(content)
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