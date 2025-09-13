**insertOne**
**Explicação:** Insere um único documento em uma coleção.
**Saída esperada:** Confirmação de que a operação foi reconhecida e o identificador do documento inserido.
**Exemplo:**

```js
db.users.insertOne({ name: "Ana", age: 22, email: "ana@x.com" })
```

---

**insertMany**
**Explicação:** Insere vários documentos de uma só vez em uma coleção.
**Saída esperada:** Confirmação da operação e a lista de identificadores dos documentos inseridos.
**Exemplo:**

```js
db.users.insertMany(
  [{ name: "Bruno" }, { name: "Clara" }, { name: "Davi" }],
  { ordered: true }
)
```

---

**updateOne**
**Explicação:** Atualiza apenas o primeiro documento que corresponde ao filtro informado. Pode utilizar operadores como \$set ou \$inc.
**Saída esperada:** Indicação de quantos documentos foram encontrados e se algum foi modificado.
**Exemplo:**

```js
db.users.updateOne(
  { email: "ana@x.com" },
  { $set: { active: true }, $inc: { logins: 1 } }
)
```

---

**updateMany**
**Explicação:** Atualiza todos os documentos que correspondem ao filtro definido.
**Saída esperada:** Indicação de quantos documentos foram encontrados e quantos foram alterados.
**Exemplo:**

```js
db.users.updateMany(
  { role: "student" },
  { $set: { verified: true } }
)
```

---

**replaceOne**
**Explicação:** Substitui completamente o primeiro documento que corresponde ao filtro por um novo documento.
**Saída esperada:** Indicação se algum documento foi encontrado, substituído ou inserido (em caso de upsert).
**Exemplo:**

```js
const id = ObjectId("665aa0...");
db.users.replaceOne(
  { _id: id },
  { _id: id, name: "Nome Novo", age: 30, active: true }
)
```

---

**deleteOne**
**Explicação:** Remove o primeiro documento que corresponde ao filtro informado.
**Saída esperada:** Indicação de quantos documentos foram removidos.
**Exemplo:**

```js
db.users.deleteOne({ _id: ObjectId("665aa0...") })
```

---

**deleteMany**
**Explicação:** Remove todos os documentos que correspondem ao filtro definido.
**Saída esperada:** Indicação de quantos documentos foram removidos.
**Exemplo:**

```js
db.users.deleteMany({ inactive: true })
```

---

### Operadores de Busca

**Comparação (\$eq, \$ne, \$gt, \$gte, \$lt, \$lte, \$in, \$nin)**
**Explicação:** Permitem comparar valores, verificar faixas e checar se um valor está ou não em uma lista.
**Saída esperada:** Lista de documentos que atendem às condições de comparação.
**Exemplo:**

```js
db.users.find({ age: { $gte: 18, $lt: 30 } })
```

---

**Lógicos (\$and, \$or, \$not, \$nor)**
**Explicação:** Combinam múltiplas condições em uma consulta.
**Saída esperada:** Lista de documentos que correspondem às condições lógicas especificadas.
**Exemplo:**

```js
db.users.find({ $or: [ { city: "São Paulo" }, { city: "Rio de Janeiro" } ] })
```

---

**Elemento (\$exists, \$type)**
**Explicação:** Permitem verificar se um campo existe e identificar o tipo de dado armazenado.
**Saída esperada:** Documentos que possuem ou não possuem determinado campo, ou que apresentam um tipo específico de dado.
**Exemplo:**

```js
db.users.find({ phone: { $exists: true } })
```

---

**Avaliação (\$regex, \$expr)**
**Explicação:** Permitem consultas com expressões regulares e condições baseadas em expressões de agregação.
**Saída esperada:** Documentos que satisfazem padrões de texto ou condições calculadas em tempo de consulta.
**Exemplo:**

```js
db.users.find({ name: { $regex: /^an/i } })
```

---

**Arrays (\$all, \$elemMatch, \$size)**
**Explicação:** Fazem buscas em campos que armazenam arrays.
**Saída esperada:** Documentos que possuem arrays com os critérios definidos.
**Exemplo:**

```js
db.courses.find({ tags: { $all: ["db", "nosql"] } })
```

---

**Texto (\$text)**
**Explicação:** Realiza pesquisas de texto completo em campos que possuem índice de texto.
**Saída esperada:** Documentos que contenham os termos pesquisados nos campos indexados.
**Exemplo:**

```js
db.articles.createIndex({ title: "text", content: "text" })
db.articles.find({ $text: { $search: "\"climate change\" policy" } })
```

---

**Geoespacial (\$near, \$geoWithin, \$geoIntersects)**
**Explicação:** Permitem realizar buscas de documentos com base em localização geográfica.
**Saída esperada:** Documentos próximos a um ponto, dentro de uma área ou que intersectam uma região.
**Exemplo:**

```js
db.places.createIndex({ location: "2dsphere" })
db.places.find({
  location: {
    $near: {
      $geometry: { type: "Point", coordinates: [-46.633, -23.550] },
      $maxDistance: 5000
    }
  }
})
```
