export function carregarComFetch(url) {
    fetch(url)
      .then(response => {

        if (!response.ok) {
          throw new Error('Erro na requisição Fetch');
        }
        return response.json();
      })
      .then(data => {
        console.log('Fetch:', `Nome: ${data.nome}, Idade: ${data.idade}`);
      })
      .catch(error => {
        console.error('Erro (fetch):', error);
      });
  }
  