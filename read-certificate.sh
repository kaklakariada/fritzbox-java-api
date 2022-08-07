#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

port=443
host=fritz.box
# Adding '|| true' is necessary as openssl returns exit code 1 and logs "poll error".
certificate=$(openssl s_client -connect "$host:$port" < /dev/null 2>/dev/null) || true

if [ -z "${certificate}" ]; then
    >&2 log_error "Error connecting $host:$port"
    openssl s_client -connect "$host:$port" < /dev/null
    exit 1
fi

fingerprint_hex=$(echo "$certificate" \
                | openssl x509 -fingerprint -sha256 -noout -in /dev/stdin \
                | sed 's/SHA256 Fingerprint=//' \
                | sed 's/://g')

fingerprint_base64=$(echo "$fingerprint_hex" \
                | xxd -r -p | base64)

echo Fingerprint hex: $fingerprint_hex
echo Fingerprint base64: $fingerprint_base64
