package dk.betex.eventcollector.task

import org.junit._
import Assert._
import dk.betex._
import org.jmock._
import org.jmock.Expectations._
import org.jmock.integration.junit4._
import org.junit.runner._
import dk.betex.eventcollector.marketservice._
import java.util.Date
import IMarketService._
import org.hamcrest.Matchers._
import org.jmock.Expectations._
import org.hamcrest._
import dk.betex._
import Market._

@RunWith(value = classOf[JMock])
class EventCollectorTaskTest {

  var eventCollectorTask: EventCollectorTask = null
  val betex = new Betex()

  private val mockery = new Mockery()
  private val marketService: IMarketService = mockery.mock(classOf[IMarketService])

  private val marketEventListener = new EventCollectorTask.EventListener {

    var discoveredMarketIds: List[Long] = Nil

    def onEvents(marketId: Long, events: List[String]): Unit = {}
    override def onMarketDiscovery(marketIds: List[Long]) = { discoveredMarketIds = marketIds }
  }

  @Before
  def setUp {
    eventCollectorTask = new EventCollectorTask(betex, marketService, -60, 60, Option(3), 0, marketEventListener)
  }

  @Test
  def expired_markets_are_removed {
    val marketRunnerPrices: Map[Long, Tuple2[List[RunnerPrice], RunnerTradedVolume]] = Map(
      11l -> Tuple2(Nil, new RunnerTradedVolume(Nil)),
      12l -> Tuple2(Nil, new RunnerTradedVolume(Nil)))
    val marketRunners = MarketRunners(0, marketRunnerPrices)
    val runnerDetails = RunnerDetails(11, "runner 1") :: RunnerDetails(12, "runner 2") :: Nil
    val marketDetails = new MarketDetails(1, "market Name", "menuPath", 3, new Date(1000), runnerDetails)

    mockery.checking(new SExpectations() {
      {
        one(marketService).getMarkets(withArg(Matchers.any(classOf[Date])), withArg(Matchers.any(classOf[Date])), withArg2(None), withArg(Option(3))); will(returnValue(List(1l)))
        one(marketService).getMarketDetails(1l); will(returnValue(marketDetails))
        one(marketService).getMarketRunners(1l); will(returnValue(marketRunners))

        /**No markets are discovered,it should trigger removing previously discovered market.*/
        one(marketService).getMarkets(withArg(Matchers.any(classOf[Date])), withArg(Matchers.any(classOf[Date])), withArg2(None), withArg(Option(3))); will(returnValue(Nil))

      }
    })

    /**Check markets*/
    assertEquals(0, betex.getMarkets.size)
    assertEquals(Nil, marketEventListener.discoveredMarketIds)

    eventCollectorTask.execute()

    /**Check markets*/
    assertEquals(1, betex.getMarkets.size)
    assertEquals(List(1), marketEventListener.discoveredMarketIds)

    eventCollectorTask.execute()

    /**Check markets*/
    assertEquals(0, betex.getMarkets.size)
    assertEquals(Nil, marketEventListener.discoveredMarketIds)
  }

  /**The 'with' method from jmock can't be used in Scala, therefore it's changed to 'withArg' method*/
  private class SExpectations extends Expectations {
    def withArg[T](matcher: Matcher[T]): T = super.`with`(matcher)
    def withArg(value: Long): Long = super.`with`(value)
    def withArg(value: String): String = super.`with`(value)
    def withArg(value: Option[String]): Option[String] = super.`with`(value)
    def withArg(value: Int): Int = super.`with`(value)
    def withArg1(value: Option[Int]): Option[Int] = super.`with`(value)
    def withArg2[T](value: Option[T]): Option[T] = super.`with`(value)
    def withArg(value: Date): Date = super.`with`(value)
    def withArg[Any](matcher: Any): Any = super.`with`(matcher)
  }

}