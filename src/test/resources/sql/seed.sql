insert into users (id, first_name, last_name, display_name, avatar_url, location)
values  ('3f238db6-4e9a-456b-8207-915e0e4b50cf', 'Luiz', 'Souza', 'luizaugustocs', 'https://123,com', 'Maringa'),
        ('8851e062-8988-46c8-bc7a-cbca77797d3c', 'Vaughn', 'Torp', 'vau.tor', 'https://123,com', 'Seattle'),
        ('717ea9f6-967d-4401-82b0-620bcfc1369a', 'Mylene', 'Wisoky', 'myl.wis', 'https://123,com', 'Washington'),
        ('8311db28-662d-4e39-967e-3512e8ece4a8', 'Tristin', 'Hills', 'tri.hil', 'https://123,com', 'Los Angeles'),
        ('1ae149e8-4792-4598-9e99-66dd52c7a45a', 'Robert', 'Zboncak', 'rob.zbo', 'https://123,com', 'New York'),
        ('7c7bebbf-221e-4c6d-9405-577d9a730bfd', 'Aaliyah', 'Spencer', 'aal.spe', 'https://123,com', 'Austin'),
        ('586d6841-5ba6-47c3-91e4-8165f119f218', 'Julie', 'Emmerich', 'jul.emm', 'https://123,com', 'Orlando');

insert into team (id, name, lead_id)
values  ('062de4c6-08e2-40da-84f3-ac4d9296889e', 'Front-end Development', '8851e062-8988-46c8-bc7a-cbca77797d3c'),
        ('ebf26e6d-7ab9-4e93-b233-f28d5f0a93ed', 'Back-end Development', '717ea9f6-967d-4401-82b0-620bcfc1369a');

insert into membership (team_id, user_id, role_id)
values  ('062de4c6-08e2-40da-84f3-ac4d9296889e', '8851e062-8988-46c8-bc7a-cbca77797d3c', 'e870f742-2627-426e-8287-5bbf391ea9e9'),
        ('ebf26e6d-7ab9-4e93-b233-f28d5f0a93ed', '8311db28-662d-4e39-967e-3512e8ece4a8', 'e870f742-2627-426e-8287-5bbf391ea9e9'),
        ('062de4c6-08e2-40da-84f3-ac4d9296889e', '8311db28-662d-4e39-967e-3512e8ece4a8', '11e30283-a92a-42fb-add8-b6c9eaf85e29');