package resilient.service

class UrlMappings {

    static mappings = {
        get "/$controller/$action"()
        get "/$controller?"(action:"index")
        post "/$controller/$action"()
        "/"(controller: 'upload', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
