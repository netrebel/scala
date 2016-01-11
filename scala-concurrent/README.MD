JFXApp does not run because of missing dependency. Error message is:

```
Error:scalac: missing or invalid dependency detected while loading class file 'ControlIncludes.class'.
Could not access type Change in value javafx.scene.control.TextFormatter,
because it (or its dependencies) are missing. Check your build definition for
missing or conflicting dependencies. (Re-run with `-Ylog-classpath` to see the problematic classpath.)
A full rebuild may help if 'ControlIncludes.class' was compiled against an incompatible version of javafx.scene.control.TextFormatter.
```

ThreadBlocker.scala runs fine if the dependency ("org.scalafx") and the EntryPoint.scala class are removed. 