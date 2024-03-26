#!/bin/sh
# create variable for current directory
current_dir=$(pwd)
if [ ! -d grafana-v10.4.1 ]; then
  curl -O https://dl.grafana.com/oss/release/grafana-10.4.1.darwin-amd64.tar.gz
  tar -zxvf grafana-10.4.1.darwin-amd64.tar.gz
fi

rm -r https://dl.grafana.com/oss/release/grafana-10.4.1.darwin-amd64.tar.gz
cd grafana-v10.4.1

# start grafana and return to terminal
./bin/grafana server --config "${current_dir}/grafana-config.ini" &
sleep 30

# load data source configuration
for i in datasources/*; do \
    curl -X "POST" "http://localhost:3000/api/datasources" \
    -H "Content-Type: application/json" \
     --user admin:admin \
     --data-binary @$i
done