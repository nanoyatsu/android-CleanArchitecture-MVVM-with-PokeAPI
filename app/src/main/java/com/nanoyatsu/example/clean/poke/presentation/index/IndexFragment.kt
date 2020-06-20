package com.nanoyatsu.example.clean.poke.presentation.index

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nanoyatsu.example.clean.poke.R
import com.nanoyatsu.example.clean.poke.databinding.FragmentIndexBinding
import org.koin.android.viewmodel.ext.android.viewModel

class IndexFragment : Fragment() {
    private lateinit var binding: FragmentIndexBinding
    private val vm: IndexViewModel by viewModel()
    private val viewHolderNavigation: IndexItemViewHolder.Navigation
            by lazy { Navigation(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentIndexBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 描画設定
        binding.index.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.index.adapter = IndexAdapter(emptyList(), viewHolderNavigation)

        // ViewModel設定
        binding.vm = setupVm(vm)
        binding.lifecycleOwner = viewLifecycleOwner

        // イベント設定
    }

    private fun setupVm(vm: IndexViewModel): IndexViewModel {
//        val adapter = binding.index.adapter as IndexAdapter
//        vm.list.observe(viewLifecycleOwner, Observer { binding.index.adapter.submitList(it) }
        vm.list.observe(
            viewLifecycleOwner,
            Observer {
                binding.index.adapter = IndexAdapter(it, viewHolderNavigation)
                binding.index.adapter?.notifyDataSetChanged()
            })
        return vm
    }

    companion object {
        fun newInstance() = IndexFragment()
    }

    class Navigation(private val navHostActivity: Activity) : IndexItemViewHolder.Navigation {
        override fun transDetail(number: Int) {
            val directions = IndexFragmentDirections
                .actionIndexFragmentToDetailFragment(number)
            navHostActivity.findNavController(R.id.container).navigate(directions)
        }
    }
}
