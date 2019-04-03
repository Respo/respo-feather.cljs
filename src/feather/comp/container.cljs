
(ns feather.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core
             :refer
             [defcomp cursor-> list-> action-> mutation-> <> div button textarea span]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [respo-md.comp.md :refer [comp-md]]
            [feather.config :refer [dev?]]
            [feather.core :refer [comp-icon comp-i]]
            ["feather-icons" :as feather-icons]
            ["copy-text-to-clipboard" :as copy!]))

(defcomp
 comp-container
 (reel)
 (let [store (:store reel)
       states (:states store)
       icons (seq (.keys js/Object (.-icons feather-icons)))]
   (div
    {:style (merge ui/global ui/fullscreen)}
    (if (some? (:icon store))
      (div
       {:style (merge
                ui/row-center
                {:padding "16px",
                 :position :fixed,
                 :top 0,
                 :width "100%",
                 :background-color (hsl 0 0 100 0.8),
                 :border-bottom (str "1px solid " (hsl 0 0 92))})}
       (<> (str "Copied " ":" (:icon store)))
       (=< 16 nil)
       (comp-i (str (:icon store)) 40 "black")))
    (list->
     {:style {:width "100%", :padding 16, :overflow :auto, :margin-top 80}}
     (->> icons
          (map
           (fn [icon]
             [icon
              (div
               {:style (merge
                        ui/center
                        {:display :inline-flex, :width 80, :height 80, :cursor :pointer}
                        (if (= icon (:icon store)) {:background-color (hsl 0 0 95)})),
                :on-click (fn [event d! m!] (copy! (str ":" icon)) (d! :exhibit icon))}
               (comp-icon icon {:font-size 24, :color (hsl 200 80 60)} nil)
               (<> icon {:font-size 12, :color (hsl 0 0 80), :white-space :nowrap}))]))))
    (when dev? (cursor-> :reel comp-reel states reel {})))))
