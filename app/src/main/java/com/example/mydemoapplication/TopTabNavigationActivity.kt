package com.example.mydemoapplication

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.tab.LeanbackTabLayout
import androidx.leanback.tab.LeanbackViewPager
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewSelectedListener
import com.example.mydemoapplication.customView.CustomCardPresenter
import com.example.mydemoapplication.Model.Data
import com.example.mydemoapplication.Model.Item
import com.example.mydemoapplication.Model.MovieList
import com.example.mydemoapplication.Model.SubCategory
import com.google.gson.Gson


class TopTabNavigationActivity : FragmentActivity() {

    private val TAG: String = javaClass.simpleName
    private var mMovieList: MovieList? = null
    private var mMovieListData: List<Data>? = null
    private var mViewPagerAdapter: TopTabViewPagerAdapter? = null
    lateinit var viewPager: LeanbackViewPager
    lateinit var leanbackTabLayout: LeanbackTabLayout

    lateinit var rowsSupportFragment: RowsSupportFragment
    lateinit var rowsAdapter: ArrayObjectAdapter
    lateinit var itemObj: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_tab_navigation)
        viewPager = findViewById(R.id.view_pager)
        leanbackTabLayout = findViewById(R.id.tab_layout)
        processData()
        processView()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (event.repeatCount == 0) { // Check if the key event is not a repeat event
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_UP -> {
                    if (::itemObj.isInitialized) {
                        println("It is Initialized: ${itemObj.name}")

                        if (itemObj.status == false) {
                            viewPager.clearFocus()
                            leanbackTabLayout.requestFocus()
                            return true
                        }
                    }

                    return false
                }


            }
        }
        return super.onKeyDown(keyCode, event)
    }


    fun processData() {
        val jsonFileString: String =
            resources.openRawResource(R.raw.movielist).bufferedReader().use { it.readText() }
        Log.d(TAG, "jsonFileString: $jsonFileString")
        mMovieList = Gson().fromJson(jsonFileString, MovieList::class.java)
        Log.i(TAG, "mMovieList: $mMovieList")
    }

    fun processView() {

        if (mMovieList != null) {
            mMovieListData = mMovieList!!.data
            if (mMovieListData != null && mMovieListData!!.isNotEmpty()) {
                mViewPagerAdapter = TopTabViewPagerAdapter(
                    supportFragmentManager,
                    1, mMovieListData
                )
            }
        }

        if (mViewPagerAdapter != null) {
            viewPager.adapter = mViewPagerAdapter
        }
        leanbackTabLayout.setupWithViewPager(viewPager)


    }

    inner class TopTabViewPagerAdapter(
        fragmentManager: FragmentManager,
        behavior: Int,
        var mMovieListData: List<Data>? = null
    ) : FragmentStatePagerAdapter(fragmentManager) {

        private val TAG: String = javaClass.simpleName

        override fun getItem(position: Int): Fragment {
            Log.v(TAG, "===== getItem =====")

            mMovieListData?.run {
                val category: Data = mMovieListData!![position]
                val subCategoryList: List<SubCategory>? = category.sub_categories
                if (!subCategoryList.isNullOrEmpty()) {
                    rowsSupportFragment = RowsSupportFragment()
                    rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
                    for ((subCategoryIndex, subCategory) in subCategoryList.withIndex()) {
                        val subCategoryName: String? = subCategory.sub_category_name
                        Log.d(TAG, "subCategoryName: $subCategoryName")
                        val items: List<Item>? = subCategory.items
                        val listRowAdapter: ArrayObjectAdapter =
                            ArrayObjectAdapter(CustomCardPresenter())
                        if (!items.isNullOrEmpty()) {
                            for (item in items) {
                                Log.i(TAG, "movieName: ${item.name}")
                                listRowAdapter.add(item)
                            }
                            val header: HeaderItem = HeaderItem(0, subCategoryName)
                            rowsAdapter.add(ListRow(header, listRowAdapter))
                        }
                    }
                    rowsSupportFragment.adapter = rowsAdapter

                    rowsSupportFragment.onItemViewSelectedListener =
                        OnItemViewSelectedListener { itemViewHolder, item, rowViewHolder, row ->
                            if (itemViewHolder != null) {
                                println("Itemviewholder: " + itemViewHolder.view.id)
                            }
                            println("item: $item")
                            if (item is Item) {
                                println("Initialized")
                                itemObj = item
                            }
                            println("RowViewHolder: ${rowViewHolder.row.id}")
                            println("Rows : ${row.id}")
                            // Do something with the focused child position

                        }
                    return rowsSupportFragment
                }
            }
            return RowsSupportFragment()
        }

        override fun getCount(): Int {
            Log.v(TAG, "===== getCount =====")
            return if (mMovieListData != null) mMovieListData!!.size else 0
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val category: Data = mMovieListData!![position]
            val categoryName: String? = category.category_name
            Log.w(TAG, "categoryName: $categoryName")
            return categoryName
        }
    }


}