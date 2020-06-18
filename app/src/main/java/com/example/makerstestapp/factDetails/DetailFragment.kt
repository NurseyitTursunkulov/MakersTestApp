package com.example.makerstestapp.factDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.makerstestapp.FactsViewModel
import com.example.makerstestapp.R
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {
    val factsViewModel : FactsViewModel by sharedViewModel()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factsViewModel.openDetailsEvent.observe(viewLifecycleOwner, Observer {
            val item = it.peekContent()
            Glide.with(this).load(item.img).centerCrop().into( imageView2);
            item_name.text = item.name
            company_text_view.text = item.company
            category_text_view.text = item.category
            price_tex_view.text = item.price.toString() + "coм"
            desc_tv.text  = item.desc
        })
    }
}