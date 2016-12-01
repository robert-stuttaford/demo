(ns demo_repl
  (:require [arachne.core :as arachne]
            [com.stuartsierra.component :as component]))

;; Create an Arachne configuration, by evaluating the config script
;; Also requires that we pass in a list of modules we want to use (in this case,
;; just arachne-pedestal)

;; To see what the Arachne program we're running is actually supposed to do,
;; read the config/demo-cfg.clj init script.
(def cfg (arachne/build-config
           [:org.arachne-framework/arachne-pedestal]
           "config/demo-cfg.clj"))

;; Based on the config, create a new Arachne runtime. The second argument is
;; the Arachne ID of the entity representing the Runtime we want to start, which
;; is defined in the config itself
(def rt (arachne/runtime cfg :demo/runtime))

;; The runtime has been built, and all the components have been instantiated.
;; However, they haven't been started yet. We will now start the runtime:
(def rt' (component/start rt))

;; That's it! Arachne is now up and running. You can observe the running
;; component ticking away in the logs, or hit one of the handler URLs defined in
;; the config to see a page that Arachne is serving

;; Note that the code in this file has absolutely nothing to do with our
;; application; all it does is load the config and bootstrap Arachne. The
;; application itself is defined entirely in the configuration.

;; To get a sense of some of the things that Arachne can do, below are some other
;; fun things to try.

;; We can query the configuration directly to get an idea of the structure of
;; our application.

;; This query returns a list of all the endpoints present in our application:
(require '[arachne.core.config :as cfg])
(cfg/q cfg '[:find ?endpoint ?name ?ctor (distinct ?method)
             :where
             [?endpoint :arachne.http.endpoint/name ?name]
             [?endpoint :arachne.component/constructor ?ctor]
             [?endpoint :arachne.http.endpoint/methods ?method]])

;; And now we can inspect an "Endpoint" entity
;; (swap in the entity ID for one returned by the query above)
(cfg/pull cfg '[*] 17592186045444)

;; This query returns all the interceptors that are currently installed,
;; including the default ones bundled with Pedestal.

;; Note that understanding this requires understanding the config data model for
;; interceptors... I am currently working on some tools to allow configuration
;; data models to be self-documenting with diagram & doc generation :)
(cfg/q cfg '[:find ?interceptor ?ctor-or-inst
             :where
             [?interceptor :arachne.pedestal.interceptor/route ?route]
             (or
               [?interceptor :arachne.component/instance ?ctor-or-inst]
               [?interceptor :arachne.component/constructor ?ctor-or-inst])])

;; Given a started runtime, we can also extract object instances based on an
;; entity ID or lookup ref. For example, the following snippet gets the
;; runtime instance of the status handler. This can be quite useful for
;; debugging.
(require '[arachne.core.runtime :as rt])
(rt/lookup rt' [:arachne/id :demo/status-handler])