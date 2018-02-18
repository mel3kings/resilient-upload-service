package resilient.service

import grails.converters.*

class UploadController {
    def uploadService
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
        def id = params.id
        def value = uploadService.retrieve(id)
        render value as JSON
    }


}
