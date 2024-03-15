package com.example.mydemoapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.example.mydemoapplication.customView.CustomHeaderItem
import com.example.mydemoapplication.Model.Data
import com.example.mydemoapplication.Model.MovieList
import com.example.mydemoapplication.Model.BannerData
import com.google.gson.Gson

class MainFragment: BrowseSupportFragment() {

    private val TAG: String = javaClass.simpleName
    private var mMovieList: MovieList? = null
    private var mMovieListData: List<Data>? = null
    private var mBannerData: BannerData? = null
    private val BANNER_ID: Long = 0x123

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBannerData()
        getMovieList()
        init()
        mainFragmentRegistry.registerFragment(PageRow::class.java, PageRowFragmentFactory(activity))

    }

    fun getBannerData(){
        Log.v(TAG, "===== getBannerData =====")
        val jsonFileString: String = resources.openRawResource(R.raw.banner).bufferedReader().use { it.readText() }
        Log.d(TAG, "jsonFileString: $jsonFileString")
        mBannerData = Gson().fromJson(jsonFileString, BannerData::class.java)
         Log.i(TAG, "mBannerData: $mBannerData")
    }

    fun getMovieList(){
        Log.v(TAG, "===== getMovieList =====")
        val jsonFileString: String = resources.openRawResource(R.raw.movielist).bufferedReader().use { it.readText() }
         Log.d(TAG, "jsonFileString: $jsonFileString")
        mMovieList = Gson().fromJson(jsonFileString, MovieList::class.java)
         Log.i(TAG, "mMovieList: $mMovieList")
    }

    fun init(){

        val mainAdapter: ArrayObjectAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = mainAdapter

        //Banner
        val header: CustomHeaderItem = CustomHeaderItem(BANNER_ID, "最新消息", mBannerData)
        val pageRow: PageRow = PageRow(header)
        mainAdapter.add(pageRow)

        if(mMovieList!=null){
            mMovieListData = mMovieList!!.data
            if(mMovieListData!=null && mMovieListData!!.isNotEmpty()){
                for((categoryIndex, category) in mMovieListData!!.withIndex()){
                    val categoryName: String? = category.category_name
                     Log.w(TAG, "categoryName: $categoryName")
                    val header: CustomHeaderItem =
                        CustomHeaderItem(
                            categoryIndex.toLong(),
                            categoryName,
                            category
                        )
                    val pageRow: PageRow = PageRow(header)
                    mainAdapter.add(pageRow)
                }
            }
        }

        if(context!=null){
            //左側 HeaderSupportFragment 的背景
            brandColor = ContextCompat.getColor(context!!, R.color.header_background)
            //右側右上方 icon
            badgeDrawable = ContextCompat.getDrawable(context!!, R.drawable.vscinemas_logo)
        }
    }
}