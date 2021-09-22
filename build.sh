#!/bin/sh
set -e

: ${VERSION:="latest"}
export VERSION

docker build -t store-service:${VERSION} .
