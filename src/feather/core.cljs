
(ns feather.core
  (:require [respo.core :refer [defcomp span div i <>]]
            [hsl.core :refer [hsl]]
            ["feather-icons" :as feather-icons]))

(def style-base {:display :inline-block})

(def style-error
  {:background-color (hsl 0 80 70),
   :color :white,
   :padding "0 8px",
   :line-height "24px",
   :border-radius "12px"})

(defcomp
 comp-icon
 (icon style on-click)
 (assert (or (string? icon) (keyword? icon)) "1: icon name in string")
 (assert (number? (:font-size style)) "2: size in number")
 (assert (string? (:color style)) "3: color in string")
 (let [icon-name (name icon), obj (aget (.-icons feather-icons) icon-name)]
   (if (some? obj)
     (i
      {:style (merge style-base style),
       :innerHTML (if (some? obj)
         (.toSvg
          obj
          (clj->js
           {:width (:font-size style), :height (:font-size style), :color (:color style)}))),
       :on-click on-click})
     (do
      (.error js/console "No icon named:" (name icon))
      (span
       {:on-click on-click, :style (merge style-base style style-error)}
       (<> (str "No " icon-name)))))))

(defcomp comp-i (icon size color) (comp-icon icon {:font-size size, :color color} nil))
