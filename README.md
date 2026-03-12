# Event Registration - Docker Setup

## 1) Start everything (business services + logging stack)

```bash
docker compose up --build -d
```

This starts:
- Core infrastructure: Postgres (3 DB instances), Redis, Kafka, Zookeeper
- Application services: Eureka, API Gateway, User, Product, Event, Booking
- Observability/logging stack: Promtail, Loki, Prometheus, Tempo, Grafana

## 2) Useful endpoints

- Eureka: http://localhost:8761
- API Gateway: http://localhost:4004
- Kafka UI: http://localhost:8080
- Grafana: http://localhost:3000 (admin/admin)
- Prometheus: http://localhost:9090
- Loki API: http://localhost:3100
- Tempo API: http://localhost:3200

## 3) Start only logging stack

```bash
docker compose -f log-service/docker-compose.yml up -d
```

Use this mode when your application services are running outside Docker and you only need central log collection + visualization.

## 4) Stop everything

```bash
docker compose down
```

To remove volumes too:

```bash
docker compose down -v
```
