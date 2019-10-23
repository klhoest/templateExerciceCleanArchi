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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.lhoest.kevin.happn.examenkevinhappn.di.DaggerMainComponent

import com.lhoest.kevin.happn.examenkevinhappn.dummy.DummyContent
import com.lhoest.kevin.happn.examenkevinhappn.dummy.DummyContent.DummyItem
import com.lhoest.kevin.happn.examenkevinhappn.viewmodel.ForecastViewModel
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [SummaryFragment.OnListFragmentInteractionListener] interface.
 */
class SummaryFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var model: ForecastViewModel
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.activity?.let {activity ->
            val component = DaggerMainComponent.create()
            viewModelFactory = component.viewModelFactory
            model = ViewModelProviders.of(activity, viewModelFactory)[ForecastViewModel::class.java]
        }

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_summary_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MySummaryRecyclerViewAdapter(DummyContent.ITEMS, listener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                SummaryFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
