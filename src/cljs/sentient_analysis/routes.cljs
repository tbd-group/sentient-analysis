(ns sentient-analysis.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require
   [secretary.core :as secretary]
   [goog.events :as gevents]
   [goog.history.EventType :as EventType]
   [re-frame.core :as re-frame]
   [re-pressed.core :as rp]
   [sentient-analysis.events :as events]
   ))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn lower->upper [c]
  (.toUpperCase c))

(defn char->charCode [c]
  (.charCodeAt c 0))

(defn key->binding [binding-opts k]
  [(merge {:keyCode k
           :altKey false
           :ctrlKey false
           :metaKey false
           :shiftKey false}
          binding-opts)])

(defn ->shortcut [handler key-combo & {:as binding-opts}]
  (let [key-bindings (mapv (comp (partial key->binding binding-opts)
                                 char->charCode
                                 lower->upper)
                           key-combo)]
    (apply conj [handler] key-bindings)))

(defn ->shortcut' [handler key-codes & {:as binding-opts}]
  (let [key-bindings (mapv (partial key->binding binding-opts) key-codes)]
    (apply conj [handler] key-bindings)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  ;; --------------------
  ;; define routes here
  (defroute "/" []
    (re-frame/dispatch [::events/set-active-panel :home-panel])
    (re-frame/dispatch [::events/set-re-pressed-example nil])
    (re-frame/dispatch
     [::rp/set-keydown-rules
      {:event-keys [[
                     ]]

       :prevent-default-keys [{:keyCode (char->charCode \space)}
                              {:keyCode (char->charCode \tab)}
                              ]
       :clear-keys
       [[{:which 27} ;; escape
         ]]}])
    )

  (defroute "/about" []
    (re-frame/dispatch [::events/set-active-panel :about-panel]))


  ;; --------------------
  (hook-browser-navigation!))
