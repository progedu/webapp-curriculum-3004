import java.lang.ArrayIndexOutOfBoundsException
import java.util.concurrent.{CopyOnWriteArrayList, Semaphore}

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
      while (true) {
        try {
          semaphore.acquire()
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

  while (!arrayList.isEmpty)
    Thread.sleep(1000)

}