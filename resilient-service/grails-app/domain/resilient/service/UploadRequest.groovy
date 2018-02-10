package resilient.service
import grails.rest.*

@Resource(uri = "/uploadRequest")
class UploadRequest {

    String type
    String location
    String description
    static constraints = {
    }


}
