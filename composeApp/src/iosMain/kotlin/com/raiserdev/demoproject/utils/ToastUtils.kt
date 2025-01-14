package com.raiserdev.demoproject.utils

import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_SEC
import platform.darwin.dispatch_after
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time
import kotlin.time.times


actual fun showToast(message: String) {
    val controller: UIViewController? = UIApplication.sharedApplication.keyWindow?.rootViewController
    val alert = UIAlertController.alertControllerWithTitle(
        title = null,
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )
    controller?.presentViewController(alert, animated = true) {
        val delayInSeconds = 2.0
        val dispatchTime = dispatch_time(DISPATCH_TIME_NOW, (delayInSeconds * NSEC_PER_SEC.toDouble()).toLong())
        dispatch_after(dispatchTime, dispatch_get_main_queue()) {
            alert.dismissViewControllerAnimated(true, null)
        }
    }
}