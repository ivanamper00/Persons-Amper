package com.amper.personapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amper.personapp.R
import com.amper.personapp.data.model.PersonDto
import com.amper.personapp.databinding.FragmentProfleListBinding
import com.amper.personapp.ui.adapter.ProfileListAdapter
import com.amper.personapp.util.NoConnectivityException
import com.amper.personapp.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileListFragment(
    private val listener : ProfileListener,
) : Fragment(R.layout.fragment_profle_list),
    ProfileListAdapter.OnItemClickListener<PersonDto>,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewBinding: FragmentProfleListBinding

    private val viewModel by viewModels<ProfileViewModel>()

    private var adapter =
        ProfileListAdapter(
            listener = this,
        )

    companion object {
        fun newInstance(
            listener : ProfileListener,
        ): ProfileListFragment = ProfileListFragment(listener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentProfleListBinding.bind(view)
        setupView()
        setupListener()
    }

    private fun setupView() {
        viewBinding.swipeRefresh.setOnRefreshListener(this)
        setRecyclerView()
        onRefresh()
    }

    private fun setupListener() {
        viewModel.personState.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Error -> {
                    when(state.error) {
                        is NoConnectivityException -> {
                            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is UiState.Loading -> {
                    if(adapter.isEmpty()){
                        viewBinding.swipeRefresh.isRefreshing = state.isLoading
                    } else {
                        viewBinding.swipeRefresh.isRefreshing = false
                    }
                }
                is UiState.Success -> {
                    adapter.add(state.data)
                }
            }
        }
    }

    private fun setRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerProfile.layoutManager = linearLayoutManager
        viewBinding.recyclerProfile.adapter = adapter
        viewBinding.recyclerProfile.addOnScrollListener(
            object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(!adapter.isEmpty()){
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount -1) {
                            viewModel.getPersonsList()
                        }
                    }
                }
            }
        )
    }

    override fun onClick(item: PersonDto, position: Int) {
        listener.onProfileClick(item)
    }

    override fun onRefresh() {
        adapter.clear()
        viewModel.getPersonsList(isRefreshed = true)
    }

    interface ProfileListener {
        fun onProfileClick(profile: PersonDto)
    }

}