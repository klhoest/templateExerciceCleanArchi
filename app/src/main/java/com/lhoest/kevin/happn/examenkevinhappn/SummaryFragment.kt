package com.lhoest.kevin.happn.examenkevinhappn

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.lhoest.kevin.happn.examenkevinhappn.di.DaggerMainComponent

import com.lhoest.kevin.happn.examenkevinhappn.viewmodel.ForecastViewModel
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class SummaryFragment : Fragment() {

    private val columnCount = 1

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.activity?.let { activity ->
            val component = DaggerMainComponent.create()
            viewModelFactory = component.viewModelFactory
            viewModel = ViewModelProviders.of(activity, viewModelFactory)[ForecastViewModel::class.java]
        }
    }

    private lateinit var adapter: MySummaryRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_summary_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            view.layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MySummaryRecyclerViewAdapter(viewModel)
            view.adapter = adapter
        }
        listenViewModel()
        return view
    }

    private fun listenViewModel() {
        viewModel.forcast.observe(this, Observer { adapter.setData(it) })
    }
}
