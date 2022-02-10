#!/bin/bash
git config core.hooksPath hooks

(
  cd keincraft || exit
  git config core.hooksPath ../hooks/subprojects
)

(
  cd keincraft-test || exit
  git config core.hooksPath ../hooks/subprojects
)