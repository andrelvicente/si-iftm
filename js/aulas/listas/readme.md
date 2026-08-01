# JavaScript Básico — Instruções para o agente

Este arquivo é a referência obrigatória para **qualquer** trabalho dentro da pasta `js/`.
Leia e siga estas regras antes de criar, editar ou revisar arquivos nesta disciplina.

Referência de estilo e estrutura: pasta [`js-old/`](../../../js-old/).

---

## Matriz da unidade curricular

| Campo | Valor |
| --- | --- |
| Unidade Curricular | JavaScript Básico |
| Período | 2 |
| C.H. Teórica | 33 |
| C.H. Prática | 34 |
| C.H. EAD | 17 |
| Carga Horária Total | 84 |
| Requisito | LP |

### Objetivo

Construir projetos web reais envolvendo validação de formulários, armazenamento de informações localmente na máquina do usuário, entre outros, a partir do conhecimento dos fundamentos básicos da linguagem JavaScript.

### Ementa (conteúdo permitido / esperado)

- Introdução à linguagem JavaScript: histórico, características e especificação ECMA-262
- Saídas, sintaxe, variáveis, operadores, tipos de dados
- Arrays, estruturas de controle e condicionais
- Objetos e classes nativas (`String`, `Array`, `Math`, `Date`)
- Funções
- DOM (HTML, CSS e eventos)
- Expressões regulares
- Debugging e boas práticas
- JSON
- Validação de formulários
- `localStorage`
- Construção de páginas web dinâmicas com JavaScript

### Bibliografia básica

- FLANAGAN, D. *JavaScript: o guia definitivo*. Porto Alegre: Bookman, 2004.
- ECMASCRIPT. *ECMAScript Language Specification*. ECMA International, 2019.  
  <https://www.ecma-international.org/publications/files/ECMA-ST/ECMA262.pdf>

### Bibliografia complementar

- PINHO, D. M. *ECMAScript 6: entre de cabeça no futuro do JavaScript*. São Paulo: Casa do Psicólogo, 2018.
- SMITH, B. *JSON básico*. São Paulo: Novatec, 2015.
- MORRISON, M. *Use a cabeça! JavaScript*. Rio de Janeiro: Alta Books, 2008.

---

## Princípio central

**Sempre manter sintaxes simples.**  
O código deve ser fácil de ler, fácil de explicar em aula e alinhado ao nível de JavaScript Básico.

Use `js-old/` como modelo de referência para organização, clareza e nível de complexidade.

---

## Regras obrigatórias de código

### Arquivos e estrutura

- Sempre criar/separar **HTML**, **CSS** e **JS** em arquivos distintos (quando houver estilo).
- **Sempre** colocar os arquivos JavaScript dentro de uma pasta `js/` (nunca na raiz do exercício).
- Preferir nomes claros e estrutura simples, no espírito de `js-old`:
  - `index.html`
  - `css/styles.css` (ou `css/style.css`)
  - `js/exN.js` ou `js/main.js`
- Uma pasta por exercício/lista/atividade quando fizer sentido.
- Páginas em português (`lang="pt-BR"`), textos e mensagens em português.

### JavaScript — o que usar

- JavaScript **vanilla** (puro), rodando no navegador.
- Sintaxe acessível ao aluno iniciante: `var` / `let` / `const`, funções, `if/else`, `for` / `while`, arrays, objetos literais.
- DOM: `getElementById`, `querySelector`, `addEventListener`, manipulação de `textContent` / `innerHTML` / `value` / `style` / `classList`.
- Eventos de formulário, validação manual e feedback visual na página.
- `JSON.stringify` / `JSON.parse` e `localStorage` quando o enunciado pedir persistência.
- Classes nativas da ementa: `String`, `Array`, `Math`, `Date`.
- Expressões regulares apenas quando forem necessárias ao exercício.
- Código legível: nomes descritivos, funções pequenas, poucos níveis de aninhamento.

### JavaScript — o que evitar

Não usar (salvo pedido explícito do usuário/professor):

- Frameworks ou bibliotecas (React, Vue, Angular, jQuery, Bootstrap JS, etc.)
- Bundlers / tooling (Vite, Webpack, npm scripts de build, TypeScript, Babel)
- Módulos complexos (`import`/`export` com bundler), Node.js backend, APIs avançadas fora da ementa
- Padrões avançados desnecessários: classes ES6 elaboradas, proxies, generators, async/await complexo, Promises encadeadas sem necessidade
- Abstrações excessivas, overengineering, “arquitetura de produção”
- Dependências externas de JS

CSS frameworks (Bootstrap, Tailwind etc.) também devem ser evitados: CSS simples e direto.

### HTML e CSS

- HTML semântico e simples (`form`, `label`, `input`, `button`, `div`/`section` quando preciso).
- CSS direto, legível, sem pré-processadores e sem utilitários de framework.
- Visual funcional e claro; não é preciso design sofisticado, a menos que o enunciado peça.

### Como entregar / comportar-se ao implementar

1. Ler o enunciado e mapear para tópicos da **ementa**.
2. Olhar um exemplo parecido em `js-old/` antes de inventar estrutura nova.
3. Preferir a solução mais simples que funcione.
4. Separar HTML / CSS / JS.
5. Validar formulários com JS puro e mensagens claras na tela.
6. Usar `localStorage` + JSON quando houver persistência local.
7. Manter o código comentado só onde ajudar o aprendizado — sem comentários óbvios demais.

---

## Escopo desta pasta

- `js/aulas/listas/` — listas e exercícios de aula da disciplina JavaScript Básico.
- Todo conteúdo novo desta disciplina deve permanecer sob `js/`, no nível e estilo descritos aqui.
- `js-old/` é material legado de referência; **não** reescrever `js-old` a menos que seja pedido.

---

## Checklist rápido (antes de finalizar qualquer alteração)

- [ ] Solução cabe na ementa de JavaScript Básico?
- [ ] HTML, CSS e JS separados e fáceis de ler?
- [ ] Sem frameworks, bundlers ou dependências?
- [ ] Estilo semelhante ao de `js-old/`?
- [ ] Validação / DOM / `localStorage` feitos de forma direta, se aplicável?
- [ ] Código explicável para aluno de período 2?
