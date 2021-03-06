(ns degree9.pandadoc.templates
  (:require [degree9.pandadoc.core :as pd]))

;; Templates API ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn template-details [id]
  (pd/get (str "/templates/" id "/details")))

(defn delete-template [id]
  (pd/delete (str "/templates/" id)))
