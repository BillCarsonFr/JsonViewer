package org.billcarsonfr.jsonviewer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
internal abstract class ValueItem : EpoxyModelWithHolder<ValueItem.Holder>() {

    @EpoxyAttribute
    var text: SafeCharSequence? = null

    @EpoxyAttribute
    var depth: Int = 0

    @EpoxyAttribute
    var copyValue: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textView.text = text?.charSequence
        holder.baseView.setPadding(Utils.dpToPx(16 * depth, holder.baseView.context), 0, 0, 0)
        itemClickListener?.let { holder.baseView.setOnClickListener(it) }
        holder.copyValue = copyValue
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.baseView.setOnClickListener(null)
        holder.copyValue = null
    }

    class Holder : EpoxyHolder(), View.OnCreateContextMenuListener {

        lateinit var textView: TextView
        lateinit var baseView: LinearLayout
        var copyValue: String? = null

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

            if (copyValue != null) {
                val menuItem = menu?.add(
                    Menu.NONE, R.id.copy_value,
                    Menu.NONE, R.string.copy_value
                )
                val clipService =
                    v?.context?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                menuItem?.setOnMenuItemClickListener {
                    clipService?.setPrimaryClip(ClipData.newPlainText("", copyValue))
                    true
                }
            }
        }
    }
}