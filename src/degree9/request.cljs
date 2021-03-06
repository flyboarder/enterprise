(ns degree9.request
  (:refer-clojure :exclude [get])
  (:require ["node-fetch" :as node-fetch]
            [degree9.debug :as dbg]))

(dbg/defdebug debug "degree9:enterprise:request")

(def fetch node-fetch)

(defn- request-status [res]
  (if (.-ok res) res
    (throw (js/Error. (.-statusText res)))))

(defn json [res]
  (.json res))

(defn request [url & [req]]
  (let [defaults {:method "GET"
                  :headers {:accept "application/json"
                            :content-type "application/json"}}
        req (clj->js (merge defaults req))]
    (debug "Constructing request to %s using %s" url req)
    (-> (fetch url req)
        (.then request-status))))

(defn get [url & [req]]
  (request url
    (merge req
      {:method "GET"})))

(defn delete [url & [req]]
  (request url
    (merge req
      {:method "GET"})))

(defn clj->json [data]
  (.stringify js/JSON (clj->js data)))

(defn post [url data & [req]]
  (request url
    (merge req
      {:method "POST"
       :headers (merge (:headers req)
                  {:accept "application/json"
                   :content-type "application/json"})
       :body (clj->json data)})))

(defn put [url data & [req]]
  (request url
    (merge req
      {:method "PUT"
       :body (clj->json data)})))

(defn patch [url data & [req]]
  (request url
    (merge req
      {:method "PATCH"
       :body (clj->json data)})))
