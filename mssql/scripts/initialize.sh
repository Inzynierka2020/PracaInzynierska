#wait for the SQL Server to come up
echo "INITIALIZING DATABASE";
sleep 30s;
#run the setup script to create the DB and the schema in the DB
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P Password@123 -i ./scripts/setup.sql;
echo "DATABASE INITIALIZED";
while :; do sleep 1; done;