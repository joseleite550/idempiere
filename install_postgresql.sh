#!/bin/bash

# Crie a configuração do repositório de arquivos
echo "Configurando o repositório do PostgreSQL..."
sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'

# Importe a chave de assinatura do repositório
echo "Importando a chave de assinatura do repositório..."
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -

# Atualize as listas de pacotes
echo "Atualizando as listas de pacotes..."
sudo apt-get update

# Instale a versão 15 do PostgreSQL
echo "Instalando o PostgreSQL 15..."
sudo apt-get -y install postgresql-15

echo "Instalação concluída!"
