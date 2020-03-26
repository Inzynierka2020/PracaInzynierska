# start SQL Server, start the script to create the DB
echo "ENTRYPOINT";
/opt/mssql/bin/sqlservr & bash ./scripts/initialize.sh;