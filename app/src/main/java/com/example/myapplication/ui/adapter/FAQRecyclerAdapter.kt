package com.example.myapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.FAQ
import com.ms.square.android.expandabletextview.ExpandableTextView
import android.view.animation.TranslateAnimation


class FAQRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<FAQ> = ArrayList()

    lateinit var ct: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        ct = parent.context
        return FAQViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)

        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FAQViewHolder -> {

                holder.bind(items[position])

                // anim and anim2 is for animation, can change the value from res -> anim -> slide down or slde up
                val anim =
                    AnimationUtils.loadAnimation(ct.applicationContext, R.anim.slide_down)
                val anim2 = AnimationUtils.loadAnimation(ct.applicationContext, R.anim.slide_up)

                holder.faq_title.setOnClickListener {


                    val layoutll = holder.ll

                    if (layoutll.visibility == View.VISIBLE) {
                        holder.ll.visibility = View.GONE
                        holder.ll.startAnimation(anim2) // is just for animation,  not compulsory, can remove
                    } else if (layoutll.visibility == View.GONE) {
                        holder.ll.visibility = View.VISIBLE
                        holder.ll.startAnimation(anim)//is just for animation, not compulsory, can remove
                    }

                }

//Click listener on body, click to close
                holder.ll.setOnClickListener {
                    if (it.visibility == View.VISIBLE) {
                        holder.ll.visibility = View.GONE
//                        holder.ll.startAnimation(anim2) // is just for animation,  not compulsory, can remove
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(faqList: List<FAQ>) {
        items = faqList
    }

    class FAQViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val faq_title: TextView = itemView.findViewById<TextView>(R.id.tvTitle)
        val faq_body: TextView = itemView.findViewById<TextView>(R.id.body_tv)

        val ll: LinearLayout = itemView.findViewById<LinearLayout>(R.id.ll)
        fun bind(faq: FAQ) {
            faq_title.text = faq.title

            faq_body.text = faq.body

        }
    }

}
