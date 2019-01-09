object Interruption extends App {

  val thread = new Thread(() => {
    try {
      while (true) {
        println("Sleeping...")
        Thread.sleep(1000)
      }
    } catch {
      case _: InterruptedException =>
    }
  })
  thread.start()
  thread.interrupt()

}
