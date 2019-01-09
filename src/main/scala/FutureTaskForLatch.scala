import java.util.concurrent.FutureTask

object FutureTaskForLatch extends App {

  val futureTasks = for (i <- 1 to 3)
    yield new FutureTask[Int](() => {
      Thread.sleep(1000)
      println(s"FutureTask ${i} finished")
      i
    })
  futureTasks.foreach((f) => new Thread(f).start())
  
  new Thread(() => {
    for (i <- 1 to 3) {
      futureTasks(i - 1).get()
    }
    println(s"All tasks finished")
  }).start()

}
