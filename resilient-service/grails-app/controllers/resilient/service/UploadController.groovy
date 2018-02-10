package resilient.service

import grails.rest.*
import grails.converters.*

class UploadController {
	static responseFormats = ['json', 'xml', 'application/html']
    static allowedMethods = [index:'GET',
                             send:['POST'],
                            sample:'GET']
    def index() {
        render "OK!"
    }

    def sample(){
        render new UploadRequest(location: "desktop",type: "sample", description: "Sample JSON response") as JSON
    }
    def send(){
        def UploadRequest = request.JSON
        def UploadResponse = new UploadResponse(status: "OK", message: "Request has been received")
        render UploadResponse as JSON
      }
}
