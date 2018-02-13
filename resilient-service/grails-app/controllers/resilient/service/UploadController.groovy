package resilient.service

import grails.rest.*
import grails.converters.*

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

class UploadController {
    def uploadService = new UploadService()
	static responseFormats = ['json', 'xml', 'application/html']
    static allowedMethods = [index:'GET',
                             send:'POST',
                             sample:'GET',
                             load:'GET']
    def index() {
        render "OK!"
    }

    def sample(){
        render new UploadRequest(location: "desktop",type: "sample", description: "Sample JSON response") as JSON
    }
    def send(){
        System.out.println("Receiving request")
        def UploadRequest = request.JSON
        uploadService.send(UploadRequest)

        def UploadResponse = new UploadResponse(status: "OK", message: "Request has been received")
        render UploadResponse as JSON
      }
    def load(){
        uploadService.load()
        render "DONE"
    }

}
