INSERT IGNORE INTO `users` (`id`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `email`, `enabled`, `name`, `password`, `username` ) VALUES
    (1, true, true, true,'user@gmail.com', true,'user','$2a$10$5pDam4WIEpv/s/LfBP3hgOO348jYtKaPwXM6rWsfwzw8YGEpIIy9S','user'),
    (2, true, true, true, 'admin@gmail.com', true,'admin','$2a$10$gqHrslMttQWSsDSVRTK1OehkkBiXsJ/a4z2OURU./dizwOQu5Lovu','admin');

INSERT IGNORE INTO `roles` VALUES (1,'ADMIN'),(2,'USER');

INSERT IGNORE INTO `users_roles` VALUES (2,1),(1,2);

INSERT IGNORE INTO `languages` (`id`, `locale`, `messagekey`, `messagecontent`) VALUES
     (1,'de','welcome.logoff','Abmelden'),
     (2,'en','welcome.logoff','Logoff'),
     (3,'de','buttons.profile','Profil'),
     (4,'en','buttons.profile','Profile'),
     (5,'de','buttons.newuser','Benutzer hinzufügen'),
     (6,'en','buttons.newuser','Add user'),
     (7,'de','buttons.userlist','Benutzerliste'),
     (8,'en','buttons.userlist','Userlist'),
     (9,'de','buttons.lang','Sprachen'),
     (10,'en','buttons.lang','Languages'),
     (11,'de','buttons.lang.german','Deutsch'),
     (12,'de','buttons.lang.english','Englisch'),
     (13,'en','buttons.lang.german','German'),
     (14,'en','buttons.lang.english','English'),
     (15,'de','buttons.roles','Rollen'),
     (16,'en','buttons.roles','Roles'),
     (17,'de','app.title','Geheimnisse v1.0'),
     (18,'en','app.title','Secrets v1.0'),
     (19,'de','page.role.name','Rolle'),
     (20,'en','page.role.name','Role'),
     (21,'de','page.role.nr','Nr.'),
     (22,'en','page.role.nr','Nr.'),
     (23,'de','page.role.new','Neue Rolle'),
     (24,'en','page.role.new','New role'),
     (25,'de','page.role.submit','Übernehmen'),
     (26,'en','page.role.submit','Submit'),
     (27,'de','page.action','Aktion'),
     (28,'en','page.action','Action'),
     (29,'de','page.delete','Löschen'),
     (30,'en','page.delete','Delete'),
     (31,'de','page.back','Zurück'),
     (32,'en','page.back','Back'),
     (33,'de','page.profile.submit','Übernehmen'),
     (34,'en','page.profile.submit','Submit'),
     (35,'de','page.profile.title','Angaben zum User...'),
     (36,'en','page.profile.title','Userdata...'),
     (37,'de','page.profile.name','Name:'),
     (38,'en','page.profile.name','Name:'),
     (39,'de','page.profile.username','Usernamen:'),
     (40,'en','page.profile.username','Username:'),
     (41,'de','page.profile.email','E-Mail:'),
     (42,'en','page.profile.email','E-Mail:'),
     (43,'de','page.profile.password','Passwort:'),
     (44,'en','page.profile.password','Password:'),
     (45,'de','page.admin.nr','Nr.'),
     (46,'en','page.admin.nr','Nr.'),
     (47,'de','page.admin.username','Username'),
     (48,'en','page.admin.username','Username'),
     (49,'de','page.admin.name','Name'),
     (50,'en','page.admin.name','Name'),
     (51,'en','page.admin.email','E-Mail'),
     (52,'de','page.admin.email','E-Mail'),
     (53,'en','page.admin.roles','Roles'),
     (54,'de','page.admin.roles','Rolle(n)'),
     (55,'en','page.admin.action','Aktion'),
     (56,'de','page.admin.action','Action'),
     (57,'en','page.admin.delete','Löschen'),
     (58,'en','page.admin.delete','Delete'),
     (59,'de','page.admin.btn.roles','Rollen'),
     (60,'en','page.admin.btn.roles','Roles'),
     (61,'de','page.admin.btn.delete','Löschen'),
     (62,'en','page.admin.btn.delete','Delete'),
     (63,'de','page.admin.btn.lock','Sperren'),
     (64,'en','page.admin.btn.lock','Lock'),
     (65,'de','page.admin.btn.unlock','Entsperren'),
     (66,'en','page.admin.btn.unlock','Unlock'),
     (67,'de','page.admin.title','Liste aller Benutzer'),
     (68,'en','page.admin.title','List of all users'),
     (69,'de','page.admin.status','Info'),
     (70,'en','page.admin.status','Info'),
     (71,'de','page.userroles.title','Rollen des Benutzers: '),
     (72,'en','page.userroles.title','Roles from User: '),
     (73,'de','page.login.title','Anmeldung'),
     (74,'en','page.login.title','Login'),
     (75,'de','page.login.email','Username oder E-Mail:'),
     (76,'en','page.login.email','Username or E-Mail:'),
     (77,'de','page.login.password','Passwort:'),
     (78,'en','page.login.password','Password:'),
     (79,'de','page.login.email.placeholder','Eingabe des E-Mail'),
     (80,'en','page.login.email.placeholder','Input of E-Mail'),
     (81,'de','page.login.password.placeholder','Eingabe des Passwort'),
     (82,'en','page.login.password.placeholder','Input of Password'),
     (83,'de','page.login.submit','Anmelden'),
     (84,'en','page.login.submit','Login'),
     (85,'de','msg.invalidlogin','Ungültige E-Mail, Username oder Passwort!'),
     (86,'en','msg.invalidlogin','Invalid Email, Username or Password!'),
     (87,'de','msg.loggedout','Sie wurden abgemeldet!'),
     (88,'en','msg.loggedout','You have been logged out.'),
     (89,'de','failure.headline','Fehlerhafte Anmeldung!'),
     (90,'en','failure.headline','Incorrect login!'),
     (91,'de','failure.message','Ihre IP-Adresse wurde aufgezeichnet!'),
     (92,'en','failure.message','Your IP address has been recorded!'),
     (93,'de','login','Anmelden'),
     (94,'en','login','Login'),
     (95,'de','error.headline','Ups, ein Fehler ist aufgetreten!'),
     (96,'en','error.headline','Something went wrong!'),
     (97,'de','error.message','Wir arbeiten schon an der Lösung.'),
     (98,'en','error.headline','We are already working on the solution.'),
     (99,'de','error404.headline','Leider konnten wir die von Ihnen gesuchte Seite nicht finden.'),
     (100,'en','error404.headline','Sorry, we couldn''t find the page you were looking for.'),
     (101,'de','error404.message','Wir arbeiten schon an der Lösung.'),
     (102,'en','error404.message','We are already working on the solution.'),
     (103,'de','error500.headline','Entschuldigung, da ist etwas schief gelaufen!'),
     (104,'en','error500.headline','Sorry, something went wrong!'),
     (105,'de','error500.message','Wir arbeiten schon an der Lösung.'),
     (106,'en','error500.message','We are already working on the solution.'),
     (107,'de','page.login.register','Registrieren'),
     (108,'en','page.login.register','Register'),
     (109,'de','page.login.noregister','Falls Sie keinen Account haben...'),
     (110,'en','page.login.noregister','If you do not have an account...'),
     (111,'de','page.register.title','Registrierung'),
     (112,'en','page.register.title','Registration'),
     (113,'de','page.register.success','Sie haben sich erfolgreich registriert!'),
     (114,'en','page.register.success','You have successfully registered!'),
     (115,'de','page.register.name','Name:'),
     (116,'en','page.register.name','Name:'),
     (117,'de','page.register.name.placeholder','Vorname & Nachname'),
     (118,'en','page.register.name.placeholder','Firstname & Surname'),
     (119,'de','page.register.username','Username:'),
     (120,'en','page.register.username','Username:'),
     (121,'de','page.register.username.placeholder','Username'),
     (122,'en','page.register.username.placeholder','Username'),
     (123,'de','page.register.email','E-Mail:'),
     (124,'en','page.register.email','E-Mail:'),
     (125,'de','page.register.email.placeholder','E-Mail'),
     (126,'en','page.register.email.placeholder','E-Mail'),
     (127,'de','page.register.password','Passwort:'),
     (128,'en','page.register.password','Password:'),
     (129,'de','page.register.password.placeholder','Passwort'),
     (130,'en','page.register.password.placeholder','Password'),
     (131,'de','page.register.submit','Registrierung'),
     (132,'en','page.register.submit','Registration'),
     (133,'de','page.register.registered','Wenn Sie sich schon registriert haben...'),
     (134,'en','page.register.registered','In case you already registered...'),
     (135,'de','page.register.login','Anmelden'),
     (136,'en','page.register.login','Login'),
     (137,'de','button.help','Hilfe'),
     (138,'en','button.help','Help'),
     (139,'de','help.title','Über...'),
     (140,'en','help.title','About...'),
     (141,'de','help.text','Dies Programm basiert auf Spring 6, Spring Boot 3, Spring Security 6, Thymeleaf, Bootstrap 5.3.2, JQuery 3.7.1, MySQL 8 und Java 17.<br /><br />Das Programm kann damit als Startpunkt für eigene, abgesicherte Anwendungen dienen, da zudem ein Rollenkonzept implementiert wurde, das es erlaubt einem Benutzer mehrere Rollen zuzuweisen.<br /><br />Zusätzlich wurde eine Datenbankbasierte I18N-Lokalisierung, mit zwei Sprachen (Deutsch & Englisch) eingebaut.<br /><br />Inspiriert wurde die Entwicklung durch die Arbeit folgender Personen bzw. Firmen mit veröffentlichten Beispielen:<br /><br />- Eugen from www.baeldung.com<br />- Ramesh Fadatare von www.javaguides.net<br />- Phrase von www.phrase.com'),
     (142,'en','help.text','This program is based on Spring 6, Spring Boot 3, Spring Security 6, Thymeleaf, Bootstrap 5.3.2, JQuery 3.7.1, MySQL 8 and Java 17.<br /><br />The program can therefore serve as a starting point for your own secure applications. A role concept has also been implemented that allows a user to be assigned several roles.<br /><br />In addition, a database-based I18N localisation with two languages (German & English) was implemented.<br /><br />The development was inspired by the work of the following people and companies with published examples:<br /><br />- Eugen from www.baeldung.com<br />- Ramesh Fadatare from www.javaguides.net<br />- Phrase from www.phrase.com');
