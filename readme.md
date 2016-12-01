# Arachne Demo

This is a simple demo of a basic Arachne project, using some of Arachne's
features that are more or less complete. It is a bare-bones "hello world"
project, and is not representative of the kind of user experience or rapid
development that Arachne will provide.

What it _does_ demonstrate is the how to build a configuration using the
configuration DSL, and how to use that to initialize and start an Arachne
runtime.

It doesn't include everything (such as the asset data pipeline or abstract data
model), and it operates at a fairly low level. In a real Arachne app, chances
are you'll be dealing with higher-level conceptual constructs, rather than with
low-level Component and Route entities.

## Running the Demo

To walk through the demo at the REPL, open the `src/demo_repl.clj` file. This
contains a set of forms that you can evaluate sequentially at a REPL to start an
Arachne runtime.

Also look at the `src/demo.clj` file for an example of the same code, but
packaged up into a `-main` function (such as you would use in a real
app.)