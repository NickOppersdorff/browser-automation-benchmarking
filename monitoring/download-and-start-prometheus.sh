#!/bin/sh

if [ ! -d prometheus-* ]; then
  // download promethues using curl from https://github.com/prometheus/prometheus/releases/download/v2.51.0/prometheus-2.51.0.darwin-amd64.tar.gz
  curl -LO https://github.com/prometheus/prometheus/releases/download/v2.51.0/prometheus-2.51.0.darwin-amd64.tar.gz

  // untar the downloaded file
  tar xvfz prometheus-*.tar.gz
fi

cd prometheus-*

// start prometheus using the prometheus.yml file
# Start Prometheus.
# By default, Prometheus stores its database in ./data (flag --storage.tsdb.path).
./prometheus --config.file=../prometheus.yml
