#!/usr/bin/env bash
set -e

# default params, can be overwrite via env variables
API_BASE_URL="${API_BASE_URL:-http://localhost:8083}"
DB_CUSTOMER_URL="${DB_CUSTOMER_URL:-jdbc:postgresql://localhost:5432/customer}"
DB_NOTIFICATION_URL="${DB_NOTIFICATION_URL:-jdbc:postgresql://localhost:5432/notification}"
DB_FRAUD_URL="${DB_FRAUD_URL:-jdbc:postgresql://localhost:5432/fraud}"
DB_USER="${DB_USER:-admin}"
DB_PASSWORD="${DB_PASSWORD:-admin}"

echo "Running BDD tests with parameters:"
echo "API_BASE_URL=$API_BASE_URL"
echo "DB_CUSTOMER_URL=$DB_CUSTOMER_URL"
echo "DB_NOTIFICATION_URL=$DB_NOTIFICATION_URL"
echo "DB_FRAUD_URL=$DB_FRAUD_URL"

mvn verify \
    -Dbdd=true \
    -Dapi.base.url="$API_BASE_URL" \
    -Ddb.customer.url="$DB_CUSTOMER_URL" \
    -Ddb.notification.url="$DB_NOTIFICATION_URL" \
    -Ddb.fraud.url="$DB_FRAUD_URL" \
    -Ddb.user="$DB_USER" \
    -Ddb.password="$DB_PASSWORD"
