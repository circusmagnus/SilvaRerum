package pl.wojtach.silvarerum

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class LazyAsync<in R, out T>(private val createInstance: suspend (R) -> T) {

    private var instance: T? = null
    private val mutex = Mutex()

    suspend fun getInstance(param: R) =
        instance ?: mutex.withLock {
            instance ?: createInstance(param).also { instance = it }
        }
}