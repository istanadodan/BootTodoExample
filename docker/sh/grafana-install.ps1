helm install grafana grafana/grafana `
    --set datasources."datasources`.yaml".apiVersion=1 `
    --set datasources."datasources`.yaml".datasources[0].name=Prometheus `
    --set datasources."datasources`.yaml".datasources[0].type=prometheus `
    --set datasources."datasources`.yaml".datasources[0].url=http://prometheus-server.monitoring.svc.cluster.local `
    --set datasources."datasources`.yaml".datasources[0].access=proxy `
    --set datasources."datasources`.yaml".datasources[0].isDefault=true `
    -n monitoring