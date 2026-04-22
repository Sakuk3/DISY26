#!/bin/sh
# Start a virtual framebuffer so JavaFX can render without a physical display.
Xvfb :99 -screen 0 1280x720x24 -nolisten tcp &
export DISPLAY=:99
exec java --module-path /app/libs \
          --add-modules javafx.controls,javafx.fxml \
          -jar /app/javafx-app.jar "$@"
