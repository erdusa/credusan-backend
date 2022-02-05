-- TIPOESTADOCAPTACION

CREATE TABLE public.tipoestadocaptacion (
	tiescaid int4 NOT NULL,
	tiescanombre varchar(30) NOT NULL,
	CONSTRAINT tipoestadocaptacion_pkey PRIMARY KEY (tiescaid)
);

-- TIPOCAPTACION

CREATE TABLE public.tipocaptacion (
	tipcapid int4 NOT NULL,
	tipcapnombre varchar(30) NOT NULL,
	CONSTRAINT tipocaptacion_pkey PRIMARY KEY (tipcapid)
);

-- TIPODOCUMENTO

CREATE TABLE public.tipodocumento (
	tipdocid int4 NOT NULL,
	tipdocabreviatura varchar(3) NOT NULL,
	tipdocdescripcion varchar(50) NOT NULL,
	CONSTRAINT tipodocumento_pkey PRIMARY KEY (tipdocid)
);

-- ASOCIADO

CREATE TABLE public.asociado (
	asocid serial NOT NULL,
	asocactivo bool NOT NULL,
	asocfechanacimiento date NOT NULL,
	asocnombres varchar(30) NOT NULL,
	asocnumerodocumento varchar(15) NOT NULL,
	asocprimerapellido varchar(30) NOT NULL,
	asocsegundoapellido varchar(30) NULL,
	tipdocid int4 NOT NULL,
	CONSTRAINT asociado_pkey PRIMARY KEY (asocid),
	CONSTRAINT uk_asociado UNIQUE (tipdocid, asocnumerodocumento)
);

ALTER TABLE public.asociado ADD CONSTRAINT fk_asoc_tipdoc FOREIGN KEY (tipdocid) REFERENCES tipodocumento(tipdocid);

-- BENEFICIARIO

CREATE TABLE public.beneficiario (
	beneid serial NOT NULL,
	benenombres varchar(30) NOT NULL,
	beneporcentaje int4 NULL,
	beneprimerapellido varchar(30) NOT NULL,
	benesegundoapellido varchar(30) NULL,
	asocid int4 NOT NULL,
	CONSTRAINT beneficiario_pkey PRIMARY KEY (beneid)
);
CREATE INDEX idx_bene_asocid ON public.beneficiario(asocid);

ALTER TABLE public.beneficiario ADD CONSTRAINT fk_bene_asoc FOREIGN KEY (asocid) REFERENCES asociado(asocid);

-- CAPTACION

CREATE TABLE public.captacion (
	captid serial NOT NULL,
	captfechaapertura date NOT NULL,
	captnumerocuenta int4 NOT NULL,
	captsaldo float8 NOT NULL,
	asocid int4 NOT NULL,
	tipcapid int4 NOT NULL,
	tiescaid int4 NOT NULL,
	CONSTRAINT captacion_pkey PRIMARY KEY (captid),
	CONSTRAINT uk_captacion UNIQUE (tipcapid, captnumerocuenta)
);
CREATE INDEX idx_capt_asocid ON public.captacion(asocid);
CREATE INDEX idx_capt_tiescaid ON public.captacion(tiescaid);
CREATE INDEX idx_capt_tipcapid ON public.captacion(tipcapid);

ALTER TABLE public.captacion ADD CONSTRAINT fk_capt_asoc FOREIGN KEY (asocid) REFERENCES asociado(asocid);
ALTER TABLE public.captacion ADD CONSTRAINT fk_capt_tiesca FOREIGN KEY (tiescaid) REFERENCES tipoestadocaptacion(tiescaid);
ALTER TABLE public.captacion ADD CONSTRAINT fk_capt_tipcap FOREIGN KEY (tipcapid) REFERENCES tipocaptacion(tipcapid);

-- CAPTACIONEXTRACTO

CREATE TABLE public.captacionextracto (
	capextid serial NOT NULL,
	capextfecha date NOT NULL,
	capexthora time NOT NULL,
	capextvalorcredito float8 NOT NULL,
	capextvalordebito float8 NOT NULL,
	captid int4 NOT NULL,
	CONSTRAINT captacionextracto_pkey PRIMARY KEY (capextid)
);

ALTER TABLE public.captacionextracto ADD CONSTRAINT fk_capext_capt FOREIGN KEY (captid) REFERENCES captacion(captid);