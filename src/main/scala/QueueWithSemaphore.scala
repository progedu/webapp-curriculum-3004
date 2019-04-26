import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{CopyOnWriteArrayList, Semaphore}

object QueueWithSemaphore extends App {

  val arrayList = new CopyOnWriteArrayList[Runnable]()
  val semaphore = new Semaphore(10)
  val counter = new AtomicInteger(0)

  for (i <- 1 to 100) {
    arrayList.add(() => {
      Thread.sleep(1000)
      println(s"Runnable: ${i} finished.")
      counter.incrementAndGet()
    })
  }

  for (i <- 1 to 20) {
    val t = new Thread(() => {
      while (true) {
        try {
          semaphore.acquire()
          if(!arrayList.isEmpty) {
            val runnable = arrayList.remove(0)
            runnable.run()
          } else {
            while(counter.get() != 100) {
              Thread.sleep(100)
            }
            System.exit(0)
          }
        } finally {
          semaphore.release()
        }
      }
    })
    t.start()
  }
}
