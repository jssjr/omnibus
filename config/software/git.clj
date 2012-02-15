;;
;; Author:: Adam Jacob (<adam@opscode.com>)
;; Author:: Christopher Brown (<cb@opscode.com>)
;; Copyright:: Copyright (c) 2010 Opscode, Inc.
;; License:: Apache License, Version 2.0
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;; 
;;     http://www.apache.org/licenses/LICENSE-2.0
;; 
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.
;;

(let [env (cond
           (and (is-os? "darwin") (is-machine? "x86_64"))
           {
            "LDFLAGS" "-R/opt/chef/embedded/lib -L/opt/chef/embedded/lib -I/opt/chef/embedded/include"
            "CFLAGS" "-I/opt/chef/embedded/include -L/opt/chef/embedded/lib"
            }
           (is-os? "linux")
           {
            "LDFLAGS" "-Wl,-rpath /opt/chef/embedded/lib -L/opt/chef/embedded/lib -I/opt/chef/embedded/include"
            "CFLAGS" "-I/opt/chef/embedded/include -L/opt/chef/embedded/lib"
            })]

(software "git"
          :source "git-1.7.6.4"
          :steps [
                  {:command "make"
                   :args ["configure"]}
                  {:command "./configure"
                   :args ["--prefix=/opt/chef/embedded" "--with-curl=/opt/chef/embedded" "--with-openssl=/opt/chef/embedded" "--with-iconv=/opt/chef/embedded"]}
                  {:command "make"}
                  {:command "make"
                   :args ["install"]}]))
