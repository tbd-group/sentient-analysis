(ns sentient-analysis.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [sentient-analysis.core-test]))

(doo-tests 'sentient-analysis.core-test)
