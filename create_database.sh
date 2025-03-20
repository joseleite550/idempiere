#!/bin/bash

# Define a senha para o usuário 'adempiere'
export PGPASSWORD='adempiere'

##  Cria o banco de dados 'idempiere' com o template0, codificação UNICODE, e com o usuário e dono 'adempiere'
echo "Criando o banco de dados 'idempiere' com o template0, codificação UNICODE, e com o usuário e dono 'adempiere'"
createdb --template=template0 -E UNICODE -O adempiere -U adempiere idempiere
echo "Definindo o search_path para o usuário 'adempiere'"
# Define o search_path para o usuário 'adempiere'
psql -d idempiere -U adempiere -c "ALTER ROLE adempiere SET search_path TO adempiere, pg_catalog"
echo "Criando a extensão 'uuid-ossp' no banco de dados 'idempiere'"
# Cria a extensão 'uuid-ossp' no banco de dados 'idempiere'
psql -d idempiere -U adempiere -c 'CREATE EXTENSION "uuid-ossp"'
echo "Exportando dump"
jar xvf org.adempiere.server-feature/data/seed/Adempiere_pg.jar
mv Adempiere_pg.dmp /tmp/Adempiere_pg.dmp
psql -d idempiere -U adempiere -f /tmp/Adempiere_pg.dmp
echo "Aplicando Scripts"
bash RUN_SyncDBDev.sh
echo 'Banco de dados idempiere criado com sucesso'
# Limpa a variável de senha por segurança
unset PGPASSWORD

