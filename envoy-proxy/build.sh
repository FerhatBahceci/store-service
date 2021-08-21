#!/bin/sh
set -e

: ${VERSION:="latest"}
export VERSION

docker build -t envoy-proxy:${VERSION} .
