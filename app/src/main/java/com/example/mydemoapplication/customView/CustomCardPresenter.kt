package com.example.mydemoapplication.customView

import android.R.drawable
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mydemoapplication.Model.Item


class CustomCardPresenter: Presenter() {

    private val TAG: String = javaClass.simpleName
    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        Log.v(TAG, "===== onCreateViewHolder =====")

        mContext = parent?.context
        Log.w(TAG, "mContext is null or not???? ${mContext == null}")

        val cardView: ImageCardView = ImageCardView(mContext)
        cardView.cardType = BaseCardView.CARD_TYPE_MAIN_ONLY

        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
         Log.v(TAG, "===== onBindViewHolder =====")

        if(viewHolder!=null){
            val cardView: ImageCardView = viewHolder.view as ImageCardView
            val movieItem: Item = item as Item
            Log.i(TAG, "movieItem: $movieItem")

            cardView.titleText = movieItem.name
            cardView.contentText = movieItem.imageUrl
            cardView.setMainImageDimensions(280, 400)
            if(mContext!=null){

                val requestOptions = RequestOptions()
                requestOptions.placeholder(drawable.alert_dark_frame)
                requestOptions.error(drawable.alert_dark_frame)


                Glide.with(mContext!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(movieItem.imageUrl)
                    .into(cardView.mainImageView)
            }

        }

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        Log.v(TAG, "===== onUnbindViewHolder =====")
    }
}