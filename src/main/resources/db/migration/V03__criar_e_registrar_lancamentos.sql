CREATE TABLE lancamento (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	id_categoria BIGINT(20) NOT NULL,
	id_pessoa BIGINT(20) NOT NULL,
	FOREIGN KEY (id_categoria) REFERENCES categoria(id),
	FOREIGN KEY (id_pessoa) REFERENCES pessoa(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Salário mensal', '2023-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 1);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Supermercado', '2023-02-10', '2023-02-10', 100.32, null, 'DESPESA', 2, 2);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Academia', '2023-06-10', null, 120, null, 'DESPESA', 3, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Conta de luz', '2023-02-10', '2023-02-10', 110.44, null, 'DESPESA', 3, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Conta de água', '2023-06-10', null, 200.30, null, 'DESPESA', 3, 5);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Restaurante', '2023-03-10', '2023-03-10', 1010.32, null, 'DESPESA', 4, 6);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Venda vídeo game', '2023-06-10', null, 500, null, 'RECEITA', 1, 7);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Clube', '2023-03-10', '2023-03-10', 400.32, null, 'DESPESA', 4, 8);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Impostos', '2023-06-10', null, 123.64, 'Multas', 'DESPESA', 3, 9);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Multa', '2023-04-10', '2023-04-10', 665.33, null, 'DESPESA', 5, 10);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Padaria', '2023-06-10', null, 8.32, null, 'DESPESA', 1, 5);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Papelaria', '2023-04-10', '2023-04-10', 2100.32, null, 'DESPESA', 5, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Almoço', '2023-06-10', null, 1040.32, null, 'DESPESA', 4, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Café', '2023-04-10', '2023-04-10', 4.32, null, 'DESPESA', 4, 2);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Lanche', '2023-06-10', null, 10.20, null, 'DESPESA', 4, 1);