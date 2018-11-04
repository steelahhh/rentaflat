package io.github.steelahhh.rent.model

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.github.steelahhh.rent.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

data class FlatItem(
    val id: Int = 0,
    val image: String = "",
    val address: String = "",
    val area: Double = 0.0,
    val price: Double = 0.0
) : AbstractItem<FlatItem, FlatItem.VH>() {
    override fun getType(): Int = R.id.item_flat_id

    override fun getViewHolder(v: View): VH = VH(v)

    override fun getLayoutRes(): Int = R.layout.item_flat

    class VH(view: View) : FastAdapter.ViewHolder<FlatItem>(view) {

        var image: AppCompatImageView = view.findViewById(R.id.image)
        var address: AppCompatTextView = view.findViewById(R.id.address)
        var price: AppCompatTextView = view.findViewById(R.id.price)
        var area: AppCompatTextView = view.findViewById(R.id.area)

        override fun bindView(item: FlatItem, payloads: MutableList<Any>) {
            Glide.with(itemView)
                .load(item.image)
                .apply(RequestOptions().centerCrop())
                .into(image)
            address.text = itemView.context.getString(R.string.flat_address, item.address)
            price.text = itemView.context.getString(
                R.string.flat_price,
                NumberFormat.getNumberInstance(Locale.US).format(item.price)
            )
            area.text = itemView.context.getString(R.string.flat_area, item.area)
        }

        override fun unbindView(item: FlatItem) {
            address.text = null
            price.text = null
            area.text = null
            price.text = null
        }
    }
}