FROM microsoft/mssql-server-linux:latest

COPY scripts/. /scripts
RUN chmod +x /scripts/.

CMD /bin/bash ./scripts/entrypoint.sh