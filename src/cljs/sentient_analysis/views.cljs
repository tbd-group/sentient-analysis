(ns sentient-analysis.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   ;; [breaking-point.core :as bp]
   [re-pressed.core :as rp]
   [sentient-analysis.subs :as subs]
   ))


;; home

;; (defn display-re-pressed-example []
;;   (let [re-pressed-example (re-frame/subscribe [::subs/re-pressed-example])]
;;     [:div

;;      [:p
;;       [:span "Re-pressed is listening for keydown events. A message will be displayed when you type "]
;;       [:strong [:code "hello"]]
;;       [:span ". So go ahead, try it out!"]]

;;      (when-let [rpe @re-pressed-example]
;;        [re-com/alert-box
;;         :alert-type :info
;;         :body rpe])]))

(defn home-title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label (str "Hello from " @name ". This is the Home Page.")
     :level :level1]))

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])

(defn home-panel []
  [re-com/v-box
   :gap "1em"
   :children [[home-title]
              [link-to-about-page]
              ;; [display-re-pressed-example]
              ;; [:div
              ;;  [:h3 (str "screen-width: " @(re-frame/subscribe [::bp/screen-width]))]
              ;;  [:h3 (str "screen: " @(re-frame/subscribe [::bp/screen]))]]
              ]])


;; about

(defn about-title []
  [re-com/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title]
              [link-to-home-page]]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :height "100%"
     :children [[panels @active-panel]]]))
