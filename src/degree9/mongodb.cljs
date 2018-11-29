(ns degree9.mongodb
  (:require [goog.object :as obj]
            [meta.server :as server]
            ["mongoose" :as mongoose]
            ["feathers-mongoose" :as mongodb]))

;; MongoDB Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn connect
  "Connect to MongoDB. Converts clj->js internally."
  [conn & [opts]]
  (.connect mongoose conn (clj->js opts)))

(defn mkconnection
  "Create a connection instance to MongoDB."
  [conn & [opts]]
  (.createConnection mongoose conn opts))

(defn schema
  "Create a Mongoose Schema. Converts clj->js internally."
  [data]
  (let [schema (obj/get mongoose "Schema")]
    (schema. (clj->js data))))

(defn model
  "Create a Mongoose Model."
  ([conn name schema]
   (.model conn name schema))
  ([name schema]
   (model mongoose name schema)))

(defn api
  "Create a feathers service backed by MongoDB."
  ([app path db-model hooks]
   (server/api app path (mongodb #js{:Model db-model}) hooks))
  ([app path db-model db-schema hooks]
   (api app path (model db-model (schema db-schema)) hooks)))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;