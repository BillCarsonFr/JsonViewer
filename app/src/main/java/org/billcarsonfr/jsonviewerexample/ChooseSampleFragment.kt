package org.billcarsonfr.jsonviewerexample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

/**
 * Activities that contain this fragment must implement the
 * [ChooseSampleFragment.OnFragmentMainNavigationListener] interface
 * to handle interaction events.
 * Use the [ChooseSampleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseSampleFragment : Fragment() {
    private var mListener: OnFragmentMainNavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_sample, container, false).also { view ->
            view.findViewById<Button>(R.id.showWrappedViewer)?.let {
                it.setOnClickListener {
                    mListener?.navigateToWrapSample(true)
                }

                view.findViewById<Button>(R.id.showWrappedViewerScroll)?.let {
                    it.setOnClickListener {
                        mListener?.navigateToWrapSample(false)
                    }
                }

                view.findViewById<Button>(R.id.showAsBottomSheet)?.let {
                    it.setOnClickListener {
                        mListener?.navigateToDialog()
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentMainNavigationListener) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentMainNavigationListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentMainNavigationListener {
        fun navigateToWrapSample(wrap: Boolean)
        fun navigateToDialog()
    }

    companion object {
        fun newInstance(): ChooseSampleFragment {
            return ChooseSampleFragment()
        }
    }
}
