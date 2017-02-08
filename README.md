# lein-dynamic-version

A Leiningen plugin to populate the `:version` field of your lein project dynamically.

[![Clojars Project](https://img.shields.io/clojars/v/lein-dynamic-version.svg)](https://clojars.org/lein-dynamic-version)

## Usage

Put `[lein-dynamic-version "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your project.clj.
From then on, dynamic-version will replace the `:version` field of the project automatically.

## How it works

This is simply a lein middleware that goes through a list of "loaders", which define how to fetch the number version. The first version returned by a loader is used to replace the `:version` key of the lein project definition.

## Configuration

You can set a `:dynamic-version` key to your project which contains any of the following entries:

#### `:env`

Fetch the version from an environment variable.

Example:

```clj
:dynamic-version {:env "FOO"}
```

This will use the environment variable `FOO` to populate the version.

#### `:file`

Fetch the version from a file on the filesystem.

##### Example

```clj
:dynamic-version {:file "../VERSION"}
```


#### `:order`

A vector that contains the loaders names in order they should be used. Existing loaders are:

* `:env` - load version from an environment variable
* `:file` - load version from a file
* `:default` - use the version specified in the project.clj file

##### Default

`[:env :file :default]`

##### Example

Load from a file, and themm default:

```clj
:dynamic-version {:order [:file :default]}
```

## License

Copyright © 2017 Thomas Pelletier

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
