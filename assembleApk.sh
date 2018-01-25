#!/bin/sh

ANDROID_HOME=/home/a/Android/Sdk/
JAVA_HOME=/usr/lib/jvm/java-8-oracle
PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$JAVA_HOME/bin
echo "Start assembleDebug" >> /home/a/dev/reaproto/conference/rea_conference_backend/gradle_script.log
echo $(date -u) >> /home/a/dev/reaproto/conference/rea_conference_backend/gradle_script.log
cd /home/a/dev/reaproto/conference/RosenergoatomConference
./gradlew :app:assembleDebug >> /home/a/dev/reaproto/conference/rea_conference_backend/gradle_script.log
echo "End assembleDebug" >> /home/a/dev/reaproto/conference/rea_conference_backend/gradle_script.log
echo $(date -u) >> /home/a/dev/reaproto/conference/rea_conference_backend/gradle_script.log
