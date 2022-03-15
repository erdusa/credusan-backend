DELETE FROM creditos.credito;
DELETE FROM asociado;
DELETE FROM tipodocumento;
DELETE FROM creditos.tipoestadocredito;

insert into tipodocumento (tipdocid, tipdocabreviatura, tipdocdescripcion) values
    (1, 'RC', 'REGISTRO CIVIL'),
    (2, 'TI', 'TARJETA DE IDENTIDAD'),
    (3, 'CC', 'CEDULA DE CIUDADANIA');

INSERT INTO creditos.tipoestadocredito (tiescrid, tiescrnombre) VALUES
    (1, 'VIGENTE'),
    (2, 'SALDADO');


--Los siguientes son necesarios ya que al crear el asociado se crea la cuenta de aportes
DELETE FROM tipocaptacion;
DELETE FROM tipoestadocaptacion;

INSERT INTO public.tipocaptacion (tipcapid, tipcapnombre) VALUES
    (1, 'APORTES'),
    (2, 'AHORROS');

INSERT INTO public.tipoestadocaptacion (tiescaid, tiescanombre) VALUES
    (1, 'ACTIVA'),
    (2, 'SALDADA');

