#!/bin/sh
set -e

HOSTNAME_ARG="$(hostname)"
OWN_IP_ARG="$(hostname -i | awk '{print $1}')"
FOLDER_ARG="/app/files"
NAMING_SERVER_IP="172.20.0.10"

echo "Starting client with:"
echo "hostname      = $HOSTNAME_ARG"
echo "own IP        = $OWN_IP_ARG"
echo "folder        = $FOLDER_ARG"
echo "naming server = $NAMING_SERVER_IP"

exec java -jar client.jar "$HOSTNAME_ARG" "$OWN_IP_ARG" "$FOLDER_ARG" "$NAMING_SERVER_IP"