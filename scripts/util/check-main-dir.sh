#!/bin/sh

if [[ "$(pwd)" == *"scripts"* ]]; then
  echo "Run this script from root directory"
  exit 0
fi