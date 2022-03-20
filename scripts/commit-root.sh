#!/bin/sh

TMP_DIR="tmp/patches"

if [[ "$(pwd)" == *"scripts"* ]]; then
   echo "Run this script from root directory"
   exit 0
fi

mkdir -p $TMP_DIR

(
  cd engine/common/src/main/java || exit
  git diff --relative HEAD^ HEAD . > ../../../../../$TMP_DIR/reset.patch
)

(
  cd aio || exit
  git apply --allow-empty --reject ../$TMP_DIR/reset.patch
  git add .
  git commit -m "[RESYNC]"
)