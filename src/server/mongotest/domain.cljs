(ns mongotest.domain
  (:require [cljs.spec.alpha :as s]))


; company
; _id
; name
; address
; phone number
; d


(s/def ::name string?)
(s/def ::full-adress string?)
(s/def ::phone-number string?)


(s/def ::contact
  (s/keys :req [::name ::full-adress ::phone-number]))


(s/def ::company
  (s/keys :req [::name ::full-adress ::phone-number ::contact]))

