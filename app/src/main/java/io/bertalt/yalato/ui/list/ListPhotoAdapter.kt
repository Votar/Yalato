package io.bertalt.yalato.ui.list

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.bertalt.yalato.R
import io.bertalt.yalato.api.response.PhotoRest

class ListPhotoAdapter(private val onClickListener: OnItemClickListener) : RecyclerView.Adapter<ViewHolder>() {

    interface OnItemClickListener {
        fun photoClicked(photo: PhotoRest)
    }

    private val dataset: MutableList<PhotoRest> = mutableListOf()
    private val imageTransition = DrawableTransitionOptions.withCrossFade()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val nextItem = dataset[position]
        val ratio = nextItem.width.toFloat() / nextItem.height.toFloat()
        val finalHeight = (holder.itemView.resources.displayMetrics.widthPixels / ratio).toInt()
        holder.image.minimumHeight = finalHeight

        holder.author.text = "${nextItem.user.last_name} ${nextItem.user.first_name}"
        Glide.with(holder.itemView.context)
                .load(nextItem.urls.regular)
                .transition(imageTransition)
                .into(holder.image)

        holder.itemView.setOnClickListener { onClickListener.photoClicked(nextItem) }
    }

    override fun getItemCount(): Int = dataset.size

    fun swapData(newList: List<PhotoRest>) {
        if (dataset.isEmpty()) {
            dataset.addAll(newList)
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return dataset.size
                }

                override fun getNewListSize(): Int {
                    return newList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = dataset[oldItemPosition]
                    val newest = newList[newItemPosition]
                    return old.id.equals(newest.id, true)
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = dataset[oldItemPosition]
                    val newest = newList[newItemPosition]
                    return old.urls.regular.equals(newest.urls.regular, true)
                }
            })
            dataset.clear()
            dataset.addAll(newList)
            diffResult.dispatchUpdatesTo(this)
        }
    }
}

class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    val image: ImageView = itemView.findViewById(R.id.item_image_img)
    val author: TextView = itemView.findViewById(R.id.item_image_author)
}