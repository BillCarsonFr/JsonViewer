package org.billcarsonfr.jsonviewer

import android.content.Context
import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import me.gujun.android.span.Span
import me.gujun.android.span.image
import me.gujun.android.span.span

class JSonViewerEpoxyController(private val context: Context) :
    TypedEpoxyController<JSonViewerState>() {

    var styleProvider: JSonViewerStyleProvider = JSonViewerStyleProvider.default(context)

    fun setStyle(styleProvider: JSonViewerStyleProvider?) {
        this.styleProvider = styleProvider ?: JSonViewerStyleProvider.default(context)
    }

    override fun buildModels(data: JSonViewerState?) {
        val async = data?.root ?: return

        when (async) {
            is Fail -> {
                valueItem {
                    id("fail")
                    text(async.error.localizedMessage)
                }
            }
            is Success -> {
                val model = data.root.invoke()

                model?.let {
                    buildRec(it, 0, "")
                }

            }
        }
    }

    private fun buildRec(
        model: JSonViewerModel,
        depth: Int,
        idBase: String
    ) {
        val id = "$idBase/${model.key ?: model.index}_${model.isExpanded}}"
        when (model) {
            is JSonViewerObject -> {
                if (model.isExpanded) {
                    open(id, model.key, model.index, depth, true, model)
                    model.keys.forEach {
                        buildRec(it.value, depth + 1, id)
                    }
                    close(id, depth, true)
                } else {
                    valueItem {
                        id(id+"_sum")
                        depth(depth)
                        text(
                            span {
                                if (model.key != null) {
                                    span("\"${model.key}\"") {
                                        textColor = styleProvider.keyColor
                                    }
                                    span(" : ") {
                                        textColor = styleProvider.baseColor
                                    }
                                }
                                if (model.index != null) {
                                    span("${model.index}") {
                                        textColor = styleProvider.secondaryColor
                                    }
                                    span(" : ") {
                                        textColor = styleProvider.baseColor
                                    }
                                }
                                span {
                                    +"{+${model.keys.size}}"
                                    textColor = styleProvider.baseColor
                                }
                            }
                        )
                        itemClickListener(View.OnClickListener { itemClicked(model) })
                    }
                }
            }
            is JSonViewerArray -> {
                if (model.isExpanded) {
                    open(id, model.key, model.index, depth, false, model)
                    model.items.forEach {
                        buildRec(it, depth + 1, id)
                    }
                    close(id, depth, false)
                } else {
                    valueItem {
                        id(id+"_sum")
                        depth(depth)
                        text(
                            span {
                                if (model.key != null) {
                                    span("\"${model.key}\"") {
                                        textColor = styleProvider.keyColor
                                    }
                                    span(" : ") {
                                        textColor = styleProvider.baseColor
                                    }
                                }
                                if (model.index != null) {
                                    span("${model.index}") {
                                        textColor = styleProvider.secondaryColor
                                    }
                                    span(" : ") {
                                        textColor = styleProvider.baseColor
                                    }
                                }
                                span {
                                    +"[+${model.items.size}]"
                                    textColor = styleProvider.baseColor
                                }
                            }
                        )
                        itemClickListener(View.OnClickListener { itemClicked(model) })
                    }
                }
            }
            is JSonViewerLeaf -> {
                valueItem {
                    id(id)
                    depth(depth)
                    text(
                        span {
                            if (model.key != null) {
                                span("\"${model.key}\"") {
                                    textColor = styleProvider.keyColor
                                }
                                span(" : ") {
                                    textColor = styleProvider.baseColor
                                }
                            }

                            if (model.index != null) {
                                span("${model.index}") {
                                    textColor = styleProvider.secondaryColor
                                }
                                span(" : ") {
                                    textColor = styleProvider.baseColor
                                }
                            }
                            append(valueToSpan(model))
                        }
                    )
                }
            }
        }
    }

    fun valueToSpan(leaf: JSonViewerLeaf): Span {
        return when (leaf.type) {
            JSONType.STRING -> {
                span("\"${leaf.stringRes}\"") {
                    textColor = styleProvider.stringColor
                }
            }
            JSONType.NUMBER -> {
                span("${leaf.stringRes}") {
                    textColor = styleProvider.numberColor
                }
            }
            JSONType.BOOLEAN -> {
                span("${leaf.stringRes}") {
                    textColor = styleProvider.booleanColor
                }
            }
            JSONType.NULL -> {
                span("null") {
                    textColor = styleProvider.booleanColor
                }
            }
        }
    }

    private fun open(
        id: String,
        key: String?,
        index: Int?,
        depth: Int,
        isObject: Boolean = true,
        composed: JSonViewerModel
    ) {
        valueItem {
            id("${id}_Open")
            depth(depth)
            text(
                span {
                    if (key != null) {
                        span("\"$key\"") {
                            textColor = styleProvider.keyColor
                        }
                        span(" : ") {
                            textColor = styleProvider.baseColor
                        }
                    }
                    if (index != null) {
                        span("$index") {
                            textColor = styleProvider.secondaryColor
                        }
                        span(" : ") {
                            textColor = styleProvider.baseColor
                        }
                    }
                    span("- ") {
                        textColor = styleProvider.secondaryColor
                    }
                    span("{".takeIf { isObject } ?: "[") {
                        textColor = styleProvider.baseColor
                    }
                }
            )
            itemClickListener(View.OnClickListener { itemClicked(composed) })
        }

    }

    private fun itemClicked(model: JSonViewerModel) {
        model.isExpanded = !model.isExpanded
        setData(currentData)
    }

    private fun close(id: String, depth: Int, isObject: Boolean = true) {
        valueItem {
            id("${id}_Close")
            depth(depth)
            text(
                span {
                    text = "}".takeIf { isObject } ?: "]"
                    textColor = styleProvider.baseColor
                })
        }
    }
}