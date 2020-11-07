#!/usr/bin/env bash
cd ..
# shellcheck disable=SC2164
cd target
start javaw -XX:+UseG1GC -Xms512M -Xmx512M -jar Mamong-Us-1.6.6-alpha.jar