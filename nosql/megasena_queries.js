db.megasena.find({'Ganhadores 5 acertos': 5});

db.megasena.insertOne({ "Concurso": 99999, "Data do Sorteio": "04/09/2024", "Bola1": 1, "Bola2": 2, "Bola3": 3, "Bola4": 4, "Bola5": 5, "Bola6": 6, "Ganhadores 6 acertos": 0, "Rateio 6 acertos": "R$0,00", "Ganhadores 5 acertos": 17, "Rateio 5 acertos": "R$39.158,92", "Arrecadação Total": "R$0,00", "Estimativa prêmio": "R$0,00", "Acumulado Sorteio Especial Mega da Virada": "R$0,00" });

db.ganhadores.insertOne({"Concurso":99999, "CPF":12345678900});

db.ganhadores.insertOne({"Concurso":99999, "CPF":12345678900, "Nome":"Coffin Joe"});

db.ganhadores2.count();

db.ganhadores2.insertOne({
"Concurso":99999,
"CPF":12345678900
});

db.ganhadores2.insertOne({
"Concurso":99999,
"CPF":12345678900,
"Nome":"Coffin Joe"
});

db.valores.insertOne({
"_id" : 111,
"valor" : 1000
});

db.valores.insertOne({
"valor" : 3000
});

db.valores.find();
