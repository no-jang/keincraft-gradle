#!/bin/sh

source "scripts/util/check-main-dir.sh"

TMP_DIR="tmp/patches/main-reset"

mkdir -p $TMP_DIR

FIRST_COMMIT="$(printf "" | git hash-object -t tree --stdin)"

(
  git diff "$FIRST_COMMIT" HEAD engine/common > $TMP_DIR/commit-common.patch
  git diff "$FIRST_COMMIT" HEAD engine/core > $TMP_DIR/commit-core.patch
  git diff "$FIRST_COMMIT" HEAD engine/graphics > $TMP_DIR/commit-graphics.patch
  git diff "$FIRST_COMMIT" HEAD engine/graphics-opengl > $TMP_DIR/commit-graphics-opengl.patch
  git diff "$FIRST_COMMIT" HEAD engine/graphics-vulkan > $TMP_DIR/commit-graphics-vulkan.patch
)

(
  cd aio || exit
  git apply --allow-empty ../$TMP_DIR/commit-common.patch
  git apply --allow-empty ../$TMP_DIR/commit-core.patch
  git apply --allow-empty ../$TMP_DIR/commit-graphics.patch
  git apply --allow-empty ../$TMP_DIR/commit-graphcis-opengl.patch
  git apply --allow-empty ../$TMP_DIR/commit-graphics-vulkan.patch
)