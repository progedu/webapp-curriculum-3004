import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Semaphore

object QueueWithSemaphore extends App {

  val arrayList = new CopyOnWriteArrayList[Runnable]()
  val semaphore = new Semaphore(10)

  for (i <- 1 to 100) {
    arrayList.add(() => {
      Thread.sleep(1000)
      println(s"Runnable: ${i} finished.")
    })
  }

  for (i <- 1 to 20) {
    val t = new Thread(() => {
      var continue = true
      while (continue) {
        try {
          semaphore.acquire()
          try {
            val runnable = arrayList.remove(0)
            runnable.run()
          } catch {
            case _: ArrayIndexOutOfBoundsException => continue = false
          }
        } finally {
          semaphore.release()
        }
      }
    })
    t.start()
  }
}