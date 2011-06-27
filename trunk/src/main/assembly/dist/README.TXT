********************************************************************************************
*Betting Exchange Event Collector Copyright 2011 Daniel Korzekwa(http://danmachine.com)    *      
*Project homepage: http://code.google.com/p/betting-exchange-event-collector/              *
*Licenced under Apache License 2.0(http://www.apache.org/licenses/LICENSE-2.0)             *
********************************************************************************************

FAQ

1) How to start market data collector from command line?
marketdatacollector marketDataDir=./marketdata bfUser=john bfPassword=green bfProductId=82 collectionInterval=1000 discoveryInterval=60000 startInMinutesFrom=-60 startInMinutesTo=300


2) How to use market data collector as a library?
Look here: src/test/scala/dk/betex/eventcollector/task/EventCollectorTaskIntegrationTest.scala