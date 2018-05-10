package xyz.hanks.lib

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.util.SparseArrayCompat

/**
 * convert {@link Activity#startActivityForResult(Intent, int, Bundle)} to callback
 * Created by hanks on 18-5-10.
 */
class ActivityResultManager(val activity: Activity) {

    private val TAG_FRAGMENT = "InnerActivityResultFragment"
    private var innerFragment: InnerActivityResultFragment? = null

    init {
        val findFragmentByTag = activity.fragmentManager.findFragmentByTag(TAG_FRAGMENT)
        if (findFragmentByTag != null && findFragmentByTag is InnerActivityResultFragment) {
            innerFragment = findFragmentByTag
        }
        if (innerFragment == null) {
            innerFragment = InnerActivityResultFragment()
            activity.fragmentManager.beginTransaction().add(innerFragment, TAG_FRAGMENT).commit()
            activity.fragmentManager.executePendingTransactions()
        }
    }

    fun startActivityForResult(intent: Intent, callBack: CallBack) {
        innerFragment?.startForResult(intent, callBack)
    }
}

typealias CallBack = (Int, Intent?) -> Unit

class InnerActivityResultFragment : Fragment() {
    private val mCallbacks = SparseArrayCompat<CallBack>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        mCallbacks.clear()
    }

    fun startForResult(intent: Intent, callBack: CallBack) {
        mCallbacks.put(callBack.hashCode(), callBack)
        startActivityForResult(intent, callBack.hashCode())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val callback = mCallbacks.get(requestCode)
        callback?.invoke(resultCode, data)
        mCallbacks.remove(requestCode)
    }
}
