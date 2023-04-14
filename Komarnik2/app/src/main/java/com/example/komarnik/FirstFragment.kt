package com.example.komarnik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.komarnik.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var nameSurname : EditText = view.findViewById(R.id.editTextImePrezime)
        var adressEdit: EditText = view.findViewById(R.id.editTextAdresa)
        var phoneNumber : EditText = view.findViewById(R.id.editTextBroj)

        binding.buttonGoToCalc.setOnClickListener {
            var name = nameSurname.text.toString()
            var adress = adressEdit.text.toString()
            var number = phoneNumber.text.toString()
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,
                Bundle().apply {
                    putString("name", name)
                    putString("adress", adress)
                    putString("number", number)
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}