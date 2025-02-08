-- Créer la table Utilisateur
CREATE TABLE Utilisateur (
   id_user SERIAL PRIMARY KEY,
   email VARCHAR(100) UNIQUE NOT NULL,
   password_hash TEXT NOT NULL,
   name VARCHAR(100) NOT NULL,
   picture_url TEXT,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Créer la table Voyage
CREATE TABLE Voyage (
   id_voyage SERIAL PRIMARY KEY,
   id_user INT NOT NULL,
   nom_voyage VARCHAR(100) NOT NULL,
   description_voyage TEXT,
   date_debut_voyage DATE NOT NULL,
   date_fin_voyage DATE NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user)
);

-- Créer la table Activity
CREATE TABLE Activity (
   id_activity SERIAL PRIMARY KEY,
   id_voyage INT NOT NULL,
   nom_activity VARCHAR(100) NOT NULL,
   lieu_activity VARCHAR(100),
   date_activity DATE NOT NULL,
   time_activity TIME,
   description_activity TEXT,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (id_voyage) REFERENCES Voyage(id_voyage)
);

-- Créer la table Vote
CREATE TABLE Vote (
   id_vote SERIAL PRIMARY KEY,
   id_user INT NOT NULL,
   id_activity INT NOT NULL,
   vote_value INT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user),
   FOREIGN KEY (id_activity) REFERENCES Activity(id_activity),
   UNIQUE (id_user, id_activity)
);

-- Créer la table Inviter
CREATE TABLE Inviter (
   id_invitation SERIAL PRIMARY KEY,
   id_user INT NOT NULL,
   id_user_invite INT NOT NULL,
   id_voyage INT NOT NULL,
   status VARCHAR(50) DEFAULT 'pending',
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user),
   FOREIGN KEY (id_user_invite) REFERENCES Utilisateur(id_user),
   FOREIGN KEY (id_voyage) REFERENCES Voyage(id_voyage),
   UNIQUE (id_user, id_user_invite, id_voyage)
);

-- Créer la table Message
CREATE TABLE Message (
   id_message SERIAL PRIMARY KEY,
   id_user INT NOT NULL,
   id_voyage INT NOT NULL,
   content TEXT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user),
   FOREIGN KEY (id_voyage) REFERENCES Voyage(id_voyage)
);
