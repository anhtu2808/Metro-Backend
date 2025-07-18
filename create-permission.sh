#!/bin/bash
# Load environment variables from .env.dev
set -a
source .env.dev
set +a

#API_URL="https://backend-metro.anhtudev.works/api/v1/permissions"

API_URL="localhost:8888/api/v1/permissions"

# List of permissions to create: NAME|DESCRIPTION
permissions=(
  "DYNAMIC_PRICE_MASTER_CREATE|Allow to create dynamic price master"
  "DYNAMIC_PRICE_MASTER_READ|Allow to read dynamic price master"
  "DYNAMIC_PRICE_MASTER_READ_ALL|Allow to read all dynamic price master"
  "DYNAMIC_PRICE_MASTER_UPDATE|Allow to update dynamic price master"
  "DYNAMIC_PRICE_MASTER_DELETE|Allow to delete dynamic price master"
)

for entry in "${permissions[@]}"; do
  NAME="${entry%%|*}"
  DESC="${entry#*|}"

  curl -X POST "$API_URL" \
    -H "Accept: application/json" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtZXRyby5jb20iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTgzODg4OTg4MiwiaWF0IjoxNzUyNDg5ODgyLCJqdGkiOiI4YTZiYmZjNS04NjcyLTQ2M2YtOGUxZS01N2E5OWJkZmVmOWUiLCJzY29wZSI6IlJPTEVfTUFOQUdFUiBidXNyb3V0ZTpjcmVhdGUgdXNlcjpjcmVhdGUifQ.pYj_VhR99QJGSXkf-MQIBvB3_MvBF7HpdAjGZs0Bxz4" \
    -H "Content-Type: application/json" \
    -d '{
      "name": "'"$NAME"'",
      "description": "'"$DESC"'"
    }'

  echo
done
