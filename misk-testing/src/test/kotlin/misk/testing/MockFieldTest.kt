package misk.testing

import misk.MiskTestingServiceModule
import misk.inject.KAbstractModule
import misk.logging.LogCollectorModule
import misk.mockito.Mockito.mock
import misk.mockito.Mockito.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import javax.inject.Inject

@MiskTest
class MockFieldTest {

  interface CountService {
    fun getAndInc(): Int
  }

  class Service @Inject constructor(val countService: CountService) {
    fun getName(): String {
      return "Name ${countService.getAndInc()}"
    }
  }

  @Inject lateinit var service: Service
  @MockAndBind @Inject lateinit var counterService: CountService

  @Test
  fun test() {
    whenever(counterService.getAndInc()).thenReturn(1313)
    assertThat(service.getName()).isEqualTo("asdf")
  }

  @MiskTestModule
  val module = object : KAbstractModule() {
    override fun configure() {
      bind<CountService>().toInstance(mock())

    }
  }

}


annotation class MockAndBind
