package resilient.service


import grails.rest.*
import grails.converters.*

class HelloController {
	static responseFormats = ['json', 'xml']
	
    def index() {
        render "Hello world!"
    }
}
