(ns degree9.auth
  (:require
   [javelin.core :as j]
   [degree9.debug :as dbg]
   [goog.object :as obj]
   [meta.client :as client]
   [meta.promise :as prom])
  (:require-macros degree9.auth))

(def ^:private debug (dbg "degree9:enterprise:auth"))

;; Authentication State ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def authentication (j/cell nil))

(defn user-cell []
  (j/cell= (:user authentication)))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Helper Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn- auth-handler [auth]
  (-> auth
    (prom/then #(js->clj % :keywordize-keys true))
    (prom/then #(reset! authentication %))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Authentication Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn auth! [app]
  (auth-handler (client/auth! app)))

(defn login! [app strategy & [opts]]
  (auth-handler (client/login! app strategy opts)))

(defn logout! [app]
  (-> (client/logout! app)
    (prom/then #(reset! authentication nil))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
