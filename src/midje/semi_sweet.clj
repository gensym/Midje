(ns midje.semi-sweet
  (:use clojure.test
	midje.semi-sweet.semi-sweet-internals
        [clojure.contrib.ns-utils :only [immigrate]]))
(immigrate 'midje.unprocessed)

(def => "=>")   ; So every namespace uses the same qualified name.

(defmacro only-mocked 
  "Defines a list of names as functions that have no implementation yet. They will
   throw Errors if ever called."
  [& names] (only-mocked* names))

(defmacro unfinished
  "Defines a list of names as functions that have no implementation yet. They will
   throw Errors if ever called."
  [& names] (only-mocked* names))

(defmacro fake 
  "Creates an expectation map that a particular call will be made. When it is made,
   the result is to be returned. Either form may contain bound variables. 
   Example: (let [a 5] (fake (f a) => a))"
  [call-form => result & overrides]
  (let [[var-sym & args] call-form & overrides]
    (make-expectation-map var-sym
                          `{:arg-matchers (map midje.unprocessed.unprocessed-internals/arg-matcher-maker [~@args])
                            :call-text-for-failures (str '~call-form)
                            :result-supplier (fn [] ~result)
			    :type :fake}
			  overrides))
)

(defmacro not-called
  "Creates an expectation map that a function will not be called.
   Example: (not-called f))"
  [var-sym & overrides]
  (make-expectation-map var-sym
                        `{:call-text-for-failures (str '~var-sym " was called.")
                          :result-supplier (fn [] nil)
                          :type :not-called}
			overrides)
)



(defmacro expect 
  "Run the call form, check that all the mocks defined in the expectations 
   (probably with 'fake') have been satisfied, and check that the actual
   results are as expected. If the expected results are a function, it
   will be called with the actual result as its single argument."
  [call-form => expected-result & overrides-and-expectations]
  (let [ [overrides expectations] (separate overrides-and-expectations)]
    `(let [call# (call-being-tested ~call-form ~expected-result ~overrides)]
       (expect* call# (vector ~@expectations))))
)
