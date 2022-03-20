#!/bin/sh

if [[ "$(pwd)" == *"scripts"* ]]; then
  echo "Run this script from root directory"
  exit 0;
fi

TMP_DIR="tmp/patches/main"

mkdir -p $TMP_DIR

(
  git diff HEAD^ HEAD engine/common > $TMP_DIR/commit-common.patch
  git diff HEAD^ HEAD engine/core > $TMP_DIR/commit-core.patch
  git diff HEAD^ HEAD engine/graphics > $TMP_DIR/commit-graphics.patch
  git diff HEAD^ HEAD engine/graphics-opengl > $TMP_DIR/commit-graphics-opengl.patch
  git diff HEAD^ HEAD engine/graphics-vulkan > $TMP_DIR/commit-graphics-vulkan.patch
)