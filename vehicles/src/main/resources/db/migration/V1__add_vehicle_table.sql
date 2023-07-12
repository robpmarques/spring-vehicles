CREATE TABLE vehicles (
	id uuid NOT NULL,
	veiculo varchar(255) NOT NULL,
	marca varchar(255) NOT NULL,
	cor varchar(255) NOT NULL,
	ano smallint NOT NULL,
	descricao varchar(255) NOT NULL,
	vendido bool NOT NULL default false,
	created varchar(255) NOT NULL,
	updated timestamp NULL,
	CONSTRAINT vehicles_pkey PRIMARY KEY (id)
);

INSERT INTO public.vehicles
(id, veiculo, marca, cor, ano, descricao, vendido, created)
VALUES('b89ca247-05fc-4c0c-ad61-c5a86d02b004', 'Civic', 'Honda', 'Branco', 2023, 'Honda Civic Descrição', false, now() - interval '2 weeks'),
('47b3b487-5dd0-491a-8347-8b840bbf29c9', 'TORO ENDURANCE TURBO', 'Fiat', 'Vermelho', 2023, 'Toro Endurance Turbo', false, now()),
('580c4476-015c-4612-a031-a562b1b629b0', 'FASTBACK IMPETUS TURBO', 'Fiat', 'Preto', 2022, 'FASTBACK IMPETUS Descrição', false, now())