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
        var number : EditText = view.findViewById(R.id.editTextNumber)

        binding.buttonGoToCalc.setOnClickListener {
            var num = number.text.toString()
            if(num == "" || num.toInt() <= 0){
                Toast.makeText(this.context,"Unesi broj različit od 0", Toast.LENGTH_LONG).show()
            }else {
                findNavController().navigate(
                    R.id.action_FirstFragment_to_SecondFragment,
                    Bundle().apply {
                        putString("number", num)
                    })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}