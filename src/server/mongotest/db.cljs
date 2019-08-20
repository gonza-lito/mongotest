(ns mongotest.db 
  (:require
   [cljs.nodejs :as node]
   [mount.core :refer [defstate]]))

(def mongodb (node/require "mongodb"))

;(def ^:export Db (aget mongodb "Db"))
(def ^:export MongoClient (aget mongodb "MongoClient"))
(def ^:export Collection (aget mongodb "Collection"))
(def ^:export ObjectID (aget mongodb "ObjectID"))



(defn connect
  ([mongourl  callback]
   (let [client (MongoClient. mongourl)]
     (.connect client callback)))
  ([callback]
   (connect "mongodb://localhost:27017" callback)))

(defn db [name client]
  (.db client name))

(defn collection [db coll]
  (.collection db coll))

(defn save! [coll doc callback]
  (let [doc (clj->js doc)]
    (.save coll doc callback)))

(defn find-all [coll query callback]
  (.find coll (clj->js query)
         (fn [err cursor]
           (.toArray cursor
                     (fn [err docs]
                       (callback (js->clj docs)))))))

(defn update-id! [coll id updater callback]
  (.find coll (clj->js {:_id (ObjectID. id)})
         (fn [err cursor]
           (.nextObject cursor
                        (fn [err doc]
                          (.log js/console "initial:" (str (js->clj doc)))
                          (let [doc (updater (js->clj doc))]
                            (.log js/console "updated:" (str doc))
                            (save! coll doc callback)))))))

(defn delete-id! [coll id callback]
  (let [_id (ObjectID. id)]
    (.log js/console "deleting" (.getTimestamp _id))
    (.remove coll (clj->js {:_id _id}) (clj->js {:safe true}) callback)))

