package org.billcarsonfr.jsonviewer

/**
 * Wrapper for a CharSequence, which support mutation of the CharSequence, which can happen during rendering
 */
internal class SafeCharSequence(val charSequence: CharSequence) {
    private val hash = charSequence.toString().hashCode()

    override fun hashCode() = hash
    override fun equals(other: Any?) = other is SafeCharSequence && other.hash == hash
}

internal fun CharSequence.toSafeCharSequence() = SafeCharSequence(this)