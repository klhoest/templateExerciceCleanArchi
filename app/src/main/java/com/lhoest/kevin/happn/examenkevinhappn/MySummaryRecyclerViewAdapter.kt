package com.lhoest.kevin.happn.examenkevinhappn

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.lhoest.kevin.happn.examenkevinhappn.viewmodel.ForecastViewModel

import kotlinx.android.synthetic.main.fragment_summary.view.*
import com.lhoest.kevin.happn.examenkevinhappn.viewmodel.SummaryViewHolder
import androidx.recyclerview.widget.DiffUtil
import java.text.DateFormat

class MySummaryRecyclerViewAdapter(
        private val viewModel: ForecastViewModel)
    : RecyclerView.Adapter<MySummaryRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var data: MutableList<SummaryViewHolder> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as SummaryViewHolder?
            item?.let { viewModel.itemClickLiveData.value = it.dateInSec }

        }
    }

    fun setData(newData: MutableList<SummaryViewHolder>?) {
        newData?.let {
            val postDiffCallback = SummaryDiffCallback(data, it)
            val diffResult = DiffUtil.calculateDiff(postDiffCallback)
            diffResult.dispatchUpdatesTo(this)
            data = newData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_summary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.id = item.dateInSec
        holder.titleTv.text = item.title
        holder.subtitleTv.text = item.subTitle
        holder.temperature.text = item.temperature
        //it will format the date according to the device locale (android dependency)
        holder.date.text = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(item.date)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var id:Int? = null
        val titleTv: TextView = mView.title
        val subtitleTv: TextView = mView.subTitle
        val temperature: TextView = mView.temperature
        val date: TextView = mView.date

        override fun toString(): String {
            return super.toString() + " '" + subtitleTv.text + "'"
        }
    }

    //inspired of https://github.com/guenodz/livedata-recyclerview-sample/blob/master/app/src/main/java/me/guendouz/livedata_recyclerview/PostsAdapter.java
    private inner class SummaryDiffCallback(private val oldItem: List<SummaryViewHolder>, private val newPosts: List<SummaryViewHolder>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItem.size

        override fun getNewListSize(): Int = newPosts.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition].dateInSec == newPosts[newItemPosition].dateInSec
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] == newPosts[newItemPosition]
        }
    }
}
