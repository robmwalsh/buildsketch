import BuildHelper._

lazy val root =
  project
    .in(file("."))
    .aggregate(
      zioLspJVM,
      zioLspJS,
      zioJsonRpcJVM,
      zioJsonRpcJS
    )

lazy val zioJsonRpc = crossProject(JSPlatform, JVMPlatform)
  .in(file("libs/zio-jsonrpc"))
  .settings(stdSettings("zio-jsonrpc"))
  .settings(crossProjectSettings)
  .settings(buildInfoSettings("zio.jsonrpc"))
  .enablePlugins(BuildInfoPlugin)

lazy val zioJsonRpcJVM = zioJsonRpc.jvm
lazy val zioJsonRpcJS = zioJsonRpc.js
  .enablePlugins(ScalaJSPlugin)

lazy val zioLsp = crossProject(JSPlatform, JVMPlatform)
  .in(file("libs/zio-lsp"))
  .settings(stdSettings("lsp"))
  .settings(crossProjectSettings)
  .settings(buildInfoSettings("zio.lsp"))
  .enablePlugins(BuildInfoPlugin)
  .dependsOn(zioJsonRpc)

lazy val zioLspJVM = zioLsp.jvm.dependsOn(zioJsonRpcJVM)
lazy val zioLspJS = zioLsp.js
  .dependsOn(zioJsonRpcJS)
  .enablePlugins(ScalaJSPlugin)
