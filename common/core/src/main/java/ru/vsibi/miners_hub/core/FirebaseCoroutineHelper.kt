/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.miners_hub.core

//import com.google.android.gms.tasks.Task
//import kotlinx.coroutines.suspendCancellableCoroutine
//import kotlin.coroutines.cancellation.CancellationException
//import kotlin.coroutines.resumeWithException
//
//suspend fun <T> Task<T>.await(): T {
//    if (isComplete) {
//        val e = exception
//        return if (e == null) {
//            if (isCanceled) {
//                throw CancellationException(
//                    "Task $this was cancelled normally.")
//            } else {
//                result
//            }
//        } else {
//            throw e
//        }
//    }
//
//    return suspendCancellableCoroutine { cont ->
//        addOnCompleteListener {
//            val e = exception
//            if (e == null) {
//                if (isCanceled) cont.cancel() else cont.resume(result, null)
//            } else {
//                cont.resumeWithException(e)
//            }
//        }
//    }
//}