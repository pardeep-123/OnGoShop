package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.CategoryAdapter
import com.ongoshop.adapter.SubCategoryAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.clickListeners.CategoryClick
import com.ongoshop.pojo.CategoryListResponse
import com.ongoshop.pojo.SubCategoryListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_sub_categories.*

class SubCategoriesActivity : BaseActivity(), Observer<RestObservable>, CategoryClick {
    var ivBack: ImageView? = null
    var mContext: SubCategoriesActivity? = null
    var ll_data: RelativeLayout? = null
    private var categoryId = ""
    private var categoryName = ""
    private lateinit var subCategoryAdapter: SubCategoryAdapter
    private var categoryList: ArrayList<SubCategoryListResponse.Body> = ArrayList()
    lateinit var rvCategory: RecyclerView

    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }


    override fun getContentId(): Int {
        return R.layout.activity_sub_categories
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ll_data = findViewById(R.id.ll_data)
        rvCategory = findViewById(R.id.rv_sub_category)

        ll_data!!.setOnClickListener(View.OnClickListener {
            val i = Intent(mContext, ProductActivity::class.java)
            startActivity(i)
        })

        if (intent.extras != null) {
            categoryName= intent.getStringExtra("categoryName")!!
            categoryList = intent.getParcelableArrayListExtra<SubCategoryListResponse.Body>("categoryList")!!
            tv_title.text = categoryName

            setCategoryAdapter(categoryList)

            ivBack!!.setOnClickListener {
               onLeftIconClick()
            }

        }
    }

    fun setCategoryAdapter(categoryList: ArrayList<SubCategoryListResponse.Body>?) {
        subCategoryAdapter = SubCategoryAdapter(mContext!!, categoryList!!, this)
        rvCategory.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rvCategory.adapter = subCategoryAdapter

    }

    private fun getcategoriesApi() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            val map = HashMap<String, String>()
            map.put("searchKeyword", "")
            map.put("categoryId", categoryId)

            viewModel.subCategoryListApi(mContext!!, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is SubCategoryListResponse) {
                    val subCategoryListResponse: SubCategoryListResponse = it.data
                    if (subCategoryListResponse.code == Constants.success_code) {
               //         showSuccessToast(mContext!!, subCategoryListResponse!!.getMessage()!!)

                        if (subCategoryListResponse.getBody()!!.size == 0) {
                            /* rvCategory!!.visibility= View.GONE
                             tv_no_sub_category!!.visibility= View.VISIBLE*/

                            val i = Intent(mContext, ProductActivity::class.java)
                            i.putExtra("categoryId", categoryId)
                            i.putExtra("categoryName", categoryName)
                            startActivity(i)

                        } else {
                            val i = Intent(mContext, SubCategoriesActivity::class.java)
                            i.putParcelableArrayListExtra("categoryList", subCategoryListResponse.body)
                            i.putExtra("categoryName", categoryName)
                            startActivity(i)

                            rvCategory!!.visibility = View.VISIBLE
                            tv_no_sub_category!!.visibility = View.GONE
                            tv_title.text = categoryName
                        }
                    } else {
                        CommonMethods.AlertErrorMessage(
                                mContext,
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

    override fun categoryClickk(pos: Int, id: String, name: String, listSize: Int) {
        categoryId = id
        categoryName = name

        getcategoriesApi()
    }
}