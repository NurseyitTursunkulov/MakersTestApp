package com.example.makerstestapp.factList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.makerstestapp.MainViewModel
import com.example.makerstestapp.R
import com.example.makerstestapp.databinding.FragmentFactsBinding
import com.example.makerstestapp.util.EventObserver
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_facts.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ItemsFragment : Fragment() {

    val mainViewModel : MainViewModel by sharedViewModel()

    lateinit var viewDataBinding: FragmentFactsBinding

    lateinit var listAdapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentFactsBinding.inflate(inflater, container, false).apply {
            viewmodel = mainViewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.inflateMenu(R.menu.menu_main);
        setMenuItemClickListener()
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        view.setupSnackbar(viewLifecycleOwner, mainViewModel.snackbarText, Snackbar.LENGTH_SHORT)

        mainViewModel.openDetailsEvent.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(R.id.action_FactsFragment_to_DetailFragment)
        })
    }


}