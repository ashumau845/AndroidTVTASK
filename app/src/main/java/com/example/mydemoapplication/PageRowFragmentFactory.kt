package com.example.mydemoapplication

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.example.mydemoapplication.customView.CustomCardPresenter
import com.example.mydemoapplication.customView.CustomHeaderItem
import com.example.mydemoapplication.customView.CustomListRowPresenter
import com.example.mydemoapplication.Model.Data
import com.example.mydemoapplication.Model.Item
import com.example.mydemoapplication.Model.SubCategory
import com.example.mydemoapplication.Model.BannerData

class PageRowFragmentFactory(var mContext: Context? = null): BrowseSupportFragment.FragmentFactory<Fragment?>() {

    private val TAG: String = javaClass.simpleName

    override fun createFragment(row: Any?): Fragment? {

        val row: Row = row as Row
        val headerItem: CustomHeaderItem = row.headerItem as CustomHeaderItem
        val item: Any? = headerItem.item
        item?.run {
            when(this){
                is Data -> {
                    val subCategoryList: List<SubCategory>? = sub_categories
                    if(!subCategoryList.isNullOrEmpty()){
                        val rowsSupportFragment: RowsSupportFragment = RowsSupportFragment()
                        val rowsAdapter: ArrayObjectAdapter = ArrayObjectAdapter(
                            CustomListRowPresenter(
                                mContext,
                                FocusType.START,
                                focusHightlightMode = FocusHighlight.ZOOM_FACTOR_XSMALL
                            )
                        )
                        for ((subCategoryIndex, subCategory) in subCategoryList.withIndex()){
                            val subCategoryName: String? = subCategory.sub_category_name
                             Log.d(TAG, "subCategoryName: $subCategoryName")
                            val items: List<Item>? = subCategory.items
                            val listRowAdapter: ArrayObjectAdapter = ArrayObjectAdapter(
                                CustomCardPresenter()
                            )
                            if(!items.isNullOrEmpty()){
                                for(item in items){
                                     Log.i(TAG, "movieName: ${item.name}")
                                    listRowAdapter.add(item)
                                }
                                val header: HeaderItem = HeaderItem(subCategoryIndex.toLong(), subCategoryName)
                                rowsAdapter.add(ListRow(header, listRowAdapter))
                            }
                        }
                        rowsSupportFragment.adapter = rowsAdapter
                        return  rowsSupportFragment
                    }
                }
                is BannerData -> {
                    val rowsSupportFragment: RowsSupportFragment = RowsSupportFragment()
                    val rowsAdapter: ArrayObjectAdapter = ArrayObjectAdapter(
                        CustomListRowPresenter(
                            mContext,
                            isBanner = true
                    )
                    )
                    val listRowAdapter: ArrayObjectAdapter = ArrayObjectAdapter(
                        CustomBannerCardPresenter()
                    )
                    val items: List<String>? = banner_list
                    if(!items.isNullOrEmpty()){
                        for(item in items){
                             Log.i(TAG, "imageUrl: $item")
                            listRowAdapter.add(item)
                        }
                        val header: HeaderItem = HeaderItem(0, "")
                        rowsAdapter.add(ListRow(header, listRowAdapter))
                        rowsSupportFragment.adapter = rowsAdapter
                        return  rowsSupportFragment
                    }
                }
            }
        }
        return null

    }

}