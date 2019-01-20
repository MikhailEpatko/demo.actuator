# demo.actuator

See `http://localhost:8080/api` for actuator's endpoints.

See `http://localhost:8080/api/status` for application status.

See `http://localhost:8080/api/env` for system properties, system environment and application configurations.

Stop application: `curl -X POST localhost:8080/api/shutdown` (only `POST` method supports).


By default, metrics are exported to Graphite running on your local machine. 

The Graphite server host and port to use can be provided adding next lines to the `application.properties`:

```
management.metrics.export.graphite.host=graphite.example.com
management.metrics.export.graphite.port=9004
```

By default, metrics are exported to Influx running on your local machine.

The location of the Influx server to use can be provided adding next line to the `application.properties`:

```
management.metrics.export.influx.uri=http://influx.example.com:8086
