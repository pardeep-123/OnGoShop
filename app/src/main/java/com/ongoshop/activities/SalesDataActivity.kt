package com.ongoshop.activities

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.GraphDetailResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.HomeViewModel
import java.util.*
import kotlin.collections.ArrayList


class SalesDataActivity : BaseActivity(), Observer<RestObservable> {
    var ivBack: ImageView? = null
    var fromdatetv: TextView? = null
    var btngo: Button? = null
    var todatetv: TextView? = null
    var earningstv: TextView? = null
    var severityBarChart: BarChart? = null
    var mContext: Context? = null

    var amount = 0
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    override fun getContentId(): Int {
        return R.layout.activity_sales_data
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        btngo = findViewById(R.id.btngograph)
        ivBack = findViewById(R.id.ivBack)
        fromdatetv = findViewById(R.id.fromdatetv)
        todatetv = findViewById(R.id.todatetv)
        earningstv = findViewById(R.id.earningtv)
        severityBarChart = findViewById(R.id.chart1)


        ivBack!!.setOnClickListener {
            finish() }

        fromdatetv?.run {
            this.setOnClickListener {

                cal(fromdatetv!!)
            }
        }
                todatetv?.run {
            this.setOnClickListener {
                cal(todatetv!!)


            }
        }
        btngo?.setOnClickListener {

                if (fromdatetv?.text!!.toString().isEmpty() || todatetv?.text!!.toString().isEmpty())
                {
                    showErrorToast(this,"Please select both dates")
                }
                else if (CommonMethods.CHANGEDATEFORMAT(fromdatetv?.text.toString()).after(CommonMethods.CHANGEDATEFORMAT(todatetv?.text.toString())))
                {
                    showErrorToast(this,"Please select correct dates")
                }
                else
                {
                    val map = HashMap<String, String>()
                    /*map.put("fromDate", "21-01-2020")
                    map.put("toDate", "10-12-2020")*/
                     map.put("fromDate", fromdatetv?.text.toString())
                     map.put("toDate", todatetv!!.text.toString())
                    viewModel.getgraphdetailapi(this, true, map)
                    viewModel.mResponse.observe(this, this)
                }
        }
    }

   var cal : (TextView)->Unit =
    {
        val newCalendar = Calendar.getInstance()
        val StartTime = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate[year, monthOfYear] = dayOfMonth

            it.setText(CommonMethods.CHANGEDATEFORMAT(newDate.time))

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
        StartTime.show()

    }



    fun setbargraph(barentrydata : ArrayList<BarEntry>,labels : ArrayList<String>)
    {
        val bardataset = BarDataSet(barentrydata, "Cells")

        val data = BarData(bardataset)
        severityBarChart?.data = data
        severityBarChart!!.setVisibleXRangeMaximum(8f)// set the data and list of labels into chart
         severityBarChart!!.description.isEnabled = false
        //  severityBarChart!!.setScaleMinima(1.5f,1f)
        val bottomAxis: XAxis = severityBarChart?.getXAxis()!!
        bottomAxis.position = XAxis.XAxisPosition.BOTTOM
        bottomAxis.valueFormatter = IndexAxisValueFormatter(labels)
        bottomAxis.labelCount = labels.size
        earningstv?.setText("$$amount")
        bottomAxis.granularity = 1f
        severityBarChart?.invalidate();
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is GraphDetailResponse) {
                    amount = 0
                    val graphDetailResponse: GraphDetailResponse = it.data
                    if (graphDetailResponse.code == Constants.success_code) {
                        if (graphDetailResponse.body!!.amount!!.isEmpty())
                        showErrorToast(this!!, "No data available for these dates")
                        else{
                            var barentry = ArrayList<BarEntry>()
                            val labels = ArrayList<String>()
                            for (i in 0 until graphDetailResponse.body!!.dates!!.size)
                            {
                                amount += graphDetailResponse.body!!.amount!![i]!!
                                barentry.add(BarEntry(i.toFloat(),graphDetailResponse.body.amount!![i]!!.toFloat()))
                                labels.add(graphDetailResponse.body.dates!![i]!!)
                            }
                            setbargraph(barentry,labels)
                        }


                    } else {
                        CommonMethods.AlertErrorMessage(this, graphDetailResponse.message)
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


}