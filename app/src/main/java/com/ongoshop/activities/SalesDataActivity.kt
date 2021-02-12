package com.ongoshop.activities

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.ongoshop.R
import com.ongoshop.base.BaseActivity


class SalesDataActivity : BaseActivity() {
    var ivBack: ImageView? = null
    var severityBarChart: BarChart? = null
    var mContext: Context? = null

    override fun getContentId(): Int {
        return R.layout.activity_sales_data
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        severityBarChart = findViewById(R.id.chart1)
        initializeBarChart()

        ivBack!!.setOnClickListener {
            finish() }
    }

    private fun initializeBarChart() {
        var NoOfEmp = ArrayList<BarEntry>()

        NoOfEmp.add(BarEntry(945f, 0f))
        NoOfEmp.add(BarEntry(1040f, 1f))
        NoOfEmp.add(BarEntry(1133f, 2f))
        NoOfEmp.add(BarEntry(1240f, 3f))
        NoOfEmp.add(BarEntry(1369f, 4f))
        NoOfEmp.add(BarEntry(1487f, 5f))
        NoOfEmp.add(BarEntry(1501f, 6f))
        NoOfEmp.add(BarEntry(1645f, 7f))
        NoOfEmp.add(BarEntry(1578f, 8f))
        NoOfEmp.add(BarEntry(1695f, 9f))

        val year = ArrayList<String>()

        year.add("2008")
        year.add("2009")
        year.add("2010")
        year.add("2011")
        year.add("2012")
        year.add("2013")
        year.add("2014")
        year.add("2015")
        year.add("2016")
        year.add("2017")

        val bardataset = BarDataSet(NoOfEmp!!, "No Of Employee")
        severityBarChart!!.animateY(5000)
        val data = BarData(bardataset)
        bardataset.setColors(*ColorTemplate.COLORFUL_COLORS)
        severityBarChart!!.setData(data)
    }
}