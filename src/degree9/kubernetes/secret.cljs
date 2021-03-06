(ns degree9.kubernetes.secret
  (:require [degree9.debug :as dbg]
            [degree9.kubernetes.core :as k8s]))

(dbg/defdebug debug "degree9:enterprise:kubernetes:secret")

;; Kubernetes Secrets ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn- list-secret
  "List all Kubernetes secrets from a Kubernetes namespace ."
  [api namespace]
  (debug "Listing kubernetes secrets from namespace" api namespace)
  (-> (.listNamespacedSecret api namespace)
    (.then k8s/k8s-response)
    (.catch k8s/k8s-error)))

(defn- read-secret
  "Read a Secret from a Kubernetes namespace."
  [api name namespace]
  (debug "Reading kubernetes secret from namespace" api name namespace)
  (-> (.readNamespacedSecret api name namespace)
    (.then k8s/k8s-response)
    (.catch k8s/k8s-error)))

(defn- create-secret
  "Create a Kubernetes secret within a Kubernetes namespace."
  [api data namespace]
  (debug "Creating kubernetes secret from namespace" api data namespace)
  (-> (.createNamespacedSecret api namespace data)
    (.then k8s/k8s-response)
    (.catch k8s/k8s-error)))

(defn- replace-secret
  "Replace a Kubernetes secret."
  [api id namespace data]
  (debug "Replacing kubernetes secret from namespace" api id namespace data)
  (-> (.replaceNamespacedSecret api id namespace data)
    (.then k8s/k8s-response)
    (.catch k8s/k8s-error)))

(defn- patch-secret
  "Patch a Kubernetes secret."
  [api id namespace data]
  (debug "Patching kubernetes secret from namespace" api id namespace data)
  (-> (.patchNamespacedSecret api id namespace data)
    (.then k8s/k8s-response)
    (.catch k8s/k8s-error)))

(defn- delete-secret
  "Delete a Kubernetes secret."
  [api id namespace]
  (debug "Deleting kubernetes secret namespace" api id namespace)
  (-> (.deleteNamespacedSecret api id namespace)
    (.then k8s/k8s-response)
    (.catch k8s/k8s-error)))

(defn secret [& [opts]]
  (let [api (:api opts)]
    (debug "Initializing kubernetes secret from namespace" api)
    (reify
      Object
      (find [this params]
        (let [namespace (get-in (js->clj params) ["query" "namespace"])]
          (list-secret api namespace)))
      (get [this id params]
        (let [namespace (get-in (js->clj params) ["query" "namespace"])]
          (read-secret api id namespace)))
      (create [this data & [params]]
        (let [namespace (get-in (js->clj params) ["query" "namespace"])]
          (create-secret api data namespace)))
      (update [this id data params]
        (let [namespace (get-in (js->clj params) ["query" "namespace"])]
          (replace-secret api id namespace data)))
      (patch [this id data params]
        (let [namespace (get-in (js->clj params) ["query" "namespace"])]
          (patch-secret api id namespace data)))
      (remove [this id params]
        (let [namespace (get-in (js->clj params) ["query" "namespace"])]
          (delete-secret api id namespace))))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
