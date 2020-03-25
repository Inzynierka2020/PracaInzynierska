#wait for the SQL Server to come up

echo "INITIALIZE 1"

sleep 90s;

echo "INITIALIZE 2"

#run the setup script to create the DB and the schema in the DB
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Password@123 -i ./scripts/setup.sql;

while :; do sleep 1; done