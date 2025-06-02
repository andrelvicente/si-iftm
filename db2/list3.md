## ✅ 1) Função `eh_ano_atual`

**Descrição**: Verifica se o ano de uma data informada é o ano atual.

```sql
CREATE OR REPLACE FUNCTION eh_ano_atual(data_input DATE)
RETURNS BOOLEAN AS $$
BEGIN
  RETURN EXTRACT(YEAR FROM data_input) = EXTRACT(YEAR FROM CURRENT_DATE);
END;
$$ LANGUAGE plpgsql;
```

---

## ✅ 2) Função `extrair_frase`

**Descrição**: Extrai a frase de um texto onde aparece a palavra-chave, removendo o ponto final.

```sql
CREATE OR REPLACE FUNCTION extrair_frase(texto TEXT, palavra_chave VARCHAR)
RETURNS TEXT AS $$
DECLARE
  frase TEXT;
  frases TEXT[];
  f TEXT;
BEGIN
  frases := regexp_split_to_array(texto, '[\.\n]');
  FOREACH f IN ARRAY frases LOOP
    IF POSITION(LOWER(palavra_chave) IN LOWER(f)) > 0 THEN
      frase := trim(f);
      RETURN frase;
    END IF;
  END LOOP;
  RETURN NULL;
END;
$$ LANGUAGE plpgsql;
```

---

## ✅ 3) Adicionar coluna `texto_readability`

**Descrição**: Adiciona uma nova coluna para armazenar frases relacionadas à palavra "readability".

```sql
ALTER TABLE pullrequests
ADD COLUMN texto_readability VARCHAR(255);
```

---

## ✅ 4) Procedure `atualizar_readability`

**Descrição**: Atualiza a nova coluna com frases extraídas do `title` ou `body` contendo "readability".

```sql
CREATE OR REPLACE PROCEDURE atualizar_readability()
LANGUAGE plpgsql
AS $$
DECLARE
  pr RECORD;
  frase TEXT;
BEGIN
  FOR pr IN SELECT * FROM pullrequests LOOP
    IF pr.title ILIKE '%readability%' THEN
      frase := extrair_frase(pr.title, 'readability');
    ELSIF pr.body ILIKE '%readability%' THEN
      frase := extrair_frase(pr.body, 'readability');
    ELSE
      frase := NULL;
    END IF;

    IF frase IS NOT NULL THEN
      UPDATE pullrequests
      SET texto_readability = frase
      WHERE id = pr.id;
    END IF;
  END LOOP;
END;
$$;
```

---

## ✅ 5) Tabela `pull_request_repetida`

**Descrição**: Registra tentativas de inserção de pull requests duplicadas.

```sql
CREATE TABLE pull_request_repetida (
  id SERIAL PRIMARY KEY,
  owner_repo VARCHAR,
  pr_number INT,
  data_tentativa TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## ✅ 6) Trigger `verificar_pullrequest_repetida`

**Descrição**: Impede inserção de pull requests duplicadas e registra a tentativa na tabela de repetidas.

```sql
CREATE OR REPLACE FUNCTION verificar_pullrequest_repetida_fn()
RETURNS TRIGGER AS $$
BEGIN
  IF EXISTS (
    SELECT 1 FROM pullrequests
    WHERE owner_repo = NEW.owner_repo AND pr_number = NEW.pr_number
  ) THEN
    INSERT INTO pull_request_repetida (owner_repo, pr_number)
    VALUES (NEW.owner_repo, NEW.pr_number);

    RAISE EXCEPTION 'Pull request duplicada: % - %', NEW.owner_repo, NEW.pr_number;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER verificar_pullrequest_repetida
BEFORE INSERT ON pullrequests
FOR EACH ROW
EXECUTE FUNCTION verificar_pullrequest_repetida_fn();
```

---

## ✅ 7) Procedure `listar_pullrequests_com_participacao_insuficiente`

**Descrição**: Lista pull requests com menos de 2 pessoas envolvidas (autor, mergedBy, revisores).

```sql
CREATE OR REPLACE PROCEDURE listar_pullrequests_com_participacao_insuficiente()
LANGUAGE plpgsql
AS $$
DECLARE
  pr RECORD;
  qtd INT;
BEGIN
  RAISE NOTICE 'Pull Requests com participação insuficiente:';

  FOR pr IN SELECT * FROM pullrequests LOOP
    SELECT COUNT(DISTINCT pessoa) INTO qtd
    FROM (
      SELECT pr.author AS pessoa
      UNION
      SELECT pr.mergedBy
      UNION
      SELECT r.login
      FROM reviews r WHERE r.pull_request_id = pr.id
    ) AS participantes;

    IF qtd < 2 THEN
      RAISE NOTICE 'ID: %, owner_repo: %, pr_number: %', pr.id, pr.owner_repo, pr.pr_number;
    END IF;
  END LOOP;
END;
$$;
```

---

## ✅ 8) Trigger `atualizar_changedfiles_mesma_linguagem`

**Descrição**: Atualiza a contagem de arquivos modificados se a linguagem do arquivo inserido for igual à principal do repositório.

```sql
CREATE OR REPLACE FUNCTION atualizar_changedfiles_mesma_linguagem_fn()
RETURNS TRIGGER AS $$
DECLARE
  linguagem_repo VARCHAR;
BEGIN
  SELECT linguagem INTO linguagem_repo
  FROM repositorios
  WHERE owner_repo = NEW.owner_repo;

  IF linguagem_repo IS NOT NULL AND NEW.linguagem = linguagem_repo THEN
    UPDATE pullrequests
    SET changedFiles = COALESCE(changedFiles, 0) + 1
    WHERE owner_repo = NEW.owner_repo AND pr_number = NEW.pr_number;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER atualizar_changedfiles_mesma_linguagem
AFTER INSERT ON changedfiles
FOR EACH ROW
EXECUTE FUNCTION atualizar_changedfiles_mesma_linguagem_fn();