(ns leiningen.tailwind
  (:require [clojure.java.shell :as shell]
            [robert.hooke :as hooke]
            [me.raynes.fs :as fs]
            [leiningen.compile :as lcompile]
            [leiningen.clean :as lclean]
            [leiningen.core.main :as lmain]))

(defn- init []
  (prn "Initializing Tailwindcss")
  (let [cmd "npx tailwindcss init"]
    (apply shell/sh (clojure.string/split cmd #"\s+"))))

(defn- abort [s]
  (println s)
  (lmain/abort))

(defn- build-style [tailwind-dir output-dir tailwind-config {:keys [src dst]}]
  (let [cmd (->> ["npx tailwindcss build" (str tailwind-dir "/" src) "-o" (str output-dir "/" dst) "-c" tailwind-config]
                 (clojure.string/join " "))]
    (prn "Building Tailwind stylesheet: " cmd)
    (apply shell/sh (clojure.string/split cmd #"\s+"))))

(defn- build [project]
  (prn "Building Tailwind stylesheets")
  (let [config (:tailwind project)
        {:keys [tailwind-dir output-dir tailwind-config styles]
         :or   {tailwind-config "tailwind.config.js"}} config]
    (doseq [style styles]
      (build-style tailwind-dir output-dir tailwind-config style))))

(defn- clean-style [output-dir {:keys [dst]}]
  (let [path (str output-dir "/" dst)]
    (prn "Cleaning Tailwind stylesheet: " path)
    (fs/delete path)))

(defn- clean [project]
  (prn "Cleaning Tailwind stylesheets")
  (let [config (:tailwind project)
        {:keys [output-dir styles]} config]
    (doseq [style styles]
      (clean-style output-dir style))))

(defn tailwind
  {:help-arglists '([init build clean])
   :subtasks      [#'init #'build #'clean]}
  [project subtask & _]
  (case (keyword subtask)
    :init (init)
    :build (build project)
    :clean (clean project)
    (abort (str "Subtask " subtask " not found."))))

;; Hooks

(defn compile-hook [task & args]
  (apply task args)
  (let [[project & _] args]
    (build project)))

(defn clean-hook [task & args]
  (apply task args)
  (let [[project & _] args]
    (clean project)))

(defn activate []
  (hooke/add-hook #'lcompile/compile #'compile-hook)
  (hooke/add-hook #'lclean/clean #'clean-hook))