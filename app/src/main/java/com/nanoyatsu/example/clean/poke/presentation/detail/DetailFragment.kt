package com.nanoyatsu.example.clean.poke.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nanoyatsu.example.clean.poke.databinding.FragmentDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val vm: DetailViewModel by viewModel { parametersOf(args) }

        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner
//        binding.fraTeki.text = args.number.toString()
    }

    companion object {
        fun newInstance() = DetailFragment()
    }
}
