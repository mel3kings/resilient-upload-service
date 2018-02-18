package resilient.service

class MessageListenerJob {
    def uploadService
    static triggers = {
      simple repeatInterval: 5000l // execute job once in 5 seconds
    }

    def execute() {
        uploadService.load()
        // execute job
    }
}
