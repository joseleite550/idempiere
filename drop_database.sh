#!/bin/bash

# Elevando para o usuário postgres
sudo su - postgres <<EOF

# Executando o comando psql para remover o banco de dados
psql -U postgres -c "DROP DATABASE idempiere;"

# Saindo da conta postgres
exit
EOF
