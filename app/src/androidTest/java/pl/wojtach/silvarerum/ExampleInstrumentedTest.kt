package pl.wojtach.silvarerum

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("pl.wojtach.silvarerum", appContext.packageName)
    }

    @Test
    fun coroutinesOnAndroid() {
        // Context of the app under test.

        runBlocking {
            val CEH = CoroutineExceptionHandler { _, throwable -> println("catched $throwable") }

            val scope = CoroutineScope(Dispatchers.Default + SupervisorJob(coroutineContext[Job]) + CEH)

            scope.cancel()

            val job = scope.launch {
                val j1 = this@launch.launch {
                    println("j1 start working in $coroutineContext")
                    delay(1000)
                    println("j1 end working")
                }
                val j2 = launch() {
                    println("j2 start working in $coroutineContext")
                    delay(500)
                    println("j3 end working in $coroutineContext")
//                    throw NullPointerException("boom j2 in $coroutineContext")
                }

//                val j3 = GlobalScope.launch(CEH) {
//                    delay(500)
//                    throw NullPointerException("boom j3 in $coroutineContext")
//                }

            }
            job.cancel()

            val flowJob = flow { emit(1); delay(10_000); emit(2) }.onEach { println(it) }.launchIn(scope)

//            val j4 = launch(CEH) {
//                delay(500)
//                throw NullPointerException("boom j4 in $coroutineContext")
//            }

            scope.launch { delay(1000); println("Am I canceld?") }

            delay(5_000)
            scope.cancel()
        }
    }
}
