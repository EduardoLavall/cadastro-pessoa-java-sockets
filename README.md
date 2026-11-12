# Cadastro de Pessoa - Cliente e Servidor

Atividade Java com interface Swing, sockets, threads e MySQL. O cliente envia uma `Pessoa` serializável ao servidor; uma thread atende cada conexão e o DAO insere os dados no banco. A resposta informa sucesso ou erro.

O exemplo da Aula 12 consulta uma pessoa pelo ID; esta atividade adapta a mesma estrutura para cadastrar. As classes `beans.Pessoa` do cliente e do servidor devem permanecer iguais.

## Como executar no Windows

Requisitos: JDK 8 ou superior e MySQL em `localhost:3306`. O driver JDBC já está em `Servidor/lib`.

1. Execute `1_PREPARAR_BANCO.bat` e informe a senha do MySQL. O script cria o banco e a tabela se ainda não existirem, sem apagar dados.
2. Execute `2_INICIAR_SERVIDOR.bat` e informe usuário e senha do MySQL. Mantenha a janela aberta.
3. Execute `3_INICIAR_CLIENTE.bat`, preencha o nome, sexo e idioma e clique em cadastrar.

O cliente e o servidor usam a porta 12345 no próprio computador. A senha é solicitada ao iniciar o servidor e não precisa ser colocada no código. O endereço JDBC usa `connectionTimeZone=America/Sao_Paulo`.

O SQL também pode ser executado manualmente a partir de `sql/01_criar_banco.sql`. Para verificar um cadastro, consulte `SELECT * FROM cadastro_pessoas.pessoa;` no MySQL.
