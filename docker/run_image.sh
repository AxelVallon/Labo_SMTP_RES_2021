#!/bin/bash

# we open 2 ports on the server because we need port 25 for SMTP and port 8282 
# for the web view of the system
docker run -p 25:25 -p 8282:8282 axel/mock-server 