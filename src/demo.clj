(ns demo
  (:require [arachne.core :as arachne]
            [com.stuartsierra.component :as component]))

(defn -main
  "Initialze and start an Arachne application, based on the config

  From the command line, you can run this with:

   lein run -m demo config/demo-cfg.clj"
  [cfg-script]
  (let [cfg (arachne/build-config
              [:org.arachne-framework/arachne-pedestal]
              cfg-script)]
    (component/start (arachne/runtime cfg :demo/runtime))))