package com.demos.yxn.lifecircle.Activity

import android.content.Context
import org.jetbrains.anko.startActivity

/**
 * Created by YXN on 2018/6/20.
 */
public class SkipUtils() {


    public fun skipToA(context: Context) {
        context.startActivity<AActivity>()
    }

    public fun skipToB(context: Context) {
        context.startActivity<BActivity>()
    }

    public fun skipToC(context: Context) {
        context.startActivity<CActivity>()
    }

    public fun skipToD(context: Context) {
        context.startActivity<DActivity>()
    }


}