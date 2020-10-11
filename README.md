# lein-tailwind

A Leiningen plugin for [Tailwind CSS](https://tailwindcss.com/)

## Usage

Put `[lein-tailwind "0.1.2"]` into the `:plugins` vector of your project.clj.

You can generate the `tailwind.config.js` file by running

    $ lein tailwind init
    
which is equivalent to running

    $ npx tailwindcss init
    

The configuration for your project.clj is specified under the :tailwind section 
```clojure
(defproject example "1.0.0"
 :tailwind {:tailwind-dir "src/css/tailwind"
            :output-dir   "src/css"
            :tailwind-config  "tailwind.config.js" ;; tailwind.config.js is the default value 
            :styles [{:src "main.css"
                      :dst "main.css"}]})
```

To build your css files containing the Tailwind CSS directives you simply run: 

    $ lein tailwind build
    
To clean the css files generated you simply run:

    $ lein tailwind clean


To add hooks you can add the following to your `project.clj` file:

```clojure
:hooks [leiningen.tailwind]
``` 

## SASS/SCSS

To use this plugin with scss you can use [lein-sassc](https://github.com/apribase/lein-sassc/) in conjunction to this like so:

```clojure
(defproject example "1.0.0"
 :sassc    [{:src          "src/scss/main.scss" 
             :output-to    "dist/main.css"      
             :style        "compressed"}]
 :tailwind {:tailwind-dir "src/scss/tailwind"
            :output-dir   "src/scss"
            :tailwind-config  "tailwind.config.js" ;; tailwind.config.js is the default value 
            :styles [{:src "main.scss"
                      :dst "main.scss"}]}
 :hooks [leiningen.tailwind leiningen.sassc])
```


## License

Copyright © 2020 Nikolas Pafitis.

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
