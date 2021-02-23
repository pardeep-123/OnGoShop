package com.ongoshop.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.ManageCategoryActivity
import com.ongoshop.activities.ProductActivity
import com.ongoshop.activities.SubCategoriesActivity
import com.ongoshop.adapter.CategoryAdapter
import com.ongoshop.adapter.SubCategoryAdapter
import com.ongoshop.base.BaseFragment
import com.ongoshop.clickListeners.CategoryClick
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.CategoryListResponse
import com.ongoshop.pojo.SubCategoryListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.AuthViewModel

class CategoriesFragment : BaseFragment(), Observer<RestObservable>, CategoryClick {

    var v: View? = null
    var mContext: Context? = null

    private lateinit var  categoryAdapter: CategoryAdapter
    private lateinit var  subCategoryAdapter: SubCategoryAdapter
    lateinit var  rvCategory: RecyclerView
    private var btnCategories: Button? = null
    private var categoryId=""
    private var categoryName=""
    private var pos=0
    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_categories, container, false)
        mContext = activity

        rvCategory = v!!.findViewById(R.id.rv_category)
        btnCategories = v!!.findViewById(R.id.btnCategories)
        btnCategories!!.setOnClickListener(View.OnClickListener {
            val i = Intent(mContext, ManageCategoryActivity::class.java)
            startActivity(i)
        })

        getcategoriesApi()
        return v
    }


    fun setCategoryAdapter(categoryList: ArrayList<CategoryListResponse.Body>?) {
        categoryAdapter = CategoryAdapter(activity, categoryList, this)
        rvCategory.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvCategory.adapter = categoryAdapter

    }

    private fun getcategoriesApi() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            val map = HashMap<String, String>()
            map.put("searchKeyword", "")

            viewModel.categoryListApi(requireActivity(), true, map)
            viewModel.mResponse.observe(requireActivity(), this)
        }
    }

    private fun getSubcategoriesApi() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            val map = HashMap<String, String>()
            map.put("searchKeyword", "")
            map.put("categoryId", categoryId)

            viewModel.subCategoryListApi(requireActivity(), true, map)
            viewModel.mResponse.observe(requireActivity(), this)
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is CategoryListResponse) {
                    val categoryListingResponse: CategoryListResponse = it.data
                    if (categoryListingResponse.code == Constants.success_code) {
                        showSuccessToast(categoryListingResponse!!.getMessage()!!)

                        setCategoryAdapter(categoryListingResponse.getBody())

                    } else {
                        CommonMethods.AlertErrorMessage(
                                activity,
                                categoryListingResponse.getMessage()
                        )
                    }
                }

                if (it.data is SubCategoryListResponse) {
                    val subCategoryListResponse: SubCategoryListResponse = it.data
                    if (subCategoryListResponse.code == Constants.success_code) {
                        showSuccessToast(subCategoryListResponse!!.getMessage()!!)

                        if (subCategoryListResponse.body.size == 0) {
                            val i = Intent(context, ProductActivity::class.java)
                            i.putExtra("categoryId", categoryId)
                            i.putExtra("categoryName", categoryName)
                            requireActivity().startActivity(i)
                        } else {
                            val i = Intent(context, SubCategoriesActivity::class.java)
                            i.putParcelableArrayListExtra("categoryList", subCategoryListResponse.body)
                            i.putExtra("categoryName", categoryName)
                            requireActivity().startActivity(i)
                        }

                    } else {
                        CommonMethods.AlertErrorMessage(
                                activity,
                                subCategoryListResponse.getMessage()
                        )
                    }
                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    showAlerterRed(it.data as String)
                } else {
                    showAlerterRed(it.error!!.toString())
                }
            }
            it.status == Status.LOADING -> {

            }
        }
    }

    override fun categoryClickk(position: Int, id: String, name: String, listSize: Int) {
        categoryId= id
        categoryName= name
      //  pos= position
        getSubcategoriesApi()
    }
}