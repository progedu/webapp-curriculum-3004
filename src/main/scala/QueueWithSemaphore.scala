import java.util.concurrent.{CopyOnWriteArrayList, Semaphore}

object QueueWithSemaphore extends App {

  val semaphore = new Semaphore(10)
  val arrayList = new CopyOnWriteArrayList[Runnable]()

  for (i <- 1 to 100) {
    arrayList.add(() => {
      Thread.sleep(1000)
      println(s"Runnable: ${i} finished.")
    })
  }

  for (i <- 1 to 20) {
    val t = new Thread(() => {
      while (true) {
        semaphore.acquire()
        try {
          val runnable = arrayList.remove(0)
          runnable.run()
        } catch {
          case _: ArrayIndexOutOfBoundsException =>
        } finally {
          semaphore.release()
        }
      }
    })
    t.start()
  }
}
