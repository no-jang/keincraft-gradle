#!/bin/sh

TMP_DIR="tmp/patches"

if [[ "$(pwd)" == *"scripts"* ]]; then
   echo "Run this script from root directory"
   exit 0
fi

mkdir -p $TMP_DIR

(
 cd one || exit
 git diff HEAD^ HEAD two > ../$TMP_DIR/commit-one.patch
)

git apply --allow-empty --reject $TMP_DIR/commit-one.patch
git add .
git commit -m "[COMMIT-ONE]"