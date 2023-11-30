FROM ubuntu:latest
LABEL authors="rebol"

ENTRYPOINT ["top", "-b"]