(ns sentient-analysis.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [re-pressed.core :as rp]
   ;; [breaking-point.core :as bp]
   [sentient-analysis.events :as events]
   [sentient-analysis.routes :as routes]
   [sentient-analysis.views :as views]
   [sentient-analysis.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch-sync [::rp/add-keyboard-event-listener "keydown"])
  ;; (re-frame/dispatch-sync [::bp/set-breakpoints
  ;;                          {:breakpoints [:mobile
  ;;                                         768
  ;;                                         :tablet
  ;;                                         992
  ;;                                         :small-monitor
  ;;                                         1200
  ;;                                         :large-monitor]
  ;;                           :debounce-ms 166}])
  (dev-setup)
  (mount-root))
