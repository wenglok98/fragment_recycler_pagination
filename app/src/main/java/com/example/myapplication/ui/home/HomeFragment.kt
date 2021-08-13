package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.FAQ
import com.example.myapplication.ui.adapter.FAQRecyclerAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private var pagination_query = 1 //First parameter for query
    private var isLoading = false // set loading status for pagination, in order to avoid keep calling api

    private var faqlist = ArrayList<FAQ>() // The array list of your object
    private lateinit var faqAdapter: FAQRecyclerAdapter // Late init the adapter for recyclerview


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Start recyclerView
        generateFakeData() // API CALL TO ADD DATA INTO LIST
        initRecyclerView() //FUnction to init recyclerview
        faqAdapter.submitList(faqlist) // Can put in here or inside api calling when api done
        initScrollListener() // Pagination Call

        //End recyclerView

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            faqAdapter = FAQRecyclerAdapter()
            adapter = faqAdapter


        }
    }

    private fun generateFakeData() {

        val tempMax = pagination_query + 30
        for (i in pagination_query..tempMax) {
            val faqtemp = FAQ(
                "the first title is ${i.toString()}",
                "body for ${i.toString()} sadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdf"
            )
            faqlist.add(faqtemp)

        }
        pagination_query = tempMax
    }

    private fun initScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                        (faqlist.size - 1)
                    ) {
                        //bottom of list!
                        isLoading = true

                        loadMore() //Call more data
                    }
                }
            }
        })
    }

    private fun loadMore() {


        val tempMax = pagination_query + 30
        for (i in pagination_query..tempMax) {
            val faqtemp = FAQ(
                "the first title is ${i.toString()}",
                "body for ${i.toString()} sadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdfsadfasdfasdfasdfassdf"
            )
            faqlist.add(faqtemp)

        }

        pagination_query = tempMax


        faqAdapter.notifyDataSetChanged() //Refresh adapter

        Log.e("tagg", "load more")
        isLoading = false // After load data, set back is loading to false in order to make available to load new data
    }
}