#!/bin/sh

TMP_DIR="tmp/patches"

if [[ "$(pwd)" == *"scripts"* ]]; then
   echo "Run this script from root directory"
   exit 0
fi

mkdir -p $TMP_DIR

