(ns demo.web
  (:require [clojure.string :as str]))

(defn hello-world
  "Hello-world handler function"
  [req]
  {:status 200
   :body (str "Hello, " (get-in req [:path-params :name]))})

(defn insulter
  "Constructor for a insulting interceptor. Builds and returns a component which
  happens to be a map compatible with Pedestal's IntoInterceptor protocol."
  []
  {:leave (fn [ctx]
            (update-in ctx [:response :body] str/replace "Luke"
              "optimistic webapp-building fool"))})

(defn status
  "Status handler function"
  [req]
  (let [ticker (:ticker req)]
    {:status 200
     :body (str "Current state: " @(:state ticker))}))