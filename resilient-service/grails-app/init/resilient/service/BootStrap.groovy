package resilient.service

class BootStrap {

    def init = { servletContext ->
       }
    def destroy = {
        System.out.println("OFFING")
    }
}
