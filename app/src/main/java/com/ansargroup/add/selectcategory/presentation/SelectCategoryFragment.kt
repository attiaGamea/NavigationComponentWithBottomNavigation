package com.ansargroup.add.selectcategory.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ansargroup.R
import com.ansargroup.add.generalinformation.presentation.AddGeneralInformationFragment
import com.ansargroup.add.generalinformation.presentation.AddGeneralInformationFragmentArgs
import com.ansargroup.add.selectcategory.data.Category
import com.multilevel.treelist.Node
import kotlinx.android.synthetic.main.fragment_select_category.*
import kotlinx.android.synthetic.main.loading_white.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SelectCategoryFragment : Fragment(R.layout.fragment_select_category) {

    private lateinit var navController: NavController

    private val viewModel: SelectCategoryViewModel by viewModel()
    private var mDatas: MutableList<Node<Any, Any>> = mutableListOf()

    private var mAdapter: CategoriesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setViewModelStateListener()
        viewModel.getCategories()
    }

    private fun setViewModelStateListener() {
        viewModel.loadingStatusLiveData.observe(viewLifecycleOwner) { isVisible ->
            transparentLoadingView.visibility = if (isVisible) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.contentStatusData.observe(viewLifecycleOwner) { isVisible ->
            groupContent.visibility = if (isVisible) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.categoriesData.observe(viewLifecycleOwner) {
            showCategories(it)
        }

        viewModel.errorData.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                ErrorState.ConnectionError -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_internet_connection),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ErrorState.GeneralError -> {
                    if (errorState.errorMessage.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), errorState.errorMessage, Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.some_thing_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun showCategories(categoriesList: List<Category>) {
        mDatas.clear()

        for (category in categoriesList) {
            mDatas.add(
                Node(
                    category.id,
                    ""
                    /** As its main categories, So no parent id */
                    ,
                    category.title
                )
            )
            addSubCategories(category)
        }

        mAdapter = CategoriesAdapter(
            mDatas,
            3,
            R.drawable.ic_baseline_keyboard_arrow_right_24,
            R.drawable.ic_baseline_keyboard_arrow_down_24
        )
        mAdapter?.setOnTreeNodeClickListener { node, _ ->
            if (node.isLeaf) {
                onLeafNodeClicked(node)
            }
        }
        recyclerViewCategories.adapter = mAdapter
    }

    private fun addSubCategories(category: Category) {
        if (category.subCategories.isNullOrEmpty().not()) {
            for (subcategory in category.subCategories!!) {
                mDatas.add(
                    Node(
                        subcategory.id,
                        category.id,
                        subcategory.title
                    )
                )
                addSubCategories(subcategory)
            }
        }
    }

    private fun onLeafNodeClicked(node: Node<*, *>) {
        val parentId =
            if (node.parent != null) node.parent.id.toString() else ""
        val id = node.id.toString()

        val bundle = Bundle()
        bundle.putString(AddGeneralInformationFragment.PARENT_ID, parentId)
        bundle.putString(AddGeneralInformationFragment.ID, id)
        navController.navigate(R.id.action_add_general_information, bundle)

    }

}