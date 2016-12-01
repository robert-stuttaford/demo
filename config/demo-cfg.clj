(require '[arachne.core.dsl :as core])
(require '[arachne.http.dsl :as http])
(require '[arachne.pedestal.dsl :as ped])

;; Create the runtime entity, and specify that it has a dependency on the server
;; component.
(core/runtime :demo/runtime [:demo/server])

;; Create the entity for a worker process component, with no dependencies
(core/component :demo/ticktock {} 'demo.ticktock/->TickTock)

;; Create the entity for a HTTP handler function, with no dependencies
(http/handler :demo/hello-world-handler {} 'demo.web/hello-world)

;; Create the entity for a HTTP handler function, that has a dependency
;; reference on the 'ticktock' component
(http/handler :demo/status-handler {:demo/ticktock :ticker} 'demo.web/status)

;; Create the entitty for an Interceptor component
(core/component :demo/insulting-interceptor {} 'demo.web/insulter)

;; Define a Pedestal HTTP server
(ped/server :demo/server 8080

  ;; Define an endpoint, backed by the status handler.
  ;; Will be accessible at http://localhost:8080/status
  (http/endpoint :get "/status" :demo/status-handler)

  ;; Define an endpoint, backed by the hello-world handler. This endpoint takes
  ;; a URL variable, :name
  ;; Will be accessible at http://localhost:8080/hello/your-name
  (http/endpoint :get "/hello/:name" :demo/hello-world-handler)

  ;; Define an interceptor that will apply to all URIs under /hello
  ;; To try it out, visit http://localhost:8080/hello/Luke
  (ped/interceptor "/hello" :demo/insulting-interceptor)

  )