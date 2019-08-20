(ns mongotest.routes
  (:require
   [bidi.bidi :as bidi]
   [hiccups.runtime]
   [macchiato.util.response :as r]
   [mongotest.db :as d]
   )
  (:require-macros
    [hiccups.core :refer [html]]))

(def db (atom nil))



(defn home [req res raise]
  (let [test (d/collection @db "test")]
    (d/find-all test {} (fn [docs]
                          (-> (html
                               [:html
                                [:head
                                 [:link {:rel "stylesheet" :href "/css/site.css"}]
                                 ]
                                [:body
                                 [:h2 "Hello World!"]
                                 (for [d docs]
                                   ;(js/console.log d)
                                   [:li "tete " (str (d "test2"))])
                                 [:p
                                  "Your user-agent is: "
                                  (str (get-in req [:headers "user-agent"]))]]])
                              (r/ok)
                              (r/content-type "text/html")
                              (res))))
    )
  )

; (defn home [req res raise]
;   (-> (html
;        [:html
;         [:head
;          [:link {:rel "stylesheet" :href "/css/site.css"}]]
;         [:body
;          [:h2 "Hello World!"]
;          [:p
;           "Your user-agent is: "
;           (str (get-in req [:headers "user-agent"]))]]])
;       (r/ok)
;       (r/content-type "text/html")
;       (res)))


(defn not-found [req res raise]
  (-> (html
       [:html
        [:body
         [:h2 (:uri req) " was not found"]]])
    (r/not-found)
    (r/content-type "text/html")
    (res)))

(def routes
  ["/" {:get home}])

(defn router [req res raise]
  (d/connect (fn [err cl] 
               (reset! db (.db cl "test"))
  (if-let [{:keys [handler route-params]} (bidi/match-route* routes (:uri req) req)]
    (handler (assoc req :route-params route-params) res raise)
    (not-found req res raise)))))
