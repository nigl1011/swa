DROP SEQUENCE hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START WITH 5000;

INSERT INTO kunde (id, nachname, vorname, seit, art, familienstand_fk, newsletter, rabatt, umsatz, email, password, erzeugt, aktualisiert,geburtsdatum,geschlecht) VALUES (1,'Admin','Admin','01.01.2001','F',NULL,1,'0,1',0,'admin@hska.de','1','01.08.2006 00:00:00','01.08.2006 00:00:00','01.01.1990',1);
INSERT INTO kunde (id, nachname, vorname, seit, art, familienstand_fk, newsletter, rabatt, umsatz, email, password, erzeugt, aktualisiert,geburtsdatum,geschlecht) VALUES (2,'Nine','Glas','01.01.2001','F',NULL,1,'0,1',100.00,'nine@hska.de','1','01.08.2006 00:00:00','01.08.2006 02:00:00','19.07.1990',0);
INSERT INTO kunde (id, nachname, vorname, seit, art, familienstand_fk, newsletter, rabatt, umsatz, email, password, erzeugt, aktualisiert,geburtsdatum,geschlecht) VALUES (3,'Kadda','Blu','01.01.2002','F',NULL,1,'0,8',10.00,'kadda@hska.de','1','01.08.2006 00:00:00','01.08.2006 02:00:00','15.02.1997',0);
INSERT INTO kunde (id, nachname, vorname, seit, art, familienstand_fk, newsletter, rabatt, umsatz, email, password, erzeugt, aktualisiert,geburtsdatum,geschlecht) VALUES (4,'Andreas','Tube','01.01.2002','F',NULL,1,'0,8',10.00,'andi@hska.de','1','01.08.2006 00:00:00','01.08.2006 02:00:00','15.02.1997',1);
INSERT INTO kunde (id, nachname, vorname, seit, art, familienstand_fk, newsletter, rabatt, umsatz, email, password, erzeugt, aktualisiert,geburtsdatum,geschlecht) VALUES (5,'Beni','Socke','01.01.2002','F',NULL,1,'0,2',10000.00,'beni@hska.de','1','01.08.2006 00:00:00','01.08.2006 02:00:00','19.02.1989',1);
INSERT INTO kunde (id, nachname, vorname, seit, art, familienstand_fk, newsletter, rabatt, umsatz, email, password, erzeugt, aktualisiert,geburtsdatum,geschlecht) VALUES (6,'Isabella','Kiwi','01.01.2001','P',NULL,1,'0,2',870.00,'isi@hska.de','1','01.08.2006 00:00:00','01.08.2006 02:00:00','12.08.1990',0);

INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (1,'76133','Karlsruhe','Moltkeweg','30',1,'01.08.2006 00:00:00','01.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (2,'76133','Karlsruhe','Moltkeweg','31',2,'02.08.2006 00:00:00','02.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (3,'76133','Karlsruhe','Silvertal','32',3,'03.08.2006 00:00:00','03.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (4,'76133','Karlsruhe','Ottobaumstr','33',4,'04.08.2006 00:00:00','04.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (5,'76133','Karlsruhe','Moltkeweg','34',5,'05.08.2006 00:00:00','05.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (6,'76133','Karlsruhe','Moltkeweg','35',6,'06.08.2006 00:00:00','06.08.2006 00:00:00');

INSERT INTO kunde_hobby (kunde_fk, hobby_fk) VALUES (1,0);
INSERT INTO kunde_hobby (kunde_fk, hobby_fk) VALUES (2,1);
INSERT INTO kunde_hobby (kunde_fk, hobby_fk) VALUES (3,0);
INSERT INTO kunde_hobby (kunde_fk, hobby_fk) VALUES (4,2);
INSERT INTO kunde_hobby (kunde_fk, hobby_fk) VALUES (5,1);
INSERT INTO kunde_hobby (kunde_fk, hobby_fk) VALUES (6,2);

INSERT INTO wartungsvertrag (nr, datum, inhalt, kunde_fk, idx, erzeugt, aktualisiert) VALUES (1,'31.01.2005','Wartungsvertrag_1_K101',1,0,'01.08.2006 00:00:00','01.08.2006 00:00:00');
INSERT INTO wartungsvertrag (nr, datum, inhalt, kunde_fk, idx, erzeugt, aktualisiert) VALUES (2,'31.01.2006','Wartungsvertrag_2_K101',2,1,'02.08.2006 00:00:00','02.08.2006 00:00:00');
INSERT INTO wartungsvertrag (nr, datum, inhalt, kunde_fk, idx, erzeugt, aktualisiert) VALUES (1,'30.06.2006','Wartungsvertrag_1_K102',3,0,'03.08.2006 00:00:00','03.08.2006 00:00:00');
