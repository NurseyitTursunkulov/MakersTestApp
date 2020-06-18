package com.example.makerstestapp.factList

import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.Item
import com.example.makerstestapp.R
import com.example.makerstestapp.util.Event
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_facts.*

fun FactsFragment.setupListAdapter() {
    val viewModel = viewDataBinding.viewmodel
    if (viewModel != null) {
        listAdapter = FactsAdapter(viewModel)
        viewDataBinding.newsList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        viewDataBinding.lifecycleOwner = this
    } else {
        Log.e("Nurs", "ViewModel not initialized when attempting to set up adapter.")
    }
    viewModel?.items?.observe(viewLifecycleOwner, Observer {
        it?.let {
            listAdapter.submitList(it)
        }
    })
}

fun FactsFragment.setMenuItemClickListener() {
    toolbar.menu.children.iterator().forEach {
        it.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sort_by_price -> {
                    Log.d("Nurs", "sort foreach")
                    factsViewModel.sortItemsByPrice()
                    true
                }
                R.id.sort_by_category -> {
                    Log.d("Nurs", "sort foreach")
                    factsViewModel.sortItemsByCategory()
                    true
                }
                else -> false
            }
        }
    }
}

fun MutableLiveData<List<Item>>.postListValue(
    factsResult: List<Item>
) {
    val list = mutableListOf<Item>()
    this.value?.let {
        list.addAll(it)
    }
    list.addAll(factsResult)
    this.postValue(list)
}

fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<String>>,
    timeLength: Int
) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            Snackbar.make(this, it, timeLength).show()
        }
    })
}