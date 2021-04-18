object Interruption extends App {

  val t = new Thread(() => {
    try {
      while (true) {
        println("Sleeping...")
        Thread.sleep(1000)
      }
    } catch {
      case _: InterruptedException => Thread.currentThread().interrupt()
    }
  })
  t.start()
  t.interrupt()

}