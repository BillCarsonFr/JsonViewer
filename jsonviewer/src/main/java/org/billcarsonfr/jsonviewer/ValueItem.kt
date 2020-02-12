package org.billcarsonfr.jsonviewer

import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@EpoxyModelClass(layout = R2.layout.item_jv_base_value)
abstract class ValueItem : EpoxyModelWithHolder<ValueItem.Holder>() {

    @EpoxyAttribute
    var text: CharSequence? = null

    @EpoxyAttribute
    var depth: Int = 0

    @EpoxyAttribute
    var itemClickListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textView.text = text
        holder.baseView.setPadding(Utils.dpToPx(16 * depth, holder.baseView.context), 0, 0, 0)
        itemClickListener?.let { holder.baseView.setOnClickListener(it) }
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.baseView.setOnClickListener(null)
    }

    class Holder : EpoxyHolder(), View.OnCreateContextMenuListener {

        lateinit var textView: TextView
        lateinit var baseView: LinearLayout

        override fun bindView(itemView: View) {
            baseView = itemView.findViewById(R.id.jvBaseLayout)
            textView = itemView.findViewById(R.id.jvValueText)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {

            menu?.add(
                Menu.NONE, R.id.copy_value,
                Menu.NONE, R.string.copy_value
            );
        }
    }
}