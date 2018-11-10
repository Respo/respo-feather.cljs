
(ns feather.util )

(defn get-env! [property] (aget (.-env js/process) property))
