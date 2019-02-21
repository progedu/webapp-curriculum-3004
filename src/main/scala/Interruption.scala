object Interruption extends App {

  new Thread(() => {
    try {
      while (true) {
        println("Sleeping...")
        Thread.sleep(1000)
        Thread.currentThread().interrupt()
      }
    } catch {
      case _: InterruptedException =>
    }
  }).start()

}
