#!/usr/bin/env sh
# =========================================================
# Gradle start script for UN*X
# =========================================================
# (Bu standart gradlew dosyası, değiştirmeden kullanabilirsin)
APP_HOME=$(dirname "$0")
APP_HOME=$(cd "$APP_HOME" && pwd)

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

if [ -z "$JAVA_HOME" ] ; then
  echo "Please set JAVA_HOME"
  exit 1
fi

"$JAVA_HOME/bin/java" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
